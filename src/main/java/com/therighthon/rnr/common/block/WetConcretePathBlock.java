package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.compat.jade.BlockEntityTooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.Helpers;

public class WetConcretePathBlock extends PathHeightDeviceBlock
{
    private static final float defaultSpeedFactor = 0.7f;
    private final int maxJointDistance = 3;
    private final int ticksToDry = 24000;

    public static final IntegerProperty CONCRETE_LEVEL = RNRBlockStateProperties.CONCRETE_LEVEL;
    public static final IntegerProperty DISTANCE_X = RNRBlockStateProperties.DISTANCE_X;
    public static final IntegerProperty DISTANCE_Z = RNRBlockStateProperties.DISTANCE_Z;
    private final Block base;
    private final BlockState baseState;

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BaseCourseHeightBlock.SHAPE;
    }

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public WetConcretePathBlock(ExtendedProperties properties)
    {
        super(properties.speedFactor(defaultSpeedFactor).randomTicks(), InventoryRemoveBehavior.NOOP);
        //TODO: maybe remove?
        this.registerDefaultState(this.defaultBlockState().setValue(CONCRETE_LEVEL, 0).setValue(DISTANCE_X, 1).setValue(DISTANCE_Z, 1));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CONCRETE_LEVEL).add(DISTANCE_X).add(DISTANCE_Z);
    }

    //Based on minecraft pressure plates
    //TODO: doesn't need to run for already trodden blocks
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide) {
            if (entity instanceof LivingEntity)
            {
                level.setBlock(pos, RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get().withPropertiesOf(state), 3);
            }
        }
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 10);
        TickCounterBlockEntity.reset(level, pos);
    }

    //TODO: Pretty janky setup, but it does work for now
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //Spreading
        int concreteLevel = state.getValue(CONCRETE_LEVEL);
        if (concreteLevel > 0)
        {
            spreadFluid(level, pos.north(), concreteLevel);
            spreadFluid(level, pos.west(), concreteLevel);
            spreadFluid(level, pos.east(), concreteLevel);
            spreadFluid(level, pos.south(), concreteLevel);
            level.setBlock(pos, RNRBlocks.WET_CONCRETE_ROAD.get().defaultBlockState().setValue(CONCRETE_LEVEL, 0), 3);
        }

        //Cracking - Distance Update
        final int oldDistanceX = state.getValue(DISTANCE_X);
        int distance = updateDistance(level, pos);
        if (distance != oldDistanceX)
        {
            level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
                long oldUpdateTick = counter.getLastUpdateTick();
                //Only update crack info within first 75% of drying process
                if (counter.getTicksSinceUpdate() < 0.75 * ticksToDry)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_X, Math.min(distance, maxJointDistance)));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            });
        }


        //Drying
        level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
            if (counter.getTicksSinceUpdate() > ticksToDry)
            {
                level.setBlockAndUpdate(pos, state.getValue(DISTANCE_X) >= maxJointDistance ? getOutputStateCracked(state) : getOutputState(state));
            }

            final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                cursor.setWithOffset(pos, d);
                final BlockState stateAt = level.getBlockState(cursor);
                if (stateAt.getBlock() instanceof WetConcretePathBlock)
                {
                    level.scheduleTick(cursor, stateAt.getBlock(), 1);
                }
            }
        });
    }

    //TODO: Make this use actual recipes rather than hard-code?
    private BlockState getOutputState(BlockState input)
    {
        if (input.is(RNRBlocks.WET_CONCRETE_ROAD.get()))
        {
            return RNRBlocks.CONCRETE_ROAD.get().defaultBlockState();
        }
        else if (input.is(RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get()))
        {
            return RNRBlocks.TRODDEN_CONCRETE_ROAD.get().defaultBlockState();
        }
        else if (input.is(RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get()))
        {
            //TODO: Control joints should be separate blocks as need to be orientable, and don't need 64 states to determine dist from a control joint
            return RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get().defaultBlockState();
        }
        else
        {
            return Blocks.AIR.defaultBlockState();
        }
    }

    //TODO: Make this use actual recipes rather than hard-code?
    private BlockState getOutputStateCracked(BlockState input)
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

    private void spreadFluid(Level level, BlockPos spreadPos, int sourceLevel)
    {
        if(level.getBlockState(spreadPos).is(RNRTags.Blocks.CONCRETE_SPREADABLE))
        {
            level.setBlock(spreadPos, RNRBlocks.WET_CONCRETE_ROAD.get().defaultBlockState().setValue(CONCRETE_LEVEL, sourceLevel - 1), 3);
        }
    }

    //Needed for drying
    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    private int getDistanceX(BlockState neighbor)
    {
        Block block = neighbor.getBlock();
        if (Helpers.isBlock(block, RNRTags.Blocks.WET_CONCRETE_ROADS) && !Helpers.isBlock(neighbor.getBlock(), RNRTags.Blocks.CONCRETE_CONTROL_JOINTS))
        {
            // Check against this leaf block only, not any leaves
            return neighbor.getValue(DISTANCE_X);
        }
        else if (Helpers.isBlock(block, RNRTags.Blocks.CRACKED_CONCRETE_ROADS))
        {
            return 3;
        }
        else if (Helpers.isBlock(block, RNRTags.Blocks.DRY_CONCRETE_ROADS))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }


    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        final int distance = getDistanceX(facingState) + 1;
        if (distance != 1 || state.getValue(DISTANCE_X) != distance)
        {
            level.scheduleTick(currentPos, this, 1);
        }
        return state;
    }

    //TODO: Directional
    private int updateDistance(LevelAccessor level, BlockPos pos)
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
}
