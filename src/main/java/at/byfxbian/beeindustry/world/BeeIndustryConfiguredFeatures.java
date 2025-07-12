package at.byfxbian.beeindustry.world;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class BeeIndustryConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIRT_NEST_KEY = registerKey("dirt_nest");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STONE_NEST_KEY = registerKey("stone_nest");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_NEST_KEY = registerKey("sand_nest");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_NEST_KEY = registerKey("gravel_nest");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, DIRT_NEST_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(BeeIndustryBlocks.DIRT_NEST)));

        register(context, STONE_NEST_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(BeeIndustryBlocks.STONE_NEST)));
        register(context, SAND_NEST_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(BeeIndustryBlocks.SAND_NEST)));
        register(context, GRAVEL_NEST_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(BeeIndustryBlocks.GRAVEL_NEST)));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(BeeIndustry.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
