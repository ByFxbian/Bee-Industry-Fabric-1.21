package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryItems {

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
