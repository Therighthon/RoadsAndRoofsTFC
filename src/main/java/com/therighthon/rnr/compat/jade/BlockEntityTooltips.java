package com.therighthon.rnr.compat.jade;

import java.util.function.Consumer;
import com.therighthon.rnr.common.block.WetConcretePathBlock;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.blocks.devices.DryingBricksBlock;
import net.dries007.tfc.compat.jade.common.BlockEntityTooltip;
import net.dries007.tfc.compat.jade.common.RegisterCallback;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.calendar.Calendars;

public class BlockEntityTooltips
{
    public static void register(RegisterCallback<BlockEntityTooltip, Block> callback)
    {
        callback.register("wet_concrete", WET_CONCRETE, WetConcretePathBlock.class);
    }

    public static final BlockEntityTooltip WET_CONCRETE = (level, state, pos, entity, tooltip) -> {
        if (entity instanceof TickCounterBlockEntity counter && state.getBlock() instanceof WetConcretePathBlock)
        {
            //TODO: Lang
            timeLeft(level, tooltip, TFCConfig.SERVER.mudBricksTicks.get() - counter.getTicksSinceUpdate(), Component.translatable("rnr.jade.wet_concrete"));
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
}
