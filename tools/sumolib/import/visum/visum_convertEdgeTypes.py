#!/usr/bin/env python
"""
@file    visum_convertEdgeTypes.py
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2009-05-27
@version $Id: visum_convertEdgeTypes.py 12599 2012-08-27 11:15:34Z dkrajzew $


This script converts edge type definitions (STRECKENTYP) into their
 SUMO-representation.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2009-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import sys, os

if len(sys.argv) < 3:
    print "Usage: " + sys.argv[0] + " <VISUM-NET> <OUTPUT>"
    sys.exit()
print "Reading VISUM..."
fd = open(sys.argv[1])
fdo = open(sys.argv[2], "w")
fdo.write("<types>\n")
parsingTypes = False
for line in fd:
    if parsingTypes:
        if line[0]=='*' or line[0]=='$' or line.find(";")<0:
            parsingTypes = False
            continue

        values = line.strip().split(";")
        map = {}
        for i in range(0, len(attributes)):
            map[attributes[i]] = values[i]
        fdo.write('   <type id="' + map["nr"])
        fdo.write('" priority="' + str(100-int(map["rang"])))
        fdo.write('" numLanes="' + map["anzfahrstreifen"])
        fdo.write('" speed="' + str(float(map["v0iv"])/3.6))
        fdo.write('"/>\n')

    if line.find("$STRECKENTYP")==0:
        parsingTypes = True
        attributes = line[len("$STRECKENTYP:"):].lower().split(";")
fdo.write("</types>\n")

