package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.command.GetEntriesCommand;
import at.byfxbian.beeindustry.command.SpawnCustomBeeCommand;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryItemGroups;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.recipe.BreedingRecipeManager;
import at.byfxbian.beeindustry.util.BeeIndustryVillagers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static at.byfxbian.beeindustry.util.BeeRegistries.BEE_REGISTRY_KEY;

public class BeeIndustry implements ModInitializer {
	public static final String MOD_ID = "beeindustry";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		BeeIndustryItemGroups.registerItemGroups();

		BeeIndustryItems.registerModItems();
		BeeIndustryBlocks.registerModBlocks();

		BeeIndustryDataComponentTypes.registerDataComponentTypes();

		BeeIndustryEntities.registerModEntities();

		DynamicRegistries.registerSynced(BEE_REGISTRY_KEY, CustomBee.CODEC);

		//Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "custom_bee"), BeeIndustryEntities.CUSTOM_BEE_ENTITY);

		FabricDefaultAttributeRegistry.register(BeeIndustryEntities.CUSTOM_BEE_ENTITY, CustomBeeEntity.createBeeAttributes());

		CommandRegistrationCallback.EVENT.register(GetEntriesCommand::register);
		CommandRegistrationCallback.EVENT.register(SpawnCustomBeeCommand::register);

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new BreedingRecipeManager());

		BeeIndustryVillagers.registerVillagers();

		registerCustomTrades();
	}

	private static void registerCustomTrades() {
		TradeOfferHelper.registerVillagerOffers(BeeIndustryVillagers.BEEKEEPER, 1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 15),
					BeeIndustryItemGroups.createBeeSpawnEgg(BeeIndustryItems.GOLD_BEE_SPAWN_EGG, "gold_bee"), 4, 1, 0.04f
			));
		});
	}
}