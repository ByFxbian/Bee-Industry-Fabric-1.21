package at.byfxbian.beeindustry.block.entity.custom;

import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class StoneNestBlockEntity extends BlockEntity {
    public StoneNestBlockEntity(BlockPos pos, BlockState state) {
        super(BeeIndustryBlockEntities.STONE_NEST_BLOCK_ENTITY, pos, state);
    }
}
