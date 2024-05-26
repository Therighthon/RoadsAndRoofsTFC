package com.therighthon.rnr.common.recipe;

import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RNRRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RoadsAndRoofs.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}
