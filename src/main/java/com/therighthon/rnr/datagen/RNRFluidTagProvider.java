package com.therighthon.rnr.datagen;

import com.therighthon.rnr.common.fluid.RNRFluids;
import com.therighthon.rnr.common.fluid.SimpleRNRFluid;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import static net.dries007.tfc.common.TFCTags.Fluids.*;

public class RNRFluidTagProvider extends FluidTagsProvider
{
    public RNRFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(USABLE_IN_BARREL).add(RNRFluids.SIMPLE_RNR_FLUIDS.get(SimpleRNRFluid.CONCRETE).getSource());
    }
}
