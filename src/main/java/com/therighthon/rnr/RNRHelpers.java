package com.therighthon.rnr;

import com.therighthon.rnr.common.recipe.BlockModRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import static net.dries007.tfc.util.Helpers.*;

public final class RNRHelpers
{
    public static ResourceLocation modIdentifier(String name) {
        return resourceLocation("rnr", name);
    }

    // TODO: Use interaction result
    public static InteractionResult blockModRecipeCompatible(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        final BlockModRecipe recipe = BlockModRecipe.getRecipe(level.getBlockState(pos), stack);
        if (recipe != null && !(player.blockPosition().equals(pos)))
        {
            final BlockState output = recipe.getOutputBlock().getBlock().withPropertiesOf(blockState);
            if (!player.isCreative() && recipe.consumesItem())
            {
                // Concrete pouring
                final IFluidHandlerItem fluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
                if (fluidHandler != null) {
                    fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    player.setItemInHand(hand, fluidHandler.getContainer());
                }
                else if (stack.isDamageableItem())
                {
                    stack.setDamageValue(stack.getDamageValue() - 1);
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
