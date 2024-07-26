package com.therighthon.rnr.common.fluid;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum SimpleRNRFluid implements StringRepresentable
{

    CONCRETE(0xBBDCDCDC);

    private final String id;
    private final int color;

    SimpleRNRFluid(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }

    public String getId()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }

    @Override
    public String getSerializedName()
    {
        return id;
    }

    public boolean isTransparent()
    {
        return true;
    }

}
