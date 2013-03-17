package com.opentrafficsimulation.screen.simulation;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import com.opentrafficsimulation.connector.Connector;
import com.opentrafficsimulation.connector.TraciConnector;
import com.opentrafficsimulation.connector.utility.ConnectorType;
import com.opentrafficsimulation.editor.light.LightEditor;
import com.opentrafficsimulation.editor.road.RoadEditor;
import com.opentrafficsimulation.gui.CreateMapFrame;
import com.opentrafficsimulation.gui.CreateSimulationFrame;
import com.opentrafficsimulation.gui.EditTrafficLights;
import com.opentrafficsimulation.gui.MainGUI;
import com.opentrafficsimulation.screen.report.EmissionScreen;
import com.opentrafficsimulation.screen.report.ReportScreen;

import java.awt.Dimension;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SimulationScreen {

    // Singleton instance
    private static SimulationScreen simulationScreen = new SimulationScreen();
    public static int SIMULATION_TIME;
    public static int SIMULATION_BEGIN;
    public static int SIMULATION_END;
    public int portNumber;
    public TraciConnector traciConnector;

    public EditTrafficLights edt;
    private SimulationScreen() {
    }

    public static SimulationScreen getInstance() {
        return simulationScreen;
    }

    public void runSimulation() {

        if (CreateMapFrame.getInstance().netgenerate_file != null) {

            try {

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // config element
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("configuration");
                rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "http://sumo.sf.net/xsd/sumoConfiguration.xsd");
                doc.appendChild(rootElement);

                // input element
                Element inputElement = doc.createElement("input");

                // net file value
                Element netFile = doc.createElement("net-file");
                netFile.setAttribute("value", CreateMapFrame.getInstance().netgenerate_file + ".net.xml");

                // rou file value
                Element rouFile = doc.createElement("route-files");
                rouFile.setAttribute("value", CreateMapFrame.getInstance().netgenerate_file + ".rou.xml");

                // appending rou and net file to input element
                inputElement.appendChild(netFile);
                inputElement.appendChild(rouFile);

                // appending input element to config element
                rootElement.appendChild(inputElement);

                // time element
                Element timeElement = doc.createElement("time");

                // begin time
                Element beginTime = doc.createElement("begin");
                beginTime.setAttribute("value", String.valueOf(SIMULATION_BEGIN));

                // TO-DO: adding report tags for report screen

                // end time
                Element endTime = doc.createElement("end");
                endTime.setAttribute("value", String.valueOf(SIMULATION_END));

                // append begin and end time to time element
                timeElement.appendChild(beginTime);
                timeElement.appendChild(endTime);

                // append time element to config element
                rootElement.appendChild(timeElement);

                // write the content into xml file with the net file name
                String configurationFileName = new Connector(ConnectorType.CONNECTOR_NETCONVERT).getOutputDir() + CreateMapFrame.getInstance().netgenerate_file + ".sumo.cfg";
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(configurationFileName));

                // Output to console for testing
                transformer.transform(source, result);

                SwingWorker trafficLightWindow = new SwingWorker<String, Object>() {
                    @Override
                    public String doInBackground() {

                        if(LightEditor.getInstance().tlModel.size() > 0){
                        edt = new EditTrafficLights();
                        edt.setVisible(true);
                        edt.setLocation(30, 30);
                        }
                        
                        return "running";
                    }

                    @Override
                    public void done() {
                        
                        
                    }
                };


                trafficLightWindow.execute();
                

                // Run the simulation with the configuration		
                SwingWorker worker = new SwingWorker<String, Object>() {
                    @Override
                    public String doInBackground() {

                        String configurationFileName = new Connector(ConnectorType.CONNECTOR_NETCONVERT).getOutputDir() + CreateMapFrame.getInstance().netgenerate_file + ".sumo.cfg";
                        traciConnector = new TraciConnector();
                        traciConnector.runSimulation(configurationFileName);


                        return "running";
                    }

                    @Override
                    public void done() {
                        System.out.println("simalation screen closed / simulation completed");
                        String emissionFilename = new Connector(ConnectorType.CONNECTOR_NETCONVERT).getOutputDir() + CreateMapFrame.getInstance().netgenerate_file + Integer.toString(portNumber) + ".emission.xml";
                        String filename = new Connector(ConnectorType.CONNECTOR_NETCONVERT).getOutputDir() + CreateMapFrame.getInstance().netgenerate_file + Integer.toString(portNumber) + ".trip.xml";
                        new ReportScreen(filename);
                        edt.dispose();
                        new EmissionScreen(emissionFilename);
                    }
                };


                worker.execute();

            } catch (Exception e) {

                e.printStackTrace();


            }

        } else {
            JDialog errorDialog = new JDialog(MainGUI.getInstance(), true);
            errorDialog.setPreferredSize(new Dimension(300, 200));
            errorDialog.setSize(300, 200);
            errorDialog.setTitle("Missing files for configuration");

            JLabel errorLabel = new JLabel();
            errorLabel.setText("<html><p>Please check net and rou files<br/>before running simulation!</p></html>");
            errorDialog.add(errorLabel);

            errorDialog.setVisible(true);
        }
    }
}