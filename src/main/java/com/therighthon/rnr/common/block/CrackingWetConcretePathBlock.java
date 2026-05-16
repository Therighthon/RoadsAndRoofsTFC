package com.therighthon.rnr.common.block;

import com.mojang.datafixers.util.Pair;
import com.therighthon.rnr.RNRHelpers;
import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;

public class CrackingWetConcretePathBlock extends WetConcretePathBlock
{
    private final int maxJointDistance = 3;


    public static final IntegerProperty DISTANCE_X = RNRBlockStateProperties.DISTANCE_X;
    public static final IntegerProperty DISTANCE_Z = RNRBlockStateProperties.DISTANCE_Z;
    private final Block base;
    private final BlockState baseState;

    public CrackingWetConcretePathBlock(ExtendedProperties properties)
    {
        super(properties.speedFactor(getDefaultSpeedFactor()).randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE_X, 0).setValue(DISTANCE_Z, 0));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE_X).add(DISTANCE_Z);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
            long oldUpdateTick = counter.getLastUpdateTick();
            long ticksSinceUpdate = counter.getTicksSinceUpdate();

            // Cracking X - Set distance if uninitialized and enough info about neighbors
            if (state.getValue(DISTANCE_X) == 0)
            {
                final int distanceX = getDistanceXAndUpdateNeighbors(level, pos);
                if (distanceX != 0)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_X, distanceX));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            }

            //Cracking Z - Set distance if uninitialized and enough info about neighbors
            if (state.getValue(DISTANCE_Z) == 0)
            {
                final int distanceZ = getDistanceZAndUpdateNeighbors(level, pos);
                if (distanceZ != 0)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_Z, distanceZ));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            }

            // Drying
            if (ticksSinceUpdate > WetConcretePathBlock.TICKS_TO_DRY)
            {
                level.setBlockAndUpdate(pos, Math.max(state.getValue(DISTANCE_X), state.getValue(DISTANCE_Z)) >= maxJointDistance ? getOutputStateCracked(state) : getOutputState(state));
                final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
                for (Direction d : Direction.Plane.HORIZONTAL)
                {
                    cursor.setWithOffset(pos, d);
                    final BlockState stateAt = level.getBlockState(cursor);
                    if (state.getBlock() instanceof CrackingWetConcretePathBlock || stateAt.getBlock() instanceof WetConcretePathControlJointBlock)
                    {
                        level.scheduleTick(cursor, stateAt.getBlock(), 1);
                    }
                }
            }
        });
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        // Removing this block effects cracking behavior of neighbors unless it is removed by curing or changed to another crackable concrete block
        if (!(newState.getBlock() instanceof CrackingWetConcretePathBlock)
            && newState != getOutputState(state) && newState != getOutputStateCracked(state))
            RNRHelpers.updateWetCrackingConcrete(level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        // So, when we place a new cracking wet concrete block, if it is adjacent to an existing wet concrete block, that may influence
        // the edge distance of said block.
        if (!(oldState.getBlock() instanceof CrackingWetConcretePathBlock))
        {
            // Probably it isn't worthwhile to use pairs just to make the flow of code feel better, but what do I know
            for (Pair pair : new Pair[] {
                Pair.of(Direction.WEST, RNRBlockStateProperties.DISTANCE_X),
                Pair.of(Direction.EAST, RNRBlockStateProperties.DISTANCE_X),
                Pair.of(Direction.NORTH, RNRBlockStateProperties.DISTANCE_Z),
                Pair.of(Direction.SOUTH, RNRBlockStateProperties.DISTANCE_Z)})
            {
                final Direction dir = (Direction) pair.getFirst();
                final BlockPos posN1 = pos.relative(dir);
                final BlockState stateN1 = level.getBlockState(posN1);
                if (stateN1.is(RNRTags.Blocks.CRACKING_WET_CONCRETE_ROADS))
                {
                    // We check the distance property here so that we don't waste time on blocks placed in the same bucket as this
                    final IntegerProperty distProp = (IntegerProperty) pair.getSecond();
                    if (stateN1.getValue(distProp) > 0)
                    {
                        level.setBlockAndUpdate(posN1, stateN1.setValue(distProp, 0));
                        level.scheduleTick(posN1, stateN1.getBlock(), 21);

                        final BlockPos posN2 = posN1.relative(dir);
                        final BlockState stateN2 = level.getBlockState(posN2);
                        if (stateN2.is(RNRTags.Blocks.CRACKING_WET_CONCRETE_ROADS))
                        {
                            level.setBlockAndUpdate(posN2, stateN2.setValue(distProp, 0));
                            level.scheduleTick(posN2, stateN2.getBlock(), 21);
                        }
                    }
                }
            }
            super.onPlace(state, level, pos, oldState, movedByPiston);
        }
    }

    public BlockState getOutputStateCracked(BlockState input)
    {
        if (input.is(RNRBlocks.WET_CONCRETE_ROAD.get()))
        {
            return RNRBlocks.CRACKED_CONCRETE_ROAD.get().defaultBlockState();
        }
        else if (input.is(RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get()))
        {
            return RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD.get().defaultBlockState();
        }
        else
        {
            return Blocks.AIR.defaultBlockState();
        }
    }

    private int getDistanceXAndUpdateNeighbors(LevelAccessor level, BlockPos pos)
    {
        // We need to loop in a custom order to check closer blocks first, so we can just return when we find somehting
        for (int dx : new int[] {1, -1, 2, -2})
        {
            final BlockPos neighborPos = pos.offset(dx, 0, 0);
            final BlockState neighborState = level.getBlockState(neighborPos);
            // If a block is found that isn't wet concrete, return the distance to that block
            // This will handle all possible states for the block at the input pos
            if (!(neighborState.getBlock() instanceof CrackingWetConcretePathBlock neighborBlock))
            {
                return Math.abs(dx);
            }

            final int neighborDistance = neighborState.getValue(DISTANCE_X);

            // Check for uninitialized neighbors, and schedule ticks for any we find
            if (neighborDistance == 0)
            {
                level.scheduleTick(neighborPos, neighborBlock, 1);
            }
        }
        // If the above loop failed to find a something besides a cracking wet concrete block, distance must be 3+
        return 3;
    }

    private int getDistanceZAndUpdateNeighbors(LevelAccessor level, BlockPos pos)
    {
        // We need to loop in a custom order to check closer blocks first, so we can just return when we find somehting
        for (int dz : new int[] {1, -1, 2, -2})
        {
            final BlockPos neighborPos = pos.offset(0, 0, dz);
            final BlockState neighborState = level.getBlockState(neighborPos);
            // If a block is found that isn't wet concrete, return the distance to that block
            // This will handle all possible states for the block at the input pos
            if (!(neighborState.getBlock() instanceof CrackingWetConcretePathBlock neighborBlock))
            {
                return Math.abs(dz);
            }

            final int neighborDistance = neighborState.getValue(DISTANCE_Z);

            // Check for uninitialized neighbors, and schedule ticks for any we find
            if (neighborDistance == 0)
            {
                level.scheduleTick(neighborPos, neighborBlock, 1);
            }
        }
        // If the above loop failed to find a something besides a cracking wet concrete block, distance must be 3+
        return 3;
    }
}
