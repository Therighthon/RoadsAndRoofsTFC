package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

//Full height block
public class GravelPathBlock extends PathHeightBlock
{
    public GravelPathBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public float getSpeedFactor()
    {
        //TODO: Add config
        final float modifier = 1.2f; // 0.0 = full speed factor, 1.0 = no modifier
        return Helpers.lerp(modifier, speedFactor, 2.0f);
    }

    //Add more gravel to make overheight gravel path, then add tamping recipe to make Macadam from that
}
