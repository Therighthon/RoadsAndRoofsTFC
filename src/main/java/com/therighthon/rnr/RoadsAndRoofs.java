package com.therighthon.rnr;

import com.mojang.logging.LogUtils;
import com.therighthon.rnr.client.ClientEventHandler;
import com.therighthon.rnr.common.RNRCreativeModeTabs;
import com.therighthon.rnr.common.block.AFCCompatBlocks;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.fluid.RNRFluids;
import com.therighthon.rnr.common.item.AFCCompatItems;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.RNRRecipeSerializers;
import com.therighthon.rnr.common.recipe.RNRRecipeTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(RoadsAndRoofs.MOD_ID)
public class RoadsAndRoofs
{
    public static final String MOD_ID = "rnr";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final boolean BLOCK_RUNNER_LOADED = ModList.get().isLoaded("blockrunner");
    public static final float DAMAGED_PATH_SPEED = BLOCK_RUNNER_LOADED ? 1.0f : 0.9f;
    public static final float SLOW_PATH_SPEED = BLOCK_RUNNER_LOADED ? 1.0f : 1.08f;
    public static final float NORMAL_PATH_SPEED = BLOCK_RUNNER_LOADED ? 1.0f : 1.15f;
    public static final float FAST_PATH_SPEED = BLOCK_RUNNER_LOADED ? 1.0f : 1.2f;


    public RoadsAndRoofs(ModContainer modContainer, IEventBus eventBus)
    {
        eventBus.addListener(this::setup);

        RNRBlocks.BLOCKS.register(eventBus);
        RNRItems.ITEMS.register(eventBus);
        RNRFluids.FLUIDS.register(eventBus);
        RNRRecipeTypes.RECIPE_TYPES.register(eventBus);
        RNRRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
        RNRCreativeModeTabs.CREATIVE_TABS.register(eventBus);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init();
        }

        if (ModList.get().isLoaded("afc"))
        {
            AFCCompatBlocks.BLOCKS.register(eventBus);
            AFCCompatItems.ITEMS.register(eventBus);
            ModEvents.initAFCCompat();
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
