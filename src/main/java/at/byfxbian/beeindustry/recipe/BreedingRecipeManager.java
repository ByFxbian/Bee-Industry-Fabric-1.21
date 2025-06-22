package at.byfxbian.beeindustry.recipe;

import at.byfxbian.beeindustry.BeeIndustry;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BreedingRecipeManager extends JsonDataLoader implements IdentifiableResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final String RECIPE_FOLDER = "breeding";

    private static final Map<Identifier, BreedingRecipe> recipes = new HashMap<>();

    public BreedingRecipeManager() {
        super(GSON, RECIPE_FOLDER);
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(BeeIndustry.MOD_ID, RECIPE_FOLDER);
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        recipes.clear();

        for(Map.Entry<Identifier, JsonElement> entry : prepared.entrySet()) {
            Identifier recipeId = entry.getKey();
            JsonElement element = entry.getValue();

            Optional<BreedingRecipe> recipe = BreedingRecipe.CODEC.parse(JsonOps.INSTANCE, element)
                    .resultOrPartial(error -> BeeIndustry.LOGGER.error("Failed to parse breeding recipe {}: {}", recipeId, error));

            recipe.ifPresent(r -> recipes.put(recipeId, r));
        }

        BeeIndustry.LOGGER.info("Loaded {} bee breeding recipes.", recipes.size());
    }

    public static Optional<BreedingRecipe> getRecipeFor(Identifier parentA, Identifier parentB) {
        for (BreedingRecipe recipe : recipes.values()) {
            if((recipe.parentA().equals(parentA) && recipe.parentB().equals(parentB)) ||
                    (recipe.parentA().equals(parentB) && recipe.parentB().equals(parentB))) {
                return Optional.of(recipe);
            }
        }
        return Optional.empty();
    }
}
