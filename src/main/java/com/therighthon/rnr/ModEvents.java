package com.therighthon.rnr;

import java.io.IOException;
import java.nio.file.Path;
import com.therighthon.afc.AFC;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.resource.PathPackResources;

public class ModEvents
{
    public static void initAFCCompat()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ModEvents::onAFCCompatPackFinder);
        bus.addListener(ModEvents::onAFCCompatDataPackFinder);
    }

    public static void onAFCCompatPackFinder(AddPackFindersEvent event)
    {
        try
        {
            if (event.getPackType() == PackType.CLIENT_RESOURCES)
            {
                final Path resourcePath = ModList.get().getModFileById(RoadsAndRoofs.MOD_ID).getFile().findResource("afc_compat_assets");
                try (PathPackResources pack = new PathPackResources("afc_compat_assets", true, resourcePath))
                {
                    final PackMetadataSection metadata = pack.getMetadataSection(PackMetadataSection.TYPE);
                    if (metadata != null)
                    {
                        RoadsAndRoofs.LOGGER.info("Adding AFC compatibility resource pack");
                        event.addRepositorySource(consumer ->
                            consumer.accept(Pack.readMetaAndCreate("afc_compat_assets", Component.literal("AFC Compat Assets"), true, id -> pack, PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN))
                        );
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void onAFCCompatDataPackFinder(AddPackFindersEvent event)
    {
        try
        {
            if (event.getPackType() == PackType.SERVER_DATA)
            {
                final Path resourcePath = ModList.get().getModFileById(RoadsAndRoofs.MOD_ID).getFile().findResource("afc_compat_data");
                try (PathPackResources pack = new PathPackResources("afc_compat_data", true, resourcePath))
                {
                    final PackMetadataSection metadata = pack.getMetadataSection(PackMetadataSection.TYPE);
                    if (metadata != null)
                    {
                        RoadsAndRoofs.LOGGER.info("Adding AFC compatibility data pack");
                        event.addRepositorySource(consumer ->
                            consumer.accept(Pack.readMetaAndCreate("afc_compat_data", Component.literal("AFC Compat Data"), true, id -> pack, PackType.SERVER_DATA, Pack.Position.TOP, PackSource.BUILT_IN))
                        );
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
