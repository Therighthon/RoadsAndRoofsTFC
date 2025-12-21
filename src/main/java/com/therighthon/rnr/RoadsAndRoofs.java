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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RoadsAndRoofs.MOD_ID)
public class RoadsAndRoofs
{
    public static final String MOD_ID = "rnr";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RoadsAndRoofs(ModContainer modContainer, IEventBus modEventBus)
    {
        modEventBus.addListener(this::setup);

        RNRBlocks.BLOCKS.register(modEventBus);
        RNRItems.ITEMS.register(modEventBus);
        RNRFluids.FLUID_TYPES.register(modEventBus);
        RNRFluids.FLUIDS.register(modEventBus);
        RNRRecipeTypes.RECIPE_TYPES.register(modEventBus);
        RNRRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        RNRCreativeModeTabs.CREATIVE_TABS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEventHandler.init(modEventBus);
        }

        if (ModList.get().isLoaded("afc"))
        {
            AFCCompatBlocks.BLOCKS.register(modEventBus);
            AFCCompatItems.AFC_COMPAT_ITEMS.register(modEventBus);
            ModEvents.initAFCCompat(modEventBus);
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("ROADS AND ROOFS COMMON SETUP");
    }

}
