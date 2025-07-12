package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.datagen.*;
import at.byfxbian.beeindustry.datagen.custom.BeeIndustryDynamicRegistryProvider;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.util.BeeRegistries;
import at.byfxbian.beeindustry.world.BeeIndustryConfiguredFeatures;
import at.byfxbian.beeindustry.world.BeeIndustryPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.io.DataOutput;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BeeIndustryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();


		pack.addProvider(BeeIndustryBlockTagProvider::new);
		pack.addProvider(BeeIndustryItemTagProvider::new);
		pack.addProvider(BeeIndustryLootTableGenerator::new);
		pack.addProvider(BeeIndustryModelProvider::new);
		pack.addProvider(BeeIndustryRecipeGenerator::new);
		pack.addProvider(BeeIndustryPoiTagProvider::new);
		pack.addProvider(BeeIndustryFluidTagProvider::new);
		pack.addProvider(BeeIndustryWorldGenerator::new);

		pack.addProvider(BeeIndustryDynamicRegistryProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(BeeRegistries.BEE_REGISTRY_KEY, CustomBees::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, BeeIndustryConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, BeeIndustryPlacedFeatures::bootstrap);
	}
}
