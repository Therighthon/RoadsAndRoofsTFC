import assets
import data
from mcresources import ResourceManager


def main():
    rm = ResourceManager('rnr')

    # Anything we want to run here
    assets.generate(rm)

    rm.flush()  # call flush to finish any tag or lang files, see note below
