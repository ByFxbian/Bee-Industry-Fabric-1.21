package at.byfxbian.beeindustry.entities.custom;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
        if(nbt.contains("BeeType")) {
            String beeType = nbt.getString("BeeType");
            System.out.println("readCustomDataFromNbt: BeeType read as " + beeType);

            Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
            if(registry != null) {
                registry.getEntry(Identifier.of(BeeIndustry.MOD_ID, beeType)).ifPresent(this::setCustomBee);
            } else {
                System.out.println("readCustomDataFromNbt: Registry is null!");
            }
        } else {
            System.out.println("readCustomDataFromNbt: BeeType not found in NBT!");
        }
        /*String beeType = nbt.getString("BeeType");
        this.setBeeType(beeType);

        Registry<CustomBee> registry = this.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
        registry.getEntry(Identifier.of(BeeIndustry.MOD_ID, beeType)).ifPresent(this::setCustomBee);
    */}

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
