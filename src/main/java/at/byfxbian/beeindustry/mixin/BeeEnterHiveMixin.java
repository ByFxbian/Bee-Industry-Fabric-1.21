package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.block.entity.custom.AdvancedBeehiveBlockEntity;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeeEntity.class)
public abstract class BeeEnterHiveMixin {
    @Redirect(method = "canEnterHive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isNight()Z"))
    private boolean beeindustry$ignoreNightForWorkerBees(World world) {
        BeeEntity bee = (BeeEntity) (Object) this;
        if (bee instanceof CustomBeeEntity customBee && customBee.getHivePos() != null) {
            BlockEntity blockEntity = world.getBlockEntity(customBee.getHivePos());

            if (blockEntity instanceof AdvancedBeehiveBlockEntity hive) {
                if (hive.hasUpgrade(BeeIndustryItems.NIGHT_CHARM)) {
                    System.out.println("Night Charm active! Bee is ignoring the night.");
                    return false;
                }
            }
        }
        return world.isNight();
    }
}
