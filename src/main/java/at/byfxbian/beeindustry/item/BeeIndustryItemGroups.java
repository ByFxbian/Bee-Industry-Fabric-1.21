package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
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
                        ItemStack goldBeeEgg = new ItemStack(BeeIndustryItems.GOLD_BEE_SPAWN_EGG);
                        goldBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "gold_bee");
                        ItemStack ironBeeEgg = new ItemStack(BeeIndustryItems.IRON_BEE_SPAWN_EGG);
                        ironBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "iron_bee");
                        ItemStack farmingBeeEgg = new ItemStack(BeeIndustryItems.FARMING_BEE_SPAWN_EGG);
                        farmingBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "farming_bee");
                        ItemStack miningBeeEgg = new ItemStack(BeeIndustryItems.MINING_BEE_SPAWN_EGG);
                        miningBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "mining_bee");
                        ItemStack fightingBeeEgg = new ItemStack(BeeIndustryItems.FIGHTING_BEE_SPAWN_EGG);
                        fightingBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "fighting_bee");
                        ItemStack dirtBeeEgg = new ItemStack(BeeIndustryItems.DIRT_BEE_SPAWN_EGG);
                        dirtBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "dirt_bee");
                        ItemStack stoneBeeEgg = new ItemStack(BeeIndustryItems.STONE_BEE_SPAWN_EGG);
                        stoneBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "stone_bee");
                        ItemStack sandBeeEgg = new ItemStack(BeeIndustryItems.SAND_BEE_SPAWN_EGG);
                        sandBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "sand_bee");
                        ItemStack gravelBeeEgg = new ItemStack(BeeIndustryItems.GRAVEL_BEE_SPAWN_EGG);
                        gravelBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "gravel_bee");
                        ItemStack mossyBeeEgg = new ItemStack(BeeIndustryItems.MOSSY_BEE_SPAWN_EGG);
                        mossyBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "mossy_bee");
                        ItemStack coalBeeEgg = new ItemStack(BeeIndustryItems.COAL_BEE_SPAWN_EGG);
                        coalBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "coal_bee");
                        ItemStack copperBeeEgg = new ItemStack(BeeIndustryItems.COPPER_BEE_SPAWN_EGG);
                        copperBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "copper_bee");
                        ItemStack diamondBeeEgg = new ItemStack(BeeIndustryItems.DIAMOND_BEE_SPAWN_EGG);
                        diamondBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "diamond_bee");
                        ItemStack emeraldBeeEgg = new ItemStack(BeeIndustryItems.EMERALD_BEE_SPAWN_EGG);
                        emeraldBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "emerald_bee");


                        ItemStack rideableBeeEgg = new ItemStack(BeeIndustryItems.RIDEABLE_BEE_SPAWN_EGG);
                        rideableBeeEgg.set(BeeIndustryDataComponentTypes.BEE_TYPE, "rideable_bee");

                        entries.add(goldBeeEgg);
                        entries.add(ironBeeEgg);
                        entries.add(farmingBeeEgg);
                        entries.add(miningBeeEgg);
                        entries.add(fightingBeeEgg);
                        entries.add(dirtBeeEgg);
                        entries.add(stoneBeeEgg);
                        entries.add(sandBeeEgg);
                        entries.add(gravelBeeEgg);
                        entries.add(mossyBeeEgg);
                        entries.add(coalBeeEgg);
                        entries.add(copperBeeEgg);
                        entries.add(diamondBeeEgg);
                        entries.add(emeraldBeeEgg);
                        entries.add(rideableBeeEgg);
                        /*entries.add(createBeeSpawnEgg(BeeIndustryItems.GOLD_BEE_SPAWN_EGG, "gold_bee"));
                        entries.add(createBeeSpawnEgg(BeeIndustryItems.IRON_BEE_SPAWN_EGG, "iron_bee"));
                        entries.add(createBeeSpawnEgg(BeeIndustryItems.FARMING_BEE_SPAWN_EGG, "farming_bee"));
                        entries.add(createBeeSpawnEgg(BeeIndustryItems.MINING_BEE_SPAWN_EGG, "mining_bee"));
*/

                        entries.add(BeeIndustryItems.BEEKEEPER_BOOTS);
                        entries.add(BeeIndustryItems.BEEKEEPER_LEGGINGS);
                        entries.add(BeeIndustryItems.BEEKEEPER_CHESTPLATE);
                        entries.add(BeeIndustryItems.BEEKEEPER_HELMET);

                        entries.add(BeeIndustryItems.BEE_CONTAINER);
                        entries.add(BeeIndustryItems.EFFICIENCY_UPGRADE);
                        entries.add(BeeIndustryItems.QUANTITY_UPGRADE);
                        entries.add(BeeIndustryItems.RANGE_UPGRADE);

                        entries.add(BeeIndustryItems.CHARM_TEMPLATE);
                        entries.add(BeeIndustryItems.NIGHT_CHARM);

                        entries.add(BeeIndustryItems.SWEET_HONEY);
                        entries.add(BeeIndustryItems.BEE_SMOKER);

                        entries.add(BeeIndustryBlocks.ADVANCED_BEEHIVE);
                        entries.add(BeeIndustryBlocks.BEEPOST);
                        entries.add(BeeIndustryBlocks.DIRT_NEST);
                        entries.add(BeeIndustryBlocks.GRAVEL_NEST);
                        entries.add(BeeIndustryBlocks.SAND_NEST);
                        entries.add(BeeIndustryBlocks.STONE_NEST);
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

