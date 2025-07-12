package at.byfxbian.beeindustry.mixin;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {

    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;spawnFromItemStack(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;ZZ)Lnet/minecraft/entity/Entity;"))
    private Entity beeindustry$setBeeDataOnSpawn(EntityType<?> entityType, ServerWorld world, ItemStack stack, PlayerEntity player, BlockPos pos, SpawnReason spawnReason, boolean alignPosition, boolean invertY) {
        Entity entity = entityType.spawnFromItemStack(world, stack, player, pos, spawnReason, alignPosition, invertY);
        if (entity instanceof CustomBeeEntity customBeeEntity) {
            String beeTypeName = stack.get(BeeIndustryDataComponentTypes.BEE_TYPE);
            if(beeTypeName != null) {
                Registry<CustomBee> registry = entity.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                if(registry != null) {
                    Optional<RegistryEntry.Reference<CustomBee>> customBee = registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of("beeindustry", beeTypeName)));
                    customBee.ifPresent(customBeeEntity::setCustomBee);
                }
            }
        }
        return entity;
    }
}
