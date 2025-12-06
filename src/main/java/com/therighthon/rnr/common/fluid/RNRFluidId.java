package com.therighthon.rnr.common.fluid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.material.Fluid;

public record RNRFluidId(String name, OptionalInt color, Supplier<? extends Fluid> fluid)
{
    private static final Map<Enum<?>, RNRFluidId> IDENTITY = new HashMap<>();
    private static final List<RNRFluidId> VALUES = Stream.of(
            Arrays.stream(SimpleRNRFluid.values()).map(fluid -> fromEnum(fluid, fluid.getColor(), fluid.getId(), RNRFluids.SIMPLE_RNR_FLUIDS.get(fluid).source()))
        )
        .flatMap(Function.identity())
        .toList();

    public static <R> Map<RNRFluidId, R> mapOf(Function<? super RNRFluidId, ? extends R> map)
    {
        return VALUES.stream().collect(Collectors.toMap(Function.identity(), map));
    }

    public static RNRFluidId asType(Enum<?> identity)
    {
        return IDENTITY.get(identity);
    }

    private static RNRFluidId fromEnum(Enum<?> identity, int color, String name, Supplier<? extends Fluid> fluid)
    {
        final RNRFluidId type = new RNRFluidId(name, OptionalInt.of(RNRFluids.ALPHA_MASK | color), fluid);
        IDENTITY.put(identity, type);
        return type;
    }
}
