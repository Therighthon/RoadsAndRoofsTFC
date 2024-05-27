package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.dries007.tfc.util.Helpers;

public class OverHeightBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 17.0D, 16.0D);

    public OverHeightBlock(Properties pProperties)
    {
        super(pProperties);
    }
}
