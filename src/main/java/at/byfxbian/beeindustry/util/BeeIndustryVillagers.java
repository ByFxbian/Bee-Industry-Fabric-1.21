package at.byfxbian.beeindustry.util;

import at.byfxbian.beeindustry.BeeIndustry;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

public class BeeIndustryVillagers {

    public static final RegistryKey<PointOfInterestType> BEEKEEPER_POI_KEY = registerPoiKey("beekeeper_poi");
    public static final PointOfInterestType BEEKEEPER_POI = registerPOI("beekeeper_poi", Blocks.HONEY_BLOCK);
    public static final VillagerProfession BEEKEEPER = registerProfession("beekeeper", BEEKEEPER_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(BeeIndustry.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_BEE_LOOP));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(BeeIndustry.MOD_ID, name),
                1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(BeeIndustry.MOD_ID, name));
    }

    public static void registerVillagers() {
        BeeIndustry.LOGGER.info("Registering Villagers for " + BeeIndustry.MOD_ID);
    }

}
