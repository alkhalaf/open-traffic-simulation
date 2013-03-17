#!/usr/bin/env python
"""
@file    netcheck.py
@author  Michael Behrisch
@author  Daniel Krajzewicz
@author  Laura Bieker
@date    2007-03-20
@version $Id: netcheck.py 12371 2012-06-07 20:27:10Z behrisch $

This script does simple check for the network.
It tests whether the network is (weakly) connected.
It needs one parameter, the SUMO net (.net.xml).

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import os, sys
from optparse import OptionParser
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import sumolib.net

def parse_args():
    USAGE = "Usage: " + sys.argv[0] + " <net> <options>"
    optParser = OptionParser()
    optParser.add_option("-s", "--source",
            default=False, help="List edges reachable from the source")
    optParser.add_option("--selection-output", 
            help="When used with --source, write output to file as a loadable selection")

    options, args = optParser.parse_args()
    if len(args) != 1:
        sys.exit(USAGE)
    options.net = args[0]
    return options 


def getWeaklyConnected(net):
    components = []
    edgesLeft = set(net.getEdges())
    queue = list()
    while len(edgesLeft) != 0:
        component = set()
        queue.append(edgesLeft.pop())
        while not len(queue) == 0:
            edge = queue.pop(0)
            component.add(edge.getID())
            for n in edge.getOutgoing().iterkeys():
                if n in edgesLeft:
                    queue.append(n)
                    edgesLeft.remove(n)
            for n in edge.getIncoming().iterkeys():
                if n in edgesLeft:
                    queue.append(n)
                    edgesLeft.remove(n)
        components.append(component)
    return components


def getReachable(net, source_id, options):
    if not net.hasEdge(source_id):
        sys.exit("'%s' is not a valid edge id" % source_id)
    source = net.getEdge(source_id)
    fringe = [source]
    found = set()
    found.add(source)
    while len(fringe) > 0:
        new_fringe = []
        for edge in fringe:
            for reachable in edge.getOutgoing().iterkeys():
                if not reachable in found:
                    found.add(reachable)
                    new_fringe.append(reachable)
        fringe = new_fringe

    print "%s of %s edges are reachable from edge '%s':" % (
            len(found), len(net.getEdges()), source_id)

    if options.selection_output:
        with open(options.selection_output, 'w') as f:
            for e in found:
                f.write("edge:%s\n" % e.getID())
    else:
        print [e.getID() for e in found]


if __name__ == "__main__":
    options = parse_args()

    net = sumolib.net.readNet(options.net)
    if options.source:
        getReachable(net, options.source, options)
    else:
        components = getWeaklyConnected(net)
        if len(components) != 1:
            print "Warning! Net is not connected."
            for idx, comp in enumerate(sorted(components, key=lambda c: iter(c).next())):
                print "Component", idx
                print " ".join(comp)
                print

