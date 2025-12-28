package com.therighthon.rnr.common.recipe;

import com.mojang.serialization.MapCodec;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.dries007.tfc.util.registry.RegistryHolder;

public class RNRRecipeSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, RoadsAndRoofs.MOD_ID);

    public static final Id<MattockRecipe> MATTOCK_RECIPE = register("mattock", MattockRecipe.CODEC, MattockRecipe.STREAM_CODEC);
    public static final Id<BlockModRecipe> BLOCK_MOD_RECIPE = register("block_mod", BlockModRecipe.CODEC, BlockModRecipe.STREAM_CODEC);
    public static final Id<FluidBlockModRecipe> FLUID_BLOCK_MOD_RECIPE = register("fluid_block_mod", FluidBlockModRecipe.CODEC, FluidBlockModRecipe.STREAM_CODEC);

    private static <R extends Recipe<?>> Id<R> register(String name, MapCodec<R> codec, StreamCodec<RegistryFriendlyByteBuf, R> stream)
    {
        return register(name, new RecipeSerializerImpl<>(codec, stream));
    }

    private static <R extends Recipe<?>> Id<R> register(String name, RecipeSerializer<R> serializer)
    {
        return new Id<>(RECIPE_SERIALIZERS.register(name, () -> serializer));
    }


    public record Id<T extends Recipe<?>>(DeferredHolder<RecipeSerializer<?>, RecipeSerializer<T>> holder)
        implements RegistryHolder<RecipeSerializer<?>, RecipeSerializer<T>> {}
}
