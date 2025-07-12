package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin {
    @Shadow private @Nullable UUID angryAt;

    @Inject(method = "isFlowers", at = @At("HEAD"), cancellable = true)
    private void beeindustry$injectCustomIsFlowers(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BeeEntity thisBee = (BeeEntity) (Object) this;
        if(!(thisBee instanceof CustomBeeEntity customBeeEntity)) {
            return;
        }

        RegistryEntry<CustomBee> beeEntry = customBeeEntity.getCustomBee();
        if(beeEntry != null) {
            String flowerTagString   = beeEntry.value().flowerBlockTag();

            if(flowerTagString   != null && !flowerTagString  .isEmpty()) {
                TagKey<Block> flowerTag = TagKey.of(RegistryKeys.BLOCK, Identifier.of(flowerTagString));

                BlockState state = thisBee.getWorld().getBlockState(pos);
                boolean result = state.isIn(flowerTag);

                cir.setReturnValue(result);
            }
        }
    }

    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    private void beeindustry$preventAttackWithArmorSet(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if(target instanceof PlayerEntity player) {
            if(isWearingFullBeekeeperSet(player)) {
                cir.setReturnValue(false);
            }
        }
    }

    private boolean isWearingFullBeekeeperSet(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        // Prüft, ob alle Slots belegt sind und die Items die richtigen sind.
        return !helmet.isEmpty() && helmet.isOf(BeeIndustryItems.BEEKEEPER_HELMET) &&
                !chestplate.isEmpty() && chestplate.isOf(BeeIndustryItems.BEEKEEPER_CHESTPLATE) &&
                !leggings.isEmpty() && leggings.isOf(BeeIndustryItems.BEEKEEPER_LEGGINGS) &&
                !boots.isEmpty() && boots.isOf(BeeIndustryItems.BEEKEEPER_BOOTS);
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void beeindustry$reduceAngerWithElement(CallbackInfo ci) {
        BeeEntity bee = (BeeEntity) (Object)this;

        if(bee instanceof Angerable angerable) {
            if(angerable.getAngerTime() > 0 && angerable.getAngryAt() != null) {
                Entity target = bee.getWorld().getPlayerByUuid(angerable.getAngryAt());

                if (target instanceof PlayerEntity player) {
                    ItemStack helmet = player.getInventory().getArmorStack(3);
                    if(helmet.isOf(BeeIndustryItems.BEEKEEPER_HELMET)) {
                        angerable.setAngerTime(angerable.getAngerTime() - 9);
                    }
                }

            }
        }
    }
}
