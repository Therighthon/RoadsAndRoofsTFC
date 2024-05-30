#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from enum import Enum, auto

from mcresources import ResourceManager, utils, loot_tables
from mcresources.type_definitions import ResourceIdentifier

from constants import *
from recipes import fluid_ingredient


class Size(Enum):
    tiny = auto()
    very_small = auto()
    small = auto()
    normal = auto()
    large = auto()
    very_large = auto()
    huge = auto()


class Weight(Enum):
    very_light = auto()
    light = auto()
    medium = auto()
    heavy = auto()
    very_heavy = auto()


class Category(Enum):
    fruit = auto()
    vegetable = auto()
    grain = auto()
    bread = auto()
    dairy = auto()
    meat = auto()
    cooked_meat = auto()
    other = auto()


def generate(rm: ResourceManager):

    # Item Sizes

    item_size(rm, 'logs', '#minecraft:logs', Size.very_large, Weight.medium)
    item_size(rm, 'quern', 'tfc:quern', Size.very_large, Weight.very_heavy)
    item_size(rm, 'tool_racks', '#tfc:tool_racks', Size.large, Weight.very_heavy)
    item_size(rm, 'chests', '#forge:chests', Size.large, Weight.light)
    item_size(rm, 'slabs', '#minecraft:slabs', Size.small, Weight.very_light)
    item_size(rm, 'vessels', '#tfc:vessels', Size.normal, Weight.heavy)
    item_size(rm, 'large_vessels', '#tfc:large_vessels', Size.huge, Weight.heavy)
    item_size(rm, 'molds', '#tfc:molds', Size.normal, Weight.medium)
    item_size(rm, 'doors', '#minecraft:doors', Size.very_large, Weight.heavy)
    item_size(rm, 'mortar', '#tfc:mortar', Size.tiny, Weight.very_light)
    item_size(rm, 'stick_bunch', 'tfc:stick_bunch', Size.normal, Weight.light)
    item_size(rm, 'stick_bundle', 'tfc:stick_bundle', Size.very_large, Weight.medium)
    item_size(rm, 'jute_fiber', 'tfc:jute_fiber', Size.small, Weight.very_light)
    item_size(rm, 'burlap_cloth', 'tfc:burlap_cloth', Size.small, Weight.very_light)
    item_size(rm, 'straw', 'tfc:straw', Size.small, Weight.very_light)
    item_size(rm, 'wool', 'tfc:wool', Size.small, Weight.light)
    item_size(rm, 'wool_cloth', 'tfc:wool_cloth', Size.small, Weight.light)
    item_size(rm, 'silk_cloth', 'tfc:silk_cloth', Size.small, Weight.light)
    item_size(rm, 'alabaster_brick', 'tfc:alabaster_brick', Size.small, Weight.light)
    item_size(rm, 'glue', 'tfc:glue', Size.tiny, Weight.light)
    item_size(rm, 'brass_mechanisms', 'tfc:brass_mechanisms', Size.normal, Weight.light)
    item_size(rm, 'wrought_iron_grill', 'tfc:wrought_iron_grill', Size.large, Weight.heavy)
    item_size(rm, 'dyes', '#tfc:dyes', Size.tiny, Weight.light)
    item_size(rm, 'foods', '#tfc:foods', Size.small, Weight.light)
    item_size(rm, 'plants', '#tfc:plants', Size.tiny, Weight.very_light)
    item_size(rm, 'jute', 'tfc:jute', Size.small, Weight.very_light)
    item_size(rm, 'bloomery', 'tfc:bloomery', Size.large, Weight.very_heavy)
    item_size(rm, 'sluice', '#tfc:sluices', Size.very_large, Weight.very_heavy)
    item_size(rm, 'lamps', '#tfc:lamps', Size.normal, Weight.medium)
    item_size(rm, 'signs', '#minecraft:signs', Size.very_small, Weight.heavy)
    item_size(rm, 'hanging_signs', '#minecraft:hanging_signs', Size.very_small, Weight.heavy)
    item_size(rm, 'soups', '#tfc:soups', Size.very_small, Weight.medium)
    item_size(rm, 'salads', '#tfc:salads', Size.very_small, Weight.medium)
    item_size(rm, 'buckets', '#tfc:buckets', Size.large, Weight.medium)
    item_size(rm, 'anvils', '#tfc:anvils', Size.huge, Weight.very_heavy)
    item_size(rm, 'minecarts', '#tfc:minecarts', Size.very_large, Weight.heavy)
    item_size(rm, 'boats', '#minecraft:boats', Size.very_large, Weight.heavy)
    item_size(rm, 'looms', '#tfc:looms', Size.large, Weight.very_heavy)
    item_size(rm, 'ingots', '#forge:ingots', Size.large, Weight.medium)
    item_size(rm, 'double_ingots', '#forge:double_ingots', Size.large, Weight.heavy)
    item_size(rm, 'sheets', '#forge:sheets', Size.large, Weight.medium)
    item_size(rm, 'double_sheets', '#forge:double_sheets', Size.large, Weight.heavy)
    item_size(rm, 'rods', '#forge:rods', Size.normal, Weight.light)
    item_size(rm, 'tuyeres', '#tfc:tuyeres', Size.large, Weight.heavy)
    item_size(rm, 'trapdoors', '#tfc:trapdoors', Size.large, Weight.heavy)
    item_size(rm, 'small_tools', ['#tfc:chisels', '#tfc:knives', '#tfc:shears'], Size.large, Weight.medium)
    item_size(rm, 'large_tools', ['#forge:fishing_rods', '#tfc:pickaxes', '#tfc:propicks', '#tfc:axes', '#tfc:shovels', '#tfc:hoes', '#tfc:hammers', '#tfc:saws', '#tfc:javelins', '#tfc:swords', '#tfc:maces', '#tfc:scythes', '#tfc:shields', '#tfc:glassworking_tools', '#tfc:all_blowpipes'], Size.very_large, Weight.very_heavy)
    item_size(rm, 'ore_pieces', '#tfc:ore_pieces', Size.small, Weight.medium)
    item_size(rm, 'small_ore_pieces', '#tfc:small_ore_pieces', Size.small, Weight.light)
    item_size(rm, 'jars', '#tfc:jars', Size.very_large, Weight.heavy)
    item_size(rm, 'empty_jar', ['tfc:empty_jar', 'tfc:empty_jar_with_lid'], Size.tiny, Weight.medium)
    item_size(rm, 'glass_bottles', '#tfc:glass_bottles', Size.large, Weight.heavy)
    item_size(rm, 'windmill_blades', '#tfc:windmill_blades', Size.very_large, Weight.very_heavy)
    item_size(rm, 'water_wheels', '#tfc:water_wheels', Size.very_large, Weight.very_heavy)

