package at.byfxbian.beeindustry.datagen.custom;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BeeIndustryDynamicRegistryProvider extends FabricDynamicRegistryProvider {

    public BeeIndustryDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        BeeIndustry.LOGGER.debug("configuring data generator for dynamic registries");
        entries.addAll(wrapperLookup.getWrapperOrThrow(BeeRegistries.BEE_REGISTRY_KEY));
       /* entries.add(BeeRegistries.BEE_REGISTRY_KEY, CustomBees.EXAMPLE, CustomBee.of(
                "example_bee",
                "#f2f24f",
                "#d05581f",
                "#f2f24f",
                "An example bee description.",
                "beeindustry:textures/entity/bee/example_bee.png",
                true,
                "beeindustry:flowers/example",
                1.0f,
                false,
                true,
                new CustomBee.Attributes(1,1,1)
        ));*/
    }


    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}
