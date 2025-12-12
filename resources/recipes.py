#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from enum import Enum
from itertools import repeat
from typing import Union

from mcresources import ResourceManager, RecipeContext, utils, loot_tables
from mcresources.type_definitions import ResourceIdentifier, Json, JsonObject, ResourceLocation, ResourceIdentifier, TypeWithOptionalConfig

from constants import *


class Rules(Enum):
    hit_any = 'hit_any'
    hit_not_last = 'hit_not_last'
    hit_last = 'hit_last'
    hit_second_last = 'hit_second_last'
    hit_third_last = 'hit_third_last'
    draw_any = 'draw_any'
    draw_last = 'draw_last'
    draw_not_last = 'draw_not_last'
    draw_second_last = 'draw_second_last'
    draw_third_last = 'draw_third_last'
    punch_any = 'punch_any'
    punch_last = 'punch_last'
    punch_not_last = 'punch_not_last'
    punch_second_last = 'punch_second_last'
    punch_third_last = 'punch_third_last'
    bend_any = 'bend_any'
    bend_last = 'bend_last'
    bend_not_last = 'bend_not_last'
    bend_second_last = 'bend_second_last'
    bend_third_last = 'bend_third_last'
    upset_any = 'upset_any'
    upset_last = 'upset_last'
    upset_not_last = 'upset_not_last'
    upset_second_last = 'upset_second_last'
    upset_third_last = 'upset_third_last'
    shrink_any = 'shrink_any'
    shrink_last = 'shrink_last'
    shrink_not_last = 'shrink_not_last'
    shrink_second_last = 'shrink_second_last'
    shrink_third_last = 'shrink_third_last'


