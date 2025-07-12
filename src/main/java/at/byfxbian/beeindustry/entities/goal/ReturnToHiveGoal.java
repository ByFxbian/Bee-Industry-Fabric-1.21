package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.block.entity.custom.AdvancedBeehiveBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ReturnToHiveGoal extends Goal {
    private final CustomBeeEntity bee;

    public ReturnToHiveGoal(CustomBeeEntity bee) {
        this.bee = bee;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return bee.hasWorked && bee.getHivePos() != null;
    }

    @Override
    public void start() {
        BlockPos hivePos = bee.getHivePos();
        if(hivePos != null) {
            bee.getNavigation().startMovingTo(hivePos.getX(), hivePos.getY(), hivePos.getZ(), 1.0);
            System.out.println("Bee is returning to hive at " + hivePos);
        }
    }

    @Override
    public boolean shouldContinue() {
        return bee.getHivePos() != null && !bee.getBlockPos().isWithinDistance(bee.getHivePos(), 2.0);
    }

    @Override
    public void stop() {
        if(bee.getHivePos() != null && bee.getBlockPos().isWithinDistance(bee.getHivePos(), 2.0)) {
            BlockEntity blockEntity = bee.getWorld().getBlockEntity(bee.getHivePos());
            if(blockEntity instanceof AdvancedBeehiveBlockEntity beehive) {
                beehive.onWorkedBeeReturned();

                bee.discard();
                System.out.println("Bee has returned and entered the hive.");
            }
        }
    }
}
