package com.therighthon.rnr.common.block;

import java.util.function.Supplier;
import com.therighthon.rnr.RNRHelpers;
import javax.swing.text.html.BlockView;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockModEnabledSlabBlock extends SlabBlock
{
    private final boolean isTransparent;

    public BlockModEnabledSlabBlock(Properties properties, Boolean isTransparent)
    {
        super(properties);
        this.isTransparent = isTransparent;
    }

    public BlockModEnabledSlabBlock(Properties properties)
    {
        super(properties);
        this.isTransparent = false;
    }

    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos)
    {
        return this.isTransparent;
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        return RNRHelpers.blockModRecipeCompatible(blockState, level, pos, player, hand, hit);
    }
}
