"""
@file    dijkstra.py
@author  Yun-Pang Wang
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2007-10-25
@version $Id: dijkstra.py 11700 2012-01-10 22:20:15Z behrisch $

This script is based on the script from David Eppstein, UC Irvine.
This script is to find the shortest path from the given origin 'start' to the other nodes in the investigated network. 
The Dijkstra algorithm is used for searching the respective shortest paths. 
the link information about the shortest paths and the corresponding travel times   
will be stored in the lists P and D respectively.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
class priorityDictionary(dict):
    def __init__(self):
        '''Initialize priorityDictionary by creating binary heap
            of pairs (value,key).  Note that changing or removing a dict entry will
            not remove the old pair from the heap until it is found by smallest() or
            until the heap is rebuilt.'''
        self.__heap = []
        dict.__init__(self)

    def smallest(self):
        '''Find smallest item after removing deleted items from heap.'''
        if len(self) == 0:
            raise IndexError, "smallest of empty priorityDictionary"
        heap = self.__heap
        while heap[0][1] not in self or self[heap[0][1]] != heap[0][0]:
            lastItem = heap.pop()
            insertionPoint = 0
            while 1:
                smallChild = 2*insertionPoint+1
                if smallChild+1 < len(heap) and \
                        heap[smallChild][0] > heap[smallChild+1][0]:
                    smallChild += 1
                if smallChild >= len(heap) or lastItem <= heap[smallChild]:
                    heap[insertionPoint] = lastItem
                    break
                heap[insertionPoint] = heap[smallChild]
                insertionPoint = smallChild
        return heap[0][1]

    def __iter__(self):
        '''Create destructive sorted iterator of priorityDictionary.'''
        def iterfn():
            while len(self) > 0:
                x = self.smallest()
                yield x
                del self[x]
        return iterfn()

    def __setitem__(self,key,val):
        '''Change value stored in dictionary and add corresponding
            pair to heap.  Rebuilds the heap if the number of deleted items grows
            too large, to avoid memory leakage.'''
        dict.__setitem__(self,key,val)
        heap = self.__heap
        if len(heap) > 2 * len(self):
            self.__heap = [(v,k) for k,v in self.iteritems()]
            self.__heap.sort()  # builtin sort likely faster than O(n) heapify
        else:
            newPair = (val,key)
            insertionPoint = len(heap)
            heap.append(None)
            while insertionPoint > 0 and val < heap[(insertionPoint-1)//2][0]:
                heap[insertionPoint] = heap[(insertionPoint-1)//2]
                insertionPoint = (insertionPoint-1)//2
            heap[insertionPoint] = newPair

    def setdefault(self,key,val):
        '''Reimplement setdefault to call our customized __setitem__.'''
        if key not in self:
            self[key] = val
        return self[key]

    def update(self, other):
        for key in other.keys():
            self[key] = other[key]


def dijkstra(start, targets):
    # dictionary of final distances
    D = {}
    # dictionary of predecessors
    P = {}
    # est.dist. of non-final vert.
    Q = priorityDictionary()
    Q[start] = 0
    for v in Q:
        D[v] = Q[v]
        if targets.discard(v):
            if len(targets) == 0:
                return (D, P)
        isConflictCandidate = (v != start) and (P[v].conflictlink != None)
        for edge in v.outEdges:
            w = edge.target
            vwLength = D[v] + edge.helpacttime
            if isConflictCandidate:
                if (edge.kind == "junction" and iter(edge.target.outEdges).next() in P[v].leftlink) or\
                   (edge.kind != "junction" and edge in P[v].leftlink):
                    vwLength += P[v].penalty

            if w not in D and (w not in Q or vwLength < Q[w]):
                Q[w] = vwLength
                P[w] = edge
    return (D, P)


def dijkstraPlain(start, targets):
    # dictionary of final distances
    D = {}
    # dictionary of predecessors
    P = {}
    # est.dist. of non-final vert.
    Q = priorityDictionary()
    Q[start] = 0
    for v in Q:
        D[v] = Q[v]
        if targets.discard(v):
            if len(targets) == 0:
                return (D, P)
        for edge in v.getOutgoing():
            w = edge._to
            vwLength = D[v] + edge.helpacttime

            if w not in D and (w not in Q or vwLength < Q[w]):
                Q[w] = vwLength
                P[w] = edge
    return (D, P)


def dijkstraBoost(boostGraph, start):
    from boost.graph import dijkstra_shortest_paths
    dijkstra_shortest_paths(boostGraph, start,
                            distance_map = boostGraph.vertex_properties['distance'], 
                            predecessor_map = boostGraph.vertex_properties['predecessor'], 
                            weight_map = boostGraph.edge_properties['weight'])
    
    # dictionary of final distances
    D = {}
    # dictionary of predecessors
    P = {}
    for v in boostGraph.vertices:
        D[v.partner] = v.distance
        for edge in v.partner.inEdges:
            if edge.source == v.predecessor.partner:
                P[v.partner] = edge
                break
    return (D, P)
