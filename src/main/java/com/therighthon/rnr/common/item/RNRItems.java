package com.therighthon.rnr.common.item;

import com.therighthon.rnr.common.fluid.RNRFluidId;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.dries007.tfc.util.registry.RegistryMetal;

public class RNRItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, RoadsAndRoofs.MOD_ID);

    //Metal Items
    public static final Map<Metal, ItemId> MATTOCK_HEADS = Helpers.mapOf(Metal.class, Metal::allParts, metal -> register("metal/mattock_head/" + metal.name()));
    public static final Map<Metal, ItemId> MATTOCKS = Helpers.mapOf(Metal.class, Metal::allParts, metal -> register("metal/mattock/" + metal.name(), () -> new MattockItem(metal.toolTier(), tool(metal, 0.8f, -3.0f))));

    //Stone items
    public static final Map<Rock, ItemId> FLAGSTONE = Helpers.mapOf(Rock.class, type ->
        register("flagstone/" + type.name())
    );
    public static final Map<Rock, ItemId> GRAVEL_FILL = Helpers.mapOf(Rock.class, type ->
        register("gravel_fill/" + type.name())
    );
    public static final Map<SandBlockType, ItemId> SANDSTONE_FLAGSTONE = Helpers.mapOf(SandBlockType.class, type ->
        register("flagstone/" + type.name() + "_sandstone")
    );

    public static final ItemId UNFIRED_ROOF_TILE = register("unfired_roof_tile");
    public static final ItemId UNFIRED_TERRACOTTA_ROOF_TILE = register("unfired_terracotta_roof_tile");
    public static final ItemId CERAMIC_ROOF_TILE = register("ceramic_roof_tile");
    public static final ItemId TERRACOTTA_ROOF_TILE = register("terracotta_roof_tile");
    public static final ItemId CONCRETE_POWDER = register("concrete_powder");
    public static final Map<Wood, ItemId> WOOD_SHINGLE = Helpers.mapOf(Wood.class, wood -> register("wood/shingle/" + wood.getSerializedName()));

    //Misc items
    public static final ItemId CRUSHED_BASE_COURSE = register("crushed_base_course");
    public static final ItemId HOGGIN_MIX = register("hoggin_mix");

    // Buckets
    public static final Map<RNRFluidId, RNRItems.ItemId> FLUID_BUCKETS = RNRFluidId.mapOf(fluid ->
        register("bucket/" + fluid.name(), () -> new BucketItem(fluid.fluid().get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );


    // Registration stuff
    private static ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> ItemId register(String name, Supplier<T> item)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }

    public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get();
        }
    }

    // From TFC's Metal.java
    private static Item.Properties tool(RegistryMetal metal, float attackDamageFactor, float attackSpeed)
    {
        return base(metal).attributes(ToolItem.productAttributes(metal.toolTier(), attackDamageFactor, attackSpeed));
    }

    private static Item.Properties base(RegistryMetal metal)
    {
        return new Item.Properties().rarity(metal.rarity());
    }

}

