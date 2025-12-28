package com.therighthon.rnr;

import com.therighthon.rnr.common.block.RNRBlocks;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;

public class RNREvents
{
    public static void init()
    {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(RNREvents::onUseItemOnBlock);
    }

    public static void initAFCCompat(IEventBus bus)
    {
        bus.addListener(RNREvents::onAFCCompatPackFinder);
    }

    public static void onAFCCompatPackFinder(AddPackFindersEvent event)
    {
        if (event.getPackType() == PackType.CLIENT_RESOURCES)
        {
            final ResourceLocation location = Helpers.resourceLocation(RoadsAndRoofs.MOD_ID, "afc_compat_assets");
            event.addPackFinders(location, PackType.CLIENT_RESOURCES, Component.literal("AFC Comapt Assets"), PackSource.DEFAULT, true, Pack.Position.TOP);
        }
        else if (event.getPackType() == PackType.SERVER_DATA)
        {
            final ResourceLocation location = Helpers.resourceLocation(RoadsAndRoofs.MOD_ID, "afc_compat_data");
            event.addPackFinders(location, PackType.SERVER_DATA, Component.literal("AFC Compat Data"), PackSource.DEFAULT, true, Pack.Position.TOP);
        }
    }

    public static void onUseItemOnBlock(UseItemOnBlockEvent event)
    {
        final Level level = event.getLevel();
        final BlockPos pos = event.getPos();
        RNRHelpers.blockModRecipeCompatible(level.getBlockState(pos), level, pos, event.getPlayer(), event.getHand());
    }

    @SubscribeEvent
    public static void addToBlockEntities(BlockEntityTypeAddBlocksEvent event)
    {
        Stream<Block> blocks = Stream.of(
            RNRBlocks.WET_CONCRETE_ROAD.get(),
            RNRBlocks.WET_CONCRETE_ROAD_PANEL.get(),
            RNRBlocks.WET_CONCRETE_ROAD_FLAGSTONES.get(),
            RNRBlocks.WET_CONCRETE_ROAD_SETT.get(),
            RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get(),
            RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get(),
            RNRBlocks.POURING_CONCRETE_ROAD.get()
        );
        modifyBlockEntityType(TFCBlockEntities.TICK_COUNTER.get(), blocks, event);
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks, BlockEntityTypeAddBlocksEvent event)
    {
        extraBlocks.forEach(
            (block -> event.modify(type, block))
        );
    }

}
