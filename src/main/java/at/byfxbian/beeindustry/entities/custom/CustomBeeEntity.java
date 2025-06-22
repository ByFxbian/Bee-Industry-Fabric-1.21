package at.byfxbian.beeindustry.entities.custom;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.recipe.BreedingRecipe;
import at.byfxbian.beeindustry.recipe.BreedingRecipeManager;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomBeeEntity extends BeeEntity {
    private static final TrackedData<String> BEE_TYPE = DataTracker.registerData(CustomBeeEntity.class, TrackedDataHandlerRegistry.STRING);

    private RegistryEntry<CustomBee> customBee;

    private boolean renderStatic = false;
    private boolean fireImmune = false;
    protected FollowParentGoal followParentGoal;
    protected BreedTask breedTask;
    protected MoveToHiveGoal moveToHiveGoal;
    private int breedItemCount;

    public CustomBeeEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
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
            System.out.println("CustomBee Attributes: " + beeData);
            this.setFireImmune(beeData.fireproof());
            this.calculateDimensions();
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
            /*Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
            if(registry != null) {
                registry.getEntry(Identifier.of(BeeIndustry.MOD_ID, beeType)).ifPresent(this::setCustomBee);
            } else {
                System.out.println("readCustomDataFromNbt: Registry is null!");
            }*/
        } else {
            System.out.println("readCustomDataFromNbt: BeeType not found in NBT!");
        }
        /*String beeType = nbt.getString("BeeType");
        this.setBeeType(beeType);

        Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
        registry.getEntry(Identifier.of(BeeIndustry.MOD_ID, beeType)).ifPresent(this::setCustomBee);
    */}

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

        if (recipeOptional.isPresent()) {
            BreedingRecipe recipe = recipeOptional.get();

            if(serverWorld.random.nextFloat() < recipe.chance()) {
                Registry<CustomBee> registry = serverWorld.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                if(registry != null) {
                    Optional<RegistryEntry.Reference<CustomBee>> childEntry = registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, recipe.child()));

                    if (childEntry.isPresent()) {
                        CustomBeeEntity child = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(serverWorld);
                        if(child != null) {
                            child.setCustomBee(childEntry.get());
                        }
                        return child;
                    }
                }
            }
        }

        /*if (parentA_type == parentB_type) {
            CustomBeeEntity child = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(serverWorld);
            if(child != null) {
                child.setCustomBee(parentA_type);
            }
            return child;
        }

        RegistryKey<CustomBee> goldKey = CustomBees.GOLD;
        RegistryKey<CustomBee> ironKey = CustomBees.IRON;

        if ((parentA_type.matchesKey(goldKey) && parentB_type.matchesKey(ironKey)) ||
                (parentA_type.matchesKey(ironKey) && parentB_type.matchesKey(goldKey))) {
            if(serverWorld.random.nextFloat() < 0.15f) {
                return null;
            } else {
                CustomBeeEntity child = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(serverWorld);
                if(child != null) {
                    child.setCustomBee(serverWorld.random.nextBoolean() ? parentA_type : parentB_type);
                }
                return child;
            }
        }*/

        return null;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BEE_TYPE.equals(data) && this.getWorld().isClient) {
            String beeTypeId = this.getDataTracker().get(BEE_TYPE);

            Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
            if(registry != null){
                registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, beeTypeId)))
                        .ifPresent(entry -> this.customBee = entry);
            }
        }

        super.onTrackedDataSet(data);
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
