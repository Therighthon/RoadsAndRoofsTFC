package com.therighthon.rnr.compat.jei;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import java.util.stream.Collectors;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import net.dries007.tfc.client.IngameOverlays;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.player.ChiselMode;
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;
import net.dries007.tfc.util.Metal;

public class MattockCategory extends BaseRecipeCategory<MattockRecipe>
{
    private final Map<ChiselMode, IDrawableStatic> modes;

    public MattockCategory(RecipeType<MattockRecipe> type, IGuiHelper helper)
    {
        super(type, helper, 118, 26, new ItemStack(RNRItems.MATTOCKS.get(Metal.BISMUTH_BRONZE)));
        modes = ChiselMode.REGISTRY.stream()
            .collect(Collectors.toMap(
                e -> e,
                e -> e.createIcon(helper::createDrawable)
            ));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MattockRecipe recipe, IFocusGroup focuses)
    {
        Ingredient mattockIngredient = Ingredient.of(RNRTags.Items.MATTOCKS);

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getIngredient()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 5)
            .addIngredients(mattockIngredient)
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addItemStack(new ItemStack(recipe.getOutputBlock().getBlock()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 5)
            .addItemStack(recipe.getExtraDrop(ItemStack.EMPTY))
            .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(MattockRecipe recipe, IRecipeSlotsView recipeSlots, GuiGraphics stack, double mouseX, double mouseY)
    {
        modes.get(recipe.getMode()).draw(stack, 48, 3);
    }
}
