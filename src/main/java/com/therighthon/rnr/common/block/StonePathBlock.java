package com.therighthon.rnr.common.block;

public class StonePathBlock extends PathHeightBlock
{
    private static final float speedFactor = 1.2f;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }
    public StonePathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

    //TODO: Add configurable speed factors

}
