package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryItems {

    public static final Item GOLD_BEE_SPAWN_EGG = registerItem("gold_bee_spawn_egg",
            new CustomBeeSpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xFFD700,
                    0xFF1500,
                    new Item.Settings()
            ));

    public static final Item IRON_BEE_SPAWN_EGG = registerItem("iron_bee_spawn_egg",
            new CustomBeeSpawnEggItem(
                    BeeIndustryEntities.CUSTOM_BEE_ENTITY,
                    0xf2f24f,
                    0xd0581f,
                    new Item.Settings()
            ));

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
