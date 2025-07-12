package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Optional;

public class GoToProductionBlockGoal extends Goal {
    private final CustomBeeEntity bee;
    private static final int SEARCH_RANGE = 20;
    private int workingTicks = 0;

    public GoToProductionBlockGoal(CustomBeeEntity bee) {
        this.bee = bee;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return bee.getHivePos() != null && !bee.hasWorked;
    }

    @Override
    public boolean shouldContinue() {
        return bee.productionBlockPos != null && workingTicks < 60;
    }

    @Override
    public void start() {
        findProductionBlock();
    }

    @Override
    public void stop() {
        bee.getNavigation().stop();
        this.workingTicks = 0;
        bee.productionBlockPos = null;
    }

    @Override
    public void tick() {
        BlockPos productionPos = bee.productionBlockPos;
        if (productionPos == null) {
            return;
        }
        if (bee.getBlockPos().isWithinDistance(productionPos, 2.0)) {
            workingTicks++;
            if(workingTicks >= 60) {
                bee.hasWorked = true;
                System.out.println("Bee finished working at " + productionPos);
            }
        } else {
            if (bee.getNavigation().isIdle()) {
                bee.getNavigation().startMovingTo(productionPos.getX(), productionPos.getY(), productionPos.getZ(), 1.0);
            }
        }
    }

    private void findProductionBlock() {
        Optional<CustomBee> beeDataOptional = Optional.ofNullable(bee.getCustomBee().value());
        if(beeDataOptional.isEmpty()) return;

        String tagIdString = beeDataOptional.get().flowerBlockTag();
        TagKey<Block> productionTag = TagKey.of(RegistryKeys.BLOCK, Identifier.of(tagIdString));

        Optional<BlockPos> nearestBlock = BlockPos.findClosest(
                bee.getBlockPos(),
                SEARCH_RANGE,
                SEARCH_RANGE,
                pos -> bee.getWorld().getBlockState(pos).isIn(productionTag));

        nearestBlock.ifPresent(pos -> {
            bee.productionBlockPos = pos;
            bee.getNavigation().startMovingTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
            System.out.println("Worker bee found production block at " + pos);
        });
    }
}
