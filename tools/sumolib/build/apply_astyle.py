#!/usr/bin/env python
"""
@file    apply_astyle.py
@author  Michael Behrisch
@author  Daniel Krajzewicz
@date    2007
@version $Id: apply_astyle.py 11671 2012-01-07 20:14:30Z behrisch $

Applies astyle with the proper settings used in SUMO on all
 files in src.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import os, subprocess

srcRoot = os.path.join(os.path.dirname(__file__), "../../src/")
for root, dirs, files in os.walk(srcRoot):
    for name in files:
        if name.endswith(".h") or name.endswith(".cpp"):
            subprocess.call("astyle --style=java --unpad-paren --pad-header --pad-oper --add-brackets --indent-switches --align-pointer=type -n".split() + [os.path.join(root, name)])
        for ignoreDir in ['.svn', 'foreign']:
            if ignoreDir in dirs:
                dirs.remove(ignoreDir)
