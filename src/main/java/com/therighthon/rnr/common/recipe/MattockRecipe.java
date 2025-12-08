package com.therighthon.rnr.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.datafixers.util.Either;
import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.player.ChiselMode;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.dries007.tfc.common.recipes.INoopInputRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.network.StreamCodecs;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.dries007.tfc.world.Codecs;

// TODO: I removed the optional item ingredient because it didn't seem to do anything, may need to revisit that decision

//Mostly copied from TFC's ChiselRecipe.java and is under the TFC License
public class MattockRecipe implements INoopInputRecipe
{
    public static final IndirectHashCollection<Block, MattockRecipe> CACHE = IndirectHashCollection.createForRecipe(r -> r.ingredient.blocks(), RNRRecipeTypes.MATTOCK_RECIPE);

    private final BlockIngredient ingredient;
    private final BlockState output;
    private final ChiselMode mode;
    private final ItemStackProvider extraDrop;

    public MattockRecipe(BlockIngredient ingredient, BlockState outputState, ChiselMode mode, @Nullable ItemStackProvider extraDrop)
    {
        this.ingredient = ingredient;
        this.output = outputState;
        this.mode = mode;
        this.extraDrop = extraDrop;
    }

    public static final MapCodec<MattockRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        BlockIngredient.CODEC.fieldOf("ingredient").forGetter(c -> c.ingredient),
        Codecs.BLOCK_STATE.fieldOf("result").forGetter(c -> c.output),
        ChiselMode.REGISTRY.byNameCodec().fieldOf("mode").forGetter(c -> c.mode),
        ItemStackProvider.CODEC.optionalFieldOf("extra_drop", ItemStackProvider.empty()).forGetter(c -> c.extraDrop)
    ).apply(i, MattockRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MattockRecipe> STREAM_CODEC = StreamCodec.composite(
        BlockIngredient.STREAM_CODEC, c -> c.ingredient,
        StreamCodecs.BLOCK_STATE, c -> c.output,
        ByteBufCodecs.registry(ChiselMode.KEY), c -> c.mode,
        ItemStackProvider.STREAM_CODEC, c -> c.extraDrop,
        MattockRecipe::new
    );

    /**
     * In a sentence, this method returns "Either" a BlockState, which the caller must handle, or an InteractionResult to be returned
     */
    public static Either<BlockState, InteractionResult> computeResult(Player player, BlockState state, BlockHitResult hit, boolean informWhy)
    {

        ItemStack held = player.getMainHandItem();
        if (Helpers.isItem(held, RNRTags.Items.MATTOCKS))
        {
            final ChiselMode mode = IPlayerInfo.get(player).chiselMode();
            final MattockRecipe recipe = MattockRecipe.getRecipe(state, held, mode);
            BlockPos pos = hit.getBlockPos();
            if (recipe == null)
            {
                if (informWhy) complain(player, "no_recipe");
                return Either.<BlockState, InteractionResult>right(InteractionResult.PASS);
            }
            else
            {
                @Nullable BlockState chiseled = mode.modifyStateForPlacement(state, recipe.output, player, hit);
                // covers case where a waterlogged block is chiseled and the new block can't take the fluid contained
                chiseled = FluidHelpers.fillWithFluid(chiseled, player.level().getFluidState(pos).getType());
                if (chiseled == null)
                {
                    if (informWhy) complain(player, "bad_fluid");
                    return Either.<BlockState, InteractionResult>right(InteractionResult.FAIL);
                }
                return Either.left(chiseled);
            }

        }
        return Either.right(InteractionResult.PASS);
    }

    private static void complain(Player player, String message)
    {
        player.displayClientMessage(Component.translatable("rnr.mattock." + message), true);
    }

    @Nullable
    public static MattockRecipe getRecipe(BlockState state, ItemStack held, ChiselMode mode)
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

    public boolean matches(BlockState state, ItemStack stack, ChiselMode mode)
    {
        return mode == this.mode && ingredient.test(state.getBlock());
    }

    public ChiselMode getMode()
    {
        return mode;
    }

    public ItemStack getExtraDrop(ItemStack mattock)
    {
        return extraDrop.getSingleStack(mattock);
    }

    public BlockIngredient getIngredient()
    {
        return ingredient;
    }

    public BlockState getOutputBlock()
    {
        return output;
    }

    public ItemStackProvider getExtraDrop()
    {
        return extraDrop;
    }
}
