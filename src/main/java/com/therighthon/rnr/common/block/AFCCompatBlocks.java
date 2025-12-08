package com.therighthon.rnr.common.block;

import java.util.Map;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;

public class AFCCompatBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, RoadsAndRoofs.MOD_ID);

    public static final BlockBehaviour.Properties ROOF_PROPERTIES = ExtendedProperties.of(MapColor.WOOD).strength(1.0F, 0.6F).noOcclusion().isViewBlocking(TFCBlocks::never).sound(SoundType.WOOD).flammable(50, 100).properties();

    public static final Map<AFCWood, RNRBlocks.Id<Block>> WOOD_SHINGLE_ROOFS = Helpers.mapOf(AFCWood.class, wood -> RNRBlocks.register("wood/shingles/" + wood.getSerializedName(), () -> new Block(ROOF_PROPERTIES)));
    public static final Map<AFCWood, RNRBlocks.Id<Block>> WOOD_SHINGLE_ROOF_SLABS = Helpers.mapOf(AFCWood.class, wood -> RNRBlocks.register("wood/shingles/" + wood.getSerializedName() + "_slab", () -> new SlabBlock(ROOF_PROPERTIES)));
    public static final Map<AFCWood, RNRBlocks.Id<Block>> WOOD_SHINGLE_ROOF_STAIRS = Helpers.mapOf(AFCWood.class, wood -> RNRBlocks.register("wood/shingles/" + wood.getSerializedName() + "_stairs", () -> new StairBlock(WOOD_SHINGLE_ROOFS.get(wood).get().defaultBlockState(), ROOF_PROPERTIES)));

}
