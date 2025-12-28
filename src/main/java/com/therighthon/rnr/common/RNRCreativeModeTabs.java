package com.therighthon.rnr.common;

import com.therighthon.rnr.common.fluid.RNRFluids;
import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.rnr.common.block.AFCCompatBlocks;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.block.StoneBlockType;
import com.therighthon.rnr.common.item.AFCCompatItems;
import com.therighthon.rnr.common.item.RNRItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.SelfTests;

public class RNRCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, com.therighthon.rnr.RoadsAndRoofs.MOD_ID);

    public static final Id RNR_TAB = register("roads_and_roofs", () -> new ItemStack(RNRItems.HOGGIN_MIX), RNRCreativeModeTabs::fillTab);


    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        out.accept(RNRItems.CRUSHED_BASE_COURSE.get());
        out.accept(RNRBlocks.BASE_COURSE.get());
        out.accept(RNRItems.HOGGIN_MIX.get());

        out.accept(RNRItems.MATTOCKS.get(Metal.COPPER).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.BISMUTH_BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.BLACK_BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.WROUGHT_IRON).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.BLACK_STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.RED_STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.BLUE_STEEL).get());

        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.COPPER).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.BISMUTH_BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.BLACK_BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.WROUGHT_IRON).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.BLACK_STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.RED_STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.BLUE_STEEL).get());

        RNRFluids.FLUIDS.getEntries().forEach(fluid -> out.accept(fluid.value().getBucket()));

        out.accept(RNRBlocks.HOGGIN.get());
        out.accept(RNRBlocks.HOGGIN_SLAB.get());
        out.accept(RNRBlocks.HOGGIN_STAIRS.get());

        for (Rock rock : Rock.VALUES)
        {
            for (StoneBlockType type : new StoneBlockType[] {
                StoneBlockType.COBBLED_ROAD,
                StoneBlockType.SETT_ROAD,
                StoneBlockType.FLAGSTONES,
                StoneBlockType.GRAVEL_ROAD,
                StoneBlockType.MACADAM_ROAD,
                StoneBlockType.OVER_HEIGHT_GRAVEL,


            })
            {
                accept(out, RNRBlocks.ROCK_BLOCKS.get(rock).get(type));
                if (!type.equals(StoneBlockType.OVER_HEIGHT_GRAVEL))
                {
                    accept(out, RNRBlocks.ROCK_BLOCKS.get(rock).get(type));
                    accept(out, RNRBlocks.ROCK_STAIRS.get(rock).get(type));
                    accept(out, RNRBlocks.ROCK_SLABS.get(rock).get(type));
                }
            }

            accept(out, RNRItems.GRAVEL_FILL.get(rock));
            accept(out, RNRItems.FLAGSTONE.get(rock));
        }

        out.accept(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.PINK_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.PINK_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.PINK_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.RED_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.RED_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.RED_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES_STAIRS.get());
        out.accept(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES_SLAB.get());
        out.accept(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES_STAIRS.get());

        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BLACK).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BROWN).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.GREEN).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.RED).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.PINK).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.WHITE).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.YELLOW).get());

        for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
        {
            for (RNRBlocks.RNRSoilBlockType type : RNRBlocks.RNRSoilBlockType.values())
            {
                accept(out, RNRBlocks.TAMPED_SOILS, type, variant);
            }
        }

        out.accept(RNRBlocks.TAMPED_PEAT.get());
        out.accept(RNRBlocks.TAMPED_KAOLIN.get());
        out.accept(RNRBlocks.BRICK_ROAD.get());
        out.accept(RNRBlocks.BRICK_ROAD_SLAB.get());
        out.accept(RNRBlocks.BRICK_ROAD_STAIRS.get());

        out.accept(RNRItems.CONCRETE_POWDER.get());
        out.accept(RNRBlocks.CONCRETE_ROAD.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get());
        out.accept(RNRBlocks.CRACKED_CONCRETE_ROAD.get());
        out.accept(RNRBlocks.TRODDEN_CONCRETE_ROAD.get());
        out.accept(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_PANEL.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_SETT.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get());

        out.accept(RNRBlocks.CONCRETE_ROAD_STAIRS.get());
        out.accept(RNRBlocks.CRACKED_CONCRETE_ROAD_STAIRS.get());
        out.accept(RNRBlocks.TRODDEN_CONCRETE_ROAD_STAIRS.get());
        out.accept(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_STAIRS.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_PANEL_STAIRS.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_SETT_STAIRS.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_STAIRS.get());

        out.accept(RNRBlocks.CONCRETE_ROAD_SLAB.get());
        out.accept(RNRBlocks.CRACKED_CONCRETE_ROAD_SLAB.get());
        out.accept(RNRBlocks.TRODDEN_CONCRETE_ROAD_SLAB.get());
        out.accept(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_SLAB.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_PANEL_SLAB.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_SETT_SLAB.get());
        out.accept(RNRBlocks.CONCRETE_ROAD_FLAGSTONES_SLAB.get());

        out.accept(RNRBlocks.ROOF_FRAME.get());
        out.accept(RNRBlocks.ROOF_FRAME_SLAB.get());
        out.accept(RNRBlocks.ROOF_FRAME_STAIRS.get());

        out.accept(RNRBlocks.THATCH_ROOF.get());
        out.accept(RNRBlocks.THATCH_ROOF_SLAB.get());
        out.accept(RNRBlocks.THATCH_ROOF_STAIRS.get());

        out.accept(RNRItems.UNFIRED_TERRACOTTA_ROOF_TILE.get());
        out.accept(RNRItems.TERRACOTTA_ROOF_TILE.get());
        out.accept(RNRBlocks.TERRACOTTA_ROOF.get());
        out.accept(RNRBlocks.TERRACOTTA_ROOF_SLAB.get());
        out.accept(RNRBlocks.TERRACOTTA_ROOF_STAIRS.get());
        out.accept(RNRItems.UNFIRED_ROOF_TILE.get());
        out.accept(RNRItems.CERAMIC_ROOF_TILE.get());
        out.accept(RNRBlocks.CERAMIC_ROOF.get());
        out.accept(RNRBlocks.CERAMIC_ROOF_SLAB.get());
        out.accept(RNRBlocks.CERAMIC_ROOF_STAIRS.get());

        for (Wood wood : Wood.VALUES)
        {
            out.accept(RNRItems.WOOD_SHINGLE.get(wood).get());
            out.accept(RNRBlocks.WOOD_SHINGLE_ROOFS.get(wood).get());
            out.accept(RNRBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get());
            out.accept(RNRBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get());
        }
        if (ModList.get().isLoaded("afc"))
        {
            for (AFCWood wood : AFCWood.VALUES)
            {
                out.accept(AFCCompatItems.WOOD_SHINGLE.get(wood).get());
                out.accept(AFCCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood));
                out.accept(AFCCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get());
                out.accept(AFCCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get());
            }
        }
    }


    //Helpers from TFC
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }

    //Helpers from TFC
    private static RNRCreativeModeTabs.Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final var holder = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
            .icon(icon)
            .title(Component.translatable("rnr.creative_tab." + name))
            .displayItems(displayItems)
            .build());
        return new RNRCreativeModeTabs.Id(holder, displayItems);
    }

    public static record Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
        public Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
            this.tab = tab;
            this.generator = generator;
        }

        public DeferredHolder<CreativeModeTab, CreativeModeTab> tab() {
            return this.tab;
        }

        public CreativeModeTab.DisplayItemsGenerator generator() {
            return this.generator;
        }
    }
}
