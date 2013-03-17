# -*- coding: Latin-1 -*-
"""
@file    GenerateTaxiRoutes.py
@author  Sascha Krieg
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2008-04-07

Creates an Route-File, which contains for each Taxi the route, from an FCD-File.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""

from util.CalcTime import getTimeInSecs
from util.CalcTime import getNiceTimeLabel
import util.Path as path
import util.Reader as reader
from cPickle import load

#global vars
taxis=[]
routes=[]
vlsEdges=[]
taxiIdDict={} #contains for each Taxi the actual "TaxiId" based on the number of single routes which are created for them
fcdDict={}
vehIdListKeys=[]
vehIdListValues=[]
    
def getTaxiId(taxi):   
        
    return "%s_%s" %(taxi,taxiIdDict.setdefault(taxi,0))
    
def getSimTaxiId(taxi):  
    global vehIdListKeys, vehIdListValues
    
    if len(vehIdListKeys)<1:
        vehIdList=load(open(path.rawFcdVehIdList,'r'))
        vehIdListKeys=vehIdList.keys()
        vehIdListValues=vehIdList.values()     
   
    return "%s" %( vehIdListKeys[vehIdListValues.index(int(taxi))])

def readFCD(): 
    """Reads the FCD and creates a list of Taxis and for each a list of routes"""
    vlsEdges=reader.readVLS_Edges()
       
    inputFile=open(path.fcd,'r')
    for line in inputFile:
        words= line.split("\t")
        #add route
        taxiId=getTaxiId(words[4]) 
        actTime=getTimeInSecs(words[0])              
        if taxiId in taxis:
            prevTime=routes[taxis.index(taxiId)][-1][0]           
            if words[1] in vlsEdges and (actTime-prevTime)<180: #check if time lies not to far away from each other 
                routes[taxis.index(taxiId)].append((actTime, words[1]))
            elif words[1] in vlsEdges: #if time diff >3min add a new taxiId and start a new route
                taxiIdDict[words[4]]+=1   #create new taxiId    
                taxis.append(getTaxiId(words[4])) #append new created id
                routes.append([(actTime,words[1])]) #append new list (list will be filled with edges) 
            else:
                taxiIdDict[words[4]]+=1                
        elif words[1] in vlsEdges: #if the edge is in the VLS-Area a new route is created 
            taxis.append(taxiId)
            #                 departTime               
            routes.append([(actTime,words[1])])
           
    inputFile.close() 
    print len(taxis) 
    
def readFCDOLD(): 
    """Reads the FCD and creates a list of Taxis and for each a list of routes"""
    vlsEdges=reader.readVLS_Edges()
       
    inputFile=open(path.fcd,'r')
    for line in inputFile:
        words= line.split("\t")
        #add route
        taxiId=getTaxiId(words[4])              
        if taxiId in taxis:           
            if words[1] in vlsEdges:
                routes[taxis.index(taxiId)].append(words[1])
            else:
                taxiIdDict[words[4]]+=1                
        elif words[1] in vlsEdges: #if the edge is in the VLS-Area a new route is created 
            taxis.append(taxiId)
            #                 departTime               
            routes.append([getTimeInSecs(words[0]),words[1]])
           
    inputFile.close() 
    print len(taxis) 

def readFCDCompleteOLD(fcdPath):
    """Reads the FCD-File and creates a list of Id's with a belonging List of Data tuples."""
    #reset all
    global taxis, routes, vlsEdges, taxiIdDict, fcdDict
    taxis=[]
    routes=[]
    vlsEdges=[]
    taxiIdDict={} 
    fcdDict={}
    
    vlsEdges=reader.readVLS_Edges()
    
    inputFile=open(fcdPath,'r')
    for line in inputFile:
        words= line.split("\t")
        #add route
        taxiId=getTaxiId(words[4])              
        if taxiId in taxis:           
            if words[1] in vlsEdges:
                #routes[taxis.index(taxiId)].append(words[1])                
                fcdDict[taxiId].append((getTimeInSecs(words[0]),words[1],words[2]))
            else:
                taxiIdDict[words[4]]+=1                
        elif words[1] in vlsEdges: #if the edge is in the VLS-Area a new route is created 
            taxis.append(taxiId)
            #                 departTime               
            #routes.append([(int)(mktime(strptime(words[0],format))-simDate),words[1]])
            fcdDict[taxiId]=[(getTimeInSecs(words[0]),words[1],words[2])]
           
    inputFile.close()
    return fcdDict

