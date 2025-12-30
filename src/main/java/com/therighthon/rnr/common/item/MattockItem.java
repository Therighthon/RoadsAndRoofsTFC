package com.therighthon.rnr.common.item;

import java.util.function.Function;
import com.mojang.datafixers.util.Either;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.block.WetConcretePathControlJointBlock;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

//Mostly copied from TFC's ChiselItem.java and is under the TFC License
public class MattockItem extends ToolItem
{
    public MattockItem(Tier tier, Properties properties)
    {
        super(tier, RNRTags.Blocks.MINEABLE_WITH_MATTOCK, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        final Player player = context.getPlayer();
        if (player != null)
        {
            final Level level = context.getLevel();
            final BlockPos pos = context.getClickedPos();
            final BlockState state = level.getBlockState(pos);
            final Either<BlockState, InteractionResult> result = MattockRecipe.computeResult(player, state, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside()), true);
            return result.map(resultState -> {
                player.playSound(resultState.getSoundType().getHitSound(), 1f, 1f);

                ItemStack held = player.getMainHandItem();
                if (!level.isClientSide)
                {
                    if (TFCConfig.SERVER.enableChiselsStartCollapses.get())
                    {
                        if (Helpers.isBlock(state, TFCTags.Blocks.CAN_TRIGGER_COLLAPSE) && CollapseRecipe.tryTriggerCollapse(level, pos))
                        {
                            return InteractionResult.SUCCESS; // Abort chiseling
                        }
                    }

                    final MattockRecipe recipeUsed = MattockRecipe.getRecipe(state, player.getMainHandItem(), IPlayerInfo.get(player).chiselMode());
                    if (recipeUsed != null)
                    {
                        ItemStack extraDrop = recipeUsed.getExtraDrop(held);
                        if (!extraDrop.isEmpty())
                        {
                            ItemHandlerHelper.giveItemToPlayer(player, extraDrop);
                        }
                    }
                }
                // Silly hard-code to make joints connect properly
                if (resultState.getBlock() instanceof WetConcretePathControlJointBlock)
                {
                    resultState = WetConcretePathControlJointBlock.updateControlJointShape(resultState, Direction.NORTH, level.getBlockState(pos.north()));
                    resultState = WetConcretePathControlJointBlock.updateControlJointShape(resultState, Direction.EAST, level.getBlockState(pos.east()));
                    resultState = WetConcretePathControlJointBlock.updateControlJointShape(resultState, Direction.SOUTH, level.getBlockState(pos.south()));
                    resultState = WetConcretePathControlJointBlock.updateControlJointShape(resultState, Direction.WEST, level.getBlockState(pos.west()));
                }
                level.setBlockAndUpdate(pos, resultState);

                Helpers.damageItem(held, player, InteractionHand.MAIN_HAND);
                player.getCooldowns().addCooldown(this, 5);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }, Function.identity()); // returns the interaction result if we are given one
        }
        return InteractionResult.PASS;
    }
}