def generate(rm: ResourceManager, afc_rm: ResourceManager):
    def craft_decorations(recipe_name: str, base_block: str, has_wall: bool = True):
        crafting_shaped(rm, recipe_name + '_slab', ['XXX'], base_block, (6, base_block + '_slab'))
        crafting_shaped(rm, recipe_name + '_stairs', ['X  ', 'XX ', 'XXX'], base_block, (8, base_block + '_stairs'))
        if has_wall:
            crafting_shaped(rm, recipe_name + '_wall', ['XXX', 'XXX'], base_block, (6, base_block + '_wall'))

    def afc_craft_decorations(recipe_name: str, base_block: str, has_wall: bool = True):
        crafting_shaped(afc_rm, recipe_name + '_slab', ['XXX'], base_block, (6, base_block + '_slab'))
        crafting_shaped(afc_rm, recipe_name + '_stairs', ['X  ', 'XX ', 'XXX'], base_block, (8, base_block + '_stairs'))
        if has_wall:
           crafting_shaped(afc_rm, recipe_name + '_wall', ['XXX', 'XXX'], base_block, (6, base_block + '_wall'))

    for rock in ROCKS:
        for type in STONE_PATHS:

            # Loot
            for suffix in SUFFIXES:
                rm.block(('rock', type, '%s%s' % (rock, suffix))).with_block_loot(({
                    'name': 'rnr:rock/%s/%s%s' % (type, rock, suffix)
                }))

        rm.block(('rock', 'over_height_gravel', rock)).with_block_loot(({
            'name': 'rnr:rock/%s/%s' % ('over_height_gravel', rock)
        }))

    for sand in SAND_BLOCK_TYPES:
        for suffix in SUFFIXES:
            rm.block((sand + '_sandstone_flagstones' + suffix)).with_block_loot(({
                'name': 'rnr:%s_sandstone_flagstones%s' % (sand, suffix)
            }))

    rm.block(('base_course')).with_block_loot(({
        'name': 'rnr:base_course'
    }))
    rm.block(('brick_road')).with_block_loot(({
        'name': 'rnr:brick_road'
    }))
    rm.block(('brick_road_slab')).with_block_loot(({
        'name': 'rnr:brick_road_slab'
    }))
    rm.block(('brick_road_stairs')).with_block_loot(({
        'name': 'rnr:brick_road_stairs'
    }))
    rm.block(('hoggin')).with_block_loot(({
        'name': 'rnr:hoggin'
    }))
    rm.block(('hoggin_slab')).with_block_loot(({
        'name': 'rnr:hoggin_slab'
    }))
    rm.block(('hoggin_stairs')).with_block_loot(({
        'name': 'rnr:hoggin_stairs'
    }))
    for dirt in SOIL_BLOCK_VARIANTS:
        rm.block(('tamped_' + dirt)).with_block_loot(({
            'name': 'tfc:dirt/%s' % dirt
        }))
        rm.block(('tamped_' + dirt + '_mud')).with_block_loot(({
            'name': 'tfc:mud/%s' % dirt
        }))

    # TRIGGERS include raw, hardened, and ores
    # STARTS include raw, ores
    # COLLAPSIBLE includes raw, hardened, ores (lossy), bricks, smooth, spikes (special)
    # NOT SOLID SUPPORTING includes blocks that don't count as a solid block below for support purposes, which is just smooth + all slabs, stairs, etc.
    for wood in WOODS.keys():
        rm.block_loot('rnr:wood/shingles/%s' % wood, 'rnr:wood/shingles/%s' % wood)
        slab_loot(rm, 'rnr:wood/shingles/%s_slab' % wood)
        rm.block_loot('rnr:wood/shingles/%s_stairs' % wood, 'rnr:wood/shingles/%s_stairs' % wood)

    for wood in AFC_WOODS.keys():
        afc_rm.block_loot('rnr:wood/shingles/%s' % wood, 'rnr:wood/shingles/%s' % wood)
        slab_loot(afc_rm, 'rnr:wood/shingles/%s_slab' % wood)
        afc_rm.block_loot('rnr:wood/shingles/%s_stairs' % wood, 'rnr:wood/shingles/%s_stairs' % wood)

    rm.block_loot('rnr:ceramic_roof', 'rnr:ceramic_roof')
    slab_loot(rm, 'rnr:ceramic_roof_slab')
    rm.block_loot('rnr:ceramic_roof_stairs', 'rnr:ceramic_roof_stairs')

    rm.block_loot('rnr:terracotta_roof', 'rnr:terracotta_roof')
    slab_loot(rm, 'rnr:terracotta_roof_slab')
    rm.block_loot('rnr:terracotta_roof_stairs', 'rnr:terracotta_roof_stairs')

    rm.block_loot('rnr:thatch_roof', 'rnr:thatch_roof')
    slab_loot(rm, 'rnr:thatch_roof_slab')
    rm.block_loot('rnr:thatch_roof_stairs', 'rnr:thatch_roof_stairs')

    rm.block_loot('rnr:roof_frame', 'rnr:roof_frame')
    slab_loot(rm, 'rnr:roof_frame_slab')
    rm.block_loot('rnr:roof_frame_stairs', 'rnr:roof_frame_stairs')

    def self_loot(rm: ResourceManager, block: str):
        rm.block_loot(block, block)

    self_loot(rm, 'rnr:concrete_road')
    self_loot(rm, 'rnr:concrete_road_slab')
    self_loot(rm, 'rnr:concrete_road_stairs')
    self_loot(rm, 'rnr:concrete_road_panel')
    self_loot(rm, 'rnr:concrete_road_panel_slab')
    self_loot(rm, 'rnr:concrete_road_panel_stairs')
    self_loot(rm, 'rnr:concrete_road_sett')
    self_loot(rm, 'rnr:concrete_road_sett_slab')
    self_loot(rm, 'rnr:concrete_road_sett_stairs')
    self_loot(rm, 'rnr:concrete_road_flagstones')
    self_loot(rm, 'rnr:concrete_road_flagstones_slab')
    self_loot(rm, 'rnr:concrete_road_flagstones_stairs')
    self_loot(rm, 'rnr:trodden_concrete_road')
    self_loot(rm, 'rnr:trodden_concrete_road_slab')
    self_loot(rm, 'rnr:trodden_concrete_road_stairs')
    self_loot(rm, 'rnr:cracked_trodden_concrete_road')
    self_loot(rm, 'rnr:cracked_trodden_concrete_road_slab')
    self_loot(rm, 'rnr:cracked_trodden_concrete_road_stairs')
    self_loot(rm, 'rnr:cracked_concrete_road')
    self_loot(rm, 'rnr:cracked_concrete_road_slab')
    self_loot(rm, 'rnr:cracked_concrete_road_stairs')
    self_loot(rm, 'rnr:concrete_road_control_joint')

    rm.block_loot('rnr:wet_concrete_road', 'rnr:base_course')
    rm.block_loot('rnr:wet_concrete_road_panel', 'rnr:base_course')
    rm.block_loot('rnr:wet_concrete_road_sett', 'rnr:base_course')
    rm.block_loot('rnr:wet_concrete_road_flagstones', 'rnr:base_course')
    rm.block_loot('rnr:wet_concrete_road_control_joint', 'rnr:base_course')
    rm.block_loot('rnr:trodden_wet_concrete_road', 'rnr:base_course')
    rm.block_loot('rnr:pouring_concrete_road', 'rnr:base_course')

