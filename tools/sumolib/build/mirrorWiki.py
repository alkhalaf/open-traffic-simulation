#!/usr/bin/env python
"""
@file    getWikiPages.py
@author  Daniel Krajzewicz
@author  Michael Behrisch
@date    2011-10-20
@version $Id: mirrorWiki.py 11671 2012-01-07 20:14:30Z behrisch $

Mirrors wiki-documentation.

Determines what to mirror, first: if a command line argument is given,
it is interpreted as the page to mirror. Otherwise, "Special:AllPages" is
downloaded and parsed for obtaining the list of all pages which will be
converted in subsequent steps.

For each of the pages to mirror, the page is downloaded as for
being edited and is stripped from wiki-header/footer, first.
Then, the image-links are extracted from the HTML page and stored 
temporarily.
The page is saved into MIRROR_FOLDER/<PAGE_PATH>. 

After mirroring all pages, the images are downloaded and stored into 
MIRROR_FOLDER/images.

Copyright (C) 2011 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import urllib, os, sys, shutil
from optparse import OptionParser

def readParsePage(page):
    f = urllib.urlopen("http://sourceforge.net/apps/mediawiki/sumo/index.php?title=%s" % page)
    c = f.read()
    b = c.find("This page was last modified on");
    e = c.find("<", b)
    lastMod = c[b:e]
    b = c.find("globalWrapper")
    b = c.find('<a name="top"', b)
    e = c.find("<div class=\"printfooter\">")
    c = c[b:e]
    c = c.replace("<h3 id=\"siteSub\">From sumo</h3>", "")
    b = c.find("<div id=\"jump-to-nav\">")
    e = c.find("</div>", b)+6
    c = c[:b] + c[e:]
    c = c + '</div><hr/><div id="lastmod">' + lastMod + '</div>'
    return c

def readParseEditPage(page):
    f = urllib.urlopen("http://sourceforge.net/apps/mediawiki/sumo/index.php?title=%s&action=edit" % page)
    c = f.read()
    b = c.find("wpTextbox1")
    b = c.find('>', b) + 1
    e = c.find("</textarea>")
    return c[b:e]

def getImages(page):
    images = set()
    for t in ["Image:", "File:"]:
        b = page.find(t)
        while b >= 0:
            e = len(page)
            for ch in ["|", "\n", "]"]:
                pos = page.find(ch, b)
                if pos >= 0 and pos < e:
                    e = pos
            images.add(page[b:e].strip())
            b = page.find(t, b+1)
    return images

optParser = OptionParser()
optParser.add_option("-o", "--output", default="wiki", help="output folder")
(options, args) = optParser.parse_args()

try:
    os.makedirs(os.path.join(options.output, "images"))
except:
    pass
images = set()
if len(args) == 0:
    p = readParsePage("Special:AllPages")
    p = p[p.find("<input type=\"submit\" value=\"Go\" />"):]
    p = p[p.find("<table "):]
    pages = p.split("<a ")
else:
    pages = ["href=?title=" + args[0] + "\""]
for p in pages:
    if not p.startswith("href"):
        continue
    b = p.find("?title=")
    e = p.find("\"", b)
    name = p[b+7:e]
    print "Fetching %s" % name
    c = readParseEditPage(name)
    if name.find("/")>0:
        try: 
            os.makedirs(os.path.join(options.output, name[:name.rfind("/")]))
        except:
            pass
        images.update(getImages(c))
    name = name + ".txt"
    fd = open(os.path.join(options.output, name), "w")
    fd.write(c)
    fd.close()

for i in images:
    print "Fetching image %s" % i
    if i.find(":")>=0:
        f = urllib.urlopen("http://sourceforge.net/apps/mediawiki/sumo/index.php?title=%s" % i)
        c = f.read()
        b = c.find("<div class=\"fullImageLink\" id=\"file\">")
        b = c.find("href=", b)+6
        e = c.find("\"", b+1)
        f = urllib.urlopen("http://sourceforge.net/%s" % c[b:e])
        i = i[i.find(":")+1:]
    else:
        f = urllib.urlopen("http://sourceforge.net/%s" % i)
        i = i[i.rfind("/")+1:]
    if i.find("px-") >= 0:
        i = i[:i.find('-')+1]
    fd = open(os.path.join(options.output, "images", i), "wb")
    fd.write(f.read())
    fd.close()
