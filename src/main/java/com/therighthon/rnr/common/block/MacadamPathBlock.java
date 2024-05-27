package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class MacadamPathBlock extends PathHeightBlock
{
    public MacadamPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(0.9f));
    }

}
