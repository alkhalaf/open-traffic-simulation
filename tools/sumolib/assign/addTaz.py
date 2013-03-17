#!/usr/bin/env python
"""
@file    addTaz.py
@author  Michael Behrisch
@date    2009-05-20
@version $Id: addTaz.py 12595 2012-08-24 14:07:33Z dkrajzew $

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2009-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import sys
from xml.sax import make_parser, handler
from optparse import OptionParser

class RouteReader(handler.ContentHandler):

    def __init__(self, out):
        self._idToTaz = {}
        self._out = out

    def startElement(self, name, attrs):
        if name == 'trip':
            self._idToTaz[attrs['id']] = (attrs['fromtaz'], attrs['totaz'])
        elif name != 'tripdefs':
            print >> self._out, '<' + name,
            for key in attrs.keys():
                print >> self._out,  '%s="%s"' % (key, attrs[key]),
            if name == 'vehicle':
                print >> self._out,  'fromTaz="%s" toTaz="%s"' % self._idToTaz[attrs['id']],
            print >> self._out,  '>'

    def endElement(self, name):
        if name != 'tripdefs' and name != 'trip':
            print >> self._out,  '</%s>' % name

def parse(trips, routes, out):
    parser = make_parser()
    parser.setContentHandler(RouteReader(out))
    parser.parse(trips)
    parser.parse(routes)

if __name__ == "__main__":
    optParser = OptionParser(usage="usage: %prog [options] <routefile>+")
    optParser.add_option("-r", "--routes", help="routes file")
    optParser.add_option("-t", "--trips", help="trips file")
    (options, args) = optParser.parse_args()
    parse(options.trips, options.routes, sys.stdout)
