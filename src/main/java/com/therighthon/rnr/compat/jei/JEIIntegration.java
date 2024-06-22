package com.therighthon.rnr.compat.jei;

import java.util.List;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import com.therighthon.rnr.common.recipe.RNRRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

import net.dries007.tfc.client.ClientHelpers;

@JeiPlugin
public class JEIIntegration implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(RoadsAndRoofs.MOD_ID, "jei");
    }

    private static <T> RecipeType<T> type(String name, Class<T> tClass)
    {
        return RecipeType.create(RoadsAndRoofs.MOD_ID, name, tClass);
    }

    public static final RecipeType<MattockRecipe> MATTOCK = type("mattock", MattockRecipe.class);
    public static final RecipeType<BlockModRecipe> BLOCK_MOD = type("block_mod", BlockModRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration r)
    {
        IGuiHelper gui = r.getJeiHelpers().getGuiHelper();
        r.addRecipeCategories(new MattockCategory(MATTOCK, gui));
        r.addRecipeCategories(new BlockModCategory(BLOCK_MOD, gui));
    }

    @Override
    public void registerRecipes(IRecipeRegistration r)
    {
        r.addRecipes(MATTOCK, recipes(RNRRecipeTypes.MATTOCK_RECIPE.get()));
        r.addRecipes(BLOCK_MOD, recipes(RNRRecipeTypes.BLOCK_MOD_RECIPE.get()));
    }

    private static <C extends Container, T extends Recipe<C>> List<T> recipes(net.minecraft.world.item.crafting.RecipeType<T> type)
    {
        return ClientHelpers.getLevelOrThrow().getRecipeManager().getAllRecipesFor(type);
    }
}
