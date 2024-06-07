package com.therighthon.rnr.common;

import java.util.Map;
import java.util.function.Supplier;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.block.StoneBlockType;
import com.therighthon.rnr.common.item.RNRItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.SelfTests;

public class RNRCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, com.therighthon.rnr.RoadsAndRoofs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> RNR_TAB = CREATIVE_TABS.register("roads_and_roofs",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.rnr_creative_mode_tab"))
            .icon(() -> new ItemStack(RNRItems.MATTOCKS.get(Metal.Default.BISMUTH_BRONZE).get()))
            .displayItems(RNRCreativeModeTabs::fillTab)
            .build()
    );

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        out.accept(RNRItems.CRUSHED_BASE_COURSE.get());
        out.accept(RNRItems.HOGGIN_MIX.get());

        out.accept(RNRItems.MATTOCKS.get(Metal.Default.COPPER).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.BISMUTH_BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.BLACK_BRONZE).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.WROUGHT_IRON).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.BLACK_STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.RED_STEEL).get());
        out.accept(RNRItems.MATTOCKS.get(Metal.Default.BLUE_STEEL).get());

        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.COPPER).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.BISMUTH_BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.BLACK_BRONZE).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.WROUGHT_IRON).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.BLACK_STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.RED_STEEL).get());
        out.accept(RNRItems.MATTOCK_HEADS.get(Metal.Default.BLUE_STEEL).get());

        for (Rock rock : Rock.VALUES)
        {
            for (StoneBlockType type : new StoneBlockType[] {
                StoneBlockType.COBBLED_ROAD,
                StoneBlockType.SETT_ROAD,
                StoneBlockType.FLAGSTONES,
                StoneBlockType.GRAVEL_ROAD,
                StoneBlockType.MACADAM_ROAD,
                StoneBlockType.OVER_HEIGHT_GRAVEL
            })
            {
                accept(out, RNRBlocks.ROCK_BLOCKS.get(rock).get(type));
            }

            accept(out, RNRItems.GRAVEL_FILL.get(rock));
            accept(out, RNRItems.FLAGSTONE.get(rock));
        }

        out.accept(RNRBlocks.TAMPED_SILT.get());
        out.accept(RNRBlocks.TAMPED_SILTY_LOAM.get());
        out.accept(RNRBlocks.TAMPED_SANDY_LOAM.get());
        out.accept(RNRBlocks.TAMPED_LOAM.get());

        out.accept(RNRBlocks.TAMPED_SILT_MUD.get());
        out.accept(RNRBlocks.TAMPED_SILTY_LOAM_MUD.get());
        out.accept(RNRBlocks.TAMPED_SANDY_LOAM_MUD.get());
        out.accept(RNRBlocks.TAMPED_LOAM_MUD.get());

        out.accept(RNRBlocks.TAMPED_PEAT.get());
        out.accept(RNRBlocks.TAMPED_KAOLIN.get());


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
}
