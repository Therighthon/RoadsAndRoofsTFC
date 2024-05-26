package com.therighthon.rnr.common.recipe;

import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RNRRecipeTypes
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, RoadsAndRoofs.MOD_ID);

    public static final RegistryObject<RecipeType<MattockRecipe>> MATTOCK_RECIPE = register("mattock");

    private static <R extends Recipe<?>> RegistryObject<RecipeType<R>> register(String name)
    {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString()
            {
                return new ResourceLocation(RoadsAndRoofs.MOD_ID, name).toString();
            }
        });
    }
}