def entity_damage_resistance(rm: ResourceManager, name_parts: ResourceIdentifier, entity_tag: str, piercing: int = 0, slashing: int = 0, crushing: int = 0):
    rm.data(('tfc', 'entity_damage_resistances', name_parts), {
        'entity': entity_tag,
        'piercing': piercing,
        'slashing': slashing,
        'crushing': crushing
    })

def item_damage_resistance(rm: ResourceManager, name_parts: ResourceIdentifier, item: utils.Json, piercing: int = 0, slashing: int = 0, crushing: int = 0):
    rm.data(('tfc', 'item_damage_resistances', name_parts), {
        'ingredient': utils.ingredient(item),
        'piercing': piercing,
        'slashing': slashing,
        'crushing': crushing
    })

def mob_loot(rm: ResourceManager, name: str, drop: str, min_amount: int = 1, max_amount: int = None, hide_size: str = None, hide_chance: float = 1, bones: int = 0, extra_pool: Dict[str, Any] = None, livestock: bool = False, not_predated: bool = False, killed_by_player: bool = False):
    func = None if max_amount is None else loot_tables.set_count(min_amount, max_amount)
    if not_predated:
        conditions = [{'condition': 'tfc:not_predated'}]
    elif killed_by_player:
        conditions = [{'condition': 'minecraft:killed_by_player'}]
    else:
        conditions = None
    pools = [{'name': drop, 'functions': func, 'conditions': conditions}]
    if livestock:
        pools = [{'name': drop, 'functions': animal_yield(min_amount, (max(1, max_amount - 3), max_amount + 3))}]
    if hide_size is not None:
        func = None if hide_chance == 1 else loot_tables.random_chance(hide_chance)
        pools.append({'name': 'tfc:%s_raw_hide' % hide_size, 'conditions': func})
    if bones != 0:
        pools.append({'name': 'minecraft:bone', 'functions': loot_tables.set_count(1, bones)})
    if extra_pool is not None:
        pools.append(extra_pool)
    rm.entity_loot(name, *pools)

