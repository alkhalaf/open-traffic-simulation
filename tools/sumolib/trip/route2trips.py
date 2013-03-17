#!/usr/bin/env python
"""
@file    route2trips.py
@author  Michael Behrisch
@author  Daniel Krajzewicz
@date    2008-03-19
@version $Id: route2trips.py 11671 2012-01-07 20:14:30Z behrisch $

This script converts SUMO routes back into SUMO trips which serve
as input to one of the routing applications.
It reads the routes from a file given as first parameter
and outputs the trips to stdout.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import sys, datetime

from xml.sax import parse, handler

class RouteReader(handler.ContentHandler):

    def __init__(self):
        self._vType = ''
        self._vID = ''
        self._vDepart = 0
        self._routeID = ''
        self._routeString = ''
        self._routes = {}
        
    def startElement(self, name, attrs):
        if name == 'vehicle':
            self._vehicleAttrs = attrs
            self._vID = attrs['id']
            print '    <trip',
            for key in attrs.keys():
                if key == "route":
                    self._routeString = self._routes[attrs['route']]
                else:
                    print '%s="%s"' % (key, attrs[key]),
        elif name == 'route':
            if not self._vID:
                self._routeID = attrs['id']
            self._routeString = ''
            if attrs.has_key('edges'):
                self._routeString = attrs['edges']
        elif name == 'routes':
            print """<?xml version="1.0"?>
<!-- generated on %s by $Id: route2trips.py 11671 2012-01-07 20:14:30Z behrisch $ -->
<trips>""" % datetime.datetime.now()

    def endElement(self, name):
        if name == 'route':
            if not self._vID:
                self._routes[self._routeID] = self._routeString
                self._routeString = ''
            self._routeID = ''
        elif name == 'vehicle':
            edges = self._routeString.split()
            print 'from="%s" to="%s"/>' % (edges[0], edges[-1])
            self._vID = ''
            self._routeString = ''
        elif name == 'routes':
            print "</trips>"

    def characters(self, content):
        self._routeString += content


            
if len(sys.argv) < 2:
    print "Usage: " + sys.argv[0] + " <routes>"
    sys.exit()
parse(sys.argv[1], RouteReader())
