package com.therighthon.rnr.common.fluid;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import com.therighthon.rnr.RoadsAndRoofs;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.item.RNRItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.dries007.tfc.common.fluids.FluidHolder;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

public class RNRFluids
{
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, RoadsAndRoofs.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, RoadsAndRoofs.MOD_ID);

    /**
     * A mask for fluid color - most fluids should be using this
     */
    public static final int ALPHA_MASK = 0xFF000000;

    /**
     * Fluid instances
     */
    public static final Map<SimpleRNRFluid, FluidHolder<BaseFlowingFluid>> SIMPLE_RNR_FLUIDS = Helpers.mapOf(SimpleRNRFluid.class, fluid -> register(
        fluid.getId(),
        properties -> properties
            .block(RNRBlocks.SIMPLE_FLUIDS.get(fluid))
            .bucket(RNRItems.FLUID_BUCKETS.get(RNRFluidId.asType(fluid))),
        waterLike()
            .descriptionId("fluid.rnr." + fluid.getId())
            .canConvertToSource(false),
        BaseFlowingFluid.Source::new,
        BaseFlowingFluid.Flowing::new
    ));

    private static <F extends FlowingFluid> FluidHolder<F> register(String name, Consumer<BaseFlowingFluid.Properties> builder, FluidType.Properties typeProperties, Function<BaseFlowingFluid.Properties, F> sourceFactory, Function<BaseFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new FluidType(typeProperties), sourceFactory, flowingFactory);
    }

    private static FluidType.Properties waterLike()
    {
        return FluidType.Properties.create()
            .adjacentPathType(PathType.WATER)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .canConvertToSource(true)
            .canDrown(true)
            .canExtinguish(true)
            .canHydrate(true)
            .canPushEntity(true)
            .canSwim(true)
            .supportsBoating(true);
    }

}

