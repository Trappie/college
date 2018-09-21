from ants import *
hive, layout = Hive(AssaultPlan()), dry_layout
dimensions = (1, 9)
colony = AntColony(None, hive, ant_types(), layout, dimensions)
# Testing Slow
slow = SlowThrower()
bee = Bee(3)
colony.places["tunnel_0_0"].add_insect(slow)
colony.places["tunnel_0_4"].add_insect(bee)
slow.action(colony)
colony.time = 1
bee.action(colony)