def readFCDComplete(fcdPath): 
    """Reads the FCD and creates a list of Taxis and for each a list of routes"""
    #reset all
    global taxis, routes, vlsEdges, taxiIdDict, fcdDict
    taxis=[]
    routes=[]
    vlsEdges=[]
    taxiIdDict={} 
    fcdDict={}
    
    vlsEdges=reader.readVLS_Edges()
       
    inputFile=open(path.fcd,'r')
    for line in inputFile:
        words= line.split("\t")
        #add route
        taxiId=getTaxiId(words[4]) 
        actTime=getTimeInSecs(words[0])
                 
        if taxiId in taxis:
            #prevTime=routes[taxis.index(taxiId)][-1][0]       
            prevTime=fcdDict[taxiId][-1][0]          
            if words[1] in vlsEdges and (actTime-prevTime)<180: #check if time lies not to far away from each other 
                #routes[taxis.index(taxiId)].append((actTime, words[1]))
                fcdDict[taxiId].append((actTime,words[1],words[2]))
            elif words[1] in vlsEdges: #if time diff >3min add a new taxiId and start a new route
                taxiIdDict[words[4]]+=1   #create new taxiId    
                taxis.append(getTaxiId(words[4])) #append new created id
                fcdDict[getTaxiId(words[4])]=[(actTime,words[1],words[2])] #append new list (list will be filled with edges) 
            else:
                taxiIdDict[words[4]]+=1                
        elif words[1] in vlsEdges: #if the edge is in the VLS-Area a new route is created 
            taxis.append(taxiId)
            #                 departTime               
            #routes.append([(actTime,words[1])])            
            fcdDict[taxiId]=[(actTime,words[1],words[2])]
    inputFile.close() 
    return fcdDict
        
def readSimFCDComplete(fcdPath):
    """Reads the FCD-File and creates a list of Id's with a belonging List of Data tuples. Uses the given taxiIds."""
    #reset all
    global taxis, routes, vlsEdges, taxiIdDict, fcdDict
    taxis=[]
    routes=[]
    vlsEdges=[]
    taxiIdDict={} 
    fcdDict={}   
    
    inputFile=open(fcdPath,'r')
    for line in inputFile:
        words= line.split("\t")
        #add route
        taxiId=getSimTaxiId(words[4])               
        if taxiId in taxis:
            fcdDict[taxiId].append((getTimeInSecs(getNiceTimeLabel(words[0])),words[1],words[2]))                       
        else: 
            taxis.append(taxiId)            
            fcdDict[taxiId]=[(getTimeInSecs(getNiceTimeLabel(words[0])),words[1],words[2])]           
    inputFile.close()
    return fcdDict    
        
def writeRoutes():
    """Writes the collected values in a Sumo-Routes-File"""
    outputFile=open(path.taxiRoutes,'w')
    
    outputFile.write("<routes>\n")
    # known for like used in java
    for i in xrange(len(taxis)):              
        if len(routes[i])>3: 
            outputFile.write("\t<vehicle id=\""+taxis[i]+"\" type=\"taxi\" depart=\""+ str(routes[i][0][0])+"\" color=\"1,0,0\">\n")
            outputFile.write("\t\t<route>")
            for time,edge in routes[i]:
                  outputFile.write(edge+" ")
            outputFile.seek(-1,1) #delete the space between the last edge and </route>
            outputFile.write("</route>\n")
            outputFile.write("\t</vehicle>\n")
       
    outputFile.write("</routes>")
    outputFile.close()
    
    
