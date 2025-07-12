package at.byfxbian.beeindustry.world;

import at.byfxbian.beeindustry.BeeIndustry;
import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class BeeIndustryPlacedFeatures {
    public static final RegistryKey<PlacedFeature> DIRT_NEST_PLACED_KEY = registerKey("dirt_nest_placed");
    public static final RegistryKey<PlacedFeature> STONE_NEST_PLACED_KEY = registerKey("stone_nest_placed");
    public static final RegistryKey<PlacedFeature> SAND_NEST_PLACED_KEY = registerKey("sand_nest_placed");
    public static final RegistryKey<PlacedFeature> GRAVEL_NEST_PLACED_KEY = registerKey("gravel_nest_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, DIRT_NEST_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(BeeIndustryConfiguredFeatures.DIRT_NEST_KEY),
                RarityFilterPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );

        register(context, STONE_NEST_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(BeeIndustryConfiguredFeatures.STONE_NEST_KEY),
                RarityFilterPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );

        register(context, SAND_NEST_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(BeeIndustryConfiguredFeatures.SAND_NEST_KEY),
                RarityFilterPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );

        register(context, GRAVEL_NEST_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(BeeIndustryConfiguredFeatures.GRAVEL_NEST_KEY),
                RarityFilterPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(BeeIndustry.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