def animal_yield(lo: int, hi: Tuple[int, int]) -> utils.Json:
    return {
        'function': 'minecraft:set_count',
        'count': {
            'type': 'tfc:animal_yield',
            'min': lo,
            'max': {
                'type': 'minecraft:uniform',
                'min': hi[0],
                'max': hi[1]
            }
        }
    }

def lamp_fuel(rm: ResourceManager, name: str, fluid: str, burn_rate: int, valid_lamps: str = '#tfc:lamps'):
    rm.data(('tfc', 'lamp_fuels', name), {
        'fluid': fluid,
        'burn_rate': burn_rate,
        # This is a block ingredient, not an ingredient
        'valid_lamps': {'type': 'tfc:tag', 'tag': valid_lamps.replace('#', '')} if '#' in valid_lamps else valid_lamps
    })

def fertilizer(rm: ResourceManager, name: str, ingredient: str, n: float = None, p: float = None, k: float = None):
    rm.data(('tfc', 'fertilizers', name), {
        'ingredient': utils.ingredient(ingredient),
        'nitrogen': n,
        'potassium': k,
        'phosphorus': p
    })


def climate_config(min_temp: Optional[float] = None, max_temp: Optional[float] = None, min_rain: Optional[float] = None, max_rain: Optional[float] = None, needs_forest: Optional[bool] = False, fuzzy: Optional[bool] = None, min_forest: Optional[str] = None, max_forest: Optional[str] = None) -> Dict[str, Any]:
    return {
        'min_temperature': min_temp,
        'max_temperature': max_temp,
        'min_rainfall': min_rain,
        'max_rainfall': max_rain,
        'min_forest': 'normal' if needs_forest else min_forest,
        'max_forest': max_forest,
        'fuzzy': fuzzy
    }


def fauna(chance: int = None, distance_below_sea_level: int = None, climate: Dict[str, Any] = None, solid_ground: bool = None, max_brightness: int = None) -> Dict[str, Any]:
    return {
        'chance': chance,
        'distance_below_sea_level': distance_below_sea_level,
        'climate': climate,
        'solid_ground': solid_ground,
        'max_brightness': max_brightness
    }


def food_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, category: Category, hunger: int, saturation: float, water: int, decay: float, fruit: Optional[float] = None, veg: Optional[float] = None, protein: Optional[float] = None, grain: Optional[float] = None, dairy: Optional[float] = None):
    rm.item_tag('tfc:foods', ingredient)
    rm.data(('tfc', 'food_items', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'hunger': hunger,
        'saturation': saturation,
        'water': water if water != 0 else None,
        'decay_modifier': decay,
        'fruit': fruit,
        'vegetables': veg,
        'protein': protein,
        'grain': grain,
        'dairy': dairy
    })
    rm.item_tag('foods', ingredient)
    if category in (Category.fruit, Category.vegetable):
        rm.item_tag('foods/%ss' % category.name.lower(), ingredient)
    if category in (Category.meat, Category.cooked_meat):
        rm.item_tag('foods/meats', ingredient)
        if category == Category.cooked_meat:
            rm.item_tag('foods/cooked_meats', ingredient)
        else:
            rm.item_tag('foods/raw_meats', ingredient)
    if category == Category.dairy:
        rm.item_tag('foods/dairy', ingredient)

