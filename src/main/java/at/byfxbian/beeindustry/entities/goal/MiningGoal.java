package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class MiningGoal extends Goal {
    private final CustomBeeEntity bee;
    private final BeepostBlockEntity beepost;
    private BlockPos targetBlockPos;
    private int miningTicks = 0;
    private int blocksMined = 0;
    private static final int MAX_BLOCKS_TO_MINE = 5;
    private static final int SEARCH_RANGE = 10;
    private enum State { FIND_BLOCK, GO_TO_BLOCK, MINE_BLOCK, RETURN_TO_POST }
    private State currentState = State.FIND_BLOCK;

    public MiningGoal(CustomBeeEntity bee) {
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
        return bee.getHivePos() != null && currentState != State.RETURN_TO_POST;
    }

    @Override
    public void start() {
        this.currentState = State.FIND_BLOCK;
        this.blocksMined = 0;
        this.miningTicks = 0;
        this.targetBlockPos = null;
        bee.clearInventory();
    }

    @Override
    public void tick() {
        switch (currentState) {
            case FIND_BLOCK:
                findTargetBlock();
                break;
            case GO_TO_BLOCK:
                if (targetBlockPos != null && bee.getBlockPos().isWithinDistance(targetBlockPos, 5.0)) {
                    currentState = State.MINE_BLOCK;
                } else if (bee.getNavigation().isIdle()) {
                    bee.getNavigation().startMovingTo(targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ(), 1.0);
                }
                break;
            case MINE_BLOCK:
                miningTicks++;
                if (miningTicks > (40 / bee.workSpeedModifier)) {
                    mineBlock();
                    miningTicks = 0;
                    blocksMined++;
                    if (blocksMined >= MAX_BLOCKS_TO_MINE) {
                        currentState = State.RETURN_TO_POST;
                        returnToPost();
                    } else {
                        currentState = State.FIND_BLOCK;
                    }
                }
                break;
            case RETURN_TO_POST:
                break;
        }
    }

    private void findTargetBlock() {
        if (bee.getCustomBee() == null) return;
        String tagIdString = bee.getCustomBee().value().flowerBlockTag();
        TagKey<Block> mineableTag = TagKey.of(RegistryKeys.BLOCK, Identifier.of(tagIdString));

        Optional<BlockPos> nearestBlock = BlockPos.findClosest(
                bee.getHivePos(),
                bee.workRange,
                bee.workRange,
                pos -> bee.getWorld().getBlockState(pos).isIn(mineableTag));

        nearestBlock.ifPresent(pos -> {
            this.targetBlockPos = pos;
            bee.getNavigation().startMovingTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
            this.currentState = State.GO_TO_BLOCK;
            //System.out.println("Mining bee found target block at " + pos);
        });
    }

    private void mineBlock() {
        if (targetBlockPos != null && bee.getWorld() instanceof ServerWorld serverWorld) {
            BlockState blockState = serverWorld.getBlockState(targetBlockPos);
            List<ItemStack> drops = Block.getDroppedStacks(blockState, serverWorld, targetBlockPos, null);
            serverWorld.breakBlock(targetBlockPos, false, bee);

            for(ItemStack originalDrop : drops) {
                int amountToCollect = originalDrop.getCount() + bee.bonusQuantity;
                ItemStack finalDrop = new ItemStack(originalDrop.getItem(), amountToCollect);
                bee.addItemStack(finalDrop);
            }

            //System.out.println("Mining bee mined " + blockState.getBlock() + " and collected drops.");
            this.targetBlockPos = null;
        }
    }

    private void returnToPost() {
       //System.out.println("Mining bee finished work and is returning to post.");
        BlockEntity blockEntity = bee.getWorld().getBlockEntity(bee.getHivePos());
        if (blockEntity instanceof BeepostBlockEntity beepost) {
            bee.getNavigation().startMovingTo(beepost.getPos().getX(), beepost.getPos().getY() + 1, beepost.getPos().getZ(), 1.0);
            beepost.onWorkerBeeReturned(bee);
            bee.discard();
        }
    }
}
