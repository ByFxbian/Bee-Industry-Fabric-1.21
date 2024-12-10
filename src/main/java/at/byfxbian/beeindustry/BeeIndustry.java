package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.item.BeeIndustryItemGroups;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

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

		//ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(BeeIndustryReloadListener::new);
	}
}