package com.opentrafficsimulation.screen.report;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EmissionReport {
	public ArrayList<EmissionInfo> readEmissionReport(String emissionFile) {
		ArrayList<EmissionInfo> emissionInfoList = new ArrayList<>();
		try {
			File nodes = new File(emissionFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(nodes);
			document.getDocumentElement().normalize();
			NodeList tripInfoElements = document.getElementsByTagName("vehicle");
			for (int i = 0; i < tripInfoElements.getLength(); i++) {
				Node node = tripInfoElements.item(i);
				Element element = (Element) node;
				EmissionInfo emissionInfo = new EmissionInfo();
				emissionInfo.id = element.getAttribute("id");
				emissionInfo.eclass = element.getAttribute("eclass");
				emissionInfo.co2 = element.getAttribute("co2");
				emissionInfo.co = element.getAttribute("co");
				emissionInfo.hc = element.getAttribute("hc");
				emissionInfo.nox = element.getAttribute("nox");
				emissionInfo.pmx = element.getAttribute("pmx");
				emissionInfo.noise = element.getAttribute("noise");
				emissionInfo.route = element.getAttribute("route");
				emissionInfo.type = element.getAttribute("type");
				emissionInfo.waiting = element.getAttribute("waiting");
				emissionInfo.lane = element.getAttribute("lane");
				emissionInfo.pos = element.getAttribute("pos");
				emissionInfo.speed = element.getAttribute("speed");
				emissionInfo.angle = element.getAttribute("angle");
				emissionInfo.x = element.getAttribute("x");
                                emissionInfo.y = element.getAttribute("y");
				emissionInfoList.add(emissionInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emissionInfoList;
	}
	
}
