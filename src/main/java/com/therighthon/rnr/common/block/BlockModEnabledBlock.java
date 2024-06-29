package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RNRHelpers;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BlockModEnabledBlock extends Block
{
    public BlockModEnabledBlock(Properties properties)
    {
        super(properties);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        return RNRHelpers.blockModRecipeCompatible(blockState, level, pos, player, hand, hit);
    }
}
