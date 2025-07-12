package at.byfxbian.beeindustry.world.gen;

import at.byfxbian.beeindustry.world.BeeIndustryPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class BeeIndustryNestGeneration {
    public static void generateNests() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FOREST),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, BeeIndustryPlacedFeatures.DIRT_NEST_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.DESERT),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, BeeIndustryPlacedFeatures.SAND_NEST_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, BeeIndustryPlacedFeatures.STONE_NEST_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.UNDERGROUND_DECORATION, BeeIndustryPlacedFeatures.STONE_NEST_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, BeeIndustryPlacedFeatures.GRAVEL_NEST_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.STONY_PEAKS, BiomeKeys.STONY_SHORE),
                GenerationStep.Feature.UNDERGROUND_DECORATION, BeeIndustryPlacedFeatures.GRAVEL_NEST_PLACED_KEY);
    }
}