# Overrides

# Change recipe->recipes
def crafting_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    """
    Creates a shapeless crafting recipe.
    :param name_parts: The resource location, including path elements.
    :param ingredients: The ingredients.
    :param result: The result of crafting the recipe.
    :param group: The group.
    :param conditions: Any conditions for the recipe to be enabled.
    """
    res = utils.resource_location(rm.domain, name_parts)
    rm.write(('data', res.domain, 'recipe', res.path), {
        'type': 'minecraft:crafting_shapeless',
        'group': group,
        'ingredients': utils.ingredient_list(ingredients),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)

def crafting_shaped(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    """
    Creates a shaped crafting recipe.
    :param name_parts: The resource location, including path elements.
    :param pattern: The pattern for the recipe.
    :param ingredients: The ingredients, indexed by key in pattern.
    :param result: The result of crafting the recipe.
    :param group: The group.
    :param conditions: Any conditions for the recipe to be enabled.
    """
    utils.validate_crafting_pattern(pattern)
    res = utils.resource_location(rm.domain, name_parts)
    rm.write(('data', res.domain, 'recipe', res.path), {
        'type': 'minecraft:crafting_shaped',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)

def recipe(rm: ResourceManager, name_parts: ResourceIdentifier, type_in: Optional[str], data_in: JsonObject, group: Optional[str] = None, conditions: Json = None) -> RecipeContext:
    """
    Creates a non-crafting recipe file, used for custom mod recipes using vanilla's data pack system
    :param name_parts: The resource location, including path elements.
    :param type_in: The type of the recipe.
    :param data_in: Data required by the recipe, as present in json
    :param group: The group.
    :param conditions: Any conditions for the recipe to be enabled.
    """
    res = utils.resource_location(rm.domain, name_parts)
    rm.write(('data', res.domain, 'recipe', res.path), {
        'type': type_in,
        'group': group,
        **data_in,
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)

# TFC Stuff

def simple_pot_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredients: Json, fluid: str, output_fluid: str = None, output_items: Json = None, duration: int = 2000, temp: int = 300):
    recipe(rm, ('pot', name_parts), 'tfc:pot', {
        'ingredients': ingredients,
        'fluid_ingredient': fluid_stack_ingredient(fluid),
        'duration': duration,
        'temperature': temp,
        'fluid_output': fluid_stack(output_fluid) if output_fluid is not None else None,
        'item_output': [utils.item_stack(item) for item in output_items] if output_items is not None else None
    })

def disable_recipe(rm: ResourceManager, name_parts: ResourceIdentifier):
    # noinspection PyTypeChecker
    recipe(rm, name_parts, None, {}, conditions='neoforge:never')


def collapse_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient, result: Optional[utils.Json] = None, copy_input: Optional[bool] = None):
    assert result is not None or copy_input
    recipe(rm, ('collapse', name_parts), 'tfc:collapse', {
        'ingredient': ingredient,
        'result': result,
        'copy_input': copy_input
    })


def landslide_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, result: utils.Json):
    recipe(rm, ('landslide', name_parts), 'tfc:landslide', {
        'ingredient': ingredient,
        'result': result
    })


def simple_landslide_recipe(rm: ResourceManager, block: str, mod_id: str = "rnr"):
    landslide_recipe(rm, block, mod_id + ":" + block, mod_id + ":" + block)

def simple_road_landslide_recipe(rm: ResourceManager, block: str, mod_id: str = "rnr"):
    simple_landslide_recipe(rm, block, mod_id)
    simple_landslide_recipe(rm, block + "_slab", mod_id)
    landslide_recipe(rm, block + "_stairs", mod_id + ":" + block + "_stairs", mod_id + ":" + block + "_slab")

def simple_concrete_road_landslide_recipe(rm: ResourceManager, block: str, mod_id: str = "rnr"):
    landslide_recipe(rm, block, mod_id + ":" + block, "rnr:cracked_concrete_road")
    landslide_recipe(rm, block + "_slab", mod_id + ":" + block + "_slab", "rnr:cracked_concrete_road_slab")
    landslide_recipe(rm, block + "_stairs", mod_id + ":" + block + "_stairs", "rnr:cracked_concrete_road_slab")


def chisel_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, result: str, mode: str):
    recipe(rm, ('chisel', mode, name_parts), 'tfc:chisel', {
        'ingredient': ingredient,
        'result': result,
        'mode': mode,
        'extra_drop': item_stack_provider(result) if mode == 'slab' else None
    })


