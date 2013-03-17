#!/usr/bin/env python
"""
@file    one-shot.py
@author  Daniel Krajzewicz
@author  Jakob Erdmann
@author  Yun-Pang Wang
@author  Michael Behrisch
@date    2008-03-10
@version $Id: one-shot.py 12433 2012-06-26 17:30:19Z yunpangwang $

This script does multiple sumo runs with different rerouting intervals.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import os, sys, subprocess
from datetime import datetime
from optparse import OptionParser
sys.path.append(os.path.join(os.path.dirname(sys.argv[0]), '..', 'lib'))
from testUtil import checkBinary

def call(command, log):
    print >> log, "-" * 79
    print >> log, command
    retCode = subprocess.call(command, stdout=log, stderr=log)
    if retCode != 0:
        print >> sys.stderr, "Execution of %s failed. Look into %s for details." % (command, log.name)
        sys.exit(retCode) 

def writeSUMOConf(step, options, files):
    fd = open("one_shot_" + str(step) + ".sumocfg", "w")
    print >> fd, """<configuration>
    <files>
        <net-file value="%s"/>
        <route-files value="%s"/>
        <vehroutes value="vehroutes_%s.xml"/>""" % (options.net, files, step)
    if not options.noSummary:
        print >> fd, '        <summary value="summary_%s.xml"/>' % step
    if not options.noTripinfo:
        print >> fd, '        <tripinfo value="tripinfo_%s.xml"/>' % step
    if options.weightfiles:
        print >> fd, '        <weight-files value="%s"/>' % options.weightfiles

    add = 'dump_%s.add.xml' % step
    if options.additional:
        add += "," + options.additional
    print >> fd, """        <additional-files value="%s"/>
    </files>
    <process>
        <begin value="%s"/>
        <route-steps value="%s"/>""" % (add, options.begin, options.routeSteps)

    if options.end:
        print >> fd, '        <end value="%s"/>' % options.end
    if options.mesosim:
        print >> fd, '        <mesosim value="True"/>'
    if options.routingalgorithm:
        print >> fd, '        <routing-algorithm value="%s"/>' % options.routingalgorithm
    print >> fd, """        <device.rerouting.probability value="1"/>
        <device.rerouting.period value="%s"/>
        <device.rerouting.adaptation-interval value="%s"/>
        <device.rerouting.with-taz value="%s"/>
        <device.rerouting.explicit value="%s"/>
        <vehroute-output.last-route value="%s"/>
        <vehroute-output.exit-times value="%s"/>
        <vehroute-output.sorted value="%s"/>
    </process>
    <reports>
        <verbose value="True"/>
        <no-warnings value="%s"/>
    </reports>
</configuration>""" % (step, options.updateInterval, options.withtaz, options.reroutingexplicit, options.lastRoutes, options.withexittime, options.routesorted, not options.withWarnings)
    fd.close()
    fd = open("dump_%s.add.xml" % step, "w")
    print >> fd, """<a>
    <edgeData id="dump_%s_%s" freq="%s" file="dump_%s_%s.xml" excludeEmpty="true"/>
</a>""" % (step, options.aggregation, options.aggregation, step, options.aggregation)
    fd.close()

optParser = OptionParser()
optParser.add_option("-W", "--with-warnings", action="store_true", dest="withWarnings",
                     default=False, help="enables warnings")

optParser.add_option("-n", "--net-file", dest="net",
                     help="SUMO network (mandatory)", metavar="FILE")
optParser.add_option("-t", "--trips", dest="trips",
                     help="trips in step 0", metavar="FILE")

optParser.add_option("-b", "--begin", dest="begin",
                     type="int", default=0, help="Set simulation/routing begin")
optParser.add_option("-e", "--end", dest="end",
                     type="int", help="Set simulation/routing end")
optParser.add_option("-R", "--route-steps", dest="routeSteps",
                     type="int", default=200, help="Set simulation route steps")
optParser.add_option("-a", "--aggregation", dest="aggregation",
                     type="int", default=900, help="Set main weights aggregation period")
optParser.add_option("-f", "--frequencies", dest="frequencies",
                     default="-1,1800,300,15", help="Set the frequencies to iterate over")
optParser.add_option("-i", "--adaptation-interval", dest="updateInterval",
                     type="int", default=1, help="Set edge weight adaptation interval")

optParser.add_option("-E", "--disable-summary", "--disable-emissions", action="store_true", dest="noSummary",
                     default=False, help="No summaries are written by the simulation")
optParser.add_option("-T", "--disable-tripinfos", action="store_true", dest="noTripinfo",
                     default=False, help="No tripinfos are written by the simulation")
optParser.add_option("-m", "--mesosim", action="store_true", dest="mesosim",
                     default=False, help="Whether mesosim shall be used")
optParser.add_option("-w", "--with-taz", action="store_true", dest="withtaz",
                     default=False, help="Whether districts shall be used")
optParser.add_option("-+", "--additional", dest="additional",
                     default="", help="Additional files")
optParser.add_option("-L", "--lastRoutes", action="store_true", dest="lastRoutes",
                     default=False, help="only save the last routes in the vehroute-output")
optParser.add_option("-F", "--weight-files", dest="weightfiles",
                     help="Load edge/lane weights from FILE", metavar="FILE")
optParser.add_option("-A", "--routing-algorithm", dest="routingalgorithm", type="choice",
                    choices=('dijkstra', 'astar'),
                    default="astar", help="type of routing algorithm [default: %default]")
optParser.add_option("-r", "--rerouting-explicit", dest="reroutingexplicit", type="string",
                     default = "", help="define the ids of the vehicles that should be re-routed.")
optParser.add_option("-x", "--with-exittime", action="store_true", dest="withexittime",
                    default= False, help="Write the exit times for all edges")
optParser.add_option("-s", "--route-sorted", action="store_true", dest="routesorted",
                    default= False, help="sorts the output by departure time") 
optParser.add_option("-p", "--path", dest="path",
                     default=os.environ.get("SUMO_BINDIR", ""), help="Path to binaries")
(options, args) = optParser.parse_args()

sumo = "sumo"
if options.mesosim:
    sumo = "meso"
if options.path:    
    if os.path.isfile(options.path):
        sumoBinary = options.path
    else:
        sumoBinary = checkBinary(sumo, options.path)        
else:
    sumoBinary = checkBinary(sumo)

log = open("one_shot-log.txt", "w")
starttime = datetime.now()
for step in options.frequencies.split(","):
    step = int(step)
    print "> Running simulation with update frequency %s" % step
    btime = datetime.now()
    print ">> Begin time %s" % btime
    writeSUMOConf(step, options, options.trips)
    call([sumoBinary, "-c", "one_shot_%s.sumocfg" % step], log)
    etime = datetime.now()
    print ">> End time %s" % etime
    print "< Step %s ended (duration: %s)" % (step, etime-btime)
    print "------------------\n"
print "one-shot ended (duration: %s)" % (datetime.now() - starttime)

log.close()
