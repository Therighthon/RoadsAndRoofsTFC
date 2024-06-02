package com.therighthon.rnr.common.recipe;

import com.google.gson.JsonObject;
import com.therighthon.rnr.RoadsAndRoofs;
import javax.annotation.Nullable;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.recipes.SimpleBlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;

import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;

public class BlockModRecipe extends SimpleBlockRecipe
{
    private final ResourceLocation id;
    private final BlockIngredient inputBlock;
    private final BlockState outputBlock;
    private final Ingredient inputItem;

    public BlockModRecipe(ResourceLocation id, Ingredient inputItem, BlockIngredient inputBlock, BlockState outputBlock)
    {
        super(id, inputBlock, outputBlock, false);
        this.id = id;
        this.inputItem = inputItem;
        this.inputBlock = inputBlock;
        this.outputBlock = outputBlock;
    }

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

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return RNRRecipeTypes.BLOCK_MOD_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<BlockModRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
            new ResourceLocation(RoadsAndRoofs.MOD_ID,"block_mod");

        @Override
        public BlockModRecipe fromJson(ResourceLocation id, JsonObject json) {
            final Ingredient inputItem = Ingredient.fromJson(json.get("input_item"));
            final BlockIngredient inputBlock = BlockIngredient.fromJson(JsonHelpers.get(json, "input_block"));
            final BlockState outputBlock = JsonHelpers.getBlockState(GsonHelper.getAsString(json, "output_block"));

            return new BlockModRecipe(id, inputItem, inputBlock, outputBlock);
        }

        @Nullable
        @Override
        public BlockModRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            final Ingredient inputItem = Ingredient.fromNetwork(buffer);
            final BlockIngredient inputBlock = BlockIngredient.fromNetwork(buffer);
            final BlockState outputBlock = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS).defaultBlockState();

            return new BlockModRecipe(id, inputItem, inputBlock, outputBlock);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BlockModRecipe recipe) {
            recipe.inputItem.toNetwork(buffer);
            recipe.inputBlock.toNetwork(buffer);
            buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.outputBlock.getBlock());
        }

    }
}
