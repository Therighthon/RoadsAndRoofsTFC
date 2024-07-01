package com.therighthon.rnr.common.block;

import java.util.function.Supplier;
import com.therighthon.rnr.RNRHelpers;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import javax.swing.text.html.BlockView;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BlockModEnabledStairBlock extends StairBlock
{
    private final boolean isTransparent;

    public BlockModEnabledStairBlock(Supplier<BlockState> baseState, Properties properties, Boolean isTransparent)
    {
        super(baseState, properties);
        this.isTransparent = isTransparent;
    }

    public BlockModEnabledStairBlock(Supplier<BlockState> baseState, Properties properties)
    {
        super(baseState, properties);
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
