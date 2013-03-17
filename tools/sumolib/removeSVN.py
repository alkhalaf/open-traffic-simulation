#!/usr/bin/python
"""
@file    removeSVN.py
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    28-08-2008
@version $Id: removeSVN.py 11671 2012-01-07 20:14:30Z behrisch $

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import os, sys, stat, shutil


path = "./"
if len(sys.argv)>1:
    path = sys.argv[1]

# remove files in ".svn"
for root, dirs, files in os.walk(path):
    if root.find(".svn")>=0:
        for file in files:
            os.chmod(os.path.join(root, file), stat.S_IWRITE|stat.S_IREAD)
            os.remove(os.path.join(root, file))
        for dir in dirs:
            os.chmod(os.path.join(root, dir), stat.S_IWRITE|stat.S_IREAD)
    
# remove dirs in ".svn"
for root, dirs, files in os.walk(path):
    if ".svn" in dirs:
        dirs.remove(".svn")
        os.chmod(os.path.join(root, ".svn"), stat.S_IWRITE|stat.S_IREAD)
        shutil.rmtree(os.path.join(root, ".svn"))


