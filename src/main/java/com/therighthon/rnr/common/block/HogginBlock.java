package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class HogginBlock extends PathHeightBlock
{
    private static final float speedFactor = 1.0f; // 1.1f;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }

    public HogginBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

}
