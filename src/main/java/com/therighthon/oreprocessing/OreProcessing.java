package com.therighthon.oreprocessing;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.fml.loading.FMLEnvironment;

import net.dries007.tfc.common.recipes.TFCRecipeSerializers;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(OreProcessing.MOD_ID)
public class OreProcessing
{
    public static final String MOD_ID = "oreprocessing";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public OreProcessing()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
//        ModEvents.init();

//        OPBlocks.BLOCKS.register(eventBus);
//        OPITEMS.register(eventBus);
//        OPFluids.FLUIDS.register(eventBus);
//        OPEntities.ENTITIES.register(eventBus);
//        OPFeatures.FEATURES.register(eventBus);
//        OPBlockEntities.BLOCK_ENTITIES.register(eventBus);
//        OPRecipeTypes.RECIPE_TYPES.register(eventBus);
        TFCRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
//        OPRecipes.register(eventBus);
//        OPCreativeModeTabs.CREATIVE_TABS.register(eventBus);

        if (ModList.get().isLoaded("firmalife"))
        {
//            FLCompatBlocks.BLOCKS.register(eventBus);
//            ModEvents.initFLCompat();
        }
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::clientSetup);
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::registerClientReloadListeners);
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::onEntityRenderers);
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::registerColorHandlerBlocks);
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::registerColorHandlerItems);
//            eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::onLayers);
//            if (ModList.get().isLoaded("firmalife"))
//            {
//                eventBus.addListener(com.therighthon.oreprocessing.event.ModEventClientBusEvents::clientFLCompatSetup);
//            }
        }

        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("ORE PROCESSING COMMON SETUP");
    }

}
