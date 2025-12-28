package com.therighthon.rnr;

import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.FluidBlockModRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import net.dries007.tfc.common.fluids.FluidHelpers;

import static net.dries007.tfc.util.Helpers.*;

public final class RNRHelpers
{
    public static ResourceLocation modIdentifier(String name) {
        return resourceLocation("rnr", name);
    }

    // TODO: Use interaction result
    public static InteractionResult blockModRecipeCompatible(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand)
    {
        // TODO: Would be nice to change this behavior to be recipe-dependent or something
        // First check we aren't inside the clicked block
        if (!(player.blockPosition().equals(pos)))
        {
            // First, check for item recipes
            ItemStack stack = player.getItemInHand(hand);
            final BlockModRecipe itemRecipe = BlockModRecipe.getRecipe(level.getBlockState(pos), stack);
            if (itemRecipe != null)
            {
                final BlockState output = itemRecipe.getOutputBlock().getBlock().withPropertiesOf(blockState);
                if (!player.isCreative() && itemRecipe.consumesItem())
                {
                    if (stack.isDamageableItem())
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

            final IFluidHandlerItem fluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);

            // Then, check concrete pouring/custom fluid recipes
            if (fluidHandler != null)
            {
                final FluidBlockModRecipe fluidRecipe = FluidBlockModRecipe.getRecipe(blockState, fluidHandler);

                if (fluidRecipe != null)
                {
                    final FluidStack requiredFluidStack = fluidRecipe.getInputFluid().getFluids()[0];
                    final int amountRequired = requiredFluidStack.getAmount();

                    final FluidStack simulatedDrained = fluidHandler.drain(amountRequired, IFluidHandler.FluidAction.SIMULATE);

                    // This is the last check for whether the transformation should happen
                    if (simulatedDrained.getAmount() >= amountRequired)
                    {
                        if (!player.isCreative() && fluidRecipe.consumesItem())
                        {
                            fluidHandler.drain(amountRequired, IFluidHandler.FluidAction.EXECUTE);
                            //TODO? player.setItemInHand(hand, fluidHandler.getContainer());
                        }
                        final BlockState output = fluidRecipe.getOutputBlock().getBlock().withPropertiesOf(blockState);
                        level.setBlock(pos, output, 3);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
                        FluidHelpers.playTransferSound(level, pos, requiredFluidStack, FluidHelpers.Transfer.DRAIN);

                        // Splash Particles
                        if (!level.isClientSide)
                        {
                            for (int i = 0; i < 5; ++i)
                            {
                                ((ServerLevel) level).sendParticles(
                                    ParticleTypes.SPLASH,
                                    (double) pos.getX() + level.random.nextDouble(),
                                    (double) pos.getY() + 1,
                                    (double) pos.getZ() + level.random.nextDouble(),
                                    1, 0.0, 0.0, 0.0, 1.0);
                            }
                        }

                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }

        }
        return InteractionResult.FAIL;
    }
}
