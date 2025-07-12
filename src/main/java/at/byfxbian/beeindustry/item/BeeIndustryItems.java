package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.item.custom.BeeContainerItem;
import at.byfxbian.beeindustry.item.custom.BeekeeperArmorItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryItems {

    public static final Item GOLD_BEE_SPAWN_EGG = registerItem("gold_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFFFFD700,
                    0xFFFF1500,
                    new Item.Settings()
            ));

    public static final Item IRON_BEE_SPAWN_EGG = registerItem("iron_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFFf2f24f,
                    0xFFd0581f,
                    new Item.Settings()
            ));

    public static final Item FARMING_BEE_SPAWN_EGG = registerItem("farming_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFF8f6700,
                    0xFF513900,
                    new Item.Settings()
            ));

    public static final Item MINING_BEE_SPAWN_EGG = registerItem("mining_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFF6E6E6E,
                    0xFF848484,
                    new Item.Settings()
            ));

    public static final Item FIGHTING_BEE_SPAWN_EGG = registerItem("fighting_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFFB40404,
                    0xFFFF0000,
                    new Item.Settings()
            ));

    public static final Item DIRT_BEE_SPAWN_EGG = registerItem("dirt_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFF966A4A,
                    0xFF5A3A22,
                    new Item.Settings()
            ));


    public static final Item STONE_BEE_SPAWN_EGG = registerItem("stone_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFF808080, 0xFFA9A9A9, new Item.Settings()));

    public static final Item SAND_BEE_SPAWN_EGG = registerItem("sand_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFFF4A460, 0xFFF0E68C, new Item.Settings()));

    public static final Item GRAVEL_BEE_SPAWN_EGG = registerItem("gravel_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFFA9A9A9, 0xFFD3D3D3, new Item.Settings()));

    public static final Item MOSSY_BEE_SPAWN_EGG = registerItem("mossy_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFF5A7341, 0xFF789458, new Item.Settings()));

    public static final Item COAL_BEE_SPAWN_EGG = registerItem("coal_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFF343434, 0xFF1E1E1E, new Item.Settings()));

    public static final Item COPPER_BEE_SPAWN_EGG = registerItem("copper_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFFD17D58, 0xFFE7936D, new Item.Settings()));

    public static final Item DIAMOND_BEE_SPAWN_EGG = registerItem("diamond_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFF6DECF5, 0xFFABF6FB, new Item.Settings()));

    public static final Item EMERALD_BEE_SPAWN_EGG = registerItem("emerald_bee_spawn_egg",
            new SpawnEggItem(BeeIndustryEntities.CUSTOM_BEE_ENTITY, 0xFF41F384, 0xFF17AC54, new Item.Settings()));

    public static final Item RIDEABLE_BEE_SPAWN_EGG = registerItem("rideable_bee_spawn_egg",
            new SpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFF4B8BBE,
                    0xFFFFFFFF,
                    new Item.Settings()
            ));

    public static final Item BEEKEEPER_HELMET = registerItem("beekeeper_helmet",
            new BeekeeperArmorItem(BeeIndustryArmorMaterials.BEEKEEPER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item BEEKEEPER_CHESTPLATE = registerItem("beekeeper_chestplate",
            new BeekeeperArmorItem(BeeIndustryArmorMaterials.BEEKEEPER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item BEEKEEPER_LEGGINGS = registerItem("beekeeper_leggings",
            new BeekeeperArmorItem(BeeIndustryArmorMaterials.BEEKEEPER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item BEEKEEPER_BOOTS = registerItem("beekeeper_boots",
            new BeekeeperArmorItem(BeeIndustryArmorMaterials.BEEKEEPER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item BEE_CONTAINER = registerItem("bee_container", new BeeContainerItem(new Item.Settings().maxCount(1)));

    public static final Item EFFICIENCY_UPGRADE = registerItem("efficiency_upgrade", new Item(new Item.Settings().maxCount(1)));
    public static final Item QUANTITY_UPGRADE = registerItem("quantity_upgrade", new Item(new Item.Settings().maxCount(1)));
    public static final Item RANGE_UPGRADE = registerItem("range_upgrade", new Item(new Item.Settings().maxCount(1)));

    public static final Item CHARM_TEMPLATE = registerItem("charm_template", new Item(new Item.Settings()));
    public static final Item NIGHT_CHARM = registerItem("night_charm", new Item(new Item.Settings().maxCount(1)));

    public static final Item SWEET_HONEY = registerItem("sweet_honey", new Item(new Item.Settings()));

    public static final Item BEE_SMOKER = registerItem("bee_smoker", new Item(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BeeIndustry.MOD_ID, name), item);
    }

    private static void customIngredients(FabricItemGroupEntries entries) {

    }

    public static void registerModItems() {
        BeeIndustry.LOGGER.info("Registering Items for " + BeeIndustry.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BeeIndustry::customIngredients);
    }
}
