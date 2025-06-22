package at.byfxbian.beeindustry.entities;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static at.byfxbian.beeindustry.util.BeeRegistries.BEE_REGISTRY_KEY;

public class CustomBees {
    public static final RegistryKey<CustomBee> EXAMPLE = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "example_bee"));
    public static final RegistryKey<CustomBee> GOLD = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "gold_bee"));

    public static void bootstrap(Registerable<CustomBee> context) {
        register(context, EXAMPLE, "#f2f24f", "#d0581f", "#f2f24f", "Empty Description lol", "beeindustry:textures/entity/bee/example_bee.png", true, "beeindustry:flowers/example_bee", 1.0f, false, true, new CustomBee.Attributes(1,1,1));
        register(context, GOLD, "#FFD700", "#FF1500", "#FFD700", "A shiny golden bee!", "beeindustry:textures/entity/bee/gold_bee.png", true, "beeindustry:flowers/golden_bee", 1.2f, false, false, new CustomBee.Attributes(5, 2, 3));
    }

    private static void register(Registerable<CustomBee> registry,
                                 RegistryKey<CustomBee> key,
                                 String primaryColor,
                                 String secondaryColor,
                                 String pollenColor,
                                 String description,
                                 String beeTexture,
                                 boolean createNectar,
                                 String flowerBlockTag,
                                 float size,
                                 boolean translucent,
                                 boolean fireproof,
                                 CustomBee.Attributes attributes) {
        CustomBee customBee = CustomBee.of(
                key.getValue().getPath(),
                primaryColor,
                secondaryColor,
                pollenColor,
                description,
                beeTexture,
                createNectar,
                flowerBlockTag,
                size,
                translucent,
                fireproof,
                attributes
        );
        registry.register(key, customBee);
    }
}