def mattock_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, result: str, mode: str):
    recipe(rm, ('mattock', mode, name_parts), 'rnr:mattock', {
        'ingredient': ingredient,
        'result': result,
        'mode': mode,
        'extra_drop': None
    })


def block_mod_recipe_not_consumed(rm: ResourceManager, name_parts: utils.ResourceIdentifier, input_item: str, input_block: utils.Json, result: str):
    recipe(rm, ('block_mod', name_parts), 'rnr:block_mod', {
        'input_item': utils.ingredient(input_item),
        'input_block': input_block,
        'output_block': result,
        'consume_ingredient': False
    })


def block_mod_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, input_item: str, input_block: utils.Json, result: str):
    recipe(rm, ('block_mod', name_parts), 'rnr:block_mod', {
        'input_item': utils.ingredient(input_item),
        'input_block': input_block,
        'output_block': result
    })


def stone_cutting(rm: ResourceManager, name_parts: utils.ResourceIdentifier, item: str, result: str, count: int = 1) -> RecipeContext:
    return recipe(rm, ('stonecutting', name_parts), 'minecraft:stonecutting', {
        'ingredient': utils.ingredient(item),
        'result': result,
        'count': count
    })


def no_remainder_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, group: str = None, conditions: utils.Json = None) -> RecipeContext:
    return rm.recipe(name_parts, 'tfc:no_remainder_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })


def no_remainder_shaped(rm: ResourceManager, name_parts: utils.ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    return rm.recipe(name_parts, 'tfc:no_remainder_shaped_crafting', {
        'type': 'minecraft:crafting_shaped',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })


def damage_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, group: str = None, conditions: utils.Json = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:damage_inputs_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })


def damage_shaped(rm: ResourceManager, name_parts: utils.ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:damage_inputs_shaped_crafting', {
        'type': 'minecraft:crafting_shaped',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    }
                           )


def extra_products_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: str, extra_result: str) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:extra_products_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'ingredients': utils.ingredient_list(ingredients),
        'result': utils.item_stack(result)
    }, {
                               'extra_products': utils.item_stack_list(extra_result)
                           })


def write_crafting_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, data: Json) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipe', res.path), data)
    return RecipeContext(rm, res)


def delegate_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, recipe_type: str, delegate: Json, data: Json = {}) -> RecipeContext:
    return write_crafting_recipe(rm, name_parts, {
        'type': recipe_type,
        **data,
        'recipe': delegate,
    })


def advanced_shaped(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, input_xy: Tuple[int, int], group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipe', res.path), {
        'type': 'tfc:advanced_shaped_crafting',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': item_stack_provider(result),
        'input_row': input_xy[1],
        'input_column': input_xy[0],
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)


def advanced_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, primary_ingredient: Json = None, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipe', res.path), {
        'type': 'tfc:advanced_shapeless_crafting',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': result,
        'primary_ingredient': None if primary_ingredient is None else utils.ingredient(primary_ingredient),
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)


def quern_recipe(rm: ResourceManager, name: ResourceIdentifier, item: str, result: str, count: int = 1) -> RecipeContext:
    result = result if not isinstance(result, str) else utils.item_stack((count, result))
    return recipe(rm, ('quern', name), 'tfc:quern', {
        'ingredient': utils.ingredient(item),
        'result': result
    })


def scraping_recipe(rm: ResourceManager, name: ResourceIdentifier, item: str, result: str, count: int = 1, input_texture=None, output_texture=None, extra_drop: str = None) -> RecipeContext:
    return recipe(rm, ('scraping', name), 'tfc:scraping', {
        'ingredient': utils.ingredient(item),
        'result': utils.item_stack((count, result)),
        'input_texture': input_texture,
        'output_texture': output_texture,
        'extra_drop': utils.item_stack(extra_drop) if extra_drop else None
    })


def clay_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    stack = utils.item_stack(result)
    if ('count' in stack and stack['count'] == 1) or 'count' not in stack:
        rm.item_tag('clay_recycle_5', stack['item'])
    else:
        rm.item_tag('clay_recycle_1', stack['item'])
    knapping_recipe(rm, name_parts, 'tfc:clay', pattern, result, None, outside_slot_required)


