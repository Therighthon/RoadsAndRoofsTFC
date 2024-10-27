import os

dirts = open("dirts.txt", 'r').read().split("\n")
rocks = open("rocks.txt", 'r').read().split("\n")
block_types = open("rock_block_types.txt", 'r').read().split("\n")
metals = open("mattock_metals.txt", 'r').read().split("\n")
sands = open("tfc_sands.txt", 'r').read().split("\n")
woods = open("woods.txt", 'r').read().split("\n")
tfc_woods = open("tfc_woods.txt", 'r').read().split("\n")


string = """{
"""

for rock in rocks:
    for block in block_types:
        string = string + """  "block.rnr.rock.%s.%s": "%s %s",
""" % (block, rock, rock.replace("_", " ").title(), block.replace("_", " ").title())
        string = string + """  "block.rnr.rock.%s.%s_slab": "%s %s Slab",
""" % (block, rock, rock.replace("_", " ").title(), block.replace("_", " ").title())
        string = string + """  "block.rnr.rock.%s.%s_stairs": "%s %s Stairs",
""" % (block, rock, rock.replace("_", " ").title(), block.replace("_", " ").title())
    string = string + """  "block.rnr.rock.over_height_gravel.%s": "%s %s",
""" % (rock, rock.replace("_", " ").title(), "Over Height Gravel")
    string = string + """  "item.rnr.gravel_fill.%s": "%s Gravel Fill",
""" % (rock, rock.replace("_", " ").title())
    string = string + """  "item.rnr.flagstone.%s": "%s Flagstone",
""" % (rock, rock.replace("_", " ").title())

for rock in sands:
    string = string + """  "item.rnr.flagstone.%s_sandstone": "%s %s",
""" % (rock, rock.replace("_", " ").title(), "Sandstone Flagstone")
    string = string + """  "block.rnr.%s_sandstone_flagstones": "%s Sandstone Flagstones",
""" % (rock, rock.replace("_", " ").title())
    string = string + """  "block.rnr.%s_sandstone_flagstones_slab": "%s Sandstone Flagstones Slab",
""" % (rock, rock.replace("_", " ").title())
    string = string + """  "block.rnr.%s_sandstone_flagstones_stairs": "%s Sandstone Flagstones Stairs",
""" % (rock, rock.replace("_", " ").title())

for dirt in dirts:
    string = string + """  "block.rnr.tamped_%s": "Tamped %s",
""" % (dirt, dirt.replace("_", " ").title())

for metal in metals:
    string = string + """  "item.rnr.metal.mattock_head.%s": "%s Mattock Head",
""" % (metal, metal.replace("_", " ").title())
    string = string + """  "item.rnr.metal.mattock.%s": "%s Mattock",
""" % (metal, metal.replace("_", " ").title())

for wood in tfc_woods:
    string = string + """  "item.rnr.wood.shingle.%s": "%s Shingle",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s": "%s Shingled Roof",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s_slab": "%s Shingled Roof Slab",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s_stairs": "%s Shingled Roof Stairs",
""" % (wood, wood.replace("_", " ").title())

for wood in woods:
    string = string + """  "item.rnr.wood.shingle.%s": "%s Shingle",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s": "%s Shingled Roof",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s_slab": "%s Shingled Roof Slab",
""" % (wood, wood.replace("_", " ").title())
    string = string + """  "block.rnr.wood.shingles.%s_stairs": "%s Shingled Roof Stairs",
""" % (wood, wood.replace("_", " ").title())

