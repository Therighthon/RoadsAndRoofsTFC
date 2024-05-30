#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, BlockContext
from mcresources.type_definitions import ResourceIdentifier, JsonObject

from constants import *


def generate(rm: ResourceManager):
    # Rock Type Blocks
    for rock, rock_data in ROCKS.items():

        def rock_lang(_lhs: str, _rhs: str):
            return _rhs, _lhs + 'path'

        for block_type in STONE_PATHS:
            # Stairs
            rm.block(('rock', block_type, rock)).make_stairs()
            rm.block(('rock', block_type, rock + '_stairs')).with_lang(lang('%s %s Stairs', *rock_lang(block_type, rock))).with_block_loot('rnr:rock/%s/%s_stairs' % (block_type, rock))
            # Slabs
            rm.block(('rock', block_type, rock)).make_slab()
            rm.block(('rock', block_type, rock + '_slab')).with_lang(lang('%s %s Slab', *rock_lang(block_type, rock)))
            slab_loot(rm, 'rnr:rock/%s/%s_slab' % (block_type, rock))

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
