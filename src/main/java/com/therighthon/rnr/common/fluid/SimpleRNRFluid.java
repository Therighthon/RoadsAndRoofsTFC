package com.therighthon.rnr.common.fluid;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum SimpleRNRFluid
{

    CONCRETE(0xFFDCDCDC);

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

    public boolean isTransparent()
    {
        return false;
    }

}