def fire_clay_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    stack = utils.item_stack(result)
    if ('count' in stack and stack['count'] == 1) or 'count' not in stack:
        rm.item_tag('fire_clay_recycle_5', stack['item'])
    else:
        rm.item_tag('fire_clay_recycle_1', stack['item'])
    knapping_recipe(rm, name_parts, 'tfc:fire_clay', pattern, result, None, outside_slot_required)


def leather_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    knapping_recipe(rm, name_parts, 'tfc:leather', pattern, result, None, outside_slot_required)


def rock_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: ResourceIdentifier, ingredient: str = None, outside_slot_required: bool = False):
    knapping_recipe(rm, name_parts, 'tfc:rock', pattern, result, ingredient, outside_slot_required)


def horn_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: ResourceIdentifier, ingredient: str = None, outside_slot_required: bool = False):
    knapping_recipe(rm, name_parts, 'tfc:goat_horn', pattern, {'item': 'minecraft:goat_horn', 'nbt': '{"instrument": "%s"}' % result}, ingredient, outside_slot_required)


def knapping_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, knap_type: str, pattern: List[str], result: Json, ingredient: Json, outside_slot_required: bool):
    for part in pattern:
        assert 0 < len(part) < 6, 'Incorrect length: %s' % part
    recipe(rm, (knap_type.split(':')[1] + '_knapping', name_parts), 'tfc:knapping', {
        'knapping_type': knap_type,
        'outside_slot_required': outside_slot_required,
        'pattern': pattern,
        'ingredient': None if ingredient is None else utils.ingredient(ingredient),
        'result': utils.item_stack(result)
    })


def knapping_type(rm: ResourceManager, name_parts: ResourceIdentifier, item_input: Json, amount_to_consume: Optional[int], click_sound: str, consume_after_complete: bool, use_disabled_texture: bool, spawns_particles: bool, jei_icon_item: Json):
    rm.data(('tfc', 'knapping_types', name_parts), {
        'input': item_stack_ingredient(item_input),
        'amount_to_consume': amount_to_consume,
        'click_sound': click_sound,
        'consume_after_complete': consume_after_complete,
        'use_disabled_texture': use_disabled_texture,
        'spawns_particles': spawns_particles,
        'jei_icon_item': utils.item_stack(jei_icon_item)
    })


def heat_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, ingredient: Json, temperature: float, result_item: Optional[Union[str, Json]] = None, result_fluid: Optional[str] = None, use_durability: Optional[bool] = None, chance: Optional[float] = None) -> RecipeContext:
    result_item = item_stack_provider(result_item) if isinstance(result_item, str) else result_item
    result_fluid = None if result_fluid is None else fluid_stack(result_fluid)
    return recipe(rm, ('heating', name_parts), 'tfc:heating', {
        'ingredient': utils.ingredient(ingredient),
        'result_item': result_item,
        'result_fluid': result_fluid,
        'temperature': temperature,
        'use_durability': use_durability if use_durability else None,
        'chance': chance,
    })


def casting_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, mold: str, metal: str, amount: int, break_chance: float, result_item: str = None):
    recipe(rm, ('casting', name_parts), 'tfc:casting', {
        'mold': {'item': 'tfc:ceramic/%s_mold' % mold},
        'fluid': fluid_stack_ingredient('%d tfc:metal/%s' % (amount, metal)),
        'result': utils.item_stack('tfc:metal/%s/%s' % (mold, metal)) if result_item is None else utils.item_stack(result_item),
        'break_chance': break_chance
    })


def alloy_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, metal: str, *parts: Tuple[str, float, float]):
    recipe(rm, ('alloy', name_parts), 'tfc:alloy', {
        'result': 'tfc:%s' % metal,
        'contents': [{
            'metal': 'tfc:%s' % p[0],
            'min': p[1],
            'max': p[2]
        } for p in parts]
    })


def bloomery_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, result: Json, metal: Json, catalyst: Json, time: int):
    recipe(rm, ('bloomery', name_parts), 'tfc:bloomery', {
        'result': item_stack_provider(result),
        'fluid': fluid_stack_ingredient(metal),
        'catalyst': utils.ingredient(catalyst),
        'duration': time
    })


