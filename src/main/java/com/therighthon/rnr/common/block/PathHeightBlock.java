package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathHeightBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public PathHeightBlock(Properties pProperties)
    {
        super(pProperties);
    }
}
