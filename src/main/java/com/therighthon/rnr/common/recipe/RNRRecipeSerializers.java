package com.therighthon.rnr.common.recipe;

import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RNRRecipeSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RoadsAndRoofs.MOD_ID);

    public static final RegistryObject<MattockRecipe.Serializer> MATTOCK_RECIPE = register("mattock", MattockRecipe.Serializer::new);

    private static <S extends RecipeSerializer<?>> RegistryObject<S> register(String name, Supplier<S> factory)
    {
        return RECIPE_SERIALIZERS.register(name, factory);
    }
}
