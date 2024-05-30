package com.therighthon.rnr.common.block;

//Full height block
public class GravelPathBlock extends PathHeightBlock
{
    public GravelPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(1.05f));
    }


    //Add more gravel to make overheight gravel path, then add tamping recipe to make Macadam from that
}
