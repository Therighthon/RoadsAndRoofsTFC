package com.therighthon.rnr.common.block;

import com.therighthon.rnr.common.RNRTags;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.Helpers;

public class CrackingWetConcretePathBlock extends PathHeightDeviceBlock
{
    private static final float defaultSpeedFactor = 0.7f;
    private final int maxJointDistance = 3;
    private final int ticksToDry = 24000;


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

    public CrackingWetConcretePathBlock(ExtendedProperties properties)
    {
        super(properties.speedFactor(defaultSpeedFactor).randomTicks(), InventoryRemoveBehavior.NOOP);
        //TODO: maybe remove?
        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE_X, 1).setValue(DISTANCE_Z, 1));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE_X).add(DISTANCE_Z);
    }

    //Based on minecraft pressure plates
    //TODO: doesn't need to run for already trodden blocks
    //TODO: Would be swell if it didn't reset the dry timer
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide) {
            if (entity instanceof LivingEntity)
            {
                level.setBlock(pos, RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get().withPropertiesOf(state), 3);
            }
        }
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
//        level.scheduleTick(pos, this, 10);
        TickCounterBlockEntity.reset(level, pos);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
       //Cracking X - Distance Update
        final int oldDistanceX = state.getValue(DISTANCE_X);
        int distanceX = updateDistanceX(level, pos);
        if (distanceX != oldDistanceX)
        {
            level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
                long oldUpdateTick = counter.getLastUpdateTick();
                //Only update crack info within first 75% of drying process
                if (counter.getTicksSinceUpdate() < 0.75 * ticksToDry)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_X, Math.min(distanceX, maxJointDistance)));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            });
        }

        //Cracking Z - Distance Update
        final int oldDistanceZ = state.getValue(DISTANCE_Z);
        int distanceZ = updateDistanceZ(level, pos);
        if (distanceZ != oldDistanceZ)
        {
            level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
                long oldUpdateTick = counter.getLastUpdateTick();
                //Only update crack info within first 75% of drying process
                if (counter.getTicksSinceUpdate() < 0.75 * ticksToDry)
                {
                    level.setBlockAndUpdate(pos, state.setValue(DISTANCE_Z, Math.min(distanceZ, maxJointDistance)));
                    counter.setLastUpdateTick(oldUpdateTick);
                }
            });
        }

        //Drying
        level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
            if (counter.getTicksSinceUpdate() > ticksToDry)
            {
                level.setBlockAndUpdate(pos, Math.max(state.getValue(DISTANCE_X), state.getValue(DISTANCE_Z)) >= maxJointDistance ? getOutputStateCracked(state) : getOutputState(state));
            }

            final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                cursor.setWithOffset(pos, d);
                final BlockState stateAt = level.getBlockState(cursor);
                //TODO: Could be cleaner if this class and the normal wet concrete class extended a single class
                if (state.getBlock() instanceof CrackingWetConcretePathBlock || stateAt.getBlock() instanceof WetConcretePathControlJointBlock)
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



    //Needed for drying
    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    //Distance checks won't care about control joint orientation, because if the cj is oriented wrong, the block would need to
    //ask the cj how far it is from a cj, which isn't a blockstate for cjs and I don't want to quadruple the CJ blockstate count rn
    private int getDistanceX(BlockState neighbor)
    {
        Block block = neighbor.getBlock();
        if (Helpers.isBlock(block, RNRTags.Blocks.WET_CONCRETE_ROADS) && !Helpers.isBlock(neighbor.getBlock(), RNRTags.Blocks.CONCRETE_CONTROL_JOINTS))
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
        if (Helpers.isBlock(block, RNRTags.Blocks.WET_CONCRETE_ROADS) && !Helpers.isBlock(neighbor.getBlock(), RNRTags.Blocks.CONCRETE_CONTROL_JOINTS))
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
