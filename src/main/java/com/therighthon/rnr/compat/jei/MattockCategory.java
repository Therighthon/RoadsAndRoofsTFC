package com.therighthon.rnr.compat.jei;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.MattockRecipe;
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
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;
import net.dries007.tfc.util.Metal;

public class MattockCategory extends BaseRecipeCategory<MattockRecipe>
{
    private final Map<ChiselRecipe.Mode, IDrawableStatic> modes;

    public MattockCategory(RecipeType<MattockRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(118, 26), new ItemStack(RNRItems.MATTOCKS.get(Metal.Default.BISMUTH_BRONZE).get()));
        modes = ImmutableMap.of(
            ChiselRecipe.Mode.SLAB, helper.createDrawable(IngameOverlays.TEXTURE, 40, 58, 20, 20),
            ChiselRecipe.Mode.STAIR, helper.createDrawable(IngameOverlays.TEXTURE, 20, 58, 20, 20),
            ChiselRecipe.Mode.SMOOTH, helper.createDrawable(IngameOverlays.TEXTURE, 0, 58, 20, 20)
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MattockRecipe recipe, IFocusGroup focuses)
    {
        Ingredient mattockIngredient = recipe.getItemIngredient();
        if (mattockIngredient == null)
        {
            mattockIngredient = Ingredient.of(RNRTags.Items.MATTOCKS);
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getBlockIngredient()))
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 5)
            .addIngredients(mattockIngredient)
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addItemStack(new ItemStack(recipe.getBlockRecipeOutput()))
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
