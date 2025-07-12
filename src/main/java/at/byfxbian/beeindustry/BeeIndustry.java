package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.command.GetEntriesCommand;
import at.byfxbian.beeindustry.command.SpawnCustomBeeCommand;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryArmorMaterials;
import at.byfxbian.beeindustry.item.BeeIndustryItemGroups;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.networking.BeeIndustryNetworking;
import at.byfxbian.beeindustry.networking.payload.ToggleBeeSlotPayload;
import at.byfxbian.beeindustry.recipe.BreedingRecipeManager;
import at.byfxbian.beeindustry.util.BeeIndustryScreenHandlers;
import at.byfxbian.beeindustry.util.BeeIndustryVillagers;
import at.byfxbian.beeindustry.world.BeeIndustryPlacedFeatures;
import at.byfxbian.beeindustry.world.gen.BeeIndustryWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.world.gen.GenerationStep;
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
		BeeIndustryBlockEntities.registerBlockEntities();

		BeeIndustryDataComponentTypes.registerDataComponentTypes();

		BeeIndustryEntities.registerModEntities();

		BeeIndustryWorldGeneration.generateBeeIndustryWorldGeneration();

		DynamicRegistries.registerSynced(BEE_REGISTRY_KEY, CustomBee.CODEC);

		FabricDefaultAttributeRegistry.register(BeeIndustryEntities.CUSTOM_BEE_ENTITY, CustomBeeEntity.createBeeAttributes());

		CommandRegistrationCallback.EVENT.register(GetEntriesCommand::register);
		CommandRegistrationCallback.EVENT.register(SpawnCustomBeeCommand::register);

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new BreedingRecipeManager());

		BeeIndustryVillagers.registerVillagers();

		registerCustomTrades();

		BeeIndustryScreenHandlers.registerScreenHandlers();

		BeeIndustryNetworking.registerC2SPackets();

		registerServerPacketsReceivers();
	}

	private static void registerCustomTrades() {
		TradeOfferHelper.registerVillagerOffers(BeeIndustryVillagers.BEEKEEPER, 1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 5),
					BeeIndustryItems.BEE_SMOKER.getDefaultStack(), 1, 2, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 2),
					BeeIndustryItems.BEE_CONTAINER.getDefaultStack(), 10, 1, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 7),
					BeeIndustryItems.BEEKEEPER_BOOTS.getDefaultStack(), 1, 3, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 9),
					BeeIndustryItems.BEEKEEPER_LEGGINGS.getDefaultStack(), 1, 3, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 8),
					BeeIndustryItems.BEEKEEPER_HELMET.getDefaultStack(), 1, 3, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 10),
					BeeIndustryItems.BEEKEEPER_CHESTPLATE.getDefaultStack(), 1, 3, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 12),
					BeeIndustryBlocks.BEEPOST.asItem().getDefaultStack(), 1, 2, 0.04f
			));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 10),
					BeeIndustryBlocks.ADVANCED_BEEHIVE.asItem().getDefaultStack(), 1, 2, 0.04f
			));
		});

	}

	private static void registerServerPacketsReceivers() {
		ServerPlayNetworking.registerGlobalReceiver(ToggleBeeSlotPayload.ID, (payload, context) -> {
			BlockPos pos = payload.pos();
			int slotIndex = payload.slotIndex();

			context.server().execute(() -> {
				BlockEntity be = context.player().getWorld().getBlockEntity(pos);
				if(be instanceof BeepostBlockEntity beepost) {
					boolean currentState = beepost.isBeeSlotActive(slotIndex);
					beepost.setBeeSlotActive(slotIndex, !currentState);
					System.out.println("Toggled bee slot " + slotIndex + " at " + pos + " to " + !currentState);
				}
			});
		});
	}
}