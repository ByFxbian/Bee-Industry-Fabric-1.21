package at.byfxbian.beeindustry.datagen;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.entities.bee.CustomBee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BeeIndustryBeeDataProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataOutput output;
    private final List<CustomBee> bees;

    public BeeIndustryBeeDataProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, List<CustomBee> bees) {
        this.output = output;
        this.bees = bees;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {
            BeeIndustry.LOGGER.debug("BeeDataGenerator wird ausgeführt...");
            for(CustomBee bee:bees) {
                Path path = output.getResolver(DataOutput.OutputType.DATA_PACK, BeeIndustry.MOD_ID).resolveJson(Identifier.of(BeeIndustry.MOD_ID, "custom_bees/" + bee.getId()));

                BeeIndustry.LOGGER.debug("Generiere Datei für Beine: " + bee.getId());
                BeeIndustry.LOGGER.debug("Pfad: " + path);

                DataProvider.writeToPath(writer, GSON.toJsonTree(bee.toJson()), path);
            }
        });
    }

    @Override
    public String getName() {
        return "Bee Data Generator";
    }

    /*private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final FabricDataOutput output;

    public BeeIndustryBeeDataProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {
            JsonObject beeJson = createBeeJson(
                "#f2f24f",
                "#d0581f",
                "#f2f24f",
                "Empty Description lol",
                "bee_industry:textures/entity/bee/example",
                true,
                "bee_industry:flowers/example",
                1.0,
                false,
                true,
                1, 1, 1
            );

            Path path = output.getPath().resolve("data/bee_industry/bee/example.json");
            DataProvider.writeToPath(writer, GSON.toJsonTree(beeJson), path);
        });

    }

    private JsonObject createBeeJson(
            String primaryColor,
            String secondaryColor,
            String pollenColor,
            String description,
            String beeTexture,
            boolean createNectar,
            String flowerBlockTag,
            double size,
            boolean translucent,
            boolean fireproof,
            int productivity,
            int temper,
            int speed
    ) {
        JsonObject bee = new JsonObject();

        bee.addProperty("primaryColor", primaryColor);
        bee.addProperty("secondaryColor", secondaryColor);
        bee.addProperty("pollenColor", pollenColor);
        bee.addProperty("description", description);
        bee.addProperty("beeTexture", beeTexture);
        bee.addProperty("createNectar", createNectar);
        bee.addProperty("flowerBlockTag", flowerBlockTag);
        bee.addProperty("size", size);
        bee.addProperty("translucent", translucent);
        bee.addProperty("fireproof", fireproof);

        JsonObject attributes = new JsonObject();
        attributes.addProperty("productivity", productivity);
        attributes.addProperty("temper", temper);
        attributes.addProperty("speed", speed);

        bee.add("attributes", attributes);

        return bee;
    }

    @Override
    public String getName() {
        return "Bee Data Provider";
    }*/
}
