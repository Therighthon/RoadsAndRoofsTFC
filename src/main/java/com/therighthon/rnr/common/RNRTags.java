package com.therighthon.rnr.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

public class RNRTags
{
    public static class Blocks
    {
        public static final TagKey<Block> MINEABLE_WITH_MATTOCK = create("mineable_with_mattock");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registries.BLOCK, Helpers.identifier(id));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> MATTOCKS = create("mattocks");

        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registries.ITEM, Helpers.identifier(id));
        }
    }
}
