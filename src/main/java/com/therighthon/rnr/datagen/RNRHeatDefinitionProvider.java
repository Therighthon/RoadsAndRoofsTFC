package com.therighthon.rnr.datagen;

import com.therighthon.afc.datagen.DataManagerProvider;
import com.therighthon.rnr.common.item.RNRItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.heat.HeatDefinition;
import net.dries007.tfc.util.Metal;

public class RNRHeatDefinitionProvider extends DataManagerProvider<HeatDefinition>
{
    public static final float HEAT_CAPACITY = 0.003f;

    public RNRHeatDefinitionProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(HeatCapability.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider)
    {
        for (Metal metal : Metal.values())
        {
            if (metal.allParts())
            {
                add(metal.getSerializedName().toLowerCase() + "/mattock", Ingredient.of(RNRItems.MATTOCKS.get(metal).asItem()), metal, 100);
                add(metal.getSerializedName().toLowerCase() + "/mattock_head", Ingredient.of(RNRItems.MATTOCK_HEADS.get(metal).asItem()), metal, 100);
            }
        }
        add("unfired_roof_tile", RNRItems.UNFIRED_ROOF_TILE, 0.25f);
        add("unfired_terracotta_roof_tile", RNRItems.UNFIRED_TERRACOTTA_ROOF_TILE, 0.25f);
    }

    private void add(String name, ItemLike item, float heatCapacity)
    {
        add(name, new HeatDefinition(Ingredient.of(item), heatCapacity, 0f, 0f));
    }

    private void add(String name, Ingredient ingredient, Metal metal, int units)
    {
        add(name, new HeatDefinition(
            ingredient,
            (0.35f / HEAT_CAPACITY) * (units / 100f), // All tool metals have a unit heat capacity of 0.35
            getMeltTemp(metal) * 0.6f,
            getMeltTemp(metal) * 0.8f));
    }

    public static float getMeltTemp(Metal metal)
    {
        return switch (metal)
        {
            case Metal.COPPER -> 1080f;
            case Metal.BRONZE -> 950f;
            case Metal.BISMUTH_BRONZE -> 985f;
            case Metal.BLACK_BRONZE -> 1070f;
            case Metal.WROUGHT_IRON -> 1535f;
            case Metal.STEEL -> 1540f;
            case Metal.BLACK_STEEL, Metal.BLUE_STEEL, Metal.RED_STEEL -> 1540f;
            default -> 1000f;
        };
    }
}