def blast_furnace_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, metal_in: Json, metal_out: Json, catalyst: Json):
    recipe(rm, ('blast_furnace', name_parts), 'tfc:blast_furnace', {
        'fluid': fluid_stack_ingredient(metal_in),
        'result': fluid_stack(metal_out),
        'catalyst': utils.ingredient(catalyst)
    })


def barrel_sealed_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, translation: str, duration: int, input_item: Optional[Json] = None, input_fluid: Optional[Json] = None, output_item: Optional[Json] = None, output_fluid: Optional[Json] = None, on_seal: Optional[Json] = None, on_unseal: Optional[Json] = None, sound: Optional[str] = None):
    recipe(rm, ('barrel', name_parts), 'tfc:barrel_sealed', {
        'input_item': item_stack_ingredient(input_item) if input_item is not None else None,
        'input_fluid': fluid_stack_ingredient(input_fluid) if input_fluid is not None else None,
        'output_item': item_stack_provider(output_item) if isinstance(output_item, str) else output_item,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'duration': duration,
        'on_seal': on_seal,
        'on_unseal': on_unseal,
        'sound': sound
    })
    res = utils.resource_location('tfc', name_parts)
    rm.lang('tfc.recipe.barrel.' + res.domain + '.barrel.' + res.path.replace('/', '.'), lang(translation))


def barrel_instant_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, input_item: Optional[Json] = None, input_fluid: Optional[Json] = None, output_item: Optional[Json] = None, output_fluid: Optional[Json] = None, sound: Optional[str] = None):
    recipe(rm, ('barrel', name_parts), 'tfc:barrel_instant', {
        'input_item': item_stack_ingredient(input_item) if input_item is not None else None,
        'input_fluid': fluid_stack_ingredient(input_fluid) if input_fluid is not None else None,
        'output_item': item_stack_provider(output_item) if output_item is not None else None,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'sound': sound
    })


def barrel_instant_fluid_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, primary_fluid: Optional[Json] = None, added_fluid: Optional[Json] = None, output_fluid: Optional[Json] = None, sound: Optional[str] = None):
    recipe(rm, ('barrel', name_parts), 'tfc:barrel_instant_fluid', {
        'primary_fluid': fluid_stack_ingredient(primary_fluid) if primary_fluid is not None else None,
        'added_fluid': fluid_stack_ingredient(added_fluid) if added_fluid is not None else None,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'sound': sound
    })


def loom_recipe(rm: ResourceManager, name: utils.ResourceIdentifier, ingredient: Json, result: Json, steps: int, in_progress_texture: str):
    return recipe(rm, ('loom', name), 'tfc:loom', {
        'ingredient': item_stack_ingredient(ingredient),
        'result': utils.item_stack(result),
        'steps_required': steps,
        'in_progress_texture': in_progress_texture
    })


def anvil_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: Json, result: Json, tier: int, *rules: Rules, bonus: bool = None):
    recipe(rm, ('anvil', name_parts), 'tfc:anvil', {
        'input': utils.ingredient(ingredient),
        'result': item_stack_provider(result),
        'tier': tier,
        'rules': [r.name for r in rules],
        'apply_forging_bonus': bonus
    })


def welding_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, first_input: Json, second_input: Json, result: Json, tier: int, combine_forging: bool = None):
    recipe(rm, ('welding', name_parts), 'tfc:welding', {
        'first_input': utils.ingredient(first_input),
        'second_input': utils.ingredient(second_input),
        'tier': tier,
        'result': item_stack_provider(result),
        'combine_forging_bonus': combine_forging
    })


def glass_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, steps: List[str], batch: str, result: str):
    recipe(rm, ('glassworking', name_parts), 'tfc:glassworking', {
        'operations': steps,
        'batch': utils.ingredient(batch),
        'result': utils.item_stack(result)
    })


