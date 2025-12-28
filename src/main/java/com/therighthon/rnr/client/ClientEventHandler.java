package com.therighthon.rnr.client;

import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.fluids.AFCFluids;
import com.therighthon.rnr.common.fluid.RNRFluids;
import java.util.function.Predicate;
import com.therighthon.rnr.common.block.RNRBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;

import com.therighthon.rnr.client.IngameOverlays;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;

import net.dries007.tfc.client.extensions.FluidRendererExtension;
import net.dries007.tfc.client.render.blockentity.ChestItemRenderer;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.common.blocks.wood.Wood.BlockType.*;

public final class ClientEventHandler
{


    public static void init(IEventBus bus)
    {
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

    public static void registerExtensions(RegisterClientExtensionsEvent event)
    {
        // Fluids
        RNRFluids.SIMPLE_RNR_FLUIDS.forEach((fluid, holder) -> event.registerFluidType(
            new FluidRendererExtension(fluid.isTransparent() ? AFCFluids.ALPHA_MASK | fluid.getColor() : fluid.getColor(), net.dries007.tfc.client.ClientEventHandler.WATER_STILL, net.dries007.tfc.client.ClientEventHandler.WATER_FLOW, net.dries007.tfc.client.ClientEventHandler.WATER_OVERLAY, net.dries007.tfc.client.ClientEventHandler.UNDERWATER_LOCATION),
            holder.getType()
        ));
    }
}
