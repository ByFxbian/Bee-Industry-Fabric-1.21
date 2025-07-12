package at.byfxbian.beeindustry.block.entity;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.block.entity.custom.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BeeIndustryBlockEntities {
    public static final BlockEntityType<AdvancedBeehiveBlockEntity> ADVANCED_BEEHIVE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "advanced_beehive_be"),
                    BlockEntityType.Builder.create(AdvancedBeehiveBlockEntity::new, BeeIndustryBlocks.ADVANCED_BEEHIVE).build());

    public static final BlockEntityType<BeepostBlockEntity> BEEPOST_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "beepost_be"),
                    BlockEntityType.Builder.create(BeepostBlockEntity::new, BeeIndustryBlocks.BEEPOST).build());

    public static final BlockEntityType<DirtNestBlockEntity> DIRT_NEST_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "dirt_nest_be"),
                    BlockEntityType.Builder.create(DirtNestBlockEntity::new, BeeIndustryBlocks.DIRT_NEST).build());

    public static final BlockEntityType<StoneNestBlockEntity> STONE_NEST_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "stone_nest_be"),
                    BlockEntityType.Builder.create(StoneNestBlockEntity::new, BeeIndustryBlocks.STONE_NEST).build());

    public static final BlockEntityType<SandNestBlockEntity> SAND_NEST_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "sand_nest_be"),
                    BlockEntityType.Builder.create(SandNestBlockEntity::new, BeeIndustryBlocks.SAND_NEST).build());

    public static final BlockEntityType<GravelNestBlockEntity> GRAVEL_NEST_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BeeIndustry.MOD_ID, "gravel_nest_be"),
                    BlockEntityType.Builder.create(GravelNestBlockEntity::new, BeeIndustryBlocks.GRAVEL_NEST).build());

    public static void registerBlockEntities() {
        BeeIndustry.LOGGER.info("Registering Block Entities for " + BeeIndustry.MOD_ID);
    }
}
