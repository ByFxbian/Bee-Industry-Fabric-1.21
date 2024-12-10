package at.byfxbian.beeindustry.registries;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.entities.bee.CustomBee;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BeeRegistries {
    public static final RegistryKey<Registry<CustomBee>> CUSTOM_BEE_REISTRY_KEY =
            RegistryKey.ofRegistry(Identifier.of(BeeIndustry.MOD_ID, "custom_bee"));

    public static void registerDynamicRegistry(DynamicRegistryManager.Immutable dynamicRegistryManager) {
        Registry<CustomBee> beeRegistry = dynamicRegistryManager.get(CUSTOM_BEE_REISTRY_KEY);
    }
}
