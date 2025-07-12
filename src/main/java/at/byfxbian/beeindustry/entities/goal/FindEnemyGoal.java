package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.List;

public class FindEnemyGoal extends TrackTargetGoal {
    private static final int DEFENSE_RADIUS = 10;
    private final CustomBeeEntity bee;

    public FindEnemyGoal(CustomBeeEntity bee) {
        super(bee, false);
        this.bee = bee;
    }

    @Override
    public boolean canStart() {
        if (this.bee.getHivePos() == null) {
            return false;
        }

        this.target = findClosestTarget();

        return this.target != null;
    }

    @Override
    public void start() {
        this.bee.setTarget(this.target);
        System.out.println("Fighting bee engaging target: " + this.target.getType().getTranslationKey());
        super.start();
    }

    private HostileEntity findClosestTarget() {
        if(this.bee.getHivePos() == null) return null;
        int defenseRadius = DEFENSE_RADIUS;
        if (this.mob instanceof CustomBeeEntity customBee && customBee.getCustomBee() != null) {
            defenseRadius += customBee.getCustomBee().value().attributes().temper();
        }
        Box searchBox = new Box(this.bee.getHivePos()).expand(defenseRadius);
        List<HostileEntity> hostiles = this.bee.getWorld().getEntitiesByClass(HostileEntity.class, searchBox, (entity) -> true);
        return hostiles.stream().min(Comparator.comparingDouble(e -> e.squaredDistanceTo(this.bee))).orElse(null);
    }
}
