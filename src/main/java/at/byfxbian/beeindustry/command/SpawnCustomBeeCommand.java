package at.byfxbian.beeindustry.command;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryKeyArgumentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Optional;

public class SpawnCustomBeeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                CommandManager.literal("spawncustombee")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("bee_type", RegistryKeyArgumentType.registryKey(BeeRegistries.BEE_REGISTRY_KEY))
                            .executes(context -> {
                                RegistryKey<CustomBee> beeKey = context.getArgument("bee_type", RegistryKey.class);

                                Registry<CustomBee> registry = context.getSource().getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);

                                Optional<RegistryEntry.Reference<CustomBee>> customBee = registry.getEntry(beeKey);

                                if (customBee.isPresent()) {
                                    return execute(context, customBee.get());
                                } else {
                                    context.getSource().sendError(Text.literal("Unknown Beetype!"));
                                    return 0;
                                }
                                /*ServerWorld world = context.getSource().getWorld();

                                try {
                                    Registry<CustomBee> registry = world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                                    if(registry == null ) {
                                        context.getSource().sendMessage(Text.literal("Registry ist null!"));
                                        return 0;
                                    }
                                    RegistryEntry<CustomBee> entry = registry.getEntry(CustomBees.GOLD).orElseThrow();

                                    CustomBeeEntity bee = createCustomBee(world, entry);
                                    if(bee == null) {
                                        context.getSource().sendMessage(Text.literal("CustomBeeEntity konnte nicht erstellt werden!"));
                                        return 0;
                                    }

                                    bee.refreshPositionAndAngles(context.getSource().getPosition().x, context.getSource().getPosition().y, context.getSource().getPosition().z, 0.0f, 0.0f);
                                    world.spawnEntity(bee);

                                    context.getSource().sendMessage(Text.literal("CustomBeeEntity erfolgreich gespawnt!"));
                                    return 1;
                                } catch (Exception e) {
                                    e.printStackTrace(); // Logge den Fehler
                                    context.getSource().sendMessage(Text.literal("Ein Fehler ist aufgetreten: " + e.getMessage()));
                                    return 0;
                                }*/
                            })
                        )
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context, RegistryEntry<CustomBee> customBeeEntry) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if(player != null) {
            World world = player.getWorld();

            CustomBeeEntity beeEntity = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(world);
            if (beeEntity != null) {
                beeEntity.setCustomBee(customBeeEntry);
                beeEntity.updatePosition(player.getX(), player.getY(), player.getZ());
                world.spawnEntity(beeEntity);
                context.getSource().sendFeedback(() -> Text.literal("CustomBeeEntity erfolgreich gespawnt!"), true);
                return 1;
            }
        }
        return 0;
    }

    /*public static CustomBeeEntity createCustomBee(World world, RegistryEntry<CustomBee> beeRegistryEntry) {
        CustomBeeEntity beeEntity = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(world);
        if(beeEntity != null) {
            System.out.println("createCustomBee: Bee entity created successfully.");
            beeEntity.setBeeType(beeRegistryEntry.getKey().get().getValue().getPath());
            System.out.println("createCustomBee: BeeType set to " + beeRegistryEntry.getKey().get().getValue().getPath());
            beeEntity.setCustomBee(beeRegistryEntry);
        } else {
            System.out.println("createCustomBee: Bee entity is null!");
        }
        return beeEntity;
    }*/
}
