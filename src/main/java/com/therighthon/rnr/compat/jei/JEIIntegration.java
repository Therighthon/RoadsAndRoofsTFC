package com.therighthon.rnr.compat.jei;

import com.therighthon.rnr.RNRHelpers;
import java.util.List;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import com.therighthon.rnr.common.recipe.RNRRecipeTypes;
import java.util.function.Predicate;
import java.util.function.Supplier;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.ClientHelpers;

@JeiPlugin
public class JEIIntegration implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return RNRHelpers.modIdentifier("jei");
    }

    private static <T extends Recipe<?>> RecipeType<RecipeHolder<T>> type(String name, Class<T> kind)
    {
        return RecipeType.createRecipeHolderType(ResourceLocation.fromNamespaceAndPath(TerraFirmaCraft.MOD_ID, name));
    }

    public static final RecipeType<RecipeHolder<MattockRecipe>> MATTOCK = type("mattock", MattockRecipe.class);
    public static final RecipeType<RecipeHolder<BlockModRecipe>> BLOCK_MOD = type("block_mod", BlockModRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration r)
    {
        IGuiHelper gui = r.getJeiHelpers().getGuiHelper();
        r.addRecipeCategories(
            // TODO: Fluid block mod
            new BlockModCategory(BLOCK_MOD, gui),
            new MattockCategory(MATTOCK, gui)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration r)
    {
        r.addRecipes(MATTOCK, recipes(RNRRecipeTypes.MATTOCK_RECIPE));
        r.addRecipes(BLOCK_MOD, recipes(RNRRecipeTypes.BLOCK_MOD_RECIPE));
    }

    private static <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> recipes(Supplier<net.minecraft.world.item.crafting.RecipeType<T>> type)
    {
        return recipes(type, e -> true);
    }

    private static <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> recipes(Supplier<net.minecraft.world.item.crafting.RecipeType<T>> type, Predicate<T> filter)
    {
        return ClientHelpers.getLevelOrThrow().getRecipeManager()
            .getAllRecipesFor(type.get())
            .stream()
            .filter(holder -> filter.test(holder.value()))
            .toList();
    }
}
