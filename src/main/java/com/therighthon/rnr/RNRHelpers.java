package com.therighthon.rnr;

import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import net.dries007.tfc.common.items.TFCItems;

import static net.dries007.tfc.util.Helpers.*;

public final class RNRHelpers
{
    public static ResourceLocation modIdentifier(String name) {
        return resourceLocation("rnr", name);
    }

    public static InteractionResult blockModRecipeCompatible(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(hand);
        final BlockModRecipe recipe = BlockModRecipe.getRecipe(level.getBlockState(pos), stack);
        if (recipe != null && !(player.blockPosition().equals(pos)))
        {
            final BlockState output = recipe.getOutputBlock().getBlock().withPropertiesOf(blockState);
            if (!player.isCreative() && recipe.consumesItem())
            {
                if (stack.isDamageableItem())
                {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
                //TODO: This bucket handling stuff is hacky as all getup, should probably fix or trick Russian into going through
                // my code so he fixes it for me with some method I've never heard of
                else if (stack.is(TFCItems.WOODEN_BUCKET.get()))
                {
                    stack.shrink(1);
                    player.setItemInHand(hand, new ItemStack(TFCItems.WOODEN_BUCKET.get()));
                }
                else if (stack.is(TFCItems.RED_STEEL_BUCKET.get()))
                {
                    stack.shrink(1);
                    player.setItemInHand(hand, new ItemStack(TFCItems.RED_STEEL_BUCKET.get()));
                }
                else if (stack.is(TFCItems.BLUE_STEEL_BUCKET.get()))
                {
                    stack.shrink(1);
                    player.setItemInHand(hand, new ItemStack(TFCItems.BLUE_STEEL_BUCKET.get()));
                }
                else if (stack.is(RNRTags.Items.CONCRETE_BUCKETS))
                {
                    stack.shrink(1);
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                else
                {
                    stack.shrink(1);
                }
            }
            level.playLocalSound(pos, output.getSoundType().getHitSound(), SoundSource.BLOCKS, 1f, 1f, false);
            level.setBlock(pos, output, 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.FAIL;
    }
}
