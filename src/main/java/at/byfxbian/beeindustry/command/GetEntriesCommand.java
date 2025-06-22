package at.byfxbian.beeindustry.command;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.argument.RegistryKeyArgumentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static at.byfxbian.beeindustry.util.BeeRegistries.BEE_REGISTRY_KEY;

public class GetEntriesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("getEntries")
                .executes(GetEntriesCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().getWorld().isClient) {
            Registry<CustomBee> customBeeRegistry = context.getSource().getWorld().getRegistryManager().get(BEE_REGISTRY_KEY);
            for (RegistryEntry<CustomBee> indexedEntry : customBeeRegistry.getIndexedEntries()) {
                context.getSource().getPlayer().sendMessage(Text.of(indexedEntry.toString()));
            }
            return 1;
        }
        context.getSource().getPlayer().sendMessage(Text.literal("Der Befehl konnte nicht ausgeführt werden."));
        return 0;
    }
}
