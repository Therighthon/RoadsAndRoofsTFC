package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class TampedSoilBlock extends TampedHeightBlock
{
    public TampedSoilBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(0.9f));
    }

}
