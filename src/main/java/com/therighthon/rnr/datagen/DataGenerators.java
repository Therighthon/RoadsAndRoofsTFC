package com.therighthon.rnr.datagen;

import com.therighthon.rnr.RoadsAndRoofs;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = RoadsAndRoofs.MOD_ID)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Tags
        BlockTagsProvider blockTagsProvider = new RNRBlockTagProvider(packOutput, lookupProvider, RoadsAndRoofs.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new RNRItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), RoadsAndRoofs.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new RNRFluidTagProvider(packOutput, lookupProvider, RoadsAndRoofs.MOD_ID, existingFileHelper));

        // Recipes
        generator.addProvider(event.includeServer(), new RNRRecipeProvider(packOutput, lookupProvider));

        // Heats
        generator.addProvider(event.includeServer(), new RNRHeatDefinitionProvider(packOutput, lookupProvider));

    }
}

