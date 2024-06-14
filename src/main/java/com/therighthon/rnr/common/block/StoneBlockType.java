package com.therighthon.rnr.common.block;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;

public enum StoneBlockType implements StringRepresentable
{
    GRAVEL_ROAD((rock, self) -> new GravelPathBlock(properties(rock).sound(SoundType.GRAVEL).strength(rock.category().hardness(3.5f), 4)), true),
    OVER_HEIGHT_GRAVEL((rock, self) -> new OverHeightBlock(properties(rock).sound(SoundType.GRAVEL).strength(rock.category().hardness(3.5f), 4)), false),
    MACADAM_ROAD((rock, self) -> new MacadamPathBlock(properties(rock).sound(SoundType.GRAVEL).strength(rock.category().hardness(4f), 5)), true),
    FLAGSTONES((rock, self) -> new StonePathBlock(properties(rock).strength(rock.category().hardness(5f), 8)), true),
    SETT_ROAD((rock, self) -> new StonePathBlock(properties(rock).strength(rock.category().hardness(5f), 8)), true),
    COBBLED_ROAD((rock, self) -> new StonePathBlock(properties(rock).strength(rock.category().hardness(5f), 8)), true);

    public static final StoneBlockType[] VALUES = StoneBlockType.values();

    public static StoneBlockType valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : GRAVEL_ROAD;
    }

    private static BlockBehaviour.Properties properties(RegistryRock rock)
    {
        return BlockBehaviour.Properties.of().mapColor(rock.color()).sound(SoundType.STONE).instrument(NoteBlockInstrument.BASEDRUM);
    }

    private final boolean variants;
    private final BiFunction<RegistryRock, StoneBlockType, Block> blockFactory;
    private final String serializedName;

    StoneBlockType(BiFunction<RegistryRock, StoneBlockType, Block> blockFactory, boolean variants)
    {
        this.blockFactory = blockFactory;
        this.variants = variants;
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    /**
     * @return if this block type should be given slab, stair and wall variants
     */
    public boolean hasVariants()
    {
        return variants;
    }

    public Block create(RegistryRock rock)
    {
        return blockFactory.apply(rock, this);
    }

    public Block createRockSlab(RegistryRock rock, StoneBlockType type)
    {
        final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 10).requiresCorrectToolForDrops();
        if (type == StoneBlockType.GRAVEL_ROAD)
        {
            return new GravelPathSlabBlock(properties);
        }
        else if (type == StoneBlockType.MACADAM_ROAD)
        {
            return new MacadamPathSlabBlock(properties);
        }
        else
        {
            return new StonePathSlabBlock(properties);
        }
    }

    public PathStairBlock createPathStairs(RegistryRock rock, StoneBlockType type)
    {
        final Supplier<BlockState> state = () -> rock.getBlock(Rock.BlockType.RAW).get().defaultBlockState();
        final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f, 10).requiresCorrectToolForDrops();
        if (type == StoneBlockType.MACADAM_ROAD)
        {
            return new PathStairBlock(state, properties, MacadamPathBlock.getDefaultSpeedFactor());
        }
        else if (type == StoneBlockType.GRAVEL_ROAD)
        {
            return new PathStairBlock(state, properties, GravelPathBlock.getDefaultSpeedFactor());
        }
        else
        {
            return new PathStairBlock(state, properties, StonePathBlock.getDefaultSpeedFactor());
        }

    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }
}

