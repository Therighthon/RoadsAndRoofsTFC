package com.therighthon.rnr.common.block;

public class ConcretePathBlock extends PathHeightBlock
{
    private static final float defaultSpeedFactor = 1.3f;

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
