package com.therighthon.rnr;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import net.dries007.tfc.util.Helpers;

public class ModEvents
{
    public static void initAFCCompat(IEventBus bus)
    {
        bus.addListener(ModEvents::onAFCCompatPackFinder);
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
}
