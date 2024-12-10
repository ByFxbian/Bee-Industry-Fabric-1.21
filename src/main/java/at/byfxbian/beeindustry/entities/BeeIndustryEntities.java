package at.byfxbian.beeindustry.entities;

import at.byfxbian.beeindustry.BeeIndustry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.BeeEntity;

public class BeeIndustryEntities {


    public static void registerModEntities() {
        BeeIndustry.LOGGER.info("Registering Entities for " + BeeIndustry.MOD_ID);
    }

    public static <E extends BeeEntity> EntityType.Builder<E> createBee(EntityType.EntityFactory<E> factory, String name, int primaryColor, int secondaryColor) {
        EntityType.Builder<E> builder = EntityType.Builder.create(factory, SpawnGroup.CREATURE).dimensions(0.7F, 0.6F).maxTrackingRange(8);

        return builder;
    }
}
