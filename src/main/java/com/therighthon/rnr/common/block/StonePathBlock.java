package com.therighthon.rnr.common.block;

import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraftforge.fml.ModList;

public class StonePathBlock extends PathHeightBlock
{
    private static final float speedFactor = RoadsAndRoofs.NORMAL_PATH_SPEED;

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
