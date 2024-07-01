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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
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
    public static final RegistryObject<Block> HOGGIN_SLAB = register("hoggin_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> HOGGIN_STAIRS = register("hoggin_stairs", () -> new PathStairBlock(() -> HOGGIN.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL), 1.3f));

    public static final RegistryObject<Block> BRICK_ROAD = register("brick_road", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BRICK_ROAD_SLAB = register("brick_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BRICK_ROAD_STAIRS = register("brick_road_stairs", () -> new PathStairBlock(() -> BRICK_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE), 1.3f));

    public static final BlockBehaviour.Properties ROOF_PROPERTIES = ExtendedProperties.of(MapColor.WOOD).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.WOOD).flammable(50, 100).properties();

    public static final RegistryObject<Block> ROOF_FRAME = register("roof_frame", () -> new BlockModEnabledBlock(ROOF_PROPERTIES, true));
    public static final RegistryObject<Block> ROOF_FRAME_SLAB = register("roof_frame_slab", () -> new BlockModEnabledSlabBlock(ROOF_PROPERTIES, true));
    public static final RegistryObject<Block> ROOF_FRAME_STAIRS = register("roof_frame_stairs", () -> new BlockModEnabledStairBlock(() -> ROOF_FRAME.get().defaultBlockState(), ROOF_PROPERTIES, true));

    public static final BlockBehaviour.Properties THATCH_PROPERTIES = ExtendedProperties.of(MapColor.SAND).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(TFCSounds.THATCH).flammable(40, 75).properties();

    public static final RegistryObject<Block> THATCH_ROOF = register("thatch_roof", () -> new Block(THATCH_PROPERTIES));
    public static final RegistryObject<Block> THATCH_ROOF_SLAB = register("thatch_roof_slab", () -> new SlabBlock(THATCH_PROPERTIES));
    public static final RegistryObject<Block> THATCH_ROOF_STAIRS = register("thatch_roof_stairs", () -> new StairBlock(() -> THATCH_ROOF.get().defaultBlockState(), THATCH_PROPERTIES));

    public static final BlockBehaviour.Properties CERAMIC_PROPERTIES = ExtendedProperties.of(MapColor.TERRACOTTA_BROWN).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.DEEPSLATE_TILES).properties();

    public static final RegistryObject<Block> TERRACOTTA_ROOF = register("terracotta_roof", () -> new Block(CERAMIC_PROPERTIES));
    public static final RegistryObject<Block> TERRACOTTA_ROOF_SLAB = register("terracotta_roof_slab", () -> new SlabBlock(CERAMIC_PROPERTIES));
    public static final RegistryObject<Block> TERRACOTTA_ROOF_STAIRS = register("terracotta_roof_stairs", () -> new StairBlock(() -> TERRACOTTA_ROOF.get().defaultBlockState(), CERAMIC_PROPERTIES));
    public static final RegistryObject<Block> CERAMIC_ROOF = register("ceramic_roof", () -> new Block(CERAMIC_PROPERTIES));
    public static final RegistryObject<Block> CERAMIC_ROOF_SLAB = register("ceramic_roof_slab", () -> new SlabBlock(CERAMIC_PROPERTIES));
    public static final RegistryObject<Block> CERAMIC_ROOF_STAIRS = register("ceramic_roof_stairs", () -> new StairBlock(() -> TERRACOTTA_ROOF.get().defaultBlockState(), CERAMIC_PROPERTIES));

    //TODO: AFC Compat
    public static final Map<Wood, RegistryObject<Block>> WOOD_SHINGLE_ROOFS = Helpers.mapOfKeys(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName(), () -> new Block(ROOF_PROPERTIES)));
    public static final Map<Wood, RegistryObject<Block>> WOOD_SHINGLE_ROOF_SLABS = Helpers.mapOfKeys(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_slab", () -> new SlabBlock(ROOF_PROPERTIES)));
    public static final Map<Wood, RegistryObject<Block>> WOOD_SHINGLE_ROOF_STAIRS = Helpers.mapOfKeys(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_stairs", () -> new StairBlock(WOOD_SHINGLE_ROOFS.get(wood).get().defaultBlockState(), ROOF_PROPERTIES)));

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
    public static final RegistryObject<Block> BROWN_SANDSTONE_FLAGSTONES_SLAB = register("brown_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BROWN_SANDSTONE_FLAGSTONES_STAIRS = register("brown_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> BROWN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> BLACK_SANDSTONE_FLAGSTONES = register("black_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_SANDSTONE_FLAGSTONES_SLAB = register("black_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_SANDSTONE_FLAGSTONES_STAIRS = register("black_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> BLACK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> RED_SANDSTONE_FLAGSTONES = register("red_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_SANDSTONE_FLAGSTONES_SLAB = register("red_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_SANDSTONE_FLAGSTONES_STAIRS = register("red_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> RED_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> YELLOW_SANDSTONE_FLAGSTONES = register("yellow_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_SANDSTONE_FLAGSTONES_SLAB = register("yellow_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_SANDSTONE_FLAGSTONES_STAIRS = register("yellow_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> YELLOW_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> GREEN_SANDSTONE_FLAGSTONES = register("green_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GREEN_SANDSTONE_FLAGSTONES_SLAB = register("green_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GREEN_SANDSTONE_FLAGSTONES_STAIRS = register("green_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> GREEN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> WHITE_SANDSTONE_FLAGSTONES = register("white_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_SANDSTONE_FLAGSTONES_SLAB = register("white_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_SANDSTONE_FLAGSTONES_STAIRS = register("white_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> WHITE_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE), 1.3f));
    public static final RegistryObject<Block> PINK_SANDSTONE_FLAGSTONES = register("pink_sandstone_flagstones", () -> new StonePathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PINK_SANDSTONE_FLAGSTONES_SLAB = register("pink_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PINK_SANDSTONE_FLAGSTONES_STAIRS = register("pink_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> PINK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE), 1.3f));
}
