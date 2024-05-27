package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TampedHeightBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

    public TampedHeightBlock(Properties pProperties)
    {
        super(pProperties);
    }
}
