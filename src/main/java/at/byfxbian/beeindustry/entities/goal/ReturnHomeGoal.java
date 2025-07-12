package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Box;

import java.util.EnumSet;

public class ReturnHomeGoal extends Goal {
    private final CustomBeeEntity bee;
    private static final int DEFENSE_RADIUS = 10;

    public ReturnHomeGoal(CustomBeeEntity bee) {
        this.bee = bee;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (bee.getTarget() != null || bee.getHivePos() == null) {
            return false;
        }
        Box searchBox = new Box(bee.getHivePos()).expand(DEFENSE_RADIUS);
        return bee.getWorld().getEntitiesByClass(HostileEntity.class, searchBox, (e) -> true).isEmpty();
    }

    @Override
    public boolean shouldContinue() {
        return bee.getHivePos() != null && !bee.getBlockPos().isWithinDistance(bee.getHivePos(), 2.0);
    }

    @Override
    public void start() {
        bee.getNavigation().startMovingTo(bee.getHivePos().getX(), bee.getHivePos().getY() + 1, bee.getHivePos().getZ(), 1.0);
        System.out.println("All clear, fighting bee is returning to post.");
    }

    @Override
    public void stop() {
        BlockEntity blockEntity = bee.getWorld().getBlockEntity(bee.getHivePos());
        if (blockEntity instanceof BeepostBlockEntity beepost) {
            beepost.onWorkerBeeReturned(bee);
            bee.discard();
        }
    }
}
