package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin {
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
}