string = string + """  "rnr.mattock.cannot_place": "The mattocked version of this block cannot exist here",
  "rnr.mattock.no_recipe": "This block cannot be mattocked",
  "rnr.mattock.bad_fluid": "The mattocked version of this block cannot contain the fluid here",
  "block.rnr.base_course": "Base Course",
  "block.rnr.hoggin": "Hoggin Road",
  "block.rnr.hoggin_slab": "Hoggin Road Slab",
  "block.rnr.hoggin_stairs": "Hoggin Road Stairs",
  "block.rnr.brick_road": "Ceramic Sett Road",
  "block.rnr.brick_road_slab": "Ceramic Sett Road Slab",
  "block.rnr.brick_road_stairs": "Ceramic Sett Road Stairs",
  "block.rnr.concrete_road": "Concrete Road",
  "block.rnr.concrete_road_slab": "Concrete Road Slab",
  "block.rnr.concrete_road_stairs": "Concrete Road Stairs",
  "block.rnr.cracked_concrete_road": "Cracked Concrete Road",
  "block.rnr.cracked_concrete_road_slab": "Cracked Concrete Road Slab",
  "block.rnr.cracked_concrete_road_stairs": "Cracked Concrete Road Stairs",
  "block.rnr.trodden_concrete_road": "Trodden Concrete Road",
  "block.rnr.trodden_concrete_road_slab": "Trodden Concrete Road Slab",
  "block.rnr.trodden_concrete_road_stairs": "Trodden Concrete Road Stairs",
  "block.rnr.cracked_trodden_concrete_road": "Cracked Trodden Concrete Road",
  "block.rnr.cracked_trodden_concrete_road_slab": "Cracked Trodden Concrete Road Slab",
  "block.rnr.cracked_trodden_concrete_road_stairs": "Cracked Trodden Concrete Road Stairs",
  "block.rnr.concrete_road_panel": "Concrete Road Panel",
  "block.rnr.concrete_road_panel_slab": "Concrete Road Panel Slab",
  "block.rnr.concrete_road_panel_stairs": "Concrete Road Panel Stairs",
  "block.rnr.concrete_road_sett": "Concrete Road Faux Sett",
  "block.rnr.concrete_road_sett_slab": "Concrete Road Faux Sett Slab",
  "block.rnr.concrete_road_sett_stairs": "Concrete Road Faux Sett Stairs",
  "block.rnr.concrete_road_flagstones": "Concrete Road Faux Flagstones",
  "block.rnr.concrete_road_flagstones_slab": "Concrete Road Faux Flagstones Slab",
  "block.rnr.concrete_road_flagstones_stairs": "Concrete Road Faux Flagstones Stairs",
  "block.rnr.concrete_road_control_joint": "Concrete Road Control Joint",
  "block.rnr.pouring_concrete_road": "Pouring Concrete Road",
  "block.rnr.wet_concrete_road": "Wet Concrete Road",
  "block.rnr.wet_trodden_concrete_road": "Wet Trodden Concrete Road",
  "block.rnr.wet_concrete_road_control_joint": "Wet Concrete Road Control Joint",
  "block.rnr.wet_concrete_road_sett": "Wet Concrete Road Faux Sett",
  "block.rnr.wet_concrete_road_flagstones": "Wet Concrete Road Faux Flagstones",
  "block.rnr.wet_concrete_road_panel": "Wet Concrete Road Panel",
  "item.rnr.bucket.concrete": "Wet Concrete Mix Bucket",
  "item.rnr.hoggin_mix": "Hoggin Mix",
  "item.rnr.unfired_terracotta_roof_tile": "Unfired Terracotta Roof Tile",
  "item.rnr.terracotta_roof_tile": "Terracotta Roof Tile",
  "item.rnr.unfired_roof_tile": "Unfired Roof Tile",
  "item.rnr.ceramic_roof_tile": "Ceramic Roof Tile",
  "item.rnr.concrete_powder": "Dry Concrete Mix",
  "fluid.rnr.concrete": "Wet Concrete Mix",
  "item.rnr.crushed_base_course": "Crushed Base Course",
  "block.rnr.roof_frame": "Roof Framing",
  "block.rnr.roof_frame_stairs": "Roof Framing Stairs",
  "block.rnr.roof_frame_slab": "Roof Framing Slab",
  "block.rnr.thatch_roof": "Thatch Roof",
  "block.rnr.thatch_roof_stairs": "Thatch Roof Stairs",
  "block.rnr.thatch_roof_slab": "Thatch Roof Slab",
  "block.rnr.terracotta_roof": "Terracotta Tile Roof",
  "block.rnr.terracotta_roof_stairs": "Terracotta Tile Roof Stairs",
  "block.rnr.terracotta_roof_slab": "Terracotta Tile Roof Slab",
  "block.rnr.ceramic_roof": "Ceramic Tile Roof",
  "block.rnr.ceramic_roof_stairs": "Ceramic Tile Roof Stairs",
  "block.rnr.ceramic_roof_slab": "Ceramic Tile Roof Slab",
  "rnr.creative_tab.roads_and_roofs": "Roads and Roofs",
  "roads_and_roofs": "Roads and Roofs",
  "tfc.jei.block_mod": "Block Modification",
  "tfc.jei.mattock": "Mattock",
  "config.jade.plugin_tfc.wet_concrete": "Wet Concrete",
  "rnr.jade.will_crack": "Will crack when dry",
  "rnr.jade.will_not_crack": "Will not crack"

}"""

with open("en_us.json", 'w') as file:
    file.write(string)
    file.close()
