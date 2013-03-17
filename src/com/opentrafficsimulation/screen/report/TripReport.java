package com.opentrafficsimulation.screen.report;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TripReport {

	/*
	   <tripinfos>
		<tripinfo id="veh0" depart="54000.00" departLane="D2_0" departPos="5.10" departSpeed="0.00" departDelay="0.00"
		arrival="54306.00" arrivalLane="D7_0" arrivalPos="503.32" arrivalSpeed="12.93" duration="306.00" routeLength="3969.52"
		 waitSteps="0" rerouteNo="0" devices="tripinfo_veh0" vType="CarA" vaporized=""/>
	  </tripinfos>
	 */
	
	public ArrayList<TripInfo> readTripReport(String tripFile) {
		ArrayList<TripInfo> tripInfoList = new ArrayList<TripInfo>();
		try {
			File nodes = new File(tripFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(nodes);
			document.getDocumentElement().normalize();
			NodeList tripInfoElements = document.getElementsByTagName("tripinfo");
			for (int i = 0; i < tripInfoElements.getLength(); i++) {
				Node node = tripInfoElements.item(i);
				Element element = (Element) node;
				TripInfo tripInfo = new TripInfo();
				tripInfo.id = element.getAttribute("id");
				tripInfo.depart = element.getAttribute("depart");
				tripInfo.departLane = element.getAttribute("departLane");
				tripInfo.departPos = element.getAttribute("departPos");
				tripInfo.departSpeed = element.getAttribute("departSpeed");
				tripInfo.departDelay = element.getAttribute("departDelay");
				tripInfo.arrival = element.getAttribute("arrival");
				tripInfo.arrivalLane = element.getAttribute("arrival");
				tripInfo.arrivalPos = element.getAttribute("arrivalPos");
				tripInfo.arrivalSpeed = element.getAttribute("arrivalSpeed");
				tripInfo.duration = element.getAttribute("duration");
				tripInfo.routeLength = element.getAttribute("routeLength");
				tripInfo.waitSteps = element.getAttribute("waitSteps");
				tripInfo.rerouteNo = element.getAttribute("rerouteNo");
				tripInfo.devices = element.getAttribute("devices");
				tripInfo.vType = element.getAttribute("vType");
				tripInfo.vaporized = element.getAttribute("vaporized");
				tripInfoList.add(tripInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tripInfoList;
	}
	
	/*
	public static void main(String[] args) {
		new TripReport().readTripReport("C:\\SUMO\\docs\\tutorial\\quickstart\\data\\trip.xml");
	}
	*/
	
}
