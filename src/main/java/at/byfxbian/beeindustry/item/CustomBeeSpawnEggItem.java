package at.byfxbian.beeindustry.item;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Spawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;
import java.util.Optional;

public class CustomBeeSpawnEggItem extends SpawnEggItem {
    public CustomBeeSpawnEggItem(EntityType<? extends CustomBeeEntity> type, int primaryColor, int secondaryColor, Settings settings) {
        super(type, primaryColor, secondaryColor, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);

            if(world.getBlockEntity(blockPos) instanceof Spawner spawner) {
                EntityType<?> entityType = this.getEntityType(itemStack);
                spawner.setEntityType(entityType, world.getRandom());
                world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.decrement(1);
                return ActionResult.CONSUME;
            } else {
                BlockPos blockPos2;
                if(blockState.getCollisionShape(world, blockPos).isEmpty()) {
                    blockPos2 = blockPos;
                } else {
                    blockPos2 = blockPos.offset(direction);
                }

                EntityType<?> entityType = this.getEntityType(itemStack);

                Entity entity = entityType.spawnFromItemStack(
                        (ServerWorld)world,
                        itemStack,
                        context.getPlayer(),
                        blockPos2,
                        SpawnReason.SPAWN_EGG,
                        true,
                        !Objects.equals(blockPos, blockPos2) && direction == Direction.UP
                );

                if(entity != null) {
                    if(entity instanceof CustomBeeEntity customBeeEntity) {
                        String beeTypeName = itemStack.get(BeeIndustryDataComponentTypes.BEE_TYPE);
                        if(beeTypeName != null) {
                            Registry<CustomBee> registry = world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                            if(registry != null) {
                                Optional<RegistryEntry.Reference<CustomBee>> customBee = registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of("beeindustry", beeTypeName)));
                                customBee.ifPresent(customBeeEntity::setCustomBee);
                            }
                        }
                    }

                    itemStack.decrement(1);
                    world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                }

                return ActionResult.CONSUME;
            }
        }
    }
}
