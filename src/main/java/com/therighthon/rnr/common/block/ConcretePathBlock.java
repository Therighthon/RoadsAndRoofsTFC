package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ConcretePathBlock extends PathHeightBlock
{
    private static final float defaultSpeedFactor = 1.3f;

    //TODO: Maybe this boolean property is useless and we can just use whether the block is cracked?
    public static final BooleanProperty ADJACENT_CONCRETE_SHOULD_CRACK = RNRBlockStateProperties.ADJACENT_CONCRETE_SHOULD_CRACK;

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public ConcretePathBlock(Properties pProperties, float speedFactor)
    {
        super(pProperties.speedFactor(speedFactor));
        this.registerDefaultState(this.defaultBlockState().setValue(ADJACENT_CONCRETE_SHOULD_CRACK, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ADJACENT_CONCRETE_SHOULD_CRACK);
    }

    public ConcretePathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(defaultSpeedFactor));
    }

    //TODO: Add configurable speed factors

}
