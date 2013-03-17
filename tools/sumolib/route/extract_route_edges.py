#!/usr/bin/env python
"""
@file    randomTrips.py
@author  Jakob Erdmann
@date    2012-10-11
@version $Id: extract_route_edges.py 12845 2012-10-16 17:46:11Z behrisch $

Extract all used edges from routes and person plans and output a file suitable
for pruning edges with netconvert

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2010-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import os,sys
from collections import defaultdict
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from sumolib.output import parse, parse_fast

route_file, keep_file = sys.argv[1:]
edges = set()
for route in parse_fast(route_file, 'route', ['edges']):
    edges.update(route.edges.split())
for walk in parse_fast(route_file, 'walk', ['edges']):
    edges.update(walk.edges.split())

with open(keep_file, 'w') as outf:
    outf.write(','.join(edges) + '\n')
