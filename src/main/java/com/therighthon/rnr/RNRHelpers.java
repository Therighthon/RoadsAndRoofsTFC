package com.therighthon.rnr;

import com.mojang.datafixers.util.Either;
import com.therighthon.rnr.common.block.CrackingWetConcretePathBlock;
import com.therighthon.rnr.common.block.WetConcretePathControlJointBlock;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.FluidBlockModRecipe;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

import static com.therighthon.rnr.common.block.RNRBlockStateProperties.*;
import static net.dries007.tfc.util.Helpers.*;

public final class RNRHelpers
{
    public static ResourceLocation modIdentifier(String name) {
        return resourceLocation("rnr", name);
    }

    public static ItemInteractionResult blockModRecipeCompatible(BlockState stateIn, Level level, BlockPos pos, Player player, InteractionHand hand)
    {
        // First check we aren't inside the clicked block
        if (!(player.blockPosition().equals(pos)))
        {
            // Second, check for item recipes
            ItemStack stack = player.getItemInHand(hand);
            final BlockModRecipe itemRecipe = BlockModRecipe.getRecipe(level.getBlockState(pos), stack);
            if (itemRecipe != null)
            {
                final BlockState stateOut = itemRecipe.getOutputBlock().getBlock().withPropertiesOf(stateIn);
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
                level.playLocalSound(pos, stateOut.getSoundType().getHitSound(), SoundSource.BLOCKS, 1f, 1f, false);

                // Make sure adjustments to wet concrete update nearby blocks
                if (stateIn.getBlock() instanceof CrackingWetConcretePathBlock && !(stateOut.getBlock() instanceof CrackingWetConcretePathBlock))
                {
                    RNRHelpers.updateWetCrackingConcrete(level, pos);
                }

                // Block mod recipes should not reset concrete cure times
                if (level.getBlockEntity(pos) instanceof TickCounterBlockEntity counter)
                {
                    final long lastUpdateTick = counter.getLastUpdateTick();
                    level.setBlockAndUpdate(pos, stateOut);
                    if (level.getBlockEntity(pos) instanceof TickCounterBlockEntity newCounter)
                        newCounter.setLastUpdateTick(lastUpdateTick);
                }
                else
                {
                    level.setBlockAndUpdate(pos, stateOut);
                }

                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, stateIn));
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }

            final IFluidHandlerItem fluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);

            // Then, check concrete pouring/custom fluid recipes
            if (fluidHandler != null)
            {
                final FluidBlockModRecipe fluidRecipe = FluidBlockModRecipe.getRecipe(stateIn, fluidHandler);

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
                            //TODO: Something's up with barrels
                        }
                        final BlockState output = fluidRecipe.getOutputBlock().getBlock().withPropertiesOf(stateIn);
                        level.setBlock(pos, output, 3);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, stateIn));
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

                        return ItemInteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }

        }
        return ItemInteractionResult.FAIL;
    }

    public static ItemInteractionResult useMattockOn(UseOnContext context)
    {
        final Player player = context.getPlayer();
        if (player != null)
        {
            final Level level = context.getLevel();
            final BlockPos pos = context.getClickedPos();
            final BlockState stateIn = level.getBlockState(pos);
            final Either<BlockState, ItemInteractionResult> result = MattockRecipe.computeResult(player, stateIn, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside()), true);
            return result.map(stateOut -> {
                player.playSound(stateOut.getSoundType().getHitSound(), 1f, 1f);

                ItemStack held = player.getMainHandItem();
                if (!level.isClientSide)
                {
                    if (TFCConfig.SERVER.enableChiselsStartCollapses.get())
                    {
                        if (Helpers.isBlock(stateIn, TFCTags.Blocks.CAN_TRIGGER_COLLAPSE) && CollapseRecipe.tryTriggerCollapse(level, pos))
                        {
                            return ItemInteractionResult.SUCCESS; // Abort chiseling
                        }
                    }

                    final MattockRecipe recipeUsed = MattockRecipe.getRecipe(stateIn, player.getMainHandItem(), IPlayerInfo.get(player).chiselMode());
                    if (recipeUsed != null)
                    {
                        ItemStack extraDrop = recipeUsed.getExtraDrop(held);
                        if (!extraDrop.isEmpty())
                        {
                            ItemHandlerHelper.giveItemToPlayer(player, extraDrop);
                        }
                    }
                }
                // Make sure adjustments to wet concrete update nearby blocks
                if (stateIn.getBlock() instanceof CrackingWetConcretePathBlock && !(stateOut.getBlock() instanceof CrackingWetConcretePathBlock))
                {
                    RNRHelpers.updateWetCrackingConcrete(level, pos);
                }

                // Silly hard-code to make joints connect properly
                if (stateOut.getBlock() instanceof WetConcretePathControlJointBlock)
                {
                    Direction.Axis axis = context.getHorizontalDirection().getAxis();
                    stateOut = stateOut.setValue(WetConcretePathControlJointBlock.AXIS, axis);
                    stateOut = WetConcretePathControlJointBlock.updateControlJointShape(stateOut, Direction.NORTH, level.getBlockState(pos.north()));
                    stateOut = WetConcretePathControlJointBlock.updateControlJointShape(stateOut, Direction.EAST, level.getBlockState(pos.east()));
                    stateOut = WetConcretePathControlJointBlock.updateControlJointShape(stateOut, Direction.SOUTH, level.getBlockState(pos.south()));
                    stateOut = WetConcretePathControlJointBlock.updateControlJointShape(stateOut, Direction.WEST, level.getBlockState(pos.west()));
                }

                if (level.getBlockEntity(pos) instanceof TickCounterBlockEntity counter)
                {
                    final long lastUpdateTick = counter.getLastUpdateTick();
                    level.setBlockAndUpdate(pos, stateOut);
                    if (level.getBlockEntity(pos) instanceof TickCounterBlockEntity newCounter)
                    {
                        newCounter.setLastUpdateTick(lastUpdateTick);
                        newCounter.getTicksSinceUpdate();
                        newCounter.setChanged();
                    }

                }
                else
                {
                    level.setBlockAndUpdate(pos, stateOut);
                }

                Helpers.damageItem(held, player, InteractionHand.MAIN_HAND);
                player.getCooldowns().addCooldown(held.getItem(), 5);
                return ItemInteractionResult.SUCCESS;
            }, Function.identity()); // returns the interaction result if we are given one
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static void updateWetCrackingConcrete(Level level, BlockPos pos)
    {
        for (int i : new int[] {1, -1, 2, -2})
        {
            final int dist = Math.abs(i);

            final BlockPos xPos = pos.offset(i, 0, 0);
            final BlockState xState = level.getBlockState(xPos);
            if (xState.getBlock() instanceof CrackingWetConcretePathBlock)
            {
                // Block mod recipes should not reset concrete cure times
                if (level.getBlockEntity(xPos) instanceof TickCounterBlockEntity counter)
                {
                    final long lastUpdateTick = counter.getLastUpdateTick();
                    level.setBlockAndUpdate(xPos, xState.setValue(DISTANCE_X, Math.min(xState.getValue(DISTANCE_X), dist)));
                    if (level.getBlockEntity(xPos) instanceof TickCounterBlockEntity newCounter)
                        newCounter.setLastUpdateTick(lastUpdateTick);
                }
                else
                {
                    level.setBlockAndUpdate(xPos, xState.setValue(DISTANCE_X, Math.min(xState.getValue(DISTANCE_X), dist)));
                }
            }

            final BlockPos zPos = pos.offset(0, 0, i);
            final BlockState zState = level.getBlockState(zPos);
            if (zState.getBlock() instanceof CrackingWetConcretePathBlock)
            {

                // Block mod recipes should not reset concrete cure times
                if (level.getBlockEntity(zPos) instanceof TickCounterBlockEntity counter)
                {
                    final long lastUpdateTick = counter.getLastUpdateTick();
                    level.setBlockAndUpdate(zPos, zState.setValue(DISTANCE_Z, Math.min(zState.getValue(DISTANCE_Z), dist)));
                    if (level.getBlockEntity(zPos) instanceof TickCounterBlockEntity newCounter)
                        newCounter.setLastUpdateTick(lastUpdateTick);
                }
                else
                {
                    level.setBlockAndUpdate(zPos, zState.setValue(DISTANCE_Z, Math.min(zState.getValue(DISTANCE_Z), dist)));
                }
            }
        }
    }
}
