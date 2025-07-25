package at.byfxbian.beeindustry.datagen;

import at.byfxbian.beeindustry.BeeIndustry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BeeIndustryBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BeeIndustryBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(BeeIndustry.MOD_ID, "flowers/gold_bee")))
                .add(Blocks.GOLD_BLOCK);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(BeeIndustry.MOD_ID, "flowers/iron_bee")))
                .add(Blocks.IRON_BLOCK);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(BeeIndustry.MOD_ID, "mineable_by_bee")))
                .add(Blocks.STONE)
                .add(Blocks.DIRT)
                .add(Blocks.GRAVEL)
                .add(Blocks.IRON_ORE)
                .add(Blocks.COAL_ORE);
    }
}
