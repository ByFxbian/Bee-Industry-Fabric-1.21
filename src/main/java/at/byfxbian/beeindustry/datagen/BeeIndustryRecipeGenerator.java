package at.byfxbian.beeindustry.datagen;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.block.BeeIndustryBlocks;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BeeIndustryRecipeGenerator extends FabricRecipeProvider {

    public BeeIndustryRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeeIndustryItems.BEE_SMOKER, 1)
                .pattern("  I")
                .pattern("LFI")
                .pattern("L I")
                .input('L', Items.LEATHER)
                .input('F', Items.FURNACE)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.FURNACE), conditionsFromItem(Items.FURNACE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.BEE_SMOKER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeeIndustryItems.BEE_CONTAINER, 1)
                .pattern("PPP")
                .pattern("G G")
                .pattern("GGG")
                .input('P', ItemTags.PLANKS)
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.BEE_CONTAINER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BeeIndustryBlocks.ADVANCED_BEEHIVE, 1)
                .pattern("PPP")
                .pattern("PBP")
                .pattern("PRP")
                .input('P', ItemTags.PLANKS)
                .input('B', Items.BEEHIVE)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.BEEHIVE), conditionsFromItem(Items.BEEHIVE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryBlocks.ADVANCED_BEEHIVE.asItem())));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BeeIndustryBlocks.BEEPOST, 1)
                .pattern("PIP")
                .pattern("PBP")
                .pattern("SSS")
                .input('P', ItemTags.PLANKS)
                .input('B', BeeIndustryBlocks.ADVANCED_BEEHIVE)
                .input('S', Items.SMOOTH_STONE)
                .input('I', Items.IRON_BLOCK)
                .criterion(hasItem(BeeIndustryBlocks.ADVANCED_BEEHIVE.asItem()), conditionsFromItem(BeeIndustryBlocks.ADVANCED_BEEHIVE.asItem()))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryBlocks.BEEPOST.asItem())));

        offerBeekeeperArmorRecipe(recipeExporter, BeeIndustryItems.BEEKEEPER_HELMET, Items.LEATHER_HELMET);
        offerBeekeeperArmorRecipe(recipeExporter, BeeIndustryItems.BEEKEEPER_CHESTPLATE, Items.LEATHER_CHESTPLATE);
        offerBeekeeperArmorRecipe(recipeExporter, BeeIndustryItems.BEEKEEPER_LEGGINGS, Items.LEATHER_LEGGINGS);
        offerBeekeeperArmorRecipe(recipeExporter, BeeIndustryItems.BEEKEEPER_BOOTS, Items.LEATHER_BOOTS);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.CHARM_TEMPLATE, 2)
                .pattern(" S ")
                .pattern("SGS")
                .pattern("SSS")
                .input('G', Items.GOLD_INGOT)
                .input('S', Items.SMOOTH_STONE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.CHARM_TEMPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.SWEET_HONEY, 3)
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .input('H', Items.HONEY_BOTTLE)
                .input('S', Items.SUGAR)
                .criterion(hasItem(Items.HONEY_BOTTLE), conditionsFromItem(Items.HONEY_BOTTLE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.SWEET_HONEY)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.NIGHT_CHARM, 1)
                .input(BeeIndustryItems.CHARM_TEMPLATE)
                .input(Items.GLOWSTONE_DUST, 4)
                .criterion(hasItem(BeeIndustryItems.CHARM_TEMPLATE), conditionsFromItem(BeeIndustryItems.CHARM_TEMPLATE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.NIGHT_CHARM)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.EFFICIENCY_UPGRADE, 1)
                .input(BeeIndustryItems.CHARM_TEMPLATE)
                .input(Items.REDSTONE, 2)
                .input(Items.SUGAR)
                .criterion(hasItem(BeeIndustryItems.CHARM_TEMPLATE), conditionsFromItem(BeeIndustryItems.CHARM_TEMPLATE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.EFFICIENCY_UPGRADE)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.QUANTITY_UPGRADE, 1)
                .input(BeeIndustryItems.CHARM_TEMPLATE)
                .input(Items.REDSTONE, 2)
                .input(Items.CHEST)
                .criterion(hasItem(BeeIndustryItems.CHARM_TEMPLATE), conditionsFromItem(BeeIndustryItems.CHARM_TEMPLATE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.QUANTITY_UPGRADE)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BeeIndustryItems.RANGE_UPGRADE, 1)
                .input(BeeIndustryItems.CHARM_TEMPLATE)
                .input(Items.REDSTONE, 2)
                .input(Items.BOW)
                .criterion(hasItem(BeeIndustryItems.CHARM_TEMPLATE), conditionsFromItem(BeeIndustryItems.CHARM_TEMPLATE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(BeeIndustryItems.RANGE_UPGRADE)));
    }

    private void offerBeekeeperArmorRecipe(RecipeExporter exporter, Item output, Item input) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .input(input)
                .input(Items.HONEYCOMB, 4)
                .criterion(hasItem(Items.HONEYCOMB), conditionsFromItem(Items.HONEYCOMB))
                .offerTo(exporter, Identifier.of(getRecipeName(output)));
    }
}
