package com.therighthon.rnr.datagen;

import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.block.StoneBlockType;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;


public class RNRBlockTagProvider extends BlockTagsProvider
{
    public RNRBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {


        // Roads
        tag(RNRTags.Blocks.ALL_HOGGIN_ROADS).addTags(RNRTags.Blocks.HOGGIN_ROAD_BLOCKS, RNRTags.Blocks.HOGGIN_ROAD_SLABS, RNRTags.Blocks.HOGGIN_ROAD_STAIRS);
        tag(RNRTags.Blocks.HOGGIN_ROAD_BLOCKS).add(RNRBlocks.HOGGIN.get());
        tag(RNRTags.Blocks.HOGGIN_ROAD_SLABS).add(RNRBlocks.HOGGIN_SLAB.get());
        tag(RNRTags.Blocks.HOGGIN_ROAD_STAIRS).add(RNRBlocks.HOGGIN_STAIRS.get());

        tag(RNRTags.Blocks.ALL_DRY_CONCRETE_ROADS).addTags(RNRTags.Blocks.DRY_CONCRETE_ROAD_BLOCKS, RNRTags.Blocks.DRY_CONCRETE_ROAD_SLABS, RNRTags.Blocks.DRY_CONCRETE_ROAD_STAIRS);
        tag(RNRTags.Blocks.DRY_CONCRETE_ROAD_BLOCKS).add(RNRBlocks.CONCRETE_ROAD.get()).add(RNRBlocks.CONCRETE_ROAD_SETT.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL.get()).add(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get()).add(RNRBlocks.TRODDEN_CONCRETE_ROAD.get()).add(RNRBlocks.CRACKED_CONCRETE_ROAD.get()).add(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD.get());
        tag(RNRTags.Blocks.DRY_CONCRETE_ROAD_SLABS).add(RNRBlocks.CONCRETE_ROAD_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_SETT_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL_SLAB.get()).add(RNRBlocks.TRODDEN_CONCRETE_ROAD_SLAB.get()).add(RNRBlocks.CRACKED_CONCRETE_ROAD_SLAB.get()).add(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_SLAB.get());
        tag(RNRTags.Blocks.DRY_CONCRETE_ROAD_STAIRS).add(RNRBlocks.CONCRETE_ROAD_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_SETT_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL_STAIRS.get()).add(RNRBlocks.TRODDEN_CONCRETE_ROAD_STAIRS.get()).add(RNRBlocks.CRACKED_CONCRETE_ROAD_STAIRS.get()).add(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_STAIRS.get());

        tag(RNRTags.Blocks.ALL_WORKING_CONCRETE_ROADS).addTags(RNRTags.Blocks.WORKING_CONCRETE_ROAD_STAIRS, RNRTags.Blocks.WORKING_CONCRETE_ROAD_SLABS, RNRTags.Blocks.WORKING_CONCRETE_ROAD_STAIRS);
        tag(RNRTags.Blocks.WORKING_CONCRETE_ROAD_BLOCKS).add(RNRBlocks.CONCRETE_ROAD.get()).add(RNRBlocks.CONCRETE_ROAD_SETT.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL.get()).add(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get());
        tag(RNRTags.Blocks.WORKING_CONCRETE_ROAD_SLABS).add(RNRBlocks.CONCRETE_ROAD_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_SETT_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_SLAB.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL_SLAB.get());
        tag(RNRTags.Blocks.WORKING_CONCRETE_ROAD_STAIRS).add(RNRBlocks.CONCRETE_ROAD_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_SETT_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_STAIRS.get()).add(RNRBlocks.CONCRETE_ROAD_PANEL_STAIRS.get());

        tag(RNRTags.Blocks.WET_CONCRETE_ROADS).add(RNRBlocks.WET_CONCRETE_ROAD.get()).add(RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get()).add(RNRBlocks.WET_CONCRETE_ROAD_FLAGSTONES.get()).add(RNRBlocks.WET_CONCRETE_ROAD_SETT.get()).add(RNRBlocks.WET_CONCRETE_ROAD_PANEL.get());
        tag(RNRTags.Blocks.CONCRETE_CONTROL_JOINTS).add(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get()).add(RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get());

        tag(RNRTags.Blocks.ALL_COBBLED_ROADS).addTags(RNRTags.Blocks.COBBLED_ROAD_BLOCKS, RNRTags.Blocks.COBBLED_ROAD_SLABS, RNRTags.Blocks.COBBLED_ROAD_STAIRS);
        addAllRocks(RNRTags.Blocks.COBBLED_ROAD_BLOCKS, RNRTags.Blocks.COBBLED_ROAD_SLABS, RNRTags.Blocks.COBBLED_ROAD_STAIRS, StoneBlockType.COBBLED_ROAD);

        tag(RNRTags.Blocks.ALL_GRAVEL_ROADS).addTags(RNRTags.Blocks.GRAVEL_ROAD_BLOCKS, RNRTags.Blocks.GRAVEL_ROAD_SLABS, RNRTags.Blocks.GRAVEL_ROAD_STAIRS);
        addAllRocks(RNRTags.Blocks.GRAVEL_ROAD_BLOCKS, RNRTags.Blocks.GRAVEL_ROAD_SLABS, RNRTags.Blocks.GRAVEL_ROAD_STAIRS, StoneBlockType.GRAVEL_ROAD);

        tag(RNRTags.Blocks.ALL_MACADAM_ROADS).addTags(RNRTags.Blocks.MACADAM_ROAD_BLOCKS, RNRTags.Blocks.MACADAM_ROAD_SLABS, RNRTags.Blocks.MACADAM_ROAD_STAIRS);
        addAllRocks(RNRTags.Blocks.MACADAM_ROAD_BLOCKS, RNRTags.Blocks.MACADAM_ROAD_SLABS, RNRTags.Blocks.MACADAM_ROAD_STAIRS, StoneBlockType.MACADAM_ROAD);

        tag(RNRTags.Blocks.ALL_SETT_ROADS).addTags(RNRTags.Blocks.SETT_ROAD_BLOCKS, RNRTags.Blocks.SETT_ROAD_SLABS, RNRTags.Blocks.SETT_ROAD_STAIRS);
        addAllRocks(RNRTags.Blocks.SETT_ROAD_BLOCKS, RNRTags.Blocks.SETT_ROAD_SLABS, RNRTags.Blocks.SETT_ROAD_STAIRS, StoneBlockType.SETT_ROAD);

        tag(RNRTags.Blocks.ALL_FLAGSTONE_ROADS).addTags(RNRTags.Blocks.FLAGSTONE_ROAD_BLOCKS, RNRTags.Blocks.FLAGSTONE_ROAD_SLABS, RNRTags.Blocks.FLAGSTONE_ROAD_STAIRS);
        addAllRocks(RNRTags.Blocks.FLAGSTONE_ROAD_BLOCKS, RNRTags.Blocks.FLAGSTONE_ROAD_SLABS, RNRTags.Blocks.FLAGSTONE_ROAD_STAIRS, StoneBlockType.FLAGSTONES);

        addSoilTags(RNRTags.Blocks.TAMPABLE_ENTISOL, RNRTags.Blocks.TAMPABLE_ENTISOL_MUD, SoilBlockType.Variant.ENTISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_OXISOL, RNRTags.Blocks.TAMPABLE_OXISOL_MUD, SoilBlockType.Variant.OXISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_ARIDISOL, RNRTags.Blocks.TAMPABLE_ARIDISOL_MUD, SoilBlockType.Variant.ARIDISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_PODZOL, RNRTags.Blocks.TAMPABLE_PODZOL_MUD, SoilBlockType.Variant.PODZOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_ALFISOL, RNRTags.Blocks.TAMPABLE_ALFISOL_MUD, SoilBlockType.Variant.ALFISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_FLUVISOL, RNRTags.Blocks.TAMPABLE_FLUVISOL_MUD, SoilBlockType.Variant.FLUVISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_ANDISOL, RNRTags.Blocks.TAMPABLE_ANDISOL_MUD, SoilBlockType.Variant.ANDISOL);
        addSoilTags(RNRTags.Blocks.TAMPABLE_MOLLISOL, RNRTags.Blocks.TAMPABLE_MOLLISOL_MUD, SoilBlockType.Variant.MOLLISOL);
    }

