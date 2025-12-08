package com.therighthon.rnr.common.block;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.fluid.SimpleRNRFluid;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.fluid.RNRFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;

public class RNRBlocks
{
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, RoadsAndRoofs.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, RoadsAndRoofs.MOD_ID);

    // These are separate because they make the BlockLootProvider very angry when I try to pass them in
    public static final DeferredRegister<Block> FLUID_BLOCKS =
        DeferredRegister.create(Registries.BLOCK, RoadsAndRoofs.MOD_ID);

    public enum RNRSoilBlockType
    {
        TAMPED("tamped_", ""),
        TAMPED_MUD("tamped_", "_mud");

        public final String prefix;
        public final String suffix;

        RNRSoilBlockType(String prefix, String suffix)
        {
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }

    public static final Map<RNRSoilBlockType, Map<SoilBlockType.Variant, Id<Block>>> TAMPED_SOILS =
        Helpers.mapOf(RNRBlocks.RNRSoilBlockType.class, type -> Helpers.mapOf(SoilBlockType.Variant.class, variant ->
            register(type.prefix + variant.name() + type.suffix, () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(3.0F).sound(SoundType.ROOTED_DIRT)))));

    public static final Id<Block> TAMPED_PEAT = register("tamped_peat", () -> new TampedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(3.0F).sound(TFCSounds.PEAT)));
    public static final Id<Block> TAMPED_KAOLIN = register("tamped_kaolin", () -> new TampedSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).randomTicks().strength(5.0F).sound(SoundType.GRAVEL)));

    public static final Id<Block> BASE_COURSE = register("base_course", () -> new BaseCourseBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.0F).sound(SoundType.GRAVEL)));
    public static final Id<Block> HOGGIN = register("hoggin", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL)));
    public static final Id<Block> HOGGIN_SLAB = register("hoggin_slab", () -> new HogginSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL)));
    public static final Id<Block> HOGGIN_STAIRS = register("hoggin_stairs", () -> new PathStairBlock(() -> HOGGIN.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.GRAVEL)));

    public static final Id<Block> BRICK_ROAD = register("brick_road", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BRICK_ROAD_SLAB = register("brick_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BRICK_ROAD_STAIRS = register("brick_road_stairs", () -> new PathStairBlock(() -> BRICK_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CONCRETE_ROAD = register("concrete_road", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_SLAB = register("concrete_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_STAIRS = register("concrete_road_stairs", () -> new PathStairBlock(() -> CONCRETE_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CONCRETE_ROAD_PANEL = register("concrete_road_panel", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_PANEL_SLAB = register("concrete_road_panel_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_PANEL_STAIRS = register("concrete_road_panel_stairs", () -> new PathStairBlock(() -> CONCRETE_ROAD_PANEL.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CONCRETE_ROAD_SETT = register("concrete_road_sett", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_SETT_SLAB = register("concrete_road_sett_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_SETT_STAIRS = register("concrete_road_sett_stairs", () -> new PathStairBlock(() -> CONCRETE_ROAD_SETT.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CONCRETE_ROAD_FLAGSTONES = register("concrete_road_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_FLAGSTONES_SLAB = register("concrete_road_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CONCRETE_ROAD_FLAGSTONES_STAIRS = register("concrete_road_flagstones_stairs", () -> new PathStairBlock(() -> CONCRETE_ROAD_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> TRODDEN_CONCRETE_ROAD = register("trodden_concrete_road", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> TRODDEN_CONCRETE_ROAD_SLAB = register("trodden_concrete_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> TRODDEN_CONCRETE_ROAD_STAIRS = register("trodden_concrete_road_stairs", () -> new PathStairBlock(() -> TRODDEN_CONCRETE_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CRACKED_CONCRETE_ROAD = register("cracked_concrete_road", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CRACKED_CONCRETE_ROAD_SLAB = register("cracked_concrete_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CRACKED_CONCRETE_ROAD_STAIRS = register("cracked_concrete_road_stairs", () -> new PathStairBlock(() -> CRACKED_CONCRETE_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CRACKED_TRODDEN_CONCRETE_ROAD = register("cracked_trodden_concrete_road", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CRACKED_TRODDEN_CONCRETE_ROAD_SLAB = register("cracked_trodden_concrete_road_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> CRACKED_TRODDEN_CONCRETE_ROAD_STAIRS = register("cracked_trodden_concrete_road_stairs", () -> new PathStairBlock(() -> CRACKED_TRODDEN_CONCRETE_ROAD.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> CONCRETE_ROAD_CONTROL_JOINT = register("concrete_road_control_joint", () -> new ConcretePathControlJointBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F).sound(SoundType.STONE)));

    public static final Id<Block> POURING_CONCRETE_ROAD = register("pouring_concrete_road", () -> new PouringConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).mapColor(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD)));

    public static final Id<Block> WET_CONCRETE_ROAD = register("wet_concrete_road", () -> new CrackingWetConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).mapColor(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));
    public static final Id<Block> TRODDEN_WET_CONCRETE_ROAD = register("trodden_wet_concrete_road", () -> new CrackingWetConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));
    public static final Id<Block> WET_CONCRETE_ROAD_CONTROL_JOINT = register("wet_concrete_road_control_joint", () -> new WetConcretePathControlJointBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));

    public static final Id<Block> WET_CONCRETE_ROAD_PANEL = register("wet_concrete_road_panel", () -> new WetConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).mapColor(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));
    public static final Id<Block> WET_CONCRETE_ROAD_SETT = register("wet_concrete_road_sett", () -> new WetConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).mapColor(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));
    public static final Id<Block> WET_CONCRETE_ROAD_FLAGSTONES = register("wet_concrete_road_flagstones", () -> new WetConcretePathBlock(ExtendedProperties.of(MapColor.COLOR_GRAY).mapColor(MapColor.COLOR_GRAY).strength(2.5F).sound(SoundType.MUD).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER)));

    public static final BlockBehaviour.Properties ROOF_PROPERTIES = ExtendedProperties.of(MapColor.WOOD).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.WOOD).flammable(50, 100).properties();

    public static final Id<Block> ROOF_FRAME = register("roof_frame", () -> new BlockModEnabledBlock(ROOF_PROPERTIES, true));
    public static final Id<Block> ROOF_FRAME_SLAB = register("roof_frame_slab", () -> new BlockModEnabledSlabBlock(ROOF_PROPERTIES, true));
    public static final Id<Block> ROOF_FRAME_STAIRS = register("roof_frame_stairs", () -> new BlockModEnabledStairBlock(() -> ROOF_FRAME.get().defaultBlockState(), ROOF_PROPERTIES, true));

    public static final BlockBehaviour.Properties THATCH_PROPERTIES = ExtendedProperties.of(MapColor.SAND).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(TFCSounds.THATCH).flammable(40, 75).properties();

    public static final Id<Block> THATCH_ROOF = register("thatch_roof", () -> new Block(THATCH_PROPERTIES));
    public static final Id<Block> THATCH_ROOF_SLAB = register("thatch_roof_slab", () -> new SlabBlock(THATCH_PROPERTIES));
    public static final Id<Block> THATCH_ROOF_STAIRS = register("thatch_roof_stairs", () -> new StairBlock(THATCH_ROOF.get().defaultBlockState(), THATCH_PROPERTIES));

    public static final BlockBehaviour.Properties CERAMIC_PROPERTIES = ExtendedProperties.of(MapColor.TERRACOTTA_BROWN).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.DEEPSLATE_TILES).properties();

    public static final Id<Block> TERRACOTTA_ROOF = register("terracotta_roof", () -> new Block(CERAMIC_PROPERTIES));
    public static final Id<Block> TERRACOTTA_ROOF_SLAB = register("terracotta_roof_slab", () -> new SlabBlock(CERAMIC_PROPERTIES));
    public static final Id<Block> TERRACOTTA_ROOF_STAIRS = register("terracotta_roof_stairs", () -> new StairBlock(TERRACOTTA_ROOF.get().defaultBlockState(), CERAMIC_PROPERTIES));
    public static final Id<Block> CERAMIC_ROOF = register("ceramic_roof", () -> new Block(CERAMIC_PROPERTIES));
    public static final Id<Block> CERAMIC_ROOF_SLAB = register("ceramic_roof_slab", () -> new SlabBlock(CERAMIC_PROPERTIES));
    public static final Id<Block> CERAMIC_ROOF_STAIRS = register("ceramic_roof_stairs", () -> new StairBlock(TERRACOTTA_ROOF.get().defaultBlockState(), CERAMIC_PROPERTIES));

    public static final Map<Wood, Id<Block>> WOOD_SHINGLE_ROOFS = Helpers.mapOf(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName(), () -> new Block(ROOF_PROPERTIES)));
    public static final Map<Wood, Id<Block>> WOOD_SHINGLE_ROOF_SLABS = Helpers.mapOf(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_slab", () -> new SlabBlock(ROOF_PROPERTIES)));
    public static final Map<Wood, Id<Block>> WOOD_SHINGLE_ROOF_STAIRS = Helpers.mapOf(Wood.class, wood -> register("wood/shingles/" + wood.getSerializedName() + "_stairs", () -> new StairBlock(WOOD_SHINGLE_ROOFS.get(wood).get().defaultBlockState(), ROOF_PROPERTIES)));

    public static final Map<Rock, Map<StoneBlockType, Id<Block>>> ROCK_BLOCKS = Helpers.mapOf(Rock.class, rock ->
        Helpers.mapOf(StoneBlockType.class, type ->
            register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock))
        )
    );

    public static final Map<Rock, Map<StoneBlockType, Id<Block>>> ROCK_SLABS = Helpers.mapOf(Rock.class, rock ->
        Helpers.mapOf(StoneBlockType.class, StoneBlockType::hasVariants, type ->
            register(("rock/" + type.name() + "/" + rock.name()) + "_slab", () -> type.createRockSlab(rock, type))
        )
    );

    public static final Map<Rock, Map<StoneBlockType, Id<Block>>> ROCK_STAIRS = Helpers.mapOf(Rock.class, rock ->
        Helpers.mapOf(StoneBlockType.class, StoneBlockType::hasVariants, type ->
            register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> type.createPathStairs(rock, type))
        )
    );

    public static final Id<Block> BROWN_SANDSTONE_FLAGSTONES = register("brown_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BROWN_SANDSTONE_FLAGSTONES_SLAB = register("brown_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BROWN_SANDSTONE_FLAGSTONES_STAIRS = register("brown_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> BROWN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BLACK_SANDSTONE_FLAGSTONES = register("black_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BLACK_SANDSTONE_FLAGSTONES_SLAB = register("black_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> BLACK_SANDSTONE_FLAGSTONES_STAIRS = register("black_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> BLACK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> RED_SANDSTONE_FLAGSTONES = register("red_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> RED_SANDSTONE_FLAGSTONES_SLAB = register("red_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> RED_SANDSTONE_FLAGSTONES_STAIRS = register("red_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> RED_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> YELLOW_SANDSTONE_FLAGSTONES = register("yellow_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> YELLOW_SANDSTONE_FLAGSTONES_SLAB = register("yellow_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> YELLOW_SANDSTONE_FLAGSTONES_STAIRS = register("yellow_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> YELLOW_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> GREEN_SANDSTONE_FLAGSTONES = register("green_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> GREEN_SANDSTONE_FLAGSTONES_SLAB = register("green_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> GREEN_SANDSTONE_FLAGSTONES_STAIRS = register("green_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> GREEN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> WHITE_SANDSTONE_FLAGSTONES = register("white_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> WHITE_SANDSTONE_FLAGSTONES_SLAB = register("white_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> WHITE_SANDSTONE_FLAGSTONES_STAIRS = register("white_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> WHITE_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> PINK_SANDSTONE_FLAGSTONES = register("pink_sandstone_flagstones", () -> new PathHeightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> PINK_SANDSTONE_FLAGSTONES_SLAB = register("pink_sandstone_flagstones_slab", () -> new StonePathSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));
    public static final Id<Block> PINK_SANDSTONE_FLAGSTONES_STAIRS = register("pink_sandstone_flagstones_stairs", () -> new PathStairBlock(() -> PINK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(5.0F).sound(SoundType.STONE)));

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    protected static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, @org.jetbrains.annotations.Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new Id<>(RegistrationHelpers.registerBlock(RNRBlocks.BLOCKS, RNRItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    public static final Map<SimpleRNRFluid, Id<LiquidBlock>> SIMPLE_FLUIDS = Helpers.mapOf(SimpleRNRFluid.class, fluid ->
        registerFluidNoItem("fluid/" + fluid.getId(), () -> new LiquidBlock(RNRFluids.SIMPLE_RNR_FLUIDS.get(fluid).getSource(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()))
    );

    private static <T extends Block> Id<T> registerFluidNoItem(String name, Supplier<T> blockSupplier)
    {
        return registerFluid(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> Id<T> registerFluid(String name, Supplier<T> blockSupplier, @org.jetbrains.annotations.Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new Id<>(RegistrationHelpers.registerBlock(RNRBlocks.FLUID_BLOCKS, RNRItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    public record Id<T extends Block>(DeferredHolder<Block, T> holder) implements RegistryHolder<Block, T>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get().asItem();
        }
    }
}
