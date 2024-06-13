package com.therighthon.rnr.common.recipe;

import java.util.Locale;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.player.PlayerDataCapability;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.dries007.tfc.common.recipes.SimpleBlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;

//TODO: JEI Compat
//Mostly copied from TFC's ChiselRecipe.java and is under the TFC License
public class MattockRecipe extends SimpleBlockRecipe
{
    /**
     * In a sentence, this method returns "Either" a BlockState, which the caller must handle, or an InteractionResult to be returned
     */
    public static Either<BlockState, InteractionResult> computeResult(Player player, BlockState state, BlockHitResult hit, boolean informWhy)
    {

        ItemStack held = player.getMainHandItem();
        if (Helpers.isItem(held, RNRTags.Items.MATTOCKS))
        {
            BlockPos pos = hit.getBlockPos();
            return player.getCapability(PlayerDataCapability.CAPABILITY).map(cap -> {
                final ChiselRecipe.Mode mode = cap.getChiselMode();
                final MattockRecipe recipe = MattockRecipe.getRecipe(state, held, mode);
                if (recipe == null)
                {
                    if (informWhy) complain(player, "no_recipe");
                    return Either.<BlockState, InteractionResult>right(InteractionResult.PASS);
                }
                else
                {
                    BlockState chiseled = recipe.getBlockCraftingResult(state);
                    chiseled = chiseled.getBlock().getStateForPlacement(new BlockPlaceContext(player, InteractionHand.MAIN_HAND, new ItemStack(chiseled.getBlock()), hit));
                    if (chiseled == null)
                    {
                        if (informWhy) complain(player, "cannot_place");
                        return Either.<BlockState, InteractionResult>right(InteractionResult.FAIL);
                    }
                    else
                    {
                        // covers case where a waterlogged block is chiseled and the new block can't take the fluid contained
                        chiseled = FluidHelpers.fillWithFluid(chiseled, player.level().getFluidState(pos).getType());
                        if (chiseled == null)
                        {
                            if (informWhy) complain(player, "bad_fluid");
                            return Either.<BlockState, InteractionResult>right(InteractionResult.FAIL);
                        }
                        else
                        {
                            return Either.<BlockState, InteractionResult>left(chiseled);
                        }
                    }
                }
            }).orElse(Either.right(InteractionResult.PASS));
        }
        return Either.right(InteractionResult.PASS);
    }

    //TODO: Mattock specific complaints
    private static void complain(Player player, String message)
    {
        player.displayClientMessage(Component.translatable("rnr.mattock." + message), true);
    }

    public static final IndirectHashCollection<Block, MattockRecipe> CACHE = IndirectHashCollection.createForRecipe(recipe -> recipe.getBlockIngredient().blocks(), RNRRecipeTypes.MATTOCK_RECIPE);

    @Nullable
    public static MattockRecipe getRecipe(BlockState state, ItemStack held, ChiselRecipe.Mode mode)
    {
        for (MattockRecipe recipe : CACHE.getAll(state.getBlock()))
        {
            if (recipe.matches(state, held, mode))
            {
                return recipe;
            }
        }
        return null;
    }

    private final ChiselRecipe.Mode mode;
    @Nullable
    private final Ingredient itemIngredient;
    private final ItemStackProvider extraDrop;

    public MattockRecipe(ResourceLocation id, BlockIngredient ingredient, BlockState outputState, ChiselRecipe.Mode mode, @Nullable Ingredient itemIngredient, ItemStackProvider extraDrop)
    {
        super(id, ingredient, outputState, false);
        this.mode = mode;
        this.itemIngredient = itemIngredient;
        this.extraDrop = extraDrop;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RNRRecipeSerializers.MATTOCK_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return RNRRecipeTypes.MATTOCK_RECIPE.get();
    }

    public boolean matches(BlockState state, ItemStack stack, ChiselRecipe.Mode mode)
    {
        if (itemIngredient != null && !itemIngredient.test(stack))
        {
            return false;
        }
        return mode == this.mode && matches(state);
    }

    public ChiselRecipe.Mode getMode()
    {
        return mode;
    }

    @Nullable
    public Ingredient getItemIngredient()
    {
        return itemIngredient;
    }

    public ItemStack getExtraDrop(ItemStack mattock)
    {
        return extraDrop.getSingleStack(mattock);
    }

    public static class Serializer extends RecipeSerializerImpl<MattockRecipe>
    {
        @Override
        public MattockRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            BlockIngredient ingredient = BlockIngredient.fromJson(JsonHelpers.get(json, "ingredient"));
            BlockState state = JsonHelpers.getBlockState(GsonHelper.getAsString(json, "result"));
            ChiselRecipe.Mode mode = JsonHelpers.getEnum(json, "mode", ChiselRecipe.Mode.class, ChiselRecipe.Mode.SMOOTH);
            Ingredient itemIngredient = json.has("item_ingredient") ? Ingredient.fromJson(json.get("item_ingredient")) : null;
            ItemStackProvider drop = json.has("extra_drop") ? ItemStackProvider.fromJson(JsonHelpers.getAsJsonObject(json, "extra_drop")) : ItemStackProvider.empty();
            return new MattockRecipe(recipeId, ingredient, state, mode, itemIngredient, drop);
        }

        @Nullable
        @Override
        public MattockRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            final BlockIngredient ingredient = BlockIngredient.fromNetwork(buffer);
            final BlockState state = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS).defaultBlockState();
            final ChiselRecipe.Mode mode = buffer.readEnum(ChiselRecipe.Mode.class);
            final Ingredient itemIngredient = Helpers.decodeNullable(buffer, Ingredient::fromNetwork);
            final ItemStackProvider drop = ItemStackProvider.fromNetwork(buffer);
            return new MattockRecipe(recipeId, ingredient, state, mode, itemIngredient, drop);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MattockRecipe recipe)
        {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.outputState.getBlock());
            buffer.writeEnum(recipe.getMode());
            Helpers.encodeNullable(recipe.itemIngredient, buffer, Ingredient::toNetwork);
            recipe.extraDrop.toNetwork(buffer);
        }
    }
}
