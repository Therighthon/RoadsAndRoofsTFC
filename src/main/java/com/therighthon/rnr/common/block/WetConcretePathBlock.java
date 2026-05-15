package com.therighthon.rnr.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;

public class WetConcretePathBlock extends PathHeightDeviceBlock
{
    public static final int TICKS_TO_DRY = 24000;

    private static final float defaultSpeedFactor = 0.7f;

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return BaseCourseHeightBlock.SHAPE;
    }

    public WetConcretePathBlock(ExtendedProperties properties)
    {
        super(properties.speedFactor(defaultSpeedFactor).randomTicks(), InventoryRemoveBehavior.NOOP);
    }

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        TickCounterBlockEntity.reset(level, pos);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!level.isClientSide)
        {
            if (entity instanceof LivingEntity)
            {
                level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
                    final long oldLastUpdateTick = counter.getLastUpdateTick();
                    level.setBlock(pos, RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get().withPropertiesOf(state), 3);
                    level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(newCounter -> {
                        newCounter.setLastUpdateTick(oldLastUpdateTick);
                    });
                });
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        tick(state, level, pos, rand);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        //Drying
        level.getBlockEntity(pos, TFCBlockEntities.TICK_COUNTER.get()).ifPresent(counter -> {
            if (counter.getTicksSinceUpdate() > TICKS_TO_DRY)
            {
                level.setBlockAndUpdate(pos, getOutputState(state));

                final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
                for (Direction d : Direction.Plane.HORIZONTAL)
                {
                    cursor.setWithOffset(pos, d);
                    final BlockState stateAt = level.getBlockState(cursor);
                    if (stateAt.getBlock() instanceof WetConcretePathBlock)
                    {
                        level.scheduleTick(cursor, stateAt.getBlock(), 20);
                    }
                }
            }
        });
    }

    public BlockState getOutputState(BlockState input)
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
            return RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get().withPropertiesOf(input);
        }
        else if (input.is(RNRBlocks.WET_CONCRETE_ROAD_PANEL.get()))
        {
            return RNRBlocks.CONCRETE_ROAD_PANEL.get().withPropertiesOf(input);
        }
        else if (input.is(RNRBlocks.WET_CONCRETE_ROAD_SETT.get()))
        {
            return RNRBlocks.CONCRETE_ROAD_SETT.get().withPropertiesOf(input);
        }
        else if (input.is(RNRBlocks.WET_CONCRETE_ROAD_FLAGSTONES.get()))
        {
            return RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get().withPropertiesOf(input);
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
}
