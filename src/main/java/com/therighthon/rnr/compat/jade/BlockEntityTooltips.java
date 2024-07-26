package com.therighthon.rnr.compat.jade;

import java.util.function.Consumer;
import com.therighthon.rnr.common.block.CrackingWetConcretePathBlock;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.block.WetConcretePathBlock;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.compat.jade.common.BlockEntityTooltip;
import net.dries007.tfc.compat.jade.common.RegisterCallback;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;

public class BlockEntityTooltips
{
    public static void register(RegisterCallback<BlockEntityTooltip, Block> callback)
    {
        callback.register("wet_concrete", WET_CONCRETE, CrackingWetConcretePathBlock.class);
    }

    public static final BlockEntityTooltip WET_CONCRETE = (level, state, pos, entity, tooltip) -> {
        if (entity instanceof TickCounterBlockEntity counter && state.getBlock() instanceof CrackingWetConcretePathBlock)
        {
            //TODO: Dry time config
            timeLeft(level, tooltip, 24000 - counter.getTicksSinceUpdate(), Component.translatable("rnr.jade.wet_concrete"));
            willCrack(level, tooltip, Math.max(state.getValue(CrackingWetConcretePathBlock.DISTANCE_X), state.getValue(CrackingWetConcretePathBlock.DISTANCE_Z)));
        }
    };

    public static void timeLeft(Level level, Consumer<Component> tooltip, long ticks, @Nullable Component ifNegative)
    {
        if (ticks > 0)
        {
            tooltip.accept(Component.translatable("tfc.jade.time_left", Calendars.get(level).getTimeDelta(ticks)));
        }
        else if (ifNegative != null)
        {
            tooltip.accept(ifNegative);
        }
    }

    public static void willCrack(Level level, Consumer<Component> tooltip, int distance)
    {
        if (distance > 2)
        {
            tooltip.accept(Component.translatable("rnr.jade.will_crack"));
        }
        else
        {
            tooltip.accept(Component.translatable("rnr.jade.will_not_crack"));
        }
    }


}
