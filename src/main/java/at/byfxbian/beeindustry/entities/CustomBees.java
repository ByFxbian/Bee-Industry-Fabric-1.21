package at.byfxbian.beeindustry.entities;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

import static at.byfxbian.beeindustry.util.BeeRegistries.BEE_REGISTRY_KEY;

public class CustomBees {
    public static final RegistryKey<CustomBee> EXAMPLE = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "example_bee"));
    public static final RegistryKey<CustomBee> GOLD = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "gold_bee"));
    public static final RegistryKey<CustomBee> IRON = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "iron_bee"));

    public static final RegistryKey<CustomBee> DIRT = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "dirt_bee"));
    public static final RegistryKey<CustomBee> STONE = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "stone_bee"));
    public static final RegistryKey<CustomBee> SAND = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "sand_bee"));
    public static final RegistryKey<CustomBee> GRAVEL = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "gravel_bee"));
    public static final RegistryKey<CustomBee> MOSSY = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "mossy_bee"));

    public static final RegistryKey<CustomBee> COAL = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "coal_bee"));
    public static final RegistryKey<CustomBee> COPPER = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "copper_bee"));
    public static final RegistryKey<CustomBee> DIAMOND = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "diamond_bee"));
    public static final RegistryKey<CustomBee> EMERALD = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "emerald_bee"));

    public static final RegistryKey<CustomBee> FARMING = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "farming_bee"));
    public static final RegistryKey<CustomBee> MINING = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "mining_bee"));
    public static final RegistryKey<CustomBee> FIGHTING = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "fighting_bee"));

    public static final RegistryKey<CustomBee> RIDEABLE = RegistryKey.of(BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, "rideable_bee"));

    public static void bootstrap(Registerable<CustomBee> context) {
        register(context, EXAMPLE, "#f2f24f", "#d0581f", "#f2f24f", "Empty Description lol",
                "beeindustry:textures/entity/bee/example_bee.png",
                true,
                "beeindustry:flowers/example_bee",
                Registries.ITEM.getId(Items.HONEYCOMB).toString(),
                1.0f,
                false, true, new CustomBee.Attributes(1,1,1, 20.0, 1.0), false);
        register(context, GOLD, "#FFD700", "#FF1500", "#FFD700", "A shiny golden bee!",
                "beeindustry:textures/entity/bee/gold_bee.png",
                true,
                "beeindustry:flowers/gold_bee",
                Registries.ITEM.getId(Items.GOLD_NUGGET).toString(),
                1.2f,
                false, false, new CustomBee.Attributes(5, 2, 5, 20.0, 1.0), false);
        register(context, IRON, "#f2f24f", "#d0581f", "#f2f24f", "Iron Bee!",
                "beeindustry:textures/entity/bee/iron_bee.png",
                true,
                "beeindustry:flowers/iron_bee",
                Registries.ITEM.getId(Items.IRON_NUGGET).toString(),
                1.5f,
                false, false, new CustomBee.Attributes(3, 5, 5, 20.0, 1.0), false);

        register(context, DIRT, "#966A4A", "#5A3A22", "#966A4A", "A very common bee.",
                "beeindustry:textures/entity/bee/dirt_bee.png",
                false,
                "minecraft:dirt",
                "minecraft:dirt",
                1.0f, false, false, new CustomBee.Attributes(1, 1, 5, 20.0, 1.0), false);

        register(context, STONE, "#808080", "#A9A9A9", "#FFFFFF", "A rocky bee.",
                "beeindustry:textures/entity/bee/stone_bee.png",
                false, "minecraft:stone", "minecarft:cobblestone",
                1.0f, false, false,
                new CustomBee.Attributes(1, 1, 3, 25.0, 1.5), false);

        register(context, SAND, "#F4A460", "#F0E68C", "#F4A460", "A grainy bee.",
                "beeindustry:textures/entity/bee/sand_bee.png",
                false, "minecraft:sand", "minecarft:sand",
                1.0f, false, false,
                new CustomBee.Attributes(1, 1, 4, 15.0, 1.0), false);

        register(context, GRAVEL, "#A9A9A9", "#D3D3D3", "#808080", "A crunchy bee.",
                "beeindustry:textures/entity/bee/gravel_bee.png",
                false, "minecraft:gravel", "minecarft:gravel",
                1.0f, false, false,
                new CustomBee.Attributes(1, 1, 4, 18.0, 1.0), false);

        register(context, MOSSY, "#5A7341", "#789458", "#455938", "A bee that thrives in damp, green places.",
                "beeindustry:textures/entity/bee/mossy_bee.png",
                false, "minecraft:moss_block", Registries.ITEM.getId(Items.MOSS_BLOCK).toString(),
                1.0f, false, false, new CustomBee.Attributes(3, 1, 4, 20.0, 1.0), false);

        register(context, COAL, "#343434", "#1E1E1E", "#4A4A4A", "A dusty bee, feels warm.",
                "beeindustry:textures/entity/bee/coal_bee.png",
                false, "minecraft:coal_ore", Registries.ITEM.getId(Items.COAL).toString(),
                1.0f, false, false, new CustomBee.Attributes(2, 2, 4, 20.0, 1.0), false);

        register(context, COPPER, "#D17D58", "#E7936D", "#B36341", "A metallic bee with a greenish tint.",
                "beeindustry:textures/entity/bee/copper_bee.png",
                false, "minecraft:copper_ore", Registries.ITEM.getId(Items.COPPER_INGOT).toString(),
                1.1f, false, false, new CustomBee.Attributes(4, 3, 5, 22.0, 1.5), false);

        register(context, DIAMOND, "#6DECF5", "#ABF6FB", "#FFFFFF", "The pinnacle of bee evolution.",
                "beeindustry:textures/entity/bee/diamond_bee.png",
                false, "minecraft:diamond_ore", Registries.ITEM.getId(Items.DIAMOND).toString(),
                1.4f, true, true, new CustomBee.Attributes(10, 5, 9, 35.0, 2.5), false);

        register(context, EMERALD, "#41F384", "#17AC54", "#8CFFB9", "A very rare and valuable trading bee.",
                "beeindustry:textures/entity/bee/emerald_bee.png",
                false, "minecraft:emerald_ore", Registries.ITEM.getId(Items.EMERALD).toString(),
                1.3f, true, false, new CustomBee.Attributes(8, 2, 8, 30.0, 2.0), false);

        register(context, FARMING, "#8f6700", "#513900", "#8f6700", "A hard-working farming bee.",
                "beeindustry:textures/entity/bee/farming_bee.png",
                false,
                "minecraft:farmland",
                "minecraft:wheat_seeds",
                1.0f,
                false,
                false,
                new CustomBee.Attributes(1, 8, 8, 20.0, 1.0), false);

        register(context, MINING, "#6E6E6E", "#848484", "#FFFFFF", "A bee that loves to dig.",
                "beeindustry:textures/entity/bee/mining_bee.png",
                false,
                "beeindustry:mineable_by_bee",
                "minecraft:cobblestone",
                1.0f, false, false, new CustomBee.Attributes(5, 5, 5, 30.0, 2.0), false);

        register(context, FIGHTING, "#B40404", "#FF0000", "#FFFFFF", "A bee ready for battle.",
                "beeindustry:textures/entity/bee/iron_bee.png",
                false,
                "",
                "minecraft:string",
                1.1f, false, false, new CustomBee.Attributes(10, 10, 7, 40.0, 6.0), true);

        register(context, RIDEABLE, "#4B8BBE", "#FFFFFF", "#4B8BBE", "A large, friendly bee you can ride.",
                "beeindustry:textures/entity/bee/example_bee.png",
                false,
                "",
                "minecraft:saddle",
                2.5f,
                false, false,
                new CustomBee.Attributes(2, 2, 8, 50.0, 2.0),
                false
        );


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
                                 String productionItem,
                                 float size,
                                 boolean translucent,
                                 boolean fireproof,
                                 CustomBee.Attributes attributes,
                                 boolean invulnerable) {
        CustomBee customBee = CustomBee.of(
                key.getValue().getPath(),
                primaryColor,
                secondaryColor,
                pollenColor,
                description,
                beeTexture,
                createNectar,
                flowerBlockTag,
                productionItem,
                size,
                translucent,
                fireproof,
                attributes,
                invulnerable
        );
        registry.register(key, customBee);
    }
}
