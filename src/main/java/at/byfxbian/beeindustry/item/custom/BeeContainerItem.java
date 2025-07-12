package at.byfxbian.beeindustry.item.custom;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BeeContainerItem extends Item {

    public BeeContainerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof CustomBeeEntity bee && !stack.contains(BeeIndustryDataComponentTypes.STORED_BEE_TYPE) && !stack.contains(BeeIndustryDataComponentTypes.STORED_BEE)) {
            if(!user.getWorld().isClient()) {

                String beeTypeId = bee.getCustomBee().getKey().get().getValue().toString();

                ItemStack filledContainer = new ItemStack(this);
                filledContainer.set(BeeIndustryDataComponentTypes.STORED_BEE_TYPE, beeTypeId);
                if(bee.isBaby()) {
                    filledContainer.set(BeeIndustryDataComponentTypes.IS_BEE_BABY, true);
                }

                user.setStackInHand(hand, filledContainer);

                bee.discard();

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack stack = context.getStack();
        String beeTypeIdString = stack.get(BeeIndustryDataComponentTypes.STORED_BEE_TYPE);

        if (beeTypeIdString != null) {
            BlockPos spawnPos = context.getBlockPos().offset(context.getSide());
            Registry<CustomBee> registry = world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
            Identifier beeId = Identifier.of(beeTypeIdString);
            Optional<RegistryEntry.Reference<CustomBee>> customBeeEntryOptional = registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, beeId));

            if (customBeeEntryOptional.isPresent()) {
                RegistryEntry<CustomBee> beeToSpawnData = customBeeEntryOptional.get();
                CustomBeeEntity spawnedEntity = BeeIndustryEntities.CUSTOM_BEE_ENTITY.spawn(
                        (ServerWorld) world,
                        (beeEntity) -> {
                            if(stack.getOrDefault(BeeIndustryDataComponentTypes.IS_BEE_BABY, false)) {
                                beeEntity.setBaby(true);
                            }
                            beeEntity.setCustomBee(beeToSpawnData);
                        },
                        spawnPos,
                        SpawnReason.SPAWN_EGG,
                        true,
                        true
                );

                if (spawnedEntity != null) {
                    context.getPlayer().setStackInHand(context.getHand(), new ItemStack(this));
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        String storedBeeId = stack.get(BeeIndustryDataComponentTypes.STORED_BEE_TYPE);

        if(storedBeeId != null) {
            tooltip.add(Text.translatable("item.beeindustry.bee_container.tooltip.contains", Text.translatable(Util.createTranslationKey("entity", Identifier.of(storedBeeId)))).formatted(Formatting.GRAY));

            if(Screen.hasShiftDown()) {
                RegistryWrapper.WrapperLookup lookup = context.getRegistryLookup();
                RegistryWrapper.Impl<CustomBee> beeRegistry = lookup.getWrapperOrThrow(BeeRegistries.BEE_REGISTRY_KEY);
                Optional<RegistryEntry.Reference<CustomBee>> beeEntryOptional = beeRegistry.getOptional(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of(storedBeeId)));

                beeEntryOptional.ifPresent(beeEntry -> {
                    CustomBee beeData = beeEntry.value();
                    tooltip.add(Text.literal(""));
                    tooltip.add(Text.literal("Attributes").formatted(Formatting.GOLD));
                    tooltip.add(Text.literal(" - Max Health: " + beeData.attributes().maxHealth()).formatted(Formatting.AQUA));
                    tooltip.add(Text.literal(" - Attack Damage: " + beeData.attributes().attackDamage()).formatted(Formatting.AQUA));
                    tooltip.add(Text.literal(" - Productivity: " + beeData.attributes().productivity()).formatted(Formatting.AQUA));
                    tooltip.add(Text.literal(" - Speed: " + beeData.attributes().speed()).formatted(Formatting.AQUA));
                    tooltip.add(Text.literal(" - Temper: " + beeData.attributes().temper()).formatted(Formatting.AQUA));

                    if (stack.getOrDefault(BeeIndustryDataComponentTypes.IS_BEE_BABY, false)) {
                        tooltip.add(Text.literal(" - Age: Baby").formatted(Formatting.GREEN));
                    } else {
                        tooltip.add(Text.literal(" - Age: Adult").formatted(Formatting.GREEN));
                    }
                });
            } else {
                tooltip.add(Text.translatable("tooltip.beeindustry.hold_shift_for_info").formatted(Formatting.DARK_GRAY));
            }
        } else {
            tooltip.add(Text.translatable("item.beeindustry.bee_container.tooltip.empty").formatted(Formatting.GRAY));
        }
    }
}
