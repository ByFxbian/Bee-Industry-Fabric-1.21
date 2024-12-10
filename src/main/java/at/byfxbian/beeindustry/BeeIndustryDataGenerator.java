package at.byfxbian.beeindustry;

import at.byfxbian.beeindustry.datagen.*;
import at.byfxbian.beeindustry.entities.bee.CustomBee;
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

		List<CustomBee> bees = List.of(
				new CustomBee("example_bee", "#f2f24f", "#d0581f", "#f2f24f", "An example bee",
						"mymod:textures/entity/bee/example", true, "mymod:flowers/example", 1.0, false, true, 1, 1, 1),

				new CustomBee("fire_bee", "#ff4500", "#8b0000", "#ff6347", "A fiery bee",
						"mymod:textures/entity/bee/fire", true, "mymod:flowers/fire_flower", 1.2, false, true, 2, 3, 2)
		);

		pack.addProvider(BeeIndustryBlockTagProvider::new);
		pack.addProvider(BeeIndustryItemTagProvider::new);
		pack.addProvider(BeeIndustryLootTableGenerator::new);
		pack.addProvider(BeeIndustryModelProvider::new);
		pack.addProvider(BeeIndustryRecipeGenerator::new);
		pack.addProvider(BeeIndustryPoiTagProvider::new);
		pack.addProvider(BeeIndustryFluidTagProvider::new);
		pack.addProvider(BeeIndustryWorldGenerator::new);

		pack.addProvider((FabricDataOutput output, CompletableFuture< RegistryWrapper.WrapperLookup > registriesFuture) -> new BeeIndustryBeeDataProvider(output, registriesFuture, bees));

		/*pack.addProvider(new FabricDataGenerator.Pack.Factory<DataProvider>() {
			@Override
			public DataProvider create(FabricDataOutput fabricDataOutput) {
				return new BeeIndustryBeeDataProvider(fabricDataOutput, bees);
			}
		});*/
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {

	}
}
