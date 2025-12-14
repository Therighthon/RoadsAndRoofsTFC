package com.therighthon.rnr.common.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.util.registry.RegistryHolder;

import static com.therighthon.rnr.RoadsAndRoofs.*;

public class RNRRecipeTypes
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MOD_ID);

    public static final Id<MattockRecipe> MATTOCK_RECIPE = register("mattock");
    public static final Id<BlockModRecipe> BLOCK_MOD_RECIPE = register("block_mod");

    private static <R extends Recipe<?>> Id<R> register(String name)
    {
        return new Id<>(RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString()
            {
                return name;
            }
        }));
    }

    public record Id<T extends Recipe<?>>(DeferredHolder<RecipeType<?>, RecipeType<T>> holder)
        implements RegistryHolder<RecipeType<?>, RecipeType<T>> {}
}
