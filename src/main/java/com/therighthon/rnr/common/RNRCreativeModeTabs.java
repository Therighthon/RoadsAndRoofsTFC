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
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.SelfTests;

public class RNRCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, com.therighthon.rnr.RoadsAndRoofs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> RNR_TAB = CREATIVE_TABS.register("roads_and_roofs",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("roads_and_roofs"))
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

        out.accept(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.PINK_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.RED_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES.get());
        out.accept(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES.get());

        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BLACK).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BROWN).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.GREEN).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.RED).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.PINK).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.WHITE).get());
        out.accept(RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.YELLOW).get());

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
        out.accept(RNRBlocks.BRICK_ROAD.get());


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