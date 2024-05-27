package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.Block;

import net.dries007.tfc.util.Helpers;

//Full height block
public class GravelPathBlock extends PathHeightBlock
{
    public GravelPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(1.1f));
    }


    //Add more gravel to make overheight gravel path, then add tamping recipe to make Macadam from that
}
