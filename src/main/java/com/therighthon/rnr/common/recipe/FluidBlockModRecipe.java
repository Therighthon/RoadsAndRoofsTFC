package com.therighthon.rnr.common.recipe;

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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.dries007.tfc.common.recipes.BlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.network.StreamCodecs;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.dries007.tfc.world.Codecs;

public class FluidBlockModRecipe extends BlockRecipe
{
    private final BlockIngredient inputBlock;
    private final BlockState outputBlock;
    private final SizedFluidIngredient inputFluid;
    private final Boolean consumesItem;

    public FluidBlockModRecipe(SizedFluidIngredient inputFluid, BlockIngredient inputBlock, BlockState outputBlock, Boolean consumesItem)
    {
        super(inputBlock, Optional.of(outputBlock));

        this.inputFluid = inputFluid;
        this.inputBlock = inputBlock;
        this.outputBlock = outputBlock;
        this.consumesItem = consumesItem;
    }

    public static final MapCodec<FluidBlockModRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        SizedFluidIngredient.FLAT_CODEC.fieldOf("input_fluid").forGetter(c -> c.inputFluid),
        BlockIngredient.CODEC.fieldOf("input_block").forGetter(c -> c.inputBlock),
        Codecs.BLOCK_STATE.fieldOf("output_block").forGetter(c -> c.outputBlock),
        Codec.BOOL.optionalFieldOf("consume_ingredient", Boolean.TRUE).forGetter(c -> c.consumesItem)
    ).apply(i, FluidBlockModRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidBlockModRecipe> STREAM_CODEC = StreamCodec.composite(
        SizedFluidIngredient.STREAM_CODEC, c -> c.inputFluid,
        BlockIngredient.STREAM_CODEC, c -> c.inputBlock,
        StreamCodecs.BLOCK_STATE, c -> c.outputBlock,
        ByteBufCodecs.BOOL, c -> c.consumesItem,
        FluidBlockModRecipe::new
    );

    public static final IndirectHashCollection<Block, FluidBlockModRecipe> CACHE = IndirectHashCollection.createForRecipe(blockModRecipe -> blockModRecipe.getInputBlock().blocks(), RNRRecipeTypes.FLUID_BLOCK_MOD_RECIPE);

    public static FluidBlockModRecipe getRecipe(BlockState state, IFluidHandlerItem fluidHandler)
    {
        for (FluidBlockModRecipe recipe : CACHE.getAll(state.getBlock()))
        {
            if (recipe.matches(state, fluidHandler.getFluidInTank(1)))
            {
                return recipe;
            }
        }
        return null;
    }

    public boolean matches(BlockState state, FluidStack fluid)
    {
        return (inputBlock.test(state) && inputFluid.test(fluid));
    }

    public BlockIngredient getInputBlock()
    {
        return this.inputBlock;
    }

    public SizedFluidIngredient getInputFluid()
    {
        return this.inputFluid;
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
        return RNRRecipeSerializers.FLUID_BLOCK_MOD_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return RNRRecipeTypes.FLUID_BLOCK_MOD_RECIPE.get();
    }
}
