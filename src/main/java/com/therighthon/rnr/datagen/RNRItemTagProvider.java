package com.therighthon.rnr.datagen;

import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.item.RNRItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;

public class RNRItemTagProvider extends ItemTagsProvider
{
    public RNRItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, blockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        for (Rock rock : Rock.values())
        {
            tag(RNRTags.Items.COBBLE_ROAD_ITEMS).add(TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).asItem()).add(TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).asItem());
            tag(RNRTags.Items.SETT_ROAD_ITEMS).add(TFCItems.BRICKS.get(rock).asItem());
            tag(RNRTags.Items.FLAGSTONE_ROAD_ITEMS).add(RNRItems.FLAGSTONE.get(rock).asItem());
            tag(RNRTags.Items.GRAVEL_ROAD_ITEMS).add(RNRItems.GRAVEL_FILL.get(rock).asItem());
        }
        tag(RNRTags.Items.ROAD_MATERIALS).addTags(RNRTags.Items.COBBLE_ROAD_ITEMS).addTags(RNRTags.Items.SETT_ROAD_ITEMS).addTags(RNRTags.Items.GRAVEL_ROAD_ITEMS);
        tag(RNRTags.Items.BASE_COURSE).add(RNRItems.CRUSHED_BASE_COURSE.get());

        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                tag(RNRTags.Items.TOOLS_MATTOCKS).add(RNRItems.MATTOCKS.get(metal).get().asItem());
            }
        }
    }
}
