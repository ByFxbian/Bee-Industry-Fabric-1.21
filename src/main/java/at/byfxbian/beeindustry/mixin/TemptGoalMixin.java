package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.annotation.Target;

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PathAwareEntity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D"))
    private double beeindustry$increaseTemptRadius(PathAwareEntity mob, Entity playerEntity) {
        double originalDistanceSq = mob.squaredDistanceTo(playerEntity);

        if (playerEntity instanceof PlayerEntity player) {
            ItemStack chestplate = player.getInventory().getArmorStack(2); // Slot 2 ist die Brustplatte
            if (chestplate.isOf(BeeIndustryItems.BEEKEEPER_CHESTPLATE)) {
                return 1.0;
            }
        }
        return originalDistanceSq;
    }
}
