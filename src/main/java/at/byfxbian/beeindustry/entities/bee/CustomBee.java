package at.byfxbian.beeindustry.entities.bee;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public class CustomBee {
    private final String id;
    private final String primaryColor;
    private final String secondaryColor;
    private final String pollenColor;
    private final String description;
    private final String beeTexture;
    private final boolean createNectar;
    private final String flowerBlockTag;
    private final double size;
    private final boolean translucent;
    private final boolean fireproof;
    private final int productivity;
    private final int temper;
    private final int speed;

    public CustomBee(String id, String primaryColor, String secondaryColor, String pollenColor, String description,
                     String beeTexture, boolean createNectar, String flowerBlockTag, double size,
                     boolean translucent, boolean fireproof, int productivity, int temper, int speed) {
        this.id = id;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.pollenColor = pollenColor;
        this.description = description;
        this.beeTexture = beeTexture;
        this.createNectar = createNectar;
        this.flowerBlockTag = flowerBlockTag;
        this.size = size;
        this.translucent = translucent;
        this.fireproof = fireproof;
        this.productivity = productivity;
        this.temper = temper;
        this.speed = speed;
    }

    public String getId() {
        return id;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("primaryColor", primaryColor);
        json.addProperty("secondaryColor", secondaryColor);
        json.addProperty("pollenColor", pollenColor);
        json.addProperty("description", description);
        json.addProperty("beeTexture", beeTexture);
        json.addProperty("createNectar", createNectar);
        json.addProperty("flowerBlockTag", flowerBlockTag);
        json.addProperty("size", size);
        json.addProperty("translucent", translucent);
        json.addProperty("fireproof", fireproof);

        JsonObject attributes = new JsonObject();
        attributes.addProperty("productivity", productivity);
        attributes.addProperty("temper", temper);
        attributes.addProperty("speed", speed);

        json.add("attributes", attributes);

        return json;
    }

    public static CustomBee fromJson(JsonObject json) {
        String id = json.has("id") ? json.get("id").getAsString() : "unknown_bee";
        String primaryColor = json.get("primaryColor").getAsString();
        String secondaryColor = json.get("secondaryColor").getAsString();
        String pollenColor = json.get("pollenColor").getAsString();
        String description = json.get("description").getAsString();
        String beeTexture = json.get("beeTexture").getAsString();
        boolean createNectar = json.get("createNectar").getAsBoolean();
        String flowerBlockTag = json.get("flowerBlockTag").getAsString();
        double size = json.get("size").getAsDouble();
        boolean translucent = json.get("translucent").getAsBoolean();
        boolean fireproof = json.get("fireproof").getAsBoolean();

        JsonObject attributes = json.getAsJsonObject("attributes");
        int productivity = attributes.get("productivity").getAsInt();
        int temper = attributes.get("temper").getAsInt();
        int speed = attributes.get("speed").getAsInt();

        return new CustomBee(id, primaryColor, secondaryColor, pollenColor, description,
                beeTexture, createNectar, flowerBlockTag, size, translucent, fireproof,
                productivity, temper, speed);
    }
}
