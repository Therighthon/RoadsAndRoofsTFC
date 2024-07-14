package com.therighthon.rnr.common.block;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.item.RNRItems;
import javax.annotation.Nullable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class AFCCompatBlocks
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RoadsAndRoofs.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(BLOCKS, RNRItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    public static final BlockBehaviour.Properties ROOF_PROPERTIES = ExtendedProperties.of(MapColor.WOOD).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.WOOD).flammable(50, 100).properties();

    public static final Map<AFCWood, RegistryObject<Block>> WOOD_SHINGLE_ROOFS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/shingles/" + wood.getSerializedName(), () -> new Block(ROOF_PROPERTIES)));
    public static final Map<AFCWood, RegistryObject<Block>> WOOD_SHINGLE_ROOF_SLABS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_slab", () -> new SlabBlock(ROOF_PROPERTIES)));
    public static final Map<AFCWood, RegistryObject<Block>> WOOD_SHINGLE_ROOF_STAIRS = Helpers.mapOfKeys(AFCWood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_stairs", () -> new StairBlock(WOOD_SHINGLE_ROOFS.get(wood).get().defaultBlockState(), ROOF_PROPERTIES)));

}
