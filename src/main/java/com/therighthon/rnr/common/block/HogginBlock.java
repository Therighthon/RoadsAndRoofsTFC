package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;

public class HogginBlock extends PathHeightBlock
{
    private static final float speedFactor = RoadsAndRoofs.SLOW_PATH_SPEED;


    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }

    public HogginBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

}
