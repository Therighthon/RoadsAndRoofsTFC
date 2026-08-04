package com.therighthon.rnr;

import com.therighthon.rnr.common.RNRTags;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    public static void onUseItemOnBlock(PlayerInteractEvent.RightClickBlock event)
    {
        final Level level = event.getLevel();
        final BlockPos pos = event.getPos();
        if (event.getEntity() instanceof Player)
        {
            if (event.getItemStack().is(RNRTags.Items.MATTOCKS))
            {
                // TODO: So, this is kind of egregious, but also I couldn't figure out why my timer block entity wasn't getting set right when I did this in the mattock item
                RNRHelpers.useMattockOn(event.getEntity(), level, pos, event.getHitVec());
            }
            else
            {
                RNRHelpers.blockModRecipeCompatible(level.getBlockState(pos), level, pos, Objects.requireNonNull(event.getEntity()), event.getHand(), event.getHitVec());
            }
        }
    }
}
