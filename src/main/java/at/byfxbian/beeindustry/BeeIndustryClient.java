package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.client.*;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.screen.AdvancedBeehiveScreen;
import at.byfxbian.beeindustry.screen.AdvancedBeehiveScreenHandler;
import at.byfxbian.beeindustry.screen.BeepostScreen;
import at.byfxbian.beeindustry.util.BeeIndustryScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;

public class BeeIndustryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(BeeIndustryEntities.CUSTOM_BEE_ENTITY, CustomBeeRenderer::new);
        HandledScreens.register(BeeIndustryScreenHandlers.ADVANCED_BEEHIVE_SCREEN_HANDLER, AdvancedBeehiveScreen::new);
        HandledScreens.register(BeeIndustryScreenHandlers.BEEPOST_SCREEN_HANDLER, BeepostScreen::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.GOLD_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.IRON_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.FARMING_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.MINING_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.FIGHTING_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.DIRT_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.STONE_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.SAND_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.GRAVEL_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.MOSSY_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.COAL_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.EMERALD_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.DIAMOND_BEE_SPAWN_EGG);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((SpawnEggItem) stack.getItem()).getColor(tintIndex), BeeIndustryItems.COPPER_BEE_SPAWN_EGG);


        EntityModelLayerRegistry.registerModelLayer(BeeIndustryModelLayers.FARMING_BEE_LAYER, FarmingBeeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BeeIndustryModelLayers.MINING_BEE_LAYER, MiningBeeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BeeIndustryModelLayers.EMERALD_BEE_LAYER, EmeraldBeeModel::getTexturedModelData);

        registerBeeContainerPredicate();
    }

    private void registerBeeContainerPredicate() {
        ModelPredicateProviderRegistry.register(BeeIndustryItems.BEE_CONTAINER,
                Identifier.of(BeeIndustry.MOD_ID, "filled"), (itemStack, clientWorld, livingEntity, seed) -> {
                    if(itemStack.contains(BeeIndustryDataComponentTypes.STORED_BEE_TYPE)) {
                        return 1f;
                    } else {
                        return 0f;
                    }
                });
    }
}
