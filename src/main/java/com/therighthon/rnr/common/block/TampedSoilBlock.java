package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class TampedSoilBlock extends TampedHeightBlock
{
    public TampedSoilBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public float getSpeedFactor()
    {
        //TODO: Add config
        final float modifier = 0.9f; // 0.0 = full speed factor, 1.0 = no modifier
        return Helpers.lerp(modifier, speedFactor, 2.0f);
    }
}
