package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;

//Full height block
public class GravelPathBlock extends PathHeightBlock
{
    private static final float speedFactor = RoadsAndRoofs.SLOW_PATH_SPEED;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }

    public GravelPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

    //Add more gravel to make overheight gravel path, then add tamping recipe to make Macadam from that
}
