package com.therighthon.rnr.common.block;


public class MacadamPathBlock extends PathHeightBlock
{
    private static final float speedFactor = 1.2f;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }
    public MacadamPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

}
