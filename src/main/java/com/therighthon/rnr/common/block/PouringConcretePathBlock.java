package com.therighthon.rnr.common.block;

import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.dries007.tfc.common.blocks.ExtendedProperties;

public class PouringConcretePathBlock extends PathHeightBlock
{
    private static final float defaultSpeedFactor = 0.7f;

    public static final IntegerProperty CONCRETE_LEVEL = RNRBlockStateProperties.CONCRETE_LEVEL;
    private final Block base;
    private final BlockState baseState;

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public PouringConcretePathBlock(ExtendedProperties properties)
    {
        super(properties.properties().speedFactor(defaultSpeedFactor));
        //TODO: maybe remove?
        this.registerDefaultState(this.defaultBlockState().setValue(CONCRETE_LEVEL, 0));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CONCRETE_LEVEL);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 10);
    }

    //TODO: Pretty janky setup, but it does work for now
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        //Spreading
        int concreteLevel = state.getValue(CONCRETE_LEVEL);
        if (concreteLevel > 0)
        {
            spreadFluid(level, pos.north(), concreteLevel);
            spreadFluid(level, pos.west(), concreteLevel);
            spreadFluid(level, pos.east(), concreteLevel);
            spreadFluid(level, pos.south(), concreteLevel);
        }
        level.setBlock(pos, RNRBlocks.WET_CONCRETE_ROAD.get().defaultBlockState(), 3);
    }

    private void spreadFluid(Level level, BlockPos spreadPos, int sourceLevel)
    {
        if(level.getBlockState(spreadPos).is(RNRTags.Blocks.CONCRETE_SPREADABLE))
        {
            level.setBlock(spreadPos, RNRBlocks.POURING_CONCRETE_ROAD.get().defaultBlockState().setValue(CONCRETE_LEVEL, sourceLevel - 1), 3);
        }
    }

}
