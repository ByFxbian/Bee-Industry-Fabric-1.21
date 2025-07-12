package at.byfxbian.beeindustry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;

public record BreedingRecipe(Identifier parentA, Identifier parentB, List<Outcome> outcomes) {
    public record Outcome(Identifier child, float chance) {
        public static final Codec<Outcome> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("child").forGetter(Outcome::child),
                Codec.FLOAT.fieldOf("chance").forGetter(Outcome::chance)
        ).apply(instance, Outcome::new));
    }

    public static final Codec<BreedingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("parentA").forGetter(BreedingRecipe::parentA),
            Identifier.CODEC.fieldOf("parentB").forGetter(BreedingRecipe::parentB),
            Outcome.CODEC.listOf().fieldOf("outcomes").forGetter(BreedingRecipe::outcomes)
    ).apply(instance, BreedingRecipe::new));
}
