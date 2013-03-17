package com.opentrafficsimulation.editor.light;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TrafficLightReader {

    /*
     * This method takes a network file, and retrieves all the junctions
     * present in the file. The method will return a list of these junctions.
     * 
     * @param fileName represents the file with the SUMO network data
     * 
     * @return specifies a list of junctions present in the network file
     */
    public ArrayList<Junction> readNetworkFile(String fileName) {
        ArrayList<Junction> junctionList = new ArrayList<Junction>();
        
        try {
            /*Set<String> unset = new HashSet<String>();
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));

                while (in.ready()) {
                    String s = in.readLine();
                    if (s != null) {
                        if (s.contains("tls.unset")) {
                            Pattern p = Pattern.compile("\"([^\"]*)\"");
                            Matcher m = p.matcher(s);
                            while (m.find()) {
                                unset.add(m.group(1));
                            }
                        }
                    }
                }
                in.close();
            } catch (Exception e) {
            }*/

            File nodes = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(nodes);
            document.getDocumentElement().normalize();
            NodeList junctionElements = document.getElementsByTagName("junction");
            for (int i = 0; i < junctionElements.getLength(); i++) {
                Node node = junctionElements.item(i);
                Element element = (Element) node;

                // Only include junctions where there are links
                if (element.getAttribute("intLanes").equals("")) {
                    continue;
                }

                // Ignore junctions that begin with a colon
                if (element.getAttribute("id").startsWith(":")) {
                    continue;
                }

                String type = element.getAttribute("type");
                String id = element.getAttribute("id");
                junctionList.add(new Junction(id, type));
                /*if (unset.contains(id)) {
                    junctionList.add(new Junction(id, "internal"));
                } else {
                    junctionList.add(new Junction(id, type));
                }*/
            }
            
            /*NodeList tlogics = document.getElementsByTagName("tlLogic");
             for (int i = 0; i < tlogics.getLength(); i++) {
                Node node = junctionElements.item(i);
                Element element = (Element) node;

                
                String type = element.getAttribute("type");
                String id = element.getAttribute("id");
                junctionList.add(new Junction(id, "traffic_light"));
                /*if (unset.contains(id)) {
                    junctionList.add(new Junction(id, "internal"));
                } else {
                    junctionList.add(new Junction(id, type));
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return junctionList;
    }
    
    public ArrayList<Junction> readTLogic(String fileName) {
        ArrayList<Junction> junctionList = new ArrayList<Junction>();
        
        try {
            /*Set<String> unset = new HashSet<String>();
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));

                while (in.ready()) {
                    String s = in.readLine();
                    if (s != null) {
                        if (s.contains("tls.unset")) {
                            Pattern p = Pattern.compile("\"([^\"]*)\"");
                            Matcher m = p.matcher(s);
                            while (m.find()) {
                                unset.add(m.group(1));
                            }
                        }
                    }
                }
                in.close();
            } catch (Exception e) {
            }*/

            File nodes = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(nodes);
            document.getDocumentElement().normalize();
            NodeList junctionElements = document.getElementsByTagName("tlLogic");
            for (int i = 0; i < junctionElements.getLength(); i++) {
                Node node = junctionElements.item(i);
                Element element = (Element) node;

                /*// Only include junctions where there are links
                if (element.getAttribute("intLanes").equals("")) {
                    continue;
                }

                // Ignore junctions that begin with a colon
                if (element.getAttribute("id").startsWith(":")) {
                    continue;
                }*/

                String type = element.getAttribute("type");
                String id = element.getAttribute("id");
                junctionList.add(new Junction(id, type));
                /*if (unset.contains(id)) {
                    junctionList.add(new Junction(id, "internal"));
                } else {
                    junctionList.add(new Junction(id, type));
                }*/
            }
            
            /*NodeList tlogics = document.getElementsByTagName("tlLogic");
             for (int i = 0; i < tlogics.getLength(); i++) {
                Node node = junctionElements.item(i);
                Element element = (Element) node;

                
                String type = element.getAttribute("type");
                String id = element.getAttribute("id");
                junctionList.add(new Junction(id, "traffic_light"));
                /*if (unset.contains(id)) {
                    junctionList.add(new Junction(id, "internal"));
                } else {
                    junctionList.add(new Junction(id, type));
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return junctionList;
    }
}
