package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.RNRTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.blocks.ExtendedProperties;

public class ConcretePathControlJointBlock extends PathHeightBlock
{
    private static final float defaultSpeedFactor = 1.3f;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty CONNECT_NORTH_OR_EAST = RNRBlockStateProperties.NORTH_EAST;
    public static final BooleanProperty CONNECT_SOUTH_OR_WEST = RNRBlockStateProperties.SOUTH_WEST;
    private final Block base;
    private final BlockState baseState;

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public ConcretePathControlJointBlock(Properties properties, float speedFactor)
    {
        super(properties.speedFactor(speedFactor));
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(CONNECT_NORTH_OR_EAST, false).setValue(CONNECT_SOUTH_OR_WEST, false));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS).add(CONNECT_NORTH_OR_EAST).add(CONNECT_SOUTH_OR_WEST);
    }

    public ConcretePathControlJointBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(defaultSpeedFactor));
        this.base = Blocks.AIR; // These are unused, fields are redirected
        this.baseState = Blocks.AIR.defaultBlockState();
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

//    Commenting these out because once a concrete path block has set, the joint shouldn't really update appearance
//    public void onPlace(BlockState pState, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
//        if (!pState.is(pState.getBlock())) {
//            level.neighborChanged(this.baseState, pos, Blocks.AIR, pos, false);
//            this.base.onPlace(this.baseState, level, pos, oldState, false);
//        }
//    }
//
//    public void onRemove(BlockState pState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
//        if (!pState.is(newState.getBlock())) {
//            this.baseState.onRemove(level, pos, newState, isMoving);
//        }
//    }

    //TODO: Add configurable speed factors

}
