package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCourseHeightBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public BaseCourseHeightBlock(Properties pProperties)
    {
        super(pProperties);
    }

    //TODO: Tool/Right click actions
}
