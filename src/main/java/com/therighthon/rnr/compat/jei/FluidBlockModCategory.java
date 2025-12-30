package com.therighthon.rnr.compat.jei;

import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.FluidBlockModRecipe;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;

import net.dries007.tfc.compat.jei.JEIIntegration;
import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;

public class FluidBlockModCategory extends BaseRecipeCategory<FluidBlockModRecipe>
{
    public FluidBlockModCategory(RecipeType<RecipeHolder<FluidBlockModRecipe>> type, IGuiHelper helper)
    {
        super(type, helper,118, 26, new ItemStack(RNRItems.CRUSHED_BASE_COURSE.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidBlockModRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getInputBlock()))
            .setBackground(slot, -1, -1);

        final List<FluidStack> inputFluids = collapse(recipe.getInputFluid());
        IRecipeSlotBuilder fluidInput = builder.addSlot(RecipeIngredientRole.INPUT, 26, 5);
        fluidInput.addIngredients(JEIIntegration.FLUID_STACK, inputFluids);
        fluidInput.setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addItemStack(new ItemStack(recipe.getOutputBlock().getBlock()))
            .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(FluidBlockModRecipe recipe, IRecipeSlotsView recipeSlots, GuiGraphics stack, double mouseX, double mouseY)
    {
        arrow.draw(stack, 48, 3);
    }
}
