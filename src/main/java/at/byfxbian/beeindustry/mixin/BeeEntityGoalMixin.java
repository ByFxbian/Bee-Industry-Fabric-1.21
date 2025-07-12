package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public class BeeEntityGoalMixin {
    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    private void beeindustry$preventVanillaGoalsForWorkers(CallbackInfo ci) {
        BeeEntity thisBee = (BeeEntity) (Object) this;

        if (thisBee instanceof CustomBeeEntity customBee && customBee.getHivePos() != null) {
            System.out.println("Preventing VANILLA Goals for worker bee: " + thisBee.getUuid());
            ci.cancel();
        }
    }
}
