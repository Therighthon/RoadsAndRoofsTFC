package com.therighthon.rnr.client;

import java.util.Locale;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.dries007.tfc.util.Helpers;

// Mostly copied from TFC's IngameOverlays.java and is under the TFC License
public enum IngameOverlays
{
    MATTOCK(IngameOverlays::renderMattockMode);

    private final ResourceLocation id;
    final LayeredDraw.Layer overlay;

    IngameOverlays(LayeredDraw.Layer overlay)
    {
        this.id = Helpers.resourceLocation(name().toLowerCase(Locale.ROOT));
        this.overlay = overlay;
    }

    public static final ResourceLocation TEXTURE = Helpers.identifier("textures/gui/icons/overlay.png");

    public static void registerOverlays(RegisterGuiLayersEvent event)
    {
        top(event, MATTOCK);
    }

    private static void top(RegisterGuiLayersEvent event, IngameOverlays overlay)
    {
        event.registerAboveAll(overlay.id, overlay.overlay);
    }

    private static void renderMattockMode(GuiGraphics graphics, DeltaTracker delta)
    {
        final PoseStack stack = graphics.pose();
        final Minecraft mc = Minecraft.getInstance();
        if (setup(graphics, mc))
        {
            final Player player = ClientHelpers.getPlayer();
            if (player != null && Helpers.isItem(player.getItemInHand(InteractionHand.MAIN_HAND), TFCTags.Items.TOOLS_CHISEL))
            {
                stack.pushPose();
                if (!Helpers.isItem(player.getItemInHand(InteractionHand.OFF_HAND), TFCTags.Items.TOOLS_HAMMER))
                {
                    graphics.blit(TEXTURE, graphics.guiWidth() / 2 + 100, graphics.guiHeight() - 21, 60, 58, 20, 20);
                }
                else
                {
                    // Delegate to the chisel mode
                    IPlayerInfo.get(player).chiselMode().createHotbarIcon((texture, u, v) -> graphics.blit(texture, graphics.guiWidth() / 2 + 100, graphics.guiHeight() - 21, u, v, 20, 20));
                }
                stack.popPose();
            }
        }
    }

    public static boolean setup(GuiGraphics gui, Minecraft minecraft)
    {
        if (!minecraft.options.hideGui && minecraft.getCameraEntity() instanceof Player)
        {
            return true;
        }
        return false;
    }

}
