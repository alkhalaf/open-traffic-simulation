#!/usr/bin/env python
"""
@file    schemaCheck.py
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    03.12.2009
@version $Id: schemaCheck.py 12854 2012-10-17 08:48:36Z dkrajzew $

Checks schema for files matching certain file names using either
lxml or SAX2Count.exe depending on availability.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2009-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

import os, sys, subprocess, glob, traceback
try:
    from lxml import etree
    haveLxml = True
    schemes = {}
except ImportError:
    haveLxml = False

def relative_to_sumo_home(path):
    if "/sumo/" in path:
        return path[path.index("/sumo/")+6:]
    else:
        return path


def validate(root, f):
    root = os.path.abspath(root)
    rs = root.replace('\\', '/')
    try:
        doc = etree.parse(f)
        schemaLoc = doc.getroot().get('{http://www.w3.org/2001/XMLSchema-instance}noNamespaceSchemaLocation')
        if schemaLoc:
            localSchema = os.path.join(os.path.dirname(__file__), '..', '..', 'docs', 'internet', 'xsd', os.path.basename(schemaLoc))
            if os.path.exists(localSchema):
                schemaLoc = localSchema
            if schemaLoc not in schemes:
                schemes[schemaLoc] = etree.XMLSchema(etree.parse(schemaLoc))
            schemes[schemaLoc].validate(doc)
            errors = list(schemes[schemaLoc].error_log)
            errors.sort()
            for entry in errors:
                e = os.path.abspath(str(entry)).replace('\\', '/')
                e = e[e.find("file:")+7+len(rs):]
                print >> sys.stderr, e
    except:
        print >> sys.stderr, "Error on parsing '%s'!" % os.path.abspath(f)[len(root):].replace('\\', '/')
        traceback.print_exc()

def main(srcRoot, toCheck, err):
    if not toCheck:
        toCheck = [ "*.edg.xml", "*.nod.xml", "*.con.xml", "*.typ.xml",
                "*.net.xml", "*.rou.xml", "*.add.xml", "*.????cfg",
                "net.netgen", "net.netconvert",
                "net.scenario", "tls.scenario",
                "routes.duarouter", "alts.duarouter", "routes.jtrrouter",
                "*.turns.xml" ]
    sax2count = "SAX2Count.exe"
    if 'XERCES_64' in os.environ:
        sax2count = os.path.join(os.environ['XERCES_64'], "bin", sax2count)
    elif 'XERCES' in os.environ:
        sax2count = os.path.join(os.environ['XERCES'], "bin", sax2count)

    fileNo = 0
    if os.path.exists(srcRoot):
        if os.path.isdir(srcRoot):
            for root, dirs, files in os.walk(srcRoot):
                for pattern in toCheck:
                    for name in glob.glob(os.path.join(root, pattern)):
                        if haveLxml:
                            validate(srcRoot, name)
                        elif os.name != "posix":
                            subprocess.call(sax2count + " -v=always -f " + name, stdout=open(os.devnull), stderr=err)
                        fileNo += 1
                    if '.svn' in dirs:
                        dirs.remove('.svn')
        else:
            if haveLxml:
                validate("", srcRoot)
            elif os.name != "posix":
                subprocess.call(sax2count + " -v=always -f " + srcRoot, stdout=open(os.devnull), stderr=err)
            fileNo += 1
    else:
        print >> err, "cannot open", srcRoot
        return 1
    print "%s files checked" % fileNo
 
    if haveLxml:
        for scheme in schemes.itervalues():
            if scheme.error_log:
                print >> err, scheme.error_log
                return 1
    return 0

if __name__ == "__main__":
    if os.name == "posix" and not haveLxml:
        print >> sys.stderr, "neither SAX2Count nor lxml available, exiting"
        sys.exit(1)
    srcRoot = "."
    if len(sys.argv) > 1:
        srcRoot = sys.argv[1]
    toCheck = None
    if len(sys.argv) > 2:
        toCheck = sys.argv[2].split(",")
    sys.exit(main(srcRoot, toCheck, sys.stderr))
