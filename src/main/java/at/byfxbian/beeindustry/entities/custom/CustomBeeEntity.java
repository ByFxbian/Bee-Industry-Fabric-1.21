package at.byfxbian.beeindustry.entities.custom;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.entity.custom.AdvancedBeehiveBlockEntity;
import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.entities.goal.*;
import at.byfxbian.beeindustry.mixin.MobEntityAccessor;
import at.byfxbian.beeindustry.recipe.BreedingRecipe;
import at.byfxbian.beeindustry.recipe.BreedingRecipeManager;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;

public class CustomBeeEntity extends BeeEntity implements Mount {
    private static final TrackedData<String> BEE_TYPE = DataTracker.registerData(CustomBeeEntity.class, TrackedDataHandlerRegistry.STRING);

    private RegistryEntry<CustomBee> customBee;

    private boolean renderStatic = false;
    private boolean fireImmune = false;
    protected FollowParentGoal followParentGoal;
    protected BreedTask breedTask;
    protected MoveToHiveGoal moveToHiveGoal;
    private int breedItemCount;

    @Nullable
    private BlockPos hivePos = null;
    @Nullable
    public BlockPos productionBlockPos = null;

    public boolean hasWorked = false;
    public int workRange = 10;

    public float workSpeedModifier = 1.0f;
    public int bonusQuantity = 0;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public CustomBeeEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public void addItemStack(ItemStack stack) {
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).isEmpty()) {
                this.inventory.set(i, stack.copy());
                return;
            }
        }
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    protected RegistryKey<LootTable> getLootTableId() {
        if(this.customBee != null && this.customBee.getKey().isPresent()) {
            Identifier beeId = this.customBee.getKey().get().getValue();

            Identifier lootTableId = beeId.withPath(path -> "entities/custom_bee/" + path);
            return RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableId);
        }
        return super.getLootTableId();
    }

    public void setHivePos(BlockPos pos) {
        this.hivePos = pos;
    }

    @Nullable
    public BlockPos getHivePos() {
        return this.hivePos;
    }

    @Override
    public boolean isFireImmune() {
        return this.fireImmune;
    }

    public void setFireImmune(boolean fireImmune) {
        this.fireImmune = fireImmune;
    }

    public void setCustomBee(RegistryEntry<CustomBee> customBee) {
        this.customBee = customBee;
        if(customBee != null) {
            customBee.getKey().ifPresent(key -> {
                this.getDataTracker().set(BEE_TYPE, key.getValue().getPath());
                System.out.println("Set BeeType in DataTracker: " + key.getValue().getPath());
            });

            CustomBee beeData = customBee.value();
            this.setFireImmune(beeData.fireproof());
            this.calculateDimensions();

            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(beeData.attributes().maxHealth());
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(beeData.attributes().attackDamage());

            this.getAttributeInstance(EntityAttributes.GENERIC_FLYING_SPEED).setBaseValue(beeData.attributes().speed() / 10.0);
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(beeData.attributes().speed() / 15.0);

            this.setHealth(this.getMaxHealth());
            this.setInvulnerable(beeData.invulnerable());
        } else {
            System.out.println("CustomBee is null in setCustomBee!");
        }
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        if(customBee != null) {
            return EntityDimensions.fixed(customBee.value().size(), customBee.value().size());
        }
        return super.getBaseDimensions(pose);
    }

    public RegistryEntry<CustomBee> getCustomBee() {
        return customBee;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BEE_TYPE, "default");
    }

    public void setBeeType(String type) {
        this.dataTracker.set(BEE_TYPE, type);
    }

    public String getBeeType() {
        return this.dataTracker.get(BEE_TYPE);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if(this.customBee != null && this.customBee.getKey().isPresent()) {
            nbt.putString("BeeType", this.customBee.getKey().get().getValue().toString());
            System.out.println("writeCustomDataToNbt: BeeType saved as " + this.customBee.getKey().get().getValue());
            if(this.hivePos != null) {
                nbt.put("hive_pos", NbtHelper.fromBlockPos(this.hivePos));
            }
            if (this.productionBlockPos != null) {
                nbt.put("production_block_pos", NbtHelper.fromBlockPos(this.productionBlockPos));
            }
            nbt.putBoolean("hasWorked", this.hasWorked);
            Inventories.writeNbt(nbt, this.inventory, this.getWorld().getRegistryManager());
            nbt.putInt("workRange", this.workRange);
            nbt.putFloat("workSpeedModifier", this.workSpeedModifier);
            nbt.putInt("bonusQuantity", this.bonusQuantity);
        } else {
            System.out.println("writeCustomDataToNbt: CustomBee is null or key is absent!");
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("BeeType", NbtElement.STRING_TYPE)) {
            String beeTypeString = nbt.getString("BeeType");
            System.out.println("readCustomDataFromNbt: BeeType read as " + beeTypeString);

            try {
                Identifier beeIdentifier = Identifier.of(beeTypeString);

                Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                if (registry != null) {
                    Optional<RegistryEntry.Reference<CustomBee>> customBeeEntry = registry.getEntry(
                            RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, beeIdentifier)
                    );
                    customBeeEntry.ifPresent(this::setCustomBee);
                }
            } catch (InvalidIdentifierException e) {
                BeeIndustry.LOGGER.error("Failed to parse bee identifier from NBT: " + beeTypeString, e);
            }

            NbtHelper.toBlockPos(nbt, "hive_pos").ifPresent(pos -> this.hivePos = pos);
            System.out.println(this.hivePos);
            NbtHelper.toBlockPos(nbt, "production_block_pos").ifPresent(pos -> this.productionBlockPos = pos);
            System.out.println(this.productionBlockPos);
            this.hasWorked = nbt.getBoolean("hasWorked");
            Inventories.readNbt(nbt, this.inventory, this.getWorld().getRegistryManager());
            this.workRange = nbt.getInt("workRange");
            this.workSpeedModifier = nbt.getFloat("workSpeedModifier");
            this.bonusQuantity = nbt.getInt("bonusQuantity");
            this.initializeWorkerGoals();
        } else {
            System.out.println("readCustomDataFromNbt: BeeType not found in NBT!");
        }
    }

    private void setRiding(PlayerEntity pPlayer) {
        pPlayer.setYaw(this.getYaw());
        pPlayer.setPitch(this.getPitch());
        pPlayer.startRiding(this);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(this.getBeeType().equals("rideable_bee") && !this.hasPassengers() && !player.isSneaking() && !this.isBaby()) {
            if(!this.getWorld().isClient) {
                setRiding(player);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        Entity firstPassenger = this.getFirstPassenger();
        if(firstPassenger instanceof PlayerEntity) {
            return (PlayerEntity)firstPassenger;
        }
        return super.getControllingPassenger();
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vec3d(0.0D, dimensions.height() * 0.3D, -0.1D * (double)scaleFactor);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if(direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        }
        int[][] is = Dismounting.getDismountOffsets(direction);
        BlockPos blockPos = this.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(EntityPose entityPose : passenger.getPoses()) {
            Box box = passenger.getBoundingBox(entityPose);
            for (int[] js : is) {
                mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                double d = this.getWorld().getDismountHeight(mutable);
                if(!Dismounting.canDismountInBlock(d)) continue;
                Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                if(!Dismounting.canPlaceEntityAt(this.getWorld(), passenger,  box.offset(vec3d))) continue;
                passenger.setPose(entityPose);
                return vec3d;
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if(this.isAlive() && this.hasPassengers() && getControllingPassenger() instanceof PlayerEntity) {
            LivingEntity livingEntity = this.getControllingPassenger();

            this.setYaw(livingEntity.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(livingEntity.getPitch() * 0.5f);
            this.setRotation(this.getYaw(), this.getPitch());
            this.headYaw = this.getYaw();
            this.bodyYaw = this.getYaw();

            float sideways = livingEntity.sidewaysSpeed;
            float forward = livingEntity.forwardSpeed;

            if (forward <= 0.0f) {
                forward *= 0.25f;
            }

            if(this.isLogicalSideForUpdatingMovement()) {
                /*float newSpeed = (float)this.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED);

                if(MinecraftClient.getInstance().options.sprintKey.isPressed()) {
                    newSpeed *= 2;
                }*/

                double verticalMovement = 0.0;
                if(MinecraftClient.getInstance().options.jumpKey.isPressed()) {
                    verticalMovement = 0.2;
                } else if(MinecraftClient.getInstance().options.sprintKey.isPressed()) {
                    verticalMovement = -0.2;
                }

                Vec3d newVelocity = new Vec3d(sideways, verticalMovement, forward).rotateY(-this.getYaw() * ((float) Math.PI / 180F));
                this.addVelocity(newVelocity.x, newVelocity.y, newVelocity.z);

                //this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
                super.travel(new Vec3d(sideways, movementInput.y, forward));
            }

            //this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
            //super.travel(new Vec3d(sideways, movementInput.y, forward));
        } else {
            super.travel(movementInput);
        }
    }

    public void initializeWorkerGoals() {
        if(this.getHivePos() != null) {

            ((MobEntityAccessor) this).getGoalSelector().getGoals().removeIf(goal -> true);
            ((MobEntityAccessor) this).getTargetSelector().getGoals().removeIf(goal -> true);

            System.out.println("SUCCESS: Cleared AI goals for worker bee " + this.getUuid());

            this.goalSelector.add(0, new SwimGoal(this));

            String beeType = this.getCustomBee().getKey().get().getValue().getPath();
            System.out.println("BEETYPE - " + beeType);
            if (beeType.equals("farming_bee")) {
                System.out.println("Farming");
                this.goalSelector.add(1, new FarmingGoal(this));
            } else if (beeType.equals("mining_bee")) {
                System.out.println("Mining");
                this.goalSelector.add(1, new MiningGoal(this));
            } else if (beeType.equals("fighting_bee")) {
                System.out.println("Fighting");
                this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
                this.goalSelector.add(2, new ReturnHomeGoal(this));

                this.targetSelector.add(1, new FindEnemyGoal(this));
                //this.goalSelector.add(1, new FightingGoal(this));
            } else {
                this.goalSelector.add(1, new GoToProductionBlockGoal(this));
                this.goalSelector.add(1, new ReturnToHiveGoal(this));
            }

            this.goalSelector.add(2, new TemptGoal(this, 1.25, stack -> stack.isIn(ItemTags.BEE_FOOD), false));

            this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(new Class[0]));
            this.targetSelector.add(2, new UniversalAngerGoal<>(this, true));

            System.out.println("SUCCESS: Initialized WORKER AI for bee " + this.getUuid());
        }
    }

    @Nullable
    @Override
    public BeeEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        if(!(passiveEntity instanceof CustomBeeEntity otherBee)) {
            return null;
        }

        RegistryEntry<CustomBee> parentA_type = this.getCustomBee();
        RegistryEntry<CustomBee> parentB_type = otherBee.getCustomBee();

        if(parentA_type == null || parentB_type == null || parentA_type.getKey().isEmpty() || parentB_type.getKey().isEmpty()) {
            return null;
        }

        Identifier parentA_id = parentA_type.getKey().get().getValue();
        Identifier parentB_id = parentB_type.getKey().get().getValue();

        Optional<BreedingRecipe> recipeOptional = BreedingRecipeManager.getRecipeFor(parentA_id, parentB_id);

        if(recipeOptional.isPresent()) {
            BreedingRecipe recipe = recipeOptional.get();

            for(BreedingRecipe.Outcome outcome : recipe.outcomes()) {
                if(serverWorld.random.nextFloat() < outcome.chance()) {
                    Registry<CustomBee> registry = serverWorld.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                    Optional<RegistryEntry.Reference<CustomBee>> childEntry = registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, outcome.child()));

                    if (childEntry.isPresent()) {
                        CustomBeeEntity child = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(serverWorld);
                        if (child != null) {
                            child.setCustomBee(childEntry.get());
                            return child;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (BEE_TYPE.equals(data) && this.getWorld().isClient) {
            this.calculateDimensions();
            String beeTypeId = this.getDataTracker().get(BEE_TYPE);

            Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
            if(registry != null){
                registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, beeTypeId)))
                        .ifPresent(entry -> this.customBee = entry);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
    }

    @Override
    public boolean isUniversallyAngry(World world) {
        return super.isUniversallyAngry(world);
    }


}
