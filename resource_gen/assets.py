import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, BlockContext
from mcresources.type_definitions import ResourceIdentifier, JsonObject

from constants import *


def generate(rm: ResourceManager, afc_rm: ResourceManager):
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
    rm.blockstate('rnr:hoggin_slab').with_block_model({
        'top': 'minecraft:block/dirt_path_top',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_slab').with_item_model()
    make_path_stairs(rm, 'rnr:block/hoggin', 'hoggin_stairs', '_stairs', 'minecraft:block/dirt_path_top', 'minecraft:block/dirt_path_top', 'minecraft:block/dirt_path_top')

    rm.blockstate('rnr:brick_road').with_block_model({
        'top': 'rnr:block/brick_road',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()
    rm.blockstate('rnr:brick_road_slab').with_block_model({
        'top': 'rnr:block/brick_road',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_slab').with_item_model()
    make_path_stairs(rm, 'rnr:block/brick_road', 'rnr:brick_road_stairs')

    # Concrete roads
    rm.blockstate('rnr:concrete_road').with_block_model({
        'top': 'rnr:block/concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()
    # TODO: Stairs, slabs, variants

    # rm.blockstate('rnr:concrete_road_control_joint').with_block_model({
    #     'top': 'rnr:block/concrete_control_joint',
    #     'gravel': 'minecraft:block/gravel'
    # }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('concrete_road_control_joint', variants={
        'axis=x,connects_north_or_east=false,connects_south_or_west=false': {'model': 'rnr:block/concrete_road_control_joint'},
        'axis=x,connects_north_or_east=true,connects_south_or_west=false': {'model': 'rnr:block/concrete_road_control_joint_new'},
        'axis=x,connects_north_or_east=false,connects_south_or_west=true': {'model': 'rnr:block/concrete_road_control_joint_esw'},
        'axis=x,connects_north_or_east=true,connects_south_or_west=true': {'model': 'rnr:block/concrete_road_control_joint_nesw'},
        'axis=z,connects_north_or_east=false,connects_south_or_west=false': {'model': 'rnr:block/concrete_road_control_joint_ns'},
        'axis=z,connects_north_or_east=true,connects_south_or_west=false': {'model': 'rnr:block/concrete_road_control_joint_nes'},
        'axis=z,connects_north_or_east=false,connects_south_or_west=true': {'model': 'rnr:block/concrete_road_control_joint_nsw'},
        'axis=z,connects_north_or_east=true,connects_south_or_west=true': {'model': 'rnr:block/concrete_road_control_joint_nesw'}}) \
        .with_block_model({
        'top': 'rnr:block/concrete_control_joint_ew',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    # rm.block_model('concrete_road_control_joint_ew', {
    #     'top': 'rnr:block/concrete_control_joint_ew',
    #     'gravel': 'minecraft:block/gravel'
    # }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_new', {
        'top': 'rnr:block/concrete_control_joint_new',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_esw', {
        'top': 'rnr:block/concrete_control_joint_esw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_nesw', {
        'top': 'rnr:block/concrete_control_joint_nesw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_ns', {
        'top': 'rnr:block/concrete_control_joint_ns',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_nes', {
        'top': 'rnr:block/concrete_control_joint_nes',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('concrete_road_control_joint_nsw', {
        'top': 'rnr:block/concrete_control_joint_nsw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.blockstate('rnr:trodden_concrete_road').with_block_model({
        'top': 'rnr:block/trodden_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:cracked_concrete_road').with_block_model({
        'top': 'rnr:block/cracked_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:cracked_trodden_concrete_road').with_block_model({
        'top': 'rnr:block/cracked_trodden_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:wet_concrete_road').with_block_model({
        'top': 'rnr:block/wet_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:pouring_concrete_road').with_block_model({
        'top': 'rnr:block/wet_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('rnr:trodden_wet_concrete_road').with_block_model({
        'top': 'rnr:block/trodden_wet_concrete',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block').with_item_model()

    # rm.blockstate('rnr:wet_concrete_road_control_joint').with_block_model({
    #     'top': 'rnr:block/wet_concrete_control_joint',
    #     'gravel': 'minecraft:block/gravel'
    # }, parent='rnr:block/path_block').with_item_model()

    rm.blockstate('wet_concrete_road_control_joint', variants={
        'axis=x,connects_north_or_east=false,connects_south_or_west=false': {'model': 'rnr:block/wet_concrete_road_control_joint'},
        'axis=x,connects_north_or_east=true,connects_south_or_west=false': {'model': 'rnr:block/wet_concrete_road_control_joint_new'},
        'axis=x,connects_north_or_east=false,connects_south_or_west=true': {'model': 'rnr:block/wet_concrete_road_control_joint_esw'},
        'axis=x,connects_north_or_east=true,connects_south_or_west=true': {'model': 'rnr:block/wet_concrete_road_control_joint_nesw'},
        'axis=z,connects_north_or_east=false,connects_south_or_west=false': {'model': 'rnr:block/wet_concrete_road_control_joint_ns'},
        'axis=z,connects_north_or_east=true,connects_south_or_west=false': {'model': 'rnr:block/wet_concrete_road_control_joint_nes'},
        'axis=z,connects_north_or_east=false,connects_south_or_west=true': {'model': 'rnr:block/wet_concrete_road_control_joint_nsw'},
        'axis=z,connects_north_or_east=true,connects_south_or_west=true': {'model': 'rnr:block/wet_concrete_road_control_joint_nesw'}}) \
        .with_block_model({
            'top': 'rnr:block/wet_concrete_control_joint_ew',
            'gravel': 'minecraft:block/gravel'
        }, parent='rnr:block/path_block').with_item_model()

    # rm.block_model('wet_concrete_road_control_joint_ew', {
    #     'top': 'rnr:block/wet_concrete_control_joint_ew',
    #     'gravel': 'minecraft:block/gravel'
    # }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_new', {
        'top': 'rnr:block/wet_concrete_control_joint_new',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_esw', {
        'top': 'rnr:block/wet_concrete_control_joint_esw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_nesw', {
        'top': 'rnr:block/wet_concrete_control_joint_nesw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_ns', {
        'top': 'rnr:block/wet_concrete_control_joint_ns',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_nes', {
        'top': 'rnr:block/wet_concrete_control_joint_nes',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    rm.block_model('wet_concrete_road_control_joint_nsw', {
        'top': 'rnr:block/wet_concrete_control_joint_nsw',
        'gravel': 'minecraft:block/gravel'
    }, parent='rnr:block/path_block')

    # Roof frame models
    block = rm.blockstate('rnr:roof_frame')
    texture = 'rnr:block/roof_frame'
    block.with_block_model(texture).with_item_model()
    block.make_stairs(bottom_texture=texture, side_texture='rnr:block/roof_frame_dense', top_texture='rnr:block/roof_frame_dense')
    block.make_slab(bottom_texture=texture, side_texture='rnr:block/roof_frame_dense', top_texture=texture)

    # thatch models
    block = rm.blockstate('rnr:thatch_roof')
    texture = 'tfc:block/thatch'
    block.with_block_model(texture).with_item_model()
    block.make_stairs(bottom_texture=texture, side_texture=texture, top_texture=texture)
    block.make_slab(bottom_texture=texture, side_texture=texture, top_texture=texture)

    # terracotta models
    block = rm.blockstate('rnr:terracotta_roof')
    texture = 'rnr:block/terracotta_tiles'
    block.with_block_model(texture).with_item_model()
    block.make_stairs(bottom_texture=texture, side_texture=texture, top_texture=texture)
    block.make_slab(bottom_texture=texture, side_texture=texture, top_texture=texture)

    # ceramic models
    block = rm.blockstate('rnr:ceramic_roof')
    texture = 'rnr:block/ceramic_tiles'
    block.with_block_model(texture).with_item_model()
    block.make_stairs(bottom_texture=texture, side_texture=texture, top_texture=texture)
    block.make_slab(bottom_texture=texture, side_texture=texture, top_texture=texture)

    for wood in WOODS.keys():
        shingle = rm.blockstate('rnr:wood/shingles/%s' % wood)
        texture = 'rnr:block/wood/shingles/%s' % wood
        shingle.with_block_model(texture).with_item_model()
        shingle.make_stairs(bottom_texture=texture, side_texture=texture, top_texture=texture)
        shingle.make_slab(bottom_texture=texture, side_texture=texture, top_texture=texture)
        rm.item_model('rnr:wood/shingle/%s' % wood)

    for wood in AFC_WOODS.keys():
        shingle = afc_rm.blockstate('rnr:wood/shingles/%s' % wood)
        texture = 'rnr:block/wood/shingles/%s' % wood
        shingle.with_block_model(texture).with_item_model()
        shingle.make_stairs(bottom_texture=texture, side_texture=texture, top_texture=texture)
        shingle.make_slab(bottom_texture=texture, side_texture=texture, top_texture=texture)
        afc_rm.item_model('rnr:wood/shingle/%s' % wood)

    rm.item_model('unfired_roof_tile', 'rnr:item/unfired_roof_tile')
    rm.item_model('unfired_terracotta_roof_tile', 'rnr:item/unfired_terracotta_roof_tile')
    rm.item_model('ceramic_roof_tile', 'rnr:item/ceramic_roof_tile')
    rm.item_model('terracotta_roof_tile', 'rnr:item/terracotta_roof_tile')

    rm.item_model('concrete_powder', 'rnr:item/concrete_powder')

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
            make_rock_path_stairs(rm, block_type, rock)
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

        rm.blockstate('rnr:%s_sandstone_flagstones_slab' % sand).with_block_model({
            'top': 'rnr:block/rock/flagstones/%s_sandstone' % sand,
            'gravel': 'minecraft:block/gravel'
        }, parent='rnr:block/path_slab').with_item_model()

        make_sandstone_path_stairs(rm, 'flagstones', sand)



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


def make_rock_path_stairs(rm, type, rock, stair_suffix: str = '_stairs', bottom_texture: Optional[str] = None, side_texture: Optional[str] = None, top_texture: Optional[str] = None) -> 'BlockContext':
    """
    Generates all blockstates and models required for a standard stair block
    """
    block = 'rnr:block/rock/%s/%s' % (type, rock)
    stairs = 'rnr:rock/%s/%s%s' % (type, rock, stair_suffix)

    make_path_stairs(rm, block, stairs, stair_suffix, bottom_texture, side_texture, top_texture)
    return

def make_sandstone_path_stairs(rm, type, sand, stair_suffix: str = '_stairs', bottom_texture: Optional[str] = None, side_texture: Optional[str] = None, top_texture: Optional[str] = None) -> 'BlockContext':
    """
    Generates all blockstates and models required for a standard stair block
    make_path_stairs(rm, 'rnr:block/brick_road', 'rnr:brick_road_stairs')
    """

    block = 'rnr:%s_sandstone_%s' % (sand, type)
    stairs = 'rnr:%s_sandstone_%s' % (sand, type) + stair_suffix

    block_stairs = 'rnr:block/%s_sandstone_%s_stairs' % (sand, type)
    block_stairs_inner = block_stairs + '_inner'
    block_stairs_outer = block_stairs + '_outer'

    bottom_texture = 'rnr:block/rock/flagstones/%s_sandstone' % sand
    side_texture = bottom_texture
    top_texture = bottom_texture

    rm.blockstate(stairs, variants=path_stairs_variants(block_stairs, block_stairs_inner, block_stairs_outer))
    rm.block_model(stairs, textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_stairs')
    rm.block_model(stairs + '_inner', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_inner_stairs')
    rm.block_model(stairs + '_outer', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_outer_stairs')
    rm.item_model(stairs, parent=block_stairs, no_textures=True)
    return

def make_path_stairs(rm, block, stairs, stair_suffix: str = '_stairs', bottom_texture: Optional[str] = None, side_texture: Optional[str] = None, top_texture: Optional[str] = None) -> 'BlockContext':
    block_stairs = block + stair_suffix
    block_stairs_inner = block + stair_suffix + '_inner'
    block_stairs_outer = block + stair_suffix + '_outer'

    if bottom_texture is None:
        bottom_texture = block
    if side_texture is None:
        side_texture = block
    if top_texture is None:
        top_texture = block

    rm.blockstate(stairs, variants=path_stairs_variants(block_stairs, block_stairs_inner, block_stairs_outer))
    rm.block_model(stairs, textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_stairs')
    rm.block_model(stairs + '_inner', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_inner_stairs')
    rm.block_model(stairs + '_outer', textures={'bottom': bottom_texture, 'top': top_texture, 'side': side_texture}, parent='rnr:block/path_outer_stairs')
    rm.item_model(stairs, parent=block_stairs, no_textures=True)
    return

def path_stairs_variants(stairs: str, stairs_inner: str, stairs_outer: str) -> JsonObject:
    return {
        'facing=east,shape=straight': {'model': stairs},
        'facing=west,shape=straight': {'model': stairs, 'y': 180, 'uvlock': True},
        'facing=south,shape=straight': {'model': stairs, 'y': 90, 'uvlock': True},
        'facing=north,shape=straight': {'model': stairs, 'y': 270, 'uvlock': True},
        'facing=east,shape=outer_right': {'model': stairs_outer},
        'facing=west,shape=outer_right': {'model': stairs_outer, 'y': 180, 'uvlock': True},
        'facing=south,shape=outer_right': {'model': stairs_outer, 'y': 90, 'uvlock': True},
        'facing=north,shape=outer_right': {'model': stairs_outer, 'y': 270, 'uvlock': True},
        'facing=east,shape=outer_left': {'model': stairs_outer, 'y': 270, 'uvlock': True},
        'facing=west,shape=outer_left': {'model': stairs_outer, 'y': 90, 'uvlock': True},
        'facing=south,shape=outer_left': {'model': stairs_outer},
        'facing=north,shape=outer_left': {'model': stairs_outer, 'y': 180, 'uvlock': True},
        'facing=east,shape=inner_right': {'model': stairs_inner},
        'facing=west,shape=inner_right': {'model': stairs_inner, 'y': 180, 'uvlock': True},
        'facing=south,shape=inner_right': {'model': stairs_inner, 'y': 90, 'uvlock': True},
        'facing=north,shape=inner_right': {'model': stairs_inner, 'y': 270, 'uvlock': True},
        'facing=east,shape=inner_left': {'model': stairs_inner, 'y': 270, 'uvlock': True},
        'facing=west,shape=inner_left': {'model': stairs_inner, 'y': 90, 'uvlock': True},
        'facing=south,shape=inner_left': {'model': stairs_inner},
        'facing=north,shape=inner_left': {'model': stairs_inner, 'y': 180, 'uvlock': True}
    }
