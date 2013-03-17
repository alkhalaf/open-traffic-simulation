#!/usr/bin/env python
"""
@file    addSchema.py
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2010
@version $Id: addSchema.py 11671 2012-01-07 20:14:30Z behrisch $

Inserts XML schema references.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2010-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import os, shutil, glob

schema = 'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'

proc = {
"*.rou.xml": '<routes %s xsi:noNamespaceSchemaLocation="http://sumo.sf.net/xsd/routes_file.xsd"' % schema,
"*.edg.xml": '<edges %s xsi:noNamespaceSchemaLocation="http://sumo.sf.net/xsd/edges_file.xsd"' % schema,
"*.nod.xml": '<nodes %s xsi:noNamespaceSchemaLocation="http://sumo.sf.net/xsd/nodes_file.xsd"' % schema,
"*.typ.xml": '<types %s xsi:noNamespaceSchemaLocation="http://sumo.sf.net/xsd/types_file.xsd"' % schema,
"*.con.xml": '<connections %s xsi:noNamespaceSchemaLocation="http://sumo.sf.net/xsd/connections_file.xsd"' % schema
}

srcRoot = os.path.join(os.path.dirname(__file__), "..", "..")

for root, dirs, files in os.walk(srcRoot):
    for pattern, repTo in proc.iteritems():
        for name in glob.glob(os.path.join(root, pattern)):
            repFrom = repTo[:repTo.find(' ')]
            print "Patching '%s'" % name
            shutil.copy(name, "totest.xml")
            fdi = open("totest.xml")
            fdo = open("totest.patch", "w")
            for line in fdi:
                if repFrom in line and schema not in line:
                    line = line.replace(repFrom, repTo)
                fdo.write(line)
            fdo.close()
            fdi.close()
            shutil.copy("totest.patch", name)
        for ignoreDir in ['.svn', 'foreign']:
            if ignoreDir in dirs:
                dirs.remove(ignoreDir)