def fluid_stack(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return data_in
    fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
    assert not tag, 'fluid_stack() cannot be a tag'
    return {
        'fluid': fluid,
        'amount': amount
    }


def fluid_stack_ingredient(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return {
            'ingredient': fluid_ingredient(data_in['ingredient']),
            'amount': data_in['amount']
        }
    if pair := utils.maybe_unordered_pair(data_in, int, object):
        amount, fluid = pair
        return {'ingredient': fluid_ingredient(fluid), 'amount': amount}
    fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
    if tag:
        return {'ingredient': {'tag': fluid}, 'amount': amount}
    else:
        return {'ingredient': fluid, 'amount': amount}


def fluid_ingredient(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return data_in
    elif isinstance(data_in, List):
        return [*utils.flatten_list([fluid_ingredient(e) for e in data_in])]
    else:
        fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
        if tag:
            return {'tag': fluid}
        else:
            return fluid


def item_stack_ingredient(data_in: Json):
    if isinstance(data_in, dict):
        if 'type' in data_in:
            return item_stack_ingredient({'ingredient': data_in})
        return {
            'ingredient': utils.ingredient(data_in['ingredient']),
            'count': data_in['count'] if data_in.get('count') is not None else None
        }
    if pair := utils.maybe_unordered_pair(data_in, int, object):
        count, item = pair
        return {'ingredient': fluid_ingredient(item), 'count': count}
    item, tag, count, _ = utils.parse_item_stack(data_in, False)
    if tag:
        return {'ingredient': {'tag': item}, 'count': count}
    else:
        return {'ingredient': {'item': item}, 'count': count}


def fluid_item_ingredient(fluid: Json, delegate: Json = None):
    return {
        'type': 'tfc:fluid_item',
        'ingredient': delegate,
        'fluid_ingredient': fluid_stack_ingredient(fluid)
    }


def item_stack_provider(
    data_in: Json = None,
    # Possible Modifiers
    copy_input: bool = False,
    copy_heat: bool = False,
    copy_food: bool = False,  # copies both decay and traits
    copy_oldest_food: bool = False,  # copies only decay, from all inputs (uses crafting container)
    reset_food: bool = False,  # rest_food modifier - used for newly created food from non-food
    add_glass: bool = False,  # glassworking specific
    add_powder: bool = False,  # glassworking specific
    add_heat: float = None,
    add_trait: str = None,  # applies a food trait and adjusts decay accordingly
    remove_trait: str = None,  # removes a food trait and adjusts decay accordingly
    empty_bowl: bool = False,  # replaces a soup with its bowl
    copy_forging: bool = False,
    add_bait_to_rod: bool = False,  # adds bait to the rod, uses crafting container
    dye_color: str = None,  # applies a dye color to leather dye-able armor
    meal: Json = None  # makes a meal from input specified in json
) -> Json:
    if isinstance(data_in, dict):
        return data_in
    stack = utils.item_stack(data_in) if data_in is not None else None
    modifiers = [k for k, v in (
        # Ordering is important here
        # First, modifiers that replace the entire stack (copy input style)
        # Then, modifiers that only mutate an existing stack
        ('tfc:empty_bowl', empty_bowl),
        ('tfc:copy_input', copy_input),
        ('tfc:copy_heat', copy_heat),
        ('tfc:copy_food', copy_food),
        ('tfc:copy_oldest_food', copy_oldest_food),
        ('tfc:reset_food', reset_food),
        ('tfc:copy_forging_bonus', copy_forging),
        ('tfc:add_bait_to_rod', add_bait_to_rod),
        ('tfc:add_glass', add_glass),
        ('tfc:add_powder', add_powder),
        ({'type': 'tfc:add_heat', 'temperature': add_heat}, add_heat is not None),
        ({'type': 'tfc:add_trait', 'trait': add_trait}, add_trait is not None),
        ({'type': 'tfc:remove_trait', 'trait': remove_trait}, remove_trait is not None),
        ({'type': 'tfc:dye_leather', 'color': dye_color}, dye_color is not None),
        ({'type': 'tfc:meal', **(meal if meal is not None else {})}, meal is not None),
    ) if v]
    if modifiers:
        return {
            'stack': stack,
            'modifiers': modifiers
        }
    return stack


def not_rotten(ingredient: Json) -> Json:
    return {
        'type': 'tfc:not_rotten',
        'ingredient': utils.ingredient(ingredient)
    }


def has_trait(ingredient: Json, trait: str, invert: bool = False) -> Json:
    return {
        'type': 'tfc:lacks_trait' if invert else 'tfc:has_trait',
        'trait': trait,
        'ingredient': utils.ingredient(ingredient)
    }


def lacks_trait(ingredient: Json, trait: str) -> Json:
    return has_trait(ingredient, trait, True)


def slab_loot(rm: ResourceManager, loot: str):
    return rm.block_loot(loot, {
        'name': loot,
        'functions': [{
            'function': 'minecraft:set_count',
            'conditions': [loot_tables.block_state_property(loot + '[type=double]')],
            'count': 2,
            'add': False
        }]
    })
