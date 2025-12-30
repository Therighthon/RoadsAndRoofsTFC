package com.therighthon.rnr.common.block;

import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.Helpers;

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
        //TODO: maybe move this info to a block entity?
        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE_X, 1).setValue(DISTANCE_Z, 1));
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
            BlockState oldState = state;
            //Only update crack info within first 75% of drying process
            if (ticksSinceUpdate < 0.75 * getTicksToDry())
            {
                //Cracking X - Distance Update
                final int oldDistanceX = state.getValue(DISTANCE_X);
                int distanceX = updateDistanceX(level, pos);
                if (distanceX != oldDistanceX)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_X, Math.min(distanceX, maxJointDistance)));
                    counter.setLastUpdateTick(oldUpdateTick);
                }

                //Cracking Z - Distance Update
                final int oldDistanceZ = state.getValue(DISTANCE_Z);
                int distanceZ = updateDistanceZ(level, pos);
                if (distanceZ != oldDistanceZ)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_Z, Math.min(distanceZ, maxJointDistance)));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            }
            // Drying
            else if (ticksSinceUpdate > getTicksToDry())
            {
                level.setBlockAndUpdate(pos, Math.max(state.getValue(DISTANCE_X), state.getValue(DISTANCE_Z)) >= maxJointDistance ? getOutputStateCracked(state) : getOutputState(state));
            }

            if (level.getBlockState(pos) != oldState)
            {
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

    //Distance checks won't care about control joint orientation, because if the cj is oriented wrong, the block would need to
    //ask the cj how far it is from a cj, which isn't a blockstate for cjs and I don't want to quadruple the CJ blockstate count rn
    private int getDistanceX(BlockState neighbor)
    {
        Block block = neighbor.getBlock();
        if (Helpers.isBlock(block, RNRTags.Blocks.CRACKING_WET_CONCRETE_ROADS))
        {
            return neighbor.getValue(DISTANCE_X);
        }
        else
        {
            return 0;
        }
    }

    private int getDistanceZ(BlockState neighbor)
    {
        Block block = neighbor.getBlock();
        if (Helpers.isBlock(block, RNRTags.Blocks.CRACKING_WET_CONCRETE_ROADS))
        {
            return neighbor.getValue(DISTANCE_Z);
        }
        else
        {
            return 0;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        final int distanceX = getDistanceX(facingState) + 1;
        final int distanceZ = getDistanceZ(facingState) + 1;
        if (distanceX != 1 || state.getValue(DISTANCE_X) != distanceX || distanceZ != 1 || state.getValue(DISTANCE_Z) != distanceZ)
        {
            level.scheduleTick(currentPos, this, 1);
        }
        return state;
    }

    private int updateDistanceX(LevelAccessor level, BlockPos pos)
    {
        int distance = 1 + maxJointDistance;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : new Direction[] {Direction.EAST, Direction.WEST})
        {
            mutablePos.set(pos).move(direction);
            distance = Math.min(distance, getDistanceX(level.getBlockState(mutablePos)) + 1);
            if (distance == 1)
            {
                break;
            }
        }
        return distance;
    }

    private int updateDistanceZ(LevelAccessor level, BlockPos pos)
    {
        int distance = 1 + maxJointDistance;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : new Direction[] {Direction.NORTH, Direction.SOUTH})
        {
            mutablePos.set(pos).move(direction);
            distance = Math.min(distance, getDistanceZ(level.getBlockState(mutablePos)) + 1);
            if (distance == 1)
            {
                break;
            }
        }
        return distance;
    }
}
