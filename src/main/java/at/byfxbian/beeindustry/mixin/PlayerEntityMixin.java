package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "damage", at = @At("TAIL"))
    private void beeindustry$applyBonemealOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();

        if(!world.isClient) {
            ItemStack leggings = player.getInventory().getArmorStack(1);
            if(leggings.isOf(BeeIndustryItems.BEEKEEPER_LEGGINGS)) {
                BlockPos centerPos = player.getBlockPos().down();
                for (BlockPos currentPos : BlockPos.iterate(centerPos.add(-1, 0, -1), centerPos.add(1, 0, 1))) {
                    BlockState state = world.getBlockState(currentPos);

                    if (state.getBlock() instanceof Fertilizable fertilizable) {
                        if (fertilizable.isFertilizable(world, currentPos, state)) {
                            if (world instanceof ServerWorld serverWorld) {
                                fertilizable.grow(serverWorld, world.random, currentPos, state);
                                serverWorld.syncWorldEvent(WorldEvents.BONE_MEAL_USED, currentPos, 15);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
