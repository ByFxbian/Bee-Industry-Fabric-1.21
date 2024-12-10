package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BeeIndustryItemGroups {
    public static final ItemGroup BEE_INDUSTRY_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BeeIndustry.MOD_ID, "main"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.bee_industry"))
                    .icon(() -> new ItemStack(Items.BEEHIVE))
                    .entries((displayContext, entries) -> {
                        // Entries hier
                        entries.add(Items.HONEYCOMB);
                    }).build());

    public static void registerItemGroups() {
        BeeIndustry.LOGGER.info("Registering Item Groups for " + BeeIndustry.MOD_ID);
    }
}

