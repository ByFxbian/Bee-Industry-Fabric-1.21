package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class FarmingGoal extends Goal {
    private final CustomBeeEntity bee;
    private final BeepostBlockEntity beepost;
    private BlockPos targetCropPos;
    private enum State { FIND_CROP, GO_TO_CROP, HARVEST, RETURN_TO_POST }
    private State currentState = State.FIND_CROP;
    private int harvestTicks = 0;

    public FarmingGoal(CustomBeeEntity bee) {
        this.bee = bee;
        this.beepost = (BeepostBlockEntity) bee.getWorld().getBlockEntity(bee.getHivePos());
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return bee.getHivePos() != null && beepost != null;
    }

    @Override
    public boolean shouldContinue() {
        return bee.getHivePos() != null;
    }

    @Override
    public void tick() {
        switch (currentState) {
            case FIND_CROP:
                findMatureCrop(bee.workRange).ifPresent(pos -> {
                    targetCropPos = pos;
                    bee.getNavigation().startMovingTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
                   // System.out.println("Farming bee found target block at " + pos);
                    currentState = State.GO_TO_CROP;
                });
                break;
            case GO_TO_CROP:
                if (targetCropPos != null && bee.getBlockPos().isWithinDistance(targetCropPos, 2.0)) {
                    currentState = State.HARVEST;
                } else if (bee.getNavigation().isIdle()) {
                    bee.getNavigation().startMovingTo(targetCropPos.getX(), targetCropPos.getY(), targetCropPos.getZ(), 1.0);
                }
                break;
            case HARVEST:
                harvestTicks++;
                if (harvestTicks > (40 / bee.workSpeedModifier)) {
                    harvestCrop();
                    bee.getNavigation().startMovingTo(beepost.getPos().getX(), beepost.getPos().getY() + 1, beepost.getPos().getZ(), 1.0);
                    currentState = State.RETURN_TO_POST;
                    harvestTicks = 0;
                }
                break;
            case RETURN_TO_POST:
                if (bee.getBlockPos().isWithinDistance(beepost.getPos(), 2.0)) {
                   // System.out.println("Farming bee finished work and is returning to post.");
                    beepost.onWorkerBeeReturned(bee);
                    bee.discard();
                }
                break;
        }
    }

    private void harvestCrop() {
        if (targetCropPos != null && bee.getWorld() instanceof ServerWorld serverWorld) {
            BlockState cropState = serverWorld.getBlockState(targetCropPos);
            if (cropState.getBlock() instanceof CropBlock) {
                List<ItemStack> drops = Block.getDroppedStacks(cropState, serverWorld, targetCropPos, null);
                //System.out.println("Farming bee mined " + cropState.getBlock() + " and collected drops.");
                serverWorld.setBlockState(targetCropPos, ((CropBlock) cropState.getBlock()).withAge(0));
                for(ItemStack originalDrop : drops) {
                    int amountToCollect = originalDrop.getCount() + bee.bonusQuantity;
                    ItemStack finalDrop = new ItemStack(originalDrop.getItem(), amountToCollect);
                    beepost.insertDrop(finalDrop);
                }

                targetCropPos = null;
            }
        }
    }

    private Optional<BlockPos> findMatureCrop(int radius) {
        return BlockPos.findClosest(bee.getBlockPos(), radius, radius,
                pos -> bee.getWorld().getBlockState(pos).getBlock() instanceof CropBlock crop && crop.isMature(bee.getWorld().getBlockState(pos)));
    }
}
