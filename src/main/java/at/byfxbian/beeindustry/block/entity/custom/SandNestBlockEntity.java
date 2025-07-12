package at.byfxbian.beeindustry.block.entity.custom;

import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SandNestBlockEntity extends BlockEntity {
    public SandNestBlockEntity(BlockPos pos, BlockState state) {
        super(BeeIndustryBlockEntities.SAND_NEST_BLOCK_ENTITY, pos, state);
    }
}