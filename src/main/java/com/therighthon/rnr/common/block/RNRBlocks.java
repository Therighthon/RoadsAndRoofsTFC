package com.therighthon.rnr.common.block;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.item.RNRItems;
import javax.annotation.Nullable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class RNRBlocks
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

    public static final RegistryObject<Block> TAMPED_SILT = register("tamped_silt", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_LOAM = register("tamped_loam", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_SANDY_LOAM = register("tamped_sandy_loam", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_SILTY_LOAM = register("tamped_silty_loam", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));

    public static final RegistryObject<Block> TAMPED_SILT_MUD = register("tamped_silt_mud", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_LOAM_MUD = register("tamped_loam_mud", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_SANDY_LOAM_MUD = register("tamped_sandy_loam_mud", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> TAMPED_SILTY_LOAM_MUD = register("tamped_silty_loam_mud", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)));

    public static final RegistryObject<Block> TAMPED_PEAT = register("tamped_peat", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(3.0F).sound(TFCSounds.PEAT)));
    public static final RegistryObject<Block> TAMPED_KAOLIN = register("tamped_kaolin", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).randomTicks().strength(5.0F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> BASE_COURSE = register("base_course", () -> new BaseCourseBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.0F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> HOGGIN = register("hoggin", () -> new HogginBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL)));


    public static final RegistryObject<Block> BRICK_ROAD = register("brick_road", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));

    public static final Map<Rock, Map<StoneBlockType, RegistryObject<Block>>> ROCK_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(StoneBlockType.class, type ->
            register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock))
        )
    );

    //TODO
    public static final Map<Rock, Map<StoneBlockType, RegistryObject<Block>>> ROCK_SLABS = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(StoneBlockType.class, StoneBlockType::hasVariants, type ->
            register(("rock/" + type.name() + "/" + rock.name())+ "_slab", () -> type.createRockSlab(rock, type))
        )
    );

    //TODO
    public static final Map<Rock, Map<StoneBlockType, RegistryObject<Block>>> ROCK_STAIRS = Helpers.mapOfKeys(Rock.class, rock ->
        Helpers.mapOfKeys(StoneBlockType.class, StoneBlockType::hasVariants, type ->
            register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> type.createPathStairs(rock, type))
        )
    );

    public static final RegistryObject<Block> BROWN_SANDSTONE_FLAGSTONES = register("brown_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_SANDSTONE_FLAGSTONES = register("white_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_SANDSTONE_FLAGSTONES = register("black_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_SANDSTONE_FLAGSTONES = register("red_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_SANDSTONE_FLAGSTONES = register("yellow_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GREEN_SANDSTONE_FLAGSTONES = register("green_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PINK_SANDSTONE_FLAGSTONES = register("pink_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));

}
