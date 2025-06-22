package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.EntityNbtDataSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BeeIndustryItemGroups {
    public static final ItemGroup BEE_INDUSTRY_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BeeIndustry.MOD_ID, "beeindustry"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.beeindustry"))
                    .icon(() -> new ItemStack(BeeIndustryItems.GOLD_BEE_SPAWN_EGG))
                    .entries((displayContext, entries) -> {
                        // Entries hier
                        entries.add(createBeeSpawnEgg(BeeIndustryItems.GOLD_BEE_SPAWN_EGG, "gold_bee"));
                        entries.add(createBeeSpawnEgg(BeeIndustryItems.IRON_BEE_SPAWN_EGG, "iron_bee"));
                    }).build());

    public static void registerItemGroups() {
        BeeIndustry.LOGGER.info("Registering Item Groups for " + BeeIndustry.MOD_ID);
    }

    public static ItemStack createBeeSpawnEgg(Item spawnEggItem, String beeTypeName) {
        ItemStack stack = new ItemStack(spawnEggItem);

        /*NbtCompound nbt = new NbtCompound();
        nbt.putString("BeeType", beeTypeName);

        stack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(nbt));
*/
        stack.set(BeeIndustryDataComponentTypes.BEE_TYPE, beeTypeName);

        return stack;
    }
}

