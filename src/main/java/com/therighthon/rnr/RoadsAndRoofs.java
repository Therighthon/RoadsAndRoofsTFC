package com.therighthon.rnr;

import com.mojang.logging.LogUtils;
import com.therighthon.rnr.client.ClientEventHandler;
import com.therighthon.rnr.common.RNRCreativeModeTabs;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.RNRRecipeSerializers;
import com.therighthon.rnr.common.recipe.RNRRecipeTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.fml.loading.FMLEnvironment;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(RoadsAndRoofs.MOD_ID)
public class RoadsAndRoofs
{
    public static final String MOD_ID = "rnr";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RoadsAndRoofs()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
//        ModEvents.init();

        RNRBlocks.BLOCKS.register(eventBus);
        RNRItems.ITEMS.register(eventBus);
        RNRRecipeTypes.RECIPE_TYPES.register(eventBus);
        RNRRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
        RNRCreativeModeTabs.CREATIVE_TABS.register(eventBus);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init();
        }

        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("ROADS AND ROOFS COMMON SETUP");
    }

}
