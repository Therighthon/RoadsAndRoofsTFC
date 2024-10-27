package com.therighthon.rnr.common.block;


import com.therighthon.rnr.RoadsAndRoofs;
import net.minecraftforge.fml.ModList;

public class MacadamPathBlock extends PathHeightBlock
{
    private static final float speedFactor = RoadsAndRoofs.NORMAL_PATH_SPEED;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }
    public MacadamPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

}
