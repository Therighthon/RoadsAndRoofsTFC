package com.therighthon.rnr.common.item;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.util.Helpers;


public class AFCCompatItems
{
    public static final DeferredRegister<Item> AFC_COMPAT_ITEMS = DeferredRegister.create(Registries.ITEM, RoadsAndRoofs.MOD_ID);

    public static final Map<AFCWood, RNRItems.ItemId> WOOD_SHINGLE = Helpers.mapOf(AFCWood.class, wood -> register("wood/shingle/" + wood.getSerializedName()));

    private static RNRItems.ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RNRItems.ItemId register(String name, Supplier<T> item)
    {
        return new RNRItems.ItemId(AFC_COMPAT_ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }
}

