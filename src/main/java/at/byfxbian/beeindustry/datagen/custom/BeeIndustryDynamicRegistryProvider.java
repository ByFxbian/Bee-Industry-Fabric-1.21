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
    }


    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}
