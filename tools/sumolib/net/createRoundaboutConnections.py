#!/usr/bin/env python
"""
@file    createRoundaboutConnections.py
@author  Laura Bieker
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2007-02-21
@version $Id: createRoundaboutConnections.py 11671 2012-01-07 20:14:30Z behrisch $

This script reads in the network given as
 first parameter and generates additional connections for roundabouts.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2010-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""


import os, sys
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import sumolib.net

def writeConnections(net):
    fd = open("roundabout-connection.con.xml", "w")
    fd.write("<connections>\n")
    for ra in net.getRoundabouts():
        for node in ra.getNodes():
            edgesOut= net.getNode(node)._outgoing
            edgesIn= net.getNode(node)._incoming
            for edgeOut in edgesOut:
                outNumber= edgeOut.getLaneNumber() 
                for edgeIn in edgesIn:
                    if not edgeOut in edgeIn._outgoing:
                        continue
                    inNumber= edgeIn.getLaneNumber() 
                    for x in range(inNumber):
                        if x < inNumber and x < outNumber:
                            fd.write("   <connection from=\"" +str(edgeIn._id)+ "\" to=\"" + str(edgeOut._id)+ "\" lane=\""+ str(x) +":"+ str(x) +"\" />\n")
    fd.write("</connections>\n")

    
if len(sys.argv) < 2:
    print "Usage: " + sys.argv[0] + " <net>"
    sys.exit()
print "Reading net..."
net = sumolib.net.readNet(sys.argv[1])
print "Writing connections..."
writeConnections(net)
