package com.therighthon.rnr.common.block;

import net.dries007.tfc.util.Helpers;

public class BaseCourseBlock extends BaseCourseHeightBlock
{

    public BaseCourseBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(0.9f));
    }

    //TODO: Tool/Right click actions
}
