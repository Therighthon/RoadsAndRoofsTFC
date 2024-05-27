package com.therighthon.rnr.common;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.RegistryObject;

public record VariantBlockRegistryObject(RegistryObject<? extends SlabBlock> slab, RegistryObject<? extends StairBlock> stair) {
    public VariantBlockRegistryObject(RegistryObject<? extends SlabBlock> slab, RegistryObject<? extends StairBlock> stair) {
        this.slab = slab;
        this.stair = stair;
    }

    public RegistryObject<? extends SlabBlock> slab() {
        return this.slab;
    }

    public RegistryObject<? extends StairBlock> stair() {
        return this.stair;
    }
}
