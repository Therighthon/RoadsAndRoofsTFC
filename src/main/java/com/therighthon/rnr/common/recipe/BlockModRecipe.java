package com.therighthon.rnr.common.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import net.dries007.tfc.common.recipes.BlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;

import net.dries007.tfc.network.StreamCodecs;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.dries007.tfc.world.Codecs;

public class BlockModRecipe extends BlockRecipe
{
    private final BlockIngredient inputBlock;
    private final BlockState outputBlock;
    private final Ingredient inputItem;
    private final FluidIngredient inputFluid;
    private final Boolean consumesItem;

    public BlockModRecipe(Ingredient inputItem, FluidIngredient inputFluid, BlockIngredient inputBlock, BlockState outputBlock, Boolean consumesItem)
    {
        super(inputBlock, Optional.of(outputBlock));

        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.inputBlock = inputBlock;
        this.outputBlock = outputBlock;
        this.consumesItem = consumesItem;
    }

    public static final MapCodec<BlockModRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        Ingredient.CODEC.fieldOf("input_item").forGetter(c -> c.inputItem),
        FluidIngredient.CODEC.fieldOf("input_fluid").forGetter(c -> c.inputFluid),
        BlockIngredient.CODEC.fieldOf("input_block").forGetter(c -> c.inputBlock),
        Codecs.BLOCK_STATE.fieldOf("output_block").forGetter(c -> c.outputBlock),
        Codec.BOOL.optionalFieldOf("consume_ingredient", Boolean.TRUE).forGetter(c -> c.consumesItem)
    ).apply(i, BlockModRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockModRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, c -> c.inputItem,
        FluidIngredient.STREAM_CODEC, c -> c.inputFluid,
        BlockIngredient.STREAM_CODEC, c -> c.inputBlock,
        StreamCodecs.BLOCK_STATE, c -> c.outputBlock,
        ByteBufCodecs.BOOL, c -> c.consumesItem,
        BlockModRecipe::new
    );

    public static final IndirectHashCollection<Block, BlockModRecipe> CACHE = IndirectHashCollection.createForRecipe(blockModRecipe -> blockModRecipe.getInputBlock().blocks(), RNRRecipeTypes.BLOCK_MOD_RECIPE);

    public static BlockModRecipe getRecipe(BlockState state, ItemStack item)
    {
        for (BlockModRecipe recipe : CACHE.getAll(state.getBlock()))
        {
            if (recipe.matches(state, item))
            {
                return recipe;
            }
        }
        return null;
    }

    public boolean matches(BlockState state, ItemStack item)
    {
        return (inputBlock.test(state) && inputItem.test(item));
    }


    public BlockIngredient getInputBlock()
    {
        return this.inputBlock;
    }

    public Ingredient getInputItem()
    {
        return this.inputItem;
    }

    public BlockState getOutputBlock()
    {
        return outputBlock;
    }

    public Boolean consumesItem()
    {
        return consumesItem;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RNRRecipeSerializers.BLOCK_MOD_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return RNRRecipeTypes.BLOCK_MOD_RECIPE.get();
    }
}
