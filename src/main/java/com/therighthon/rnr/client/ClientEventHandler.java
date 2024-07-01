package com.therighthon.rnr.client;

import java.util.function.Predicate;
import com.therighthon.rnr.common.block.RNRBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.therighthon.rnr.client.IngameOverlays;

public final class ClientEventHandler
{
    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ClientEventHandler::clientSetup);
        bus.addListener(IngameOverlays::registerOverlays);
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        ItemBlockRenderTypes.setRenderLayer(RNRBlocks.ROOF_FRAME.get(), cutoutMipped);
        ItemBlockRenderTypes.setRenderLayer(RNRBlocks.ROOF_FRAME_SLAB.get(), cutoutMipped);
        ItemBlockRenderTypes.setRenderLayer(RNRBlocks.ROOF_FRAME_STAIRS.get(), cutoutMipped);
    }
}