def dynamic_food_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, handler_type: str):
    rm.item_tag('foods', ingredient)
    rm.data(('tfc', 'food_items', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'type': handler_type
    })

def drinkable(rm: ResourceManager, name_parts: utils.ResourceIdentifier, fluid: utils.Json, thirst: Optional[int] = None, intoxication: Optional[int] = None, effects: Optional[utils.Json] = None, food: Optional[utils.Json] = None, allow_full: bool = None):
    rm.data(('tfc', 'drinkables', name_parts), {
        'ingredient': fluid_ingredient(fluid),
        'thirst': thirst,
        'intoxication': intoxication,
        'effects': effects,
        'food': food,
        'may_drink_when_full': allow_full,
    })

def damage_type(rm: ResourceManager, name_parts: utils.ResourceIdentifier, message_id: str = None, exhaustion: float = 0.0, scaling: str = 'when_caused_by_living_non_player', effects: str = None, message_type: str = None):
    rm.data(('damage_type', name_parts), {
        'message_id': message_id if message_id is not None else 'tfc.' + name_parts,
        'exhaustion': exhaustion,
        'scaling': scaling,
        'effects': effects,
        'death_message_type': message_type
    })

def item_size(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, size: Size, weight: Weight):
    rm.data(('tfc', 'item_sizes', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'size': size.name,
        'weight': weight.name
    })


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, heat_capacity: float, melt_temperature: Optional[float] = None, mb: Optional[int] = None):
    if melt_temperature is not None:
        forging_temperature = round(melt_temperature * 0.6)
        welding_temperature = round(melt_temperature * 0.8)
    else:
        forging_temperature = welding_temperature = None
    if mb is not None:
        # Interpret heat capacity as a specific heat capacity - so we need to scale by the mB present. Baseline is 100 mB (an ingot)
        # Higher mB = higher heat capacity = heats and cools slower = consumes proportionally more fuel
        heat_capacity = round(10 * heat_capacity * mb) / 1000
    rm.data(('tfc', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })


def fuel_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, duration: int, temperature: float, purity: float = None):
    rm.data(('tfc', 'fuels', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'duration': duration,
        'temperature': temperature,
        'purity': purity,
    })


def panning(rm: ResourceManager, name_parts: utils.ResourceIdentifier, block: utils.Json, models: List[str], loot_table: str):
    rm.data(('tfc', 'panning', name_parts), {
        'ingredient': block,
        'model_stages': models,
        'loot_table': loot_table
    })


def sluicing(rm: ResourceManager, name_parts: utils.ResourceIdentifier, block: utils.Json, loot_table: str):
    rm.data(('tfc', 'sluicing', name_parts), {
        'ingredient': utils.ingredient(block),
        'loot_table': loot_table
    })


def trim_material(rm: ResourceManager, name: str, color: str, ingredient: str, item_model_index: float):
    rm.data(('trim_material', name), {
        'asset_name': name + '_' + rm.domain,  # this field is not properly namespaced, so we have to do that ourselves
        'description': {
            'color': color,
            'translate': 'trim_material.%s.%s' % (rm.domain, name)
        },
        'ingredient': ingredient,
        'item_model_index': item_model_index
    })

def climate_range(rm: ResourceManager, name_parts: utils.ResourceIdentifier, hydration: Tuple[int, int, int] = None, temperature: Tuple[float, float, float] = None):
    data = {}
    if hydration is not None:
        data.update({'min_hydration': hydration[0], 'max_hydration': hydration[1], 'hydration_wiggle_range': hydration[2]})
    if temperature is not None:
        data.update({'min_temperature': temperature[0], 'max_temperature': temperature[1], 'temperature_wiggle_range': temperature[2]})
    rm.data(('tfc', 'climate_ranges', name_parts), data)


def hydration_from_rainfall(rainfall: float) -> int:
    return int(rainfall) * 60 // 500


def block_and_item_tag(rm: ResourceManager, name_parts: utils.ResourceIdentifier, *values: utils.ResourceIdentifier, replace: bool = False):
    rm.block_tag(name_parts, *values, replace=replace)
    rm.item_tag(name_parts, *values, replace=replace)
