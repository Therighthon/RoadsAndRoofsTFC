package com.therighthon.rnr.common;

import com.therighthon.rnr.RNRHelpers;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RNRTags
{
    public static class Blocks
    {
        public static final TagKey<Block> ALL_ROADS = create("all_roads");
        public static final TagKey<Block> ALL_WORKING_ROADS = create("all_working_roads");
        public static final TagKey<Block> TAMPED_BLOCKS = create("tamped_blocks");

        // Stone Tags
        public static final TagKey<Block> ALL_COBBLED_ROADS = create("all_cobbled_roads");
        public static final TagKey<Block> COBBLED_ROAD_BLOCKS = create("cobbled_road_blocks");
        public static final TagKey<Block> COBBLED_ROAD_SLABS = create("cobbled_road_slabs");
        public static final TagKey<Block> COBBLED_ROAD_STAIRS = create("cobbled_road_stairs");

        public static final TagKey<Block> ALL_FLAGSTONE_ROADS = create("all_flagstone_roads");
        public static final TagKey<Block> FLAGSTONE_ROAD_BLOCKS = create("flagstone_road_blocks");
        public static final TagKey<Block> FLAGSTONE_ROAD_SLABS = create("flagstone_road_slabs");
        public static final TagKey<Block> FLAGSTONE_ROAD_STAIRS = create("flagstone_road_stairs");

        public static final TagKey<Block> ALL_GRAVEL_ROADS = create("all_gravel_roads");
        public static final TagKey<Block> GRAVEL_ROAD_BLOCKS = create("gravel_road_blocks");
        public static final TagKey<Block> GRAVEL_ROAD_SLABS = create("gravel_road_slabs");
        public static final TagKey<Block> GRAVEL_ROAD_STAIRS = create("gravel_road_stairs");

        public static final TagKey<Block> OVER_HEIGHT_GRAVELS = create("all_gravel_roads");

        public static final TagKey<Block> ALL_MACADAM_ROADS = create("all_macadam_roads");
        public static final TagKey<Block> MACADAM_ROAD_BLOCKS = create("macadam_road_blocks");
        public static final TagKey<Block> MACADAM_ROAD_SLABS = create("macadam_road_slabs");
        public static final TagKey<Block> MACADAM_ROAD_STAIRS = create("macadam_road_stairs");

        public static final TagKey<Block> ALL_SETT_ROADS = create("all_sett_roads");
        public static final TagKey<Block> SETT_ROAD_BLOCKS = create("sett_road_blocks");
        public static final TagKey<Block> SETT_ROAD_SLABS = create("sett_road_slabs");
        public static final TagKey<Block> SETT_ROAD_STAIRS = create("sett_road_stairs");

        // Concrete Tags
        public static final TagKey<Block> ALL_DRY_CONCRETE_ROADS = create("all_dry_concrete_roads");
        public static final TagKey<Block> DRY_CONCRETE_ROAD_BLOCKS = create("dry_concrete_road_blocks");
        public static final TagKey<Block> DRY_CONCRETE_ROAD_SLABS = create("dry_concrete_road_slabs");
        public static final TagKey<Block> DRY_CONCRETE_ROAD_STAIRS = create("dry_concrete_road_stairs");

        public static final TagKey<Block> WET_CONCRETE_ROADS = create("wet_concrete_roads");

        public static final TagKey<Block> ALL_WORKING_CONCRETE_ROADS = create("all_working_concrete_roads");
        public static final TagKey<Block> WORKING_CONCRETE_ROAD_BLOCKS = create("working_concrete_road_blocks");
        public static final TagKey<Block> WORKING_CONCRETE_ROAD_SLABS = create("working_concrete_road_slabs");
        public static final TagKey<Block> WORKING_CONCRETE_ROAD_STAIRS = create("working_concrete_road_stairs");

        public static final TagKey<Block> ALL_HOGGIN_ROADS = create("all_hoggin_roads");
        public static final TagKey<Block> HOGGIN_ROAD_BLOCKS = create("hoggin_road_blocks");
        public static final TagKey<Block> HOGGIN_ROAD_SLABS = create("hoggin_road_slabs");
        public static final TagKey<Block> HOGGIN_ROAD_STAIRS = create("hoggin_road_stairs");

        // Soil blocks that can be tamped
        public static final TagKey<Block> TAMPABLE_ENTISOL = create("tampable_entisol");
        public static final TagKey<Block> TAMPABLE_ENTISOL_MUD = create("tampable_entisol_mud");
        public static final TagKey<Block> TAMPABLE_OXISOL = create("tampable_oxisol");
        public static final TagKey<Block> TAMPABLE_OXISOL_MUD = create("tampable_oxisol_mud");
        public static final TagKey<Block> TAMPABLE_PODZOL = create("tampable_podzol");
        public static final TagKey<Block> TAMPABLE_PODZOL_MUD = create("tampable_podzol_mud");
        public static final TagKey<Block> TAMPABLE_ARIDISOL = create("tampable_aridisol");
        public static final TagKey<Block> TAMPABLE_ARIDISOL_MUD = create("tampable_aridisol_mud");
        public static final TagKey<Block> TAMPABLE_ALFISOL = create("tampable_alfisol");
        public static final TagKey<Block> TAMPABLE_ALFISOL_MUD = create("tampable_alfisol_mud");
        public static final TagKey<Block> TAMPABLE_FLUVISOL = create("tampable_fluvisol");
        public static final TagKey<Block> TAMPABLE_FLUVISOL_MUD = create("tampable_fluvisol_mud");
        public static final TagKey<Block> TAMPABLE_ANDISOL = create("tampable_andisol");
        public static final TagKey<Block> TAMPABLE_ANDISOL_MUD = create("tampable_andisol_mud");
        public static final TagKey<Block> TAMPABLE_MOLLISOL = create("tampable_mollisol");
        public static final TagKey<Block> TAMPABLE_MOLLISOL_MUD = create("tampable_mollisol_mud");
//        public static final TagKey<Block> = create("");
//        public static final TagKey<Block> = create("");

        public static final TagKey<Block> MINEABLE_WITH_MATTOCK = create("mineable_with_mattock");
        //Used to check if spread of concrete can replace a block with wet concrete road.
        public static final TagKey<Block> CONCRETE_SPREADABLE = create("concrete_spreadable");
        //Includes both wet and dry control joints
        public static final TagKey<Block> CONCRETE_CONTROL_JOINTS = create("concrete_control_joints");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registries.BLOCK, RNRHelpers.modIdentifier(id));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> MATTOCKS = create("mattocks");
        public static final TagKey<Item> BASE_COURSE = create("base_course");
        public static final TagKey<Item> ROAD_MATERIALS = create("road_materials");
        public static final TagKey<Item> COBBLE_ROAD_ITEMS = create("cobble_road_items");
        public static final TagKey<Item> FLAGSTONE_ROAD_ITEMS = create("flagstone_road_items");
        public static final TagKey<Item> SETT_ROAD_ITEMS = create("sett_road_items");
        public static final TagKey<Item> GRAVEL_ROAD_ITEMS = create("gravel_road_items");
        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registries.ITEM, RNRHelpers.modIdentifier(id));
        }
    }
}
