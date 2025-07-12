package at.byfxbian.beeindustry.datagen;

import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class BeeIndustryModelProvider extends FabricModelProvider {

    public BeeIndustryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(BeeIndustryBlocks.ADVANCED_BEEHIVE, TexturedModel.ORIENTABLE_WITH_BOTTOM);
        //blockStateModelGenerator.registerSimpleCubeAll(BeeIndustryBlocks.BEEPOST);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(BeeIndustryBlocks.DIRT_NEST, TexturedModel.ORIENTABLE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(BeeIndustryBlocks.SAND_NEST, TexturedModel.ORIENTABLE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(BeeIndustryBlocks.STONE_NEST, TexturedModel.ORIENTABLE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(BeeIndustryBlocks.GRAVEL_NEST, TexturedModel.ORIENTABLE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Identifier spawnEggTemplate = Identifier.of("minecraft", "item/template_spawn_egg");

        itemModelGenerator.register(BeeIndustryItems.GOLD_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.IRON_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.FARMING_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.MINING_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.FIGHTING_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.DIRT_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.STONE_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.SAND_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.GRAVEL_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.MOSSY_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.COAL_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.EMERALD_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.DIAMOND_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.COPPER_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));
        itemModelGenerator.register(BeeIndustryItems.RIDEABLE_BEE_SPAWN_EGG, new Model(Optional.of(spawnEggTemplate), Optional.empty()));

        itemModelGenerator.registerArmor((ArmorItem) BeeIndustryItems.BEEKEEPER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) BeeIndustryItems.BEEKEEPER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) BeeIndustryItems.BEEKEEPER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) BeeIndustryItems.BEEKEEPER_BOOTS);

        //itemModelGenerator.register(BeeIndustryItems.BEE_CONTAINER, Models.GENERATED);

        itemModelGenerator.register(BeeIndustryItems.EFFICIENCY_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(BeeIndustryItems.QUANTITY_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(BeeIndustryItems.RANGE_UPGRADE, Models.GENERATED);

        itemModelGenerator.register(BeeIndustryItems.CHARM_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(BeeIndustryItems.NIGHT_CHARM, Models.GENERATED);

        itemModelGenerator.register(BeeIndustryItems.SWEET_HONEY, Models.GENERATED);
        itemModelGenerator.register(BeeIndustryItems.BEE_SMOKER, Models.GENERATED);
    }
}
