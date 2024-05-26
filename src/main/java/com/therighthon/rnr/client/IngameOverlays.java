package com.therighthon.rnr.client;

import java.util.Locale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.therighthon.rnr.common.RNRTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.player.PlayerDataCapability;
import net.dries007.tfc.util.Helpers;

//Mostly copied from TFC's IngameOverlays.java and is under the TFC License
public enum IngameOverlays
{
    MATTOCK(IngameOverlays::renderMattockMode);

    private final String id;
    final IGuiOverlay overlay;

    IngameOverlays(IGuiOverlay overlay)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.overlay = overlay;
    }

    public static final ResourceLocation TEXTURE = Helpers.identifier("textures/gui/icons/overlay.png");

    public static void registerOverlays(RegisterGuiOverlaysEvent event)
    {
        top(event, MATTOCK);
    }

    private static void top(RegisterGuiOverlaysEvent event, IngameOverlays overlay)
    {
        event.registerAboveAll(overlay.id, overlay.overlay);
    }

    private static void renderMattockMode(ForgeGui gui, GuiGraphics graphics, float partialTicks, int width, int height)
    {
        final PoseStack stack = graphics.pose();
        final Minecraft mc = Minecraft.getInstance();
        if (setup(gui, mc))
        {
            final Player player = ClientHelpers.getPlayer();
            if (player != null && Helpers.isItem(player.getItemInHand(InteractionHand.MAIN_HAND), RNRTags.Items.MATTOCKS))
            {
                int u = player.getCapability(PlayerDataCapability.CAPABILITY).map(cap -> cap.getChiselMode().ordinal() * 20).orElse(0);
                stack.pushPose();
                graphics.blit(TEXTURE, width / 2 + 100, height - 21, u, 58, 20, 20);
                stack.popPose();
            }
        }
    }

    public static boolean setup(ForgeGui gui, Minecraft minecraft)
    {
        if (!minecraft.options.hideGui && minecraft.getCameraEntity() instanceof Player)
        {
            gui.setupOverlayRenderState(true, false);
            return true;
        }
        return false;
    }

}
