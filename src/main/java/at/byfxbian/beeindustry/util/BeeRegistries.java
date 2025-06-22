package at.byfxbian.beeindustry.util;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BeeRegistries {

    public static final RegistryKey<Registry<CustomBee>> BEE_REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(BeeIndustry.MOD_ID, "custom_bee"));
}
