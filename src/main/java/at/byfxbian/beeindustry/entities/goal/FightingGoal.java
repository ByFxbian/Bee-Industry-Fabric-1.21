package at.byfxbian.beeindustry.entities.goal;

import at.byfxbian.beeindustry.block.entity.custom.BeepostBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class FightingGoal extends Goal {
    private final CustomBeeEntity bee;
    private final BeepostBlockEntity beepost;
    private LivingEntity currentTarget;

    // Wir definieren die Zustände für den Arbeitszyklus
    private enum State {
        SCANNING,    // Suche nach Feinden
        ATTACKING,   // Greife das aktuelle Ziel an
        RETURNING    // Fliege zum Beepost zurück
    }
    private State currentState;

    public FightingGoal(CustomBeeEntity bee) {
        this.bee = bee;
        this.beepost = (BeepostBlockEntity) bee.getWorld().getBlockEntity(bee.getHivePos());
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK)); // Wir brauchen die volle Kontrolle
    }

    // Das Ziel kann immer starten, wenn es eine Kampfbiene ist.
    @Override
    public boolean canStart() {
        return bee.getHivePos() != null && beepost != null;
    }

    // Das Ziel läuft, solange die Biene ein Zuhause hat.
    @Override
    public boolean shouldContinue() {
        return bee.getHivePos() != null;
    }

    @Override
    public void start() {
        // Der Zyklus beginnt immer mit dem Scannen.
        this.currentState = State.SCANNING;
    }

    @Override
    public void tick() {
        switch (currentState) {
            case SCANNING:
                // Suche nach dem nächstgelegenen Feind.
                findNewTarget().ifPresentOrElse(
                        target -> {
                            // Ziel gefunden: Greife es an.
                            this.currentTarget = target;
                            this.bee.setTarget(target);
                            this.currentState = State.ATTACKING;
                            System.out.println("Fighting bee found target: " + target.getType().getTranslationKey());
                        },
                        () -> {
                            // Kein Ziel gefunden: Fliege nach Hause.
                            this.currentState = State.RETURNING;
                            this.bee.getNavigation().startMovingTo(beepost.getPos().getX(), beepost.getPos().getY() + 1.5, beepost.getPos().getZ(), 1.0);
                            System.out.println("No targets found, returning to post.");
                        }
                );
                break;

            case ATTACKING:
                // Wenn das Ziel tot ist oder nicht mehr da ist, suche ein neues.
                if (currentTarget == null || !currentTarget.isAlive() || currentTarget.isRemoved()) {
                    this.bee.setTarget(null);
                    this.currentState = State.SCANNING;
                } else {
                    // Stelle sicher, dass wir uns auch zum Ziel bewegen.
                    this.bee.getNavigation().startMovingTo(this.currentTarget, 1.2);
                }
                break;

            case RETURNING:
                // Wenn wir am Beepost angekommen sind, verschwinde.
                if (bee.getBlockPos().isWithinDistance(beepost.getPos(), 2.0)) {
                    beepost.onWorkerBeeReturned(bee);
                    bee.discard();
                } else if (findNewTarget().isPresent()) {
                    // Wenn auf dem Rückweg ein neues Ziel auftaucht, dreh um!
                    this.currentState = State.SCANNING;
                    System.out.println("New target appeared while returning, re-engaging!");
                }
                break;
        }
    }

    private Optional<HostileEntity> findNewTarget() {
        if(bee.getHivePos() == null) return Optional.empty();
        Box searchBox = new Box(bee.getHivePos()).expand(bee.workRange); // Verteidigungsradius
        List<HostileEntity> hostiles = bee.getWorld().getEntitiesByClass(HostileEntity.class, searchBox, (entity) -> true);
        return hostiles.stream().min(Comparator.comparingDouble(e -> e.squaredDistanceTo(bee)));
    }
}
