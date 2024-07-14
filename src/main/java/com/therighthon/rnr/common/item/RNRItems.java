package com.therighthon.rnr.common.item;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;

import com.therighthon.rnr.common.item.MattockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;


public class RNRItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RoadsAndRoofs.MOD_ID);

    //Metal Items
    public static final Map<Metal.Default, RegistryObject<Item>> MATTOCK_HEADS = Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasUtilities, metal -> register("metal/mattock_head/" + metal.name()));
    public static final Map<Metal.Default, RegistryObject<Item>> MATTOCKS = Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasUtilities, metal -> register("metal/mattock/" + metal.name(), () -> new MattockItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.27f, metal.toolTier()), -1.5F, Metal.ItemType.properties(metal))));

    //Stone items
    public static final Map<Rock, RegistryObject<Item>> FLAGSTONE = Helpers.mapOfKeys(Rock.class, type ->
        register("flagstone/" + type.name())
    );
    public static final Map<Rock, RegistryObject<Item>> GRAVEL_FILL = Helpers.mapOfKeys(Rock.class, type ->
        register("gravel_fill/" + type.name())
    );
    public static final Map<SandBlockType, RegistryObject<Item>> SANDSTONE_FLAGSTONE = Helpers.mapOfKeys(SandBlockType.class, type ->
        register("flagstone/" + type.name() + "_sandstone")
    );

    public static final RegistryObject<Item> UNFIRED_ROOF_TILE = register("unfired_roof_tile");
    public static final RegistryObject<Item> UNFIRED_TERRACOTTA_ROOF_TILE = register("unfired_terracotta_roof_tile");
    public static final RegistryObject<Item> CERAMIC_ROOF_TILE = register("ceramic_roof_tile");
    public static final RegistryObject<Item> TERRACOTTA_ROOF_TILE = register("terracotta_roof_tile");
    public static final Map<Wood, RegistryObject<Item>> WOOD_SHINGLE = Helpers.mapOfKeys(Wood.class, wood -> register("wood/shingle/" + wood.getSerializedName()));


    //Misc items
    public static final RegistryObject<Item> CRUSHED_BASE_COURSE = register("crushed_base_course");
    public static final RegistryObject<Item> HOGGIN_MIX = register("hoggin_mix");

    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}

