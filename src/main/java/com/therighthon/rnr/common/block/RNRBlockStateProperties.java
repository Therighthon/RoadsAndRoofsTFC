package com.therighthon.rnr.common.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RNRBlockStateProperties
{
    public static IntegerProperty CONCRETE_LEVEL = IntegerProperty.create("concrete_level", 0, 3);
    public static IntegerProperty DISTANCE_X = IntegerProperty.create("distance_x", 0, 3);
    public static IntegerProperty DISTANCE_Z = IntegerProperty.create("distance_z", 0, 3);
    public static BooleanProperty NORTH_EAST = BooleanProperty.create("connects_north_or_east");
    public static BooleanProperty SOUTH_WEST = BooleanProperty.create("connects_south_or_west");
}
