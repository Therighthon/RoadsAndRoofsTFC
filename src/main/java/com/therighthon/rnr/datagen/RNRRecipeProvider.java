package com.therighthon.rnr.datagen;

import com.therighthon.afc.AFCHelpers;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.UniqueLogs;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.block.StoneBlockType;
import com.therighthon.rnr.common.fluid.RNRFluids;
import com.therighthon.rnr.common.fluid.SimpleRNRFluid;
import com.therighthon.rnr.common.item.AFCCompatItems;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.SandstoneBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.player.ChiselMode;
import net.dries007.tfc.common.recipes.BarrelRecipe;
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.KnappingRecipe;
import net.dries007.tfc.common.recipes.LandslideRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.DataGenerationHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.data.KnappingPattern;
import net.dries007.tfc.util.data.KnappingType;

// Much of this class was reproduced from TerraFirmaCraft's data generation
// Reproduced and modified from the original work on DEc 12, 2025
public class RNRRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    RecipeOutput output;
    HolderLookup.Provider lookup;

    public RNRRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput, HolderLookup.@NotNull Provider lookup)
    {
        this.lookup = lookup;
        return super.run(cachedOutput, lookup);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput)
    {
        this.output = recipeOutput;

        soils();
        miscRoads();
        stoneRoads();
        sandRoads();
        concreteRoads();
        roofs();
        misc();
    }

    private void soils()
    {
        // Tamping soils
        mattockAnyMode(BlockIngredient.of(TFCBlocks.PEAT_GRASS.get()), RNRBlocks.TAMPED_PEAT.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(TFCBlocks.PEAT.get()), RNRBlocks.TAMPED_PEAT.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(TFCBlocks.KAOLIN_CLAY_GRASS.get()), RNRBlocks.TAMPED_KAOLIN.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(TFCBlocks.WHITE_KAOLIN_CLAY.get()), RNRBlocks.TAMPED_KAOLIN.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(TFCBlocks.PINK_KAOLIN_CLAY.get()), RNRBlocks.TAMPED_KAOLIN.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(TFCBlocks.RED_KAOLIN_CLAY.get()), RNRBlocks.TAMPED_KAOLIN.get().defaultBlockState());

        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_OXISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.OXISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_OXISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.OXISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ARIDISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.ARIDISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ARIDISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.ARIDISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_PODZOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.PODZOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_PODZOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.PODZOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ENTISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.ENTISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ENTISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.ENTISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ALFISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.ALFISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ALFISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.ALFISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ANDISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.ANDISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_ANDISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.ANDISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_FLUVISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.FLUVISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_FLUVISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.FLUVISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_MOLLISOL), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(SoilBlockType.Variant.MOLLISOL).get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRTags.Blocks.TAMPABLE_MOLLISOL_MUD), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(SoilBlockType.Variant.MOLLISOL).get().defaultBlockState());

        // Amending soils
        blockMod(RNRBlocks.TAMPED_KAOLIN.get(), RNRItems.CRUSHED_BASE_COURSE.asItem(), RNRBlocks.BASE_COURSE.get().defaultBlockState());
        blockMod(RNRBlocks.TAMPED_PEAT.get(), RNRItems.CRUSHED_BASE_COURSE.asItem(), RNRBlocks.BASE_COURSE.get().defaultBlockState());
        for (SoilBlockType.Variant var : SoilBlockType.Variant.values())
        {
            blockMod(RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(var).get(), RNRItems.CRUSHED_BASE_COURSE.asItem(), RNRBlocks.BASE_COURSE.get().defaultBlockState());
            blockMod(RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED_MUD).get(var).get(), RNRItems.CRUSHED_BASE_COURSE.asItem(), RNRBlocks.TAMPED_SOILS.get(RNRBlocks.RNRSoilBlockType.TAMPED).get(var).get().defaultBlockState());
        }
    }

    private void miscRoads()
    {
        // Block mod placements
        blockMod(RNRBlocks.BASE_COURSE.get(), RNRItems.HOGGIN_MIX.asItem(), RNRBlocks.HOGGIN.get().defaultBlockState());
        blockMod(RNRBlocks.BASE_COURSE.get(), Items.BRICK, RNRBlocks.BRICK_ROAD.get().defaultBlockState());

        // Mattock and chisel shaping
        mattockAndChisel(BlockIngredient.of(RNRBlocks.HOGGIN.get()), RNRBlocks.HOGGIN_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.HOGGIN.get()), RNRBlocks.HOGGIN_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        mattockAndChisel(BlockIngredient.of(RNRBlocks.BRICK_ROAD.get()), RNRBlocks.BRICK_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.BRICK_ROAD.get()), RNRBlocks.BRICK_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
    }

    private void stoneRoads()
    {
        for (Rock rock : Rock.values())
        {
            final var blocks = TFCBlocks.ROCK_BLOCKS.get(rock);

            // Crafting input items
            recipe().damageInputs()
                .inputIsPrimary(TFCTags.Items.TOOLS_CHISEL)
                .input(blocks.get(Rock.BlockType.RAW))
                .shapeless(RNRItems.FLAGSTONE.get(rock).asItem(), 12);
            recipe().input(blocks.get(Rock.BlockType.GRAVEL).asItem()).shapeless(RNRItems.GRAVEL_FILL.get(rock).asItem(), 6);

            rockRoadMod(RNRItems.GRAVEL_FILL.get(rock).asItem(), rock, StoneBlockType.GRAVEL_ROAD);
            rockRoadMod(RNRItems.FLAGSTONE.get(rock).asItem(), rock, StoneBlockType.FLAGSTONES);
            rockRoadMod(TFCItems.BRICKS.get(rock).asItem(), rock, StoneBlockType.SETT_ROAD);
            rockRoadMod(TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).asItem(), rock, StoneBlockType.COBBLED_ROAD);
            rockRoadMod(TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).asItem(), rock, StoneBlockType.COBBLED_ROAD);

            // TODO: This should be grav->OG->mcad
            blockMod(RNRBlocks.ROCK_BLOCKS.get(rock).get(StoneBlockType.GRAVEL_ROAD).get(),
                RNRItems.GRAVEL_FILL.get(rock).asItem(),
                RNRBlocks.ROCK_BLOCKS.get(rock).get(StoneBlockType.MACADAM_ROAD).get().defaultBlockState());
            mattockAndChisel(BlockIngredient.of(RNRBlocks.ROCK_STAIRS.get(rock).get(StoneBlockType.MACADAM_ROAD).get()), RNRBlocks.ROCK_BLOCKS.get(rock).get(StoneBlockType.MACADAM_ROAD).get().defaultBlockState(), ChiselMode.STAIR.get());
            mattockAndChisel(BlockIngredient.of(RNRBlocks.ROCK_SLABS.get(rock).get(StoneBlockType.MACADAM_ROAD).get()), RNRBlocks.ROCK_BLOCKS.get(rock).get(StoneBlockType.MACADAM_ROAD).get().defaultBlockState(), ChiselMode.SLAB.get());
        }
    }

    private void sandRoads()
    {
        // Crafting input items
        for (SandBlockType color : SandBlockType.values())
        {
            recipe().damageInputs()
                .inputIsPrimary(TFCTags.Items.TOOLS_CHISEL)
                .input(TFCBlocks.SANDSTONE.get(SandstoneBlockType.SMOOTH).get(color).asItem())
                .shapeless(RNRItems.SANDSTONE_FLAGSTONE.get(color).asItem(), 8);
        }

        // Blockmod Placements and Mattock chiseling
        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BLACK).asItem(), RNRBlocks.BLACK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES.get()), RNRBlocks.BLACK_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.BLACK_SANDSTONE_FLAGSTONES.get()), RNRBlocks.BLACK_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.YELLOW).asItem(), RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES.get()), RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES.get()), RNRBlocks.YELLOW_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.RED).asItem(), RNRBlocks.RED_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.RED_SANDSTONE_FLAGSTONES.get()), RNRBlocks.RED_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.RED_SANDSTONE_FLAGSTONES.get()), RNRBlocks.RED_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.BROWN).asItem(), RNRBlocks.BROWN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES.get()), RNRBlocks.BROWN_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.BROWN_SANDSTONE_FLAGSTONES.get()), RNRBlocks.BROWN_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.WHITE).asItem(), RNRBlocks.WHITE_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES.get()), RNRBlocks.WHITE_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.WHITE_SANDSTONE_FLAGSTONES.get()), RNRBlocks.WHITE_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.GREEN).asItem(), RNRBlocks.GREEN_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES.get()), RNRBlocks.GREEN_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.GREEN_SANDSTONE_FLAGSTONES.get()), RNRBlocks.GREEN_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), RNRItems.SANDSTONE_FLAGSTONE.get(SandBlockType.PINK).asItem(), RNRBlocks.PINK_SANDSTONE_FLAGSTONES.get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.PINK_SANDSTONE_FLAGSTONES.get()), RNRBlocks.PINK_SANDSTONE_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.PINK_SANDSTONE_FLAGSTONES.get()), RNRBlocks.PINK_SANDSTONE_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

    }

    private void concreteRoads()
    {
        // Making concrete
        recipe().input(RNRItems.CRUSHED_BASE_COURSE).input(TFCItems.POWDERS.get(Powder.LIME).asItem()).shapeless(RNRItems.CONCRETE_POWDER);
        barrel()
            .input(RNRItems.CONCRETE_POWDER)
            .input(Fluids.WATER, 100)
            .output(RNRFluids.SIMPLE_RNR_FLUIDS.get(SimpleRNRFluid.CONCRETE).getSource(), 100)
            .instant();

        // Have to have custom landslide recipes for wet concrete
        // TODO: other landslide blocks should just need the tag
        add(new LandslideRecipe(BlockIngredient.of(
            RNRBlocks.WET_CONCRETE_ROAD.get(),
            RNRBlocks.WET_CONCRETE_ROAD_PANEL.get(),
            RNRBlocks.WET_CONCRETE_ROAD_SETT.get(),
            RNRBlocks.WET_CONCRETE_ROAD_FLAGSTONES.get(),
            RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get(),
            RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get()
        ), RNRBlocks.BASE_COURSE.get().defaultBlockState()));


        // TODO: Make a concrete pouring recipe that uses liquids properly
        //  ORIGINAL: block_mod_recipe(rm, 'pouring_concrete', fluid_item_ingredient('1000 rnr:concrete'), 'rnr:base_course', 'rnr:pouring_concrete_road')

        // Control joints and smoothing footsteps
        mattockAnyMode(BlockIngredient.of(RNRBlocks.WET_CONCRETE_ROAD.get()), RNRBlocks.WET_CONCRETE_ROAD_CONTROL_JOINT.get().defaultBlockState());
        mattockAnyMode(BlockIngredient.of(RNRBlocks.TRODDEN_WET_CONCRETE_ROAD.get()), RNRBlocks.WET_CONCRETE_ROAD.get().defaultBlockState());

        // Stamping wet concrete
        concreteBlockMod(RNRBlocks.WET_CONCRETE_ROAD.get(), Tags.Items.BRICKS, RNRBlocks.WET_CONCRETE_ROAD_SETT.get().defaultBlockState());
        concreteBlockMod(RNRBlocks.WET_CONCRETE_ROAD.get(), RNRTags.Items.FLAGSTONE_ROAD_ITEMS, RNRBlocks.WET_CONCRETE_ROAD_FLAGSTONES.get().defaultBlockState());
        concreteBlockMod(RNRBlocks.WET_CONCRETE_ROAD.get(), TFCTags.Items.STONES_SMOOTH, RNRBlocks.WET_CONCRETE_ROAD_PANEL.get().defaultBlockState());

        // Mattock shaping Stairs and slabs
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD.get()), RNRBlocks.CONCRETE_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD.get()), RNRBlocks.CONCRETE_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get()), RNRBlocks.CONCRETE_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_CONTROL_JOINT.get()), RNRBlocks.CONCRETE_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_PANEL.get()), RNRBlocks.CONCRETE_ROAD_PANEL_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_PANEL.get()), RNRBlocks.CONCRETE_ROAD_PANEL_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_SETT.get()), RNRBlocks.CONCRETE_ROAD_SETT_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_SETT.get()), RNRBlocks.CONCRETE_ROAD_SETT_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get()), RNRBlocks.CONCRETE_ROAD_FLAGSTONES_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CONCRETE_ROAD_FLAGSTONES.get()), RNRBlocks.CONCRETE_ROAD_FLAGSTONES_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CRACKED_CONCRETE_ROAD.get()), RNRBlocks.CRACKED_CONCRETE_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CRACKED_CONCRETE_ROAD.get()), RNRBlocks.CRACKED_CONCRETE_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.TRODDEN_CONCRETE_ROAD.get()), RNRBlocks.TRODDEN_CONCRETE_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.TRODDEN_CONCRETE_ROAD.get()), RNRBlocks.TRODDEN_CONCRETE_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD.get()), RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_STAIRS.get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD.get()), RNRBlocks.CRACKED_TRODDEN_CONCRETE_ROAD_SLAB.get().defaultBlockState(), ChiselMode.SLAB.get());

    }

    private void roofs()
    {
        // Shingling recipes
        roofMod(TFCItems.STRAW.asItem(), RNRBlocks.THATCH_ROOF.get(), RNRBlocks.THATCH_ROOF_STAIRS.get(), RNRBlocks.THATCH_ROOF_SLAB.get());
        roofMod(RNRItems.CERAMIC_ROOF_TILE.asItem(), RNRBlocks.CERAMIC_ROOF.get(), RNRBlocks.CERAMIC_ROOF_STAIRS.get(), RNRBlocks.CERAMIC_ROOF_SLAB.get());
        roofMod(RNRItems.TERRACOTTA_ROOF_TILE.asItem(), RNRBlocks.TERRACOTTA_ROOF.get(), RNRBlocks.TERRACOTTA_ROOF_STAIRS.get(), RNRBlocks.TERRACOTTA_ROOF_SLAB.get());

        //todo Crafting recipes (stairs and slabs)

        //todo Chiseling recipes (stairs and slabs)

        // TODO: Note that shingling needs to be for slabs and stairs too
        // Wood recipes (Crafting and placing shingles, todo: all crafting, chiseling)
        for (Wood wood : Wood.values())
        {
            recipe().damageInputs()
                .inputIsPrimary(TFCTags.Items.TOOLS_CHISEL)
                .input(TFCBlocks.WOODS.get(Wood.BlockType.LOG).get(wood).asItem())
                .shapeless(RNRItems.WOOD_SHINGLE.get(wood).asItem(), 12);
        }

        // TODO: Make this go to the right folder
        // Wood recipes (Crafting and placing shingles, todo: all crafting, chiseling stairs/slabs)
        for (AFCWood wood : AFCWood.values())
        {
            recipe().damageInputs()
                .inputIsPrimary(TFCTags.Items.TOOLS_CHISEL)
                .input(AFCBlocks.WOODS.get(Wood.BlockType.LOG).get(wood).asItem())
                .shapeless(AFCCompatItems.WOOD_SHINGLE.get(wood).asItem(), 12);
        }
    }

    private void misc()
    {
        // TODO: Mattock forging and crafting

        recipe()
            .input(Tags.Items.GRAVELS)
            .input(Tags.Items.SANDS)
            .input(Items.CLAY_BALL)
            .shapeless(RNRItems.HOGGIN_MIX.asItem(), 8);
        recipe().damageInputs()
            .input(Tags.Items.GRAVELS)
            .input(TFCTags.Items.STONES_LOOSE)
            .inputIsPrimary(TFCTags.Items.TOOLS_HAMMER).shapeless(RNRItems.HOGGIN_MIX.asItem(), 8);

        clayKnapping("0", RNRItems.UNFIRED_ROOF_TILE, 2, false, "XXXXX", "X   X", "     ", "XXXXX", "X   X");
        clayKnapping("1", RNRItems.UNFIRED_ROOF_TILE, 1, false, "XXXXX", "X   X");
        recipe().input(RNRItems.UNFIRED_ROOF_TILE, 8).input(TFCItems.ORE_POWDERS.get(Ore.HEMATITE).asItem()).shapeless(RNRItems.UNFIRED_TERRACOTTA_ROOF_TILE, 8);

        float POTTERY = 1399f;
        // TODO: Heat defs
        addHeatingRecipe(RNRItems.UNFIRED_ROOF_TILE, RNRItems.CERAMIC_ROOF_TILE, POTTERY);
        addHeatingRecipe(RNRItems.UNFIRED_TERRACOTTA_ROOF_TILE, RNRItems.TERRACOTTA_ROOF_TILE, POTTERY);

        recipe().pattern("XYX", "Y Y", "XYX").input('X', TFCTags.Items.LUMBER).input('Y', Tags.Items.RODS_WOODEN).shaped(RNRBlocks.ROOF_FRAME.asItem(), 6);
        recipe()
            .input('B', RNRBlocks.ROOF_FRAME.asItem())
            .pattern("BBB")
            .shaped(RNRBlocks.ROOF_FRAME_SLAB.asItem(), 6);
        recipe()
            .input('B', RNRBlocks.ROOF_FRAME.asItem())
            .pattern("B  ", "BB ", "BBB")
            .shaped(RNRBlocks.ROOF_FRAME_STAIRS.asItem(), 8);

        // TODO: chisel equivs of above stair./slab
    }

    private void clayKnapping(String suffix, ItemLike output, int count, boolean defaultOn, String... pattern)
    {
        add(nameOf(output) + (suffix.isEmpty() ? "" : "_" + suffix), new KnappingRecipe(
            KnappingType.MANAGER.getCheckedReference(Helpers.identifier("clay")),
            KnappingPattern.from(defaultOn, pattern),
            Optional.empty(),
            new ItemStack(output, count)
        ));
        // Un-crafting, only for non-suffixed recipes
        if (suffix.isEmpty()) new DataGenerationHelpers.Builder((name, r) -> add(nameOf(output) + "_to_clay", r))
            .input(output)
            .shapeless(Items.CLAY_BALL, 5 / count);
    }

    private Ingredient ingredientOf(Metal metal, Metal.BlockType type)
    {
        return type == Metal.BlockType.BLOCK
            ? Ingredient.of(storageBlockTagOf(Registries.ITEM, metal))
            : Ingredient.of(TFCBlocks.METALS.get(metal).get(type).get());
    }

    private Ingredient ingredientOf(Metal metal, Metal.ItemType type)
    {
        return type.isCommonTagPart()
            ? Ingredient.of(commonTagOf(metal, type))
            : Ingredient.of(TFCItems.METAL_ITEMS.get(metal).get(type).get());
    }

    private TagKey<Item> commonTagOf(Metal metal, Metal.ItemType type)
    {
        assert type.isCommonTagPart() : "Non-typical use of tag for " + metal.getSerializedName() + " / " + type.name();
        assert type.has(metal) : "Non-typical use of " + metal.getSerializedName() + " / " + type.name();
        return commonTagOf(Registries.ITEM, type.name() + "s/" + metal.name());
    }

    private <T> TagKey<T> commonTagOf(ResourceKey<Registry<T>> key, String name)
    {
        return TagKey.create(key, ResourceLocation.fromNamespaceAndPath("c", name.toLowerCase(Locale.ROOT)));
    }

    private <T> TagKey<T> woodLogsTagOf(ResourceKey<Registry<T>> registry, AFCWood wood)
    {
        return TagKey.create(registry, AFCHelpers.modIdentifier(wood.getSerializedName() + "_logs"));
    }

    private <T> TagKey<T> uniqueLogsTagOf(ResourceKey<Registry<T>> registry, UniqueLogs wood)
    {
        return TagKey.create(registry, AFCHelpers.modIdentifier(wood.getSerializedName() + "_logs"));
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output.
     */
    private DataGenerationHelpers.Builder recipe()
    {
        return new DataGenerationHelpers.Builder((name, r) -> {
            if (name != null) add(name, r);
            else add(r);
        });
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output, plus a suffix. The suffix should not start with an underscore.
     */
    private DataGenerationHelpers.Builder recipe(String suffix)
    {
        return new DataGenerationHelpers.Builder((name, r) -> {
            assert !suffix.startsWith("_") : "recipe(String suffix) shouldn't start with an '_', it is added for you!";
            assert name == null : "Cannot use a named recipe and recipe(String suffix) at the same time!";
            add(nameOf(r.getResultItem(lookup).getItem()) + "_" + suffix, r);
        });
    }

    private void add(Recipe<?> recipe)
    {
        add(nameOf(recipe), recipe);
    }

    private void add(String name, Recipe<?> recipe)
    {
        add(Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), "No recipe type").getPath(), name, recipe);
    }

    private void add(String prefix, String name, Recipe<?> recipe)
    {
        output.accept(AFCHelpers.modIdentifier((prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }

    private String nameOf(Recipe<?> recipe)
    {
        return nameOf(recipe.getResultItem(lookup).getItem());
    }

    private String nameOf(Ingredient ingredient)
    {
        if (ingredient.getCustomIngredient() instanceof CompoundIngredient ing) return nameOf(ing.children().get(0));
        final Ingredient.Value value = ingredient.getValues()[0];
        if (value instanceof Ingredient.TagValue(TagKey<Item> tag)) return tag.location().getPath();
        if (value instanceof Ingredient.ItemValue(ItemStack item)) return nameOf(item.getItem());
        throw new AssertionError("Unknown ingredient value");
    }

    private String nameOf(Fluid fluid)
    {
        assert fluid != Fluids.EMPTY : "Should never get name of Items.AIR";
        return BuiltInRegistries.FLUID.getKey(fluid).getPath();
    }

    private String nameOf(ItemLike item)
    {
        assert item.asItem() != Items.AIR : "Should never get name of Items.AIR";
        assert item.asItem() != Items.BARRIER : "Should never get name of Items.BARRIER";
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private <T> TagKey<T> storageBlockTagOf(ResourceKey<Registry<T>> key, Metal metal)
    {
        assert metal.defaultParts() : "Non-typical use of a non-default metal " + metal.getSerializedName();
        return commonTagOf(key, "storage_blocks/" + metal.getSerializedName());
    }

    private void addHeatingRecipe(ItemLike input, ItemLike output, float temperature)
    {
        addHeatingRecipe(Ingredient.of(input), ItemStackProvider.of(output), temperature);
    }

    private void addHeatingRecipe(Ingredient input, ItemStackProvider output, float temperature)
    {
        add(new HeatingRecipe(input, output, FluidStack.EMPTY, temperature, false));
    }

    private BarrelRecipe.Builder barrel()
    {
        return new BarrelRecipe.Builder(r -> {
            if (!r.getResultItem().isEmpty()) add("barrel", nameOf(r.getResultItem().getItem()), r);
            else if (!r.getOutputFluid().isEmpty()) add("barrel", nameOf(r.getOutputFluid().getFluid()), r);
            else throw new IllegalStateException("Barrel recipe requires a custom name!");
        });
    }

    private void roofMod(Item itemIn, Block blockOut, Block stairOut, Block slabOut)
    {
        blockMod(BlockIngredient.of(RNRBlocks.ROOF_FRAME.get()), itemIn, blockOut.defaultBlockState(), true);
        blockMod(BlockIngredient.of(RNRBlocks.ROOF_FRAME_STAIRS.get()), itemIn, stairOut.defaultBlockState(), true);
        blockMod(BlockIngredient.of(RNRBlocks.ROOF_FRAME_SLAB.get()), itemIn, slabOut.defaultBlockState(), true);
    }

    private void rockRoadMod(Item itemIn, Rock rock, StoneBlockType roadType)
    {
        blockMod(BlockIngredient.of(RNRBlocks.BASE_COURSE.get()), itemIn, RNRBlocks.ROCK_BLOCKS.get(roadType).get(rock).get().defaultBlockState(), true);
        mattockAndChisel(BlockIngredient.of(RNRBlocks.ROCK_BLOCKS.get(roadType).get(rock).get()), RNRBlocks.ROCK_STAIRS.get(roadType).get(rock).get().defaultBlockState(), ChiselMode.STAIR.get());
        mattockAndChisel(BlockIngredient.of(RNRBlocks.ROCK_BLOCKS.get(roadType).get(rock).get()), RNRBlocks.ROCK_SLABS.get(roadType).get(rock).get().defaultBlockState(), ChiselMode.SLAB.get());
    }
    private void concreteBlockMod(Block blockIn, TagKey<Item> itemIn, BlockState output)
    {
        blockMod(BlockIngredient.of(blockIn), Ingredient.of(itemIn), output, false);
    }

    private void blockMod(Block blockIn, Item itemIn, BlockState blockOut)
    {
        blockMod(BlockIngredient.of(blockIn), itemIn, blockOut, true);
    }

    private void blockMod(BlockIngredient blockIn, Item itemIn, BlockState blockOut, boolean consume)
    {
        add(new BlockModRecipe(Ingredient.of(itemIn), blockIn, blockOut, consume));
    }

    private void blockMod(BlockIngredient blockIn, Ingredient itemIn, BlockState blockOut, boolean consume)
    {
        add(new BlockModRecipe(itemIn, blockIn, blockOut, consume));
    }

    private void mattockAnyMode(BlockIngredient in, BlockState out)
    {
        mattock(in, out, ChiselMode.SMOOTH.get());
        mattock(in, out, ChiselMode.STAIR.get());
        mattock(in, out, ChiselMode.SLAB.get());
    }

    private void mattock(BlockIngredient in, BlockState out, ChiselMode mode)
    {
        add(new MattockRecipe(in, out, mode, null));
    }

    private void mattockAndChisel(BlockIngredient in, BlockState out, ChiselMode mode)
    {
        add(new MattockRecipe(in, out, mode, null));
        add(new ChiselRecipe(in, out, mode, ItemStackProvider.empty()));
    }

    private void chisel(BlockIngredient in, BlockState out, ChiselMode mode)
    {
        add(new ChiselRecipe(in, out, mode, ItemStackProvider.empty()));
    }

}