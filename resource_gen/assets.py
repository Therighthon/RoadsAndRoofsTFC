import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, BlockContext
from mcresources.type_definitions import ResourceIdentifier, JsonObject

from constants import *


def generate(rm: ResourceManager):
    # Base Course
    rm.blockstate('rnr:base_course').with_block_model({
        'dirt': 'minecraft:block/gravel'
    }, parent='rnr:block/base_course_shape').with_item_model()

    # Dirt Blocks
    for dirt in SOIL_BLOCK_VARIANTS:
        rm.blockstate('rnr:tamped_%s' % dirt).with_block_model({
            'dirt': 'tfc:block/dirt/%s' % dirt
        }, parent='rnr:block/tamped_block').with_item_model()
    # Mud Blocks
    for dirt in SOIL_BLOCK_VARIANTS:
        rm.blockstate('rnr:tamped_%s_mud' % dirt).with_block_model({
            'dirt': 'tfc:block/mud/%s' % dirt
        }, parent='rnr:block/tamped_block').with_item_model()

    rm.blockstate('rnr:tamped_peat').with_block_model({
        'dirt': 'tfc:block/peat'
    }, parent='rnr:block/tamped_block').with_item_model()

    rm.blockstate('rnr:tamped_kaolin').with_block_model({
        'dirt': 'tfc:block/red_kaolin_clay'
    }, parent='rnr:block/tamped_block').with_item_model()

    rm.blockstate('rnr:hoggin').with_block_model({
        'top': 'minecraft:block/dirt_path_top',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:brick_road').with_block_model({
        'top': 'rnr:block/brick_road',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    # Rock Type Blocks
    for rock, rock_data in ROCKS.items():

        def rock_lang(_lhs: str, _rhs: str):
            return _rhs, _lhs

        for block_type in STONE_PATHS:
            # Paths
            # TODO: special case to make cobble and brick use base TFC textures
            rm.blockstate('rnr:rock/%s/%s' % (block_type, rock)).with_block_model({
                'top': 'rnr:block/rock/%s/%s' % (block_type, rock),
                'gravel': 'minecraft:block/gravel'
            }, parent='rnr:block/path_block').with_item_model()
            # Stairs
            make_path_stairs(rm, block_type, rock)
            # Slabs
            rm.blockstate('rnr:rock/%s/%s_slab' % (block_type, rock)).with_block_model({
                'top': 'rnr:block/rock/%s/%s' % (block_type, rock),
                'gravel': 'minecraft:block/gravel'
            }, parent='rnr:block/path_slab').with_item_model()
        rm.blockstate('rnr:rock/over_height_gravel/%s' % rock).with_block_model({
            'top': 'tfc:block/rock/gravel/%s' % rock,
            'gravel': 'minecraft:block/gravel'
        }, parent='rnr:block/overfilled_block').with_item_model()
        # rm.item_model('rnr:rock/over_height_gravel/%s' % rock).with_lang(lang('overfilled %s gravel' % rock))
    # Items
    rm.item_model('crushed_base_course', 'rnr:item/crushed_base_course')
    rm.item_model('hoggin_mix', 'rnr:item/hoggin_mix')

    for sand in SAND_BLOCK_TYPES:
        rm.blockstate('rnr:%s_sandstone_flagstones' % sand).with_block_model({
            'top': 'rnr:block/rock/flagstones/%s_sandstone' % sand,
            'gravel': 'minecraft:block/gravel'
        }, parent='rnr:block/path_block').with_item_model()

    # Metal Items
    for metal, metal_data in METALS.items():
        for metal_item, metal_item_data in METAL_ITEMS.items():
            if metal_item_data.type in metal_data.types or metal_item_data.type == 'all':
                texture = 'rnr:item/metal/%s/%s' % (metal_item, metal) if metal_item != 'shield' or metal in ('red_steel', 'blue_steel', 'wrought_iron') else 'tfc:item/metal/shield/%s_front' % metal
                item = rm.item_model(('metal', metal_item, metal), texture, parent=metal_item_data.parent_model)
                item.with_lang(lang('%s %s', metal, metal_item))

    # Rock Items
    for rock in ROCKS.keys():
        rm.item_model(('flagstone', rock), 'rnr:item/flagstone/%s' % rock).with_lang(lang('%s flagstone', rock))
        rm.item_model(('gravel_fill', rock), 'rnr:item/gravel_fill/%s' % rock).with_lang(lang('%s gravel fill', rock))
    for sand in SAND_BLOCK_TYPES:
        rm.item_model(('flagstone', sand + '_sandstone'), 'rnr:item/flagstone/%s_sandstone' % sand).with_lang(lang('%s sandstone flagstone', sand))

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


def make_path_stairs(rm, type, rock, stair_suffix: str = '_stairs', bottom_texture: Optional[str] = None, side_texture: Optional[str] = None, top_texture: Optional[str] = None) -> 'BlockContext':
    """
    Generates all blockstates and models required for a standard stair block
    """
    block = 'rnr:block/rock/%s/%s' % (type, rock)
    stairs = 'rnr:rock/%s/%s%s' % (type, rock, stair_suffix)
    block_stairs = block + stair_suffix
    block_stairs_inner = block + stair_suffix + '_inner'
    block_stairs_outer = block + stair_suffix + '_outer'

    if bottom_texture is None:
        bottom_texture = block
    if side_texture is None:
        side_texture = block
    if top_texture is None:
        top_texture = block

    rm.blockstate(stairs, variants=block_states.stairs_variants(block_stairs, block_stairs_inner, block_stairs_outer))
    rm.block_model(stairs, textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_stairs')
    rm.block_model(stairs + '_inner', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_inner_stairs')
    rm.block_model(stairs + '_outer', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_outer_stairs')
    rm.item_model(stairs, parent=block_stairs, no_textures=True)
    return
