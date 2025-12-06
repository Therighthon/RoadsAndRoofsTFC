package com.therighthon.rnr.common.item;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;


public class AFCCompatItems
{
    public static final RNRItems.ItemId ITEMS = DeferredRegister.create(Registries.ITEM, RoadsAndRoofs.MOD_ID);

    public static final Map<AFCWood, RNRItems.ItemId> WOOD_SHINGLE = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/shingle/" + wood.getSerializedName()));

    private static RNRItems.ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RNRItems.ItemId register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}

