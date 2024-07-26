package com.therighthon.rnr.common.block;

import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;

//TODO: Now that this doesn't have spreading, it could probably just extend the ConcretePathControlJointBlock
public class WetConcretePathControlJointBlock extends PathHeightDeviceBlock
{
    private static final float defaultSpeedFactor = 0.7f;
    private final int ticksToDry = 24000;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty CONNECT_NORTH_OR_EAST = RNRBlockStateProperties.NORTH_EAST;
    public static final BooleanProperty CONNECT_SOUTH_OR_WEST = RNRBlockStateProperties.SOUTH_WEST;
    private final Block base;
    private final BlockState baseState;

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BaseCourseHeightBlock.SHAPE;
    }

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public WetConcretePathControlJointBlock(ExtendedProperties properties)
    {
        super(properties.speedFactor(defaultSpeedFactor).randomTicks(), InventoryRemoveBehavior.NOOP);
        //TODO: maybe remove?
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(CONNECT_NORTH_OR_EAST, false).setValue(CONNECT_SOUTH_OR_WEST, false));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS).add(CONNECT_NORTH_OR_EAST).add(CONNECT_SOUTH_OR_WEST);
    }

    //Based on minecraft pressure plates
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

        //Drying
        level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
            if (counter.getTicksSinceUpdate() > ticksToDry)
            {
                level.setBlockAndUpdate(pos, getOutputState(state));
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
        if (input.is(RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get()))
        {
            return RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get().withPropertiesOf(input);
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

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos northPos = blockPos.north();
        BlockPos eastPos = blockPos.east();
        BlockPos southPos = blockPos.south();
        BlockPos westPos = blockPos.west();
        BlockState northState = blockgetter.getBlockState(northPos);
        BlockState eastState = blockgetter.getBlockState(eastPos);
        BlockState southState = blockgetter.getBlockState(southPos);
        BlockState westState = blockgetter.getBlockState(westPos);
        Direction.Axis axis = context.getHorizontalDirection().getAxis();

        BlockState state = defaultBlockState().setValue(AXIS, axis);
        state = updateControlJointShape(state, Direction.NORTH, northState);
        state = updateControlJointShape(state, Direction.EAST, eastState);
        state = updateControlJointShape(state, Direction.SOUTH, southState);
        state = updateControlJointShape(state, Direction.WEST, westState);

        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return updateControlJointShape(state, direction, neighborState);
    }

    public BlockState updateControlJointShape(BlockState state, Direction direction, BlockState neighborState)
    {
        if (state.getValue(AXIS) == Direction.Axis.X)
        {
            return direction == Direction.NORTH
                ? state.setValue(CONNECT_NORTH_OR_EAST, neighborState.is(RNRTags.Blocks.CONCRETE_CONTROL_JOINTS) && neighborState.getValue(AXIS) != state.getValue(AXIS))
                : direction == Direction.SOUTH
                ? state.setValue(CONNECT_SOUTH_OR_WEST, neighborState.is(RNRTags.Blocks.CONCRETE_CONTROL_JOINTS) && neighborState.getValue(AXIS) != state.getValue(AXIS))
                : state;

        }
        else if (state.getValue(AXIS) == Direction.Axis.Z)
        {
            return direction == Direction.EAST
                ? state.setValue(CONNECT_NORTH_OR_EAST, neighborState.is(RNRTags.Blocks.CONCRETE_CONTROL_JOINTS) && neighborState.getValue(AXIS) != state.getValue(AXIS))
                : direction == Direction.WEST
                ? state.setValue(CONNECT_SOUTH_OR_WEST, neighborState.is(RNRTags.Blocks.CONCRETE_CONTROL_JOINTS) && neighborState.getValue(AXIS) != state.getValue(AXIS))
                : state;
        }
        else
        {
            return state;
        }
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            this.baseState.onRemove(level, pos, newState, isMoving);
        }
    }

}
