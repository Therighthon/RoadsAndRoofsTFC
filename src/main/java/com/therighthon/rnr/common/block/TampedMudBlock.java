package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class TampedMudBlock extends TampedSoilBlock
{
    public TampedMudBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(0.8f));
    }
}
