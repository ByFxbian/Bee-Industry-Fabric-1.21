package at.byfxbian.beeindustry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public record BreedingRecipe(Identifier parentA, Identifier parentB, Identifier child, float chance) {
    public static final Codec<BreedingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("parentA").forGetter(BreedingRecipe::parentA),
            Identifier.CODEC.fieldOf("parentB").forGetter(BreedingRecipe::parentB),
            Identifier.CODEC.fieldOf("child").forGetter(BreedingRecipe::child),
            Codec.FLOAT.fieldOf("chance").forGetter(BreedingRecipe::chance)
    ).apply(instance, BreedingRecipe::new));
}
