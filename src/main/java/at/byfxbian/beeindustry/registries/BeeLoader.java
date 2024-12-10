package at.byfxbian.beeindustry.registries;

import at.byfxbian.beeindustry.entities.bee.CustomBee;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BeeLoader {
    private static final Gson GSON = new Gson();
    private static final List<RegistryEntry<CustomBee>> beeEntries = new ArrayList<>();

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(BeeLoader::loadBees);
    }

    private static void loadBees(MinecraftServer server) {
        DynamicRegistryManager.Immutable dynamicRegistryManager = server.getRegistryManager();
        Registry<CustomBee> beeRegistry = dynamicRegistryManager.get(BeeRegistries.CUSTOM_BEE_REISTRY_KEY);

        server.getResourceManager().findResources("custom_bees", path -> path.getPath().endsWith(".json")).forEach((id, resource) -> {
            try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)){
                JsonObject json = GSON.fromJson(reader, JsonObject.class);
                CustomBee customBee = CustomBee.fromJson(json);
                Identifier beeId = Identifier.of(id.getNamespace(), id.getPath().replace("custom_bees/", "").replace(".json", ""));

                RegistryEntry<CustomBee> beeEntry = RegistryEntry.of(customBee);
                beeEntries.add(beeEntry);
                System.out.println("Loaded custom bee: " + beeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static List<RegistryEntry<CustomBee>> getBeeEntries() {
        return beeEntries;
    }
}
