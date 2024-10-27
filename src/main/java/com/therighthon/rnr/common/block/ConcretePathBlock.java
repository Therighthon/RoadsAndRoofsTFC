package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;

public class ConcretePathBlock extends PathHeightBlock
{
    private static final float defaultSpeedFactor = RoadsAndRoofs.FAST_PATH_SPEED;

    public static float getDefaultSpeedFactor()
    {
        return defaultSpeedFactor;
    }

    public ConcretePathBlock(Properties pProperties, float speedFactor)
    {
        super(pProperties.speedFactor(speedFactor));
    }

    public ConcretePathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(defaultSpeedFactor));
    }

    //TODO: Add configurable speed factors
}
