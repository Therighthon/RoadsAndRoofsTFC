package com.therighthon.rnr.common;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.items.ChiselItem;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;


public class RNRItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RoadsAndRoofs.MOD_ID);

    //Metal Items
    public static final Map<Metal.Default, RegistryObject<Item>> MATTOCK_HEADS = Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasUtilities, metal -> register("metal/mattock_head/" + metal.name()));
    public static final Map<Metal.Default, RegistryObject<Item>> MATTOCKS = Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasUtilities, metal -> register("metal/mattock/" + metal.name(), () -> new MattockItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.27f, metal.toolTier()), -1.5F, Metal.ItemType.properties(metal))));


    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}

