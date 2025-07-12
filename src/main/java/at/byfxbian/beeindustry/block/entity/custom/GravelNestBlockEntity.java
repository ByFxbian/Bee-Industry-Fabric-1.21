package at.byfxbian.beeindustry.block.entity.custom;

import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GravelNestBlockEntity extends BlockEntity {
    public GravelNestBlockEntity(BlockPos pos, BlockState state) {
        super(BeeIndustryBlockEntities.GRAVEL_NEST_BLOCK_ENTITY, pos, state);
    }
}
