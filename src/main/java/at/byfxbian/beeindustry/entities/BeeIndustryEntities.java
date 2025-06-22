package at.byfxbian.beeindustry.entities;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryEntities {
    public static final EntityType<CustomBeeEntity> CUSTOM_BEE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(BeeIndustry.MOD_ID, "custom_bee"),
            EntityType.Builder.create(CustomBeeEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.7f, 0.6f)
                    .maxTrackingRange(8)
                    .trackingTickInterval(2)
                    .build()
    );

    public static void registerModEntities() {
        BeeIndustry.LOGGER.info("Registering Entities for " + BeeIndustry.MOD_ID);
    }

    public static <E extends BeeEntity> EntityType.Builder<E> createBee(EntityType.EntityFactory<E> factory, String name, int primaryColor, int secondaryColor) {
        EntityType.Builder<E> builder = EntityType.Builder.create(factory, SpawnGroup.CREATURE).dimensions(0.7F, 0.6F).maxTrackingRange(8);

        return builder;
    }
}
