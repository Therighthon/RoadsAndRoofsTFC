package com.therighthon.rnr.common.block;

//Full height block
public class GravelPathBlock extends PathHeightBlock
{
    private static final float speedFactor = 1.1f;

    public static float getDefaultSpeedFactor()
    {
        return speedFactor;
    }

    public GravelPathBlock(Properties pProperties)
    {
        super(pProperties.speedFactor(speedFactor));
    }

    //Add more gravel to make overheight gravel path, then add tamping recipe to make Macadam from that
}
