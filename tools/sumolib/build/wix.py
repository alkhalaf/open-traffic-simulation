#!/usr/bin/env python
"""
@file    wix.py
@author  Michael Behrisch
@date    2011
@version $Id: wix.py 11717 2012-01-13 12:31:46Z namdre $

Builds the installer based on the nightly zip.

SUMO, Simulation of Urban MObility; see http://sumo.sourceforge.net/
Copyright (C) 2008-2012 DLR (http://www.dlr.de/) and contributors
All rights reserved
"""
import optparse, subprocess, zipfile, os, sys, tempfile, glob

INPUT_DEFAULT = r"M:\Daten\Sumo\Nightly\sumo-msvc10Win32-svn.zip"
OUTPUT_DEFAULT = r"M:\Daten\Sumo\Nightly\sumo-msvc10Win32-svn.msi"
WIX_DEFAULT = "%sbin" % os.environ.get("WIX", r"D:\Programme\Windows Installer XML v3.5\\")
WXS_DEFAULT = os.path.join(os.path.dirname(__file__), "..", "..", "build", "sumo.wxs")
LICENSE = os.path.join(os.path.dirname(__file__), "..", "..", "build", "License.rtf")

def buildFragment(wixBin, sourceDir, targetLabel, tmpDir):
    base = os.path.basename(sourceDir)
    subprocess.call([os.path.join(wixBin, "heat.exe"), "dir", sourceDir,
                     "-cg", base, "-gg", "-dr", targetLabel, "-out", os.path.join(tmpDir, "Fragment.wxs")])
    fragIn = open(os.path.join(tmpDir, "Fragment.wxs"))
    fragOut = open(os.path.join(tmpDir, base+"Fragment.wxs"), "w")
    for l in fragIn:
        fragOut.write(l.replace("SourceDir", sourceDir))
    fragOut.close()
    fragIn.close()
    return fragOut.name

def buildMSI(sourceZip=INPUT_DEFAULT, outFile=OUTPUT_DEFAULT, wixBin=WIX_DEFAULT, wxs=WXS_DEFAULT,
             license=LICENSE, platformSuffix=""):
    #tmpDir = r"C:\Users\behr_mi\AppData\Local\Temp\tmpnq_cis"
    tmpDir = tempfile.mkdtemp()
    zipfile.ZipFile(sourceZip).extractall(tmpDir)
    sumoRoot = glob.glob(os.path.join(tmpDir, "sumo-*"))[0]
    fragments = []
    for d in ["userdoc", "pydoc", "tutorial", "examples"]:
        fragments.append(buildFragment(wixBin, os.path.join(sumoRoot, "docs", d), "DOCDIR", tmpDir))
    fragments.append(buildFragment(wixBin, os.path.join(sumoRoot, "tools"), "INSTALLDIR", tmpDir))
    wxsIn = open(wxs)
    wxsOut = open(os.path.join(tmpDir, "sumo.wxs"), "w")
    for l in wxsIn:
        l = l.replace("License.rtf", license)
        l = l.replace(".exe' />", "%s.exe' />" % platformSuffix).replace(r"Nightly\sumo-gui.exe", r"Nightly\sumo-gui%s.exe" % platformSuffix)
        wxsOut.write(l.replace(r"M:\Daten\Sumo\Nightly", os.path.join(sumoRoot, "bin")))
    wxsOut.close()
    wxsIn.close()
    subprocess.call([os.path.join(wixBin, "candle.exe"), "-o", tmpDir+"\\", wxsOut.name] + fragments)
    wixObj = [f.replace(".wxs", ".wixobj") for f in [wxsOut.name] + fragments] 
    subprocess.call([os.path.join(wixBin, "light.exe"),  "-ext", "WixUIExtension", "-o", outFile] + wixObj)

if __name__ == "__main__":
    optParser = optparse.OptionParser()
    optParser.add_option("-n", "--nightly-zip", dest="nightlyZip",
                         default=INPUT_DEFAULT, help="full path to nightly zip")
    optParser.add_option("-o", "--output", default=OUTPUT_DEFAULT,
                         help="full path to output file")
    optParser.add_option("-w", "--wix", default=WIX_DEFAULT, help="path to the wix binaries")
    optParser.add_option("-x", "--wxs", default=WXS_DEFAULT, help="path to wxs template")
    optParser.add_option("-l", "--license", default=LICENSE, help="path to the license")
    (options, args) = optParser.parse_args()
    buildMSI(options.nightlyZip, options.output, options.wix, options.wxs, options.license)
