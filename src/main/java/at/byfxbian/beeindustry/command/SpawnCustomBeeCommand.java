package at.byfxbian.beeindustry.command;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SpawnCustomBeeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                CommandManager.literal("spawncustombee")
                        .executes(context -> {
                            ServerWorld world = context.getSource().getWorld();

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
                            }
                        })
        );
    }

    public static CustomBeeEntity createCustomBee(World world, RegistryEntry<CustomBee> beeRegistryEntry) {
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
    }
}