    private void addSoilTags(TagKey<Block> dryTag, TagKey<Block> wetTag, SoilBlockType.Variant variant)
    {
        tag(wetTag).add(TFCBlocks.SOIL.get(SoilBlockType.MUD).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.MUDDY_ROOTS).get(variant).get());
        tag(dryTag).add(TFCBlocks.SOIL.get(SoilBlockType.CLAY_DUFF).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.DUFF).get(variant).get())
            .add(TFCBlocks.SOIL.get(SoilBlockType.CLAY).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(variant).get())
            .add(TFCBlocks.SOIL.get(SoilBlockType.CLAY_GRASS).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.COARSE_DIRT).get(variant).get())
            .add(TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(variant).get())
            .add(TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant).get()).add(TFCBlocks.SOIL.get(SoilBlockType.ROOTED_DIRT).get(variant).get());
    }

    private void addAllRocks(TagKey<Block> blockTag, TagKey<Block> slabTag, TagKey<Block> stairTag, StoneBlockType type)
    {
        RNRBlocks.ROCK_BLOCKS.forEach(
            (r, m) -> tag(blockTag).add(m.get(type).key())
        );
        RNRBlocks.ROCK_SLABS.forEach(
            (r, m) -> tag(slabTag).add(m.get(type).key())
        );
        RNRBlocks.ROCK_STAIRS.forEach(
            (r, m) -> tag(stairTag).add(m.get(type).key())
        );
    }



}
