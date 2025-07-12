package at.byfxbian.beeindustry.block;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.block.custom.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryBlocks {
    public static final Block ADVANCED_BEEHIVE = registerBlock("advanced_beehive",
            new AdvancedBeehiveBlock(AbstractBlock.Settings.create().strength(2.0f).requiresTool()));

    public static final Block BEEPOST = registerBlock("beepost",
            new BeepostBlock(AbstractBlock.Settings.create().strength(2.5f).requiresTool().nonOpaque()));

    public static final Block DIRT_NEST = registerBlock("dirt_nest", new DirtNestBlock(AbstractBlock.Settings.copy(Blocks.DIRT).strength(0.6f)));

    public static final Block STONE_NEST = registerBlock("stone_nest",
            new StoneNestBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(1.5f, 6.0f)));

    public static final Block SAND_NEST = registerBlock("sand_nest",
            new SandNestBlock(AbstractBlock.Settings.copy(Blocks.SAND).strength(0.5f)));

    public static final Block GRAVEL_NEST = registerBlock("gravel_nest",
            new GravelNestBlock(AbstractBlock.Settings.copy(Blocks.GRAVEL).strength(0.6f)));

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(BeeIndustry.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BeeIndustry.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(BeeIndustry.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        BeeIndustry.LOGGER.info("Registering Blocks for " + BeeIndustry.MOD_ID);
    }

}
