package com.therighthon.rnr.common;

import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

public class RNRTags
{
    public static class Blocks
    {
        public static final TagKey<Block> MINEABLE_WITH_MATTOCK = create("mineable_with_mattock");
        public static final TagKey<Block> CONCRETE_SPREADABLE = create("concrete_spreadable");
        public static final TagKey<Block> DRY_CONCRETE_ROADS = create("dry_concrete_roads");
        public static final TagKey<Block> WET_CONCRETE_ROADS = create("wet_concrete_roads");
        public static final TagKey<Block> CRACKED_CONCRETE_ROADS = create("cracked_concrete_roads");
        public static final TagKey<Block> CONCRETE_CONTROL_JOINTS = create("concrete_control_joints");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(RoadsAndRoofs.MOD_ID, id));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> MATTOCKS = create("mattocks");
        public static final TagKey<Item> BASE_COURSE = create("base_course");
        public static final TagKey<Item> ROAD_MATERIALS = create("road_materials");

        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation(RoadsAndRoofs.MOD_ID, id));
        }
    }
}
