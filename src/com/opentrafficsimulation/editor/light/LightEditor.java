package com.opentrafficsimulation.editor.light;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.opentrafficsimulation.connector.Connector;
import com.opentrafficsimulation.connector.utility.ConnectorType;
import com.opentrafficsimulation.editor.road.RoadEditor;
import com.opentrafficsimulation.gui.CreateMapFrame;
import com.opentrafficsimulation.gui.CreateSimulationFrame;
import com.opentrafficsimulation.screen.simulation.SimulationScreen;
import com.opentrafficsimulation.utility.constants.AppConstants;

public class LightEditor extends JPanel {

    private static final long serialVersionUID = 6332204531497511846L;
    // Singleton instance
    private static LightEditor lightEditor = new LightEditor();
    // Content wrapper
    private JScrollPane scrollPane;
    private JPanel content = new JPanel();
    //private String networkFile = new Connector(ConnectorType.CONNECTOR_NETGENERATE).getInputDir() + "hello.net.xml";
    public String networkFile; // Added by mahmut on 10.03.13
    public static Integer PORT_NUMBER = 5443; // Added by mahmut on 11.03.13
    //public int PORT_NUMBER;
    public DefaultListModel junctionModel = new DefaultListModel();
    private JList junctionList = new JList(junctionModel);
    public DefaultListModel tlModel = new DefaultListModel();
    private JList tlList = new JList(tlModel);
    public List<String> trafficLightIDs; // Added by mahmut on 11.03.13
    public boolean isSimulationRunnig = false;

    /**
     * Singleton constructor
     */
    private LightEditor() {
        super();
    }

    /**
     * Used for invoking instance
     *
     * @return
     */
    public static LightEditor getInstance() {
        return lightEditor;
    }

    /**
     * Initialises an instance of Main GUI
     */
    public void init() {
        // Set size
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Traffic Lights"));
        setPreferredSize(new Dimension(AppConstants.APP_LEFT_COLUMN_WIDGET_WIDTH, AppConstants.APP_LEFT_COLUMN_WIDGET_HEIGHT));

        content.setSize(500, 400);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new BoxLayout(topButtonPanel, BoxLayout.X_AXIS));
        JButton generateLightsButton = new JButton("Generate traffic lights");
        topButtonPanel.add(generateLightsButton);
        content.add(topButtonPanel);

        generateLightsButton.addActionListener(lightGenerateListener);

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
        content.add(firstPanel);

        JPanel tlPanel = new JPanel();
        tlPanel.setLayout(new BoxLayout(tlPanel, BoxLayout.Y_AXIS));

        tlPanel.add(new JLabel("Traffic lights"));
        JScrollPane tlScrollPanel = new JScrollPane(tlList);
        tlScrollPanel.setMaximumSize(new Dimension(300, 100));
        tlPanel.add(tlScrollPanel);

        JPanel tlBottomPanel = new JPanel();
        tlBottomPanel.setLayout(new BoxLayout(tlBottomPanel, BoxLayout.X_AXIS));

        JPanel tlBottomRightPanel = new JPanel();
        tlBottomRightPanel.setLayout(new BoxLayout(tlBottomRightPanel, BoxLayout.X_AXIS));
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(editButtonListener);
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(removeButtonListener);
        tlBottomRightPanel.add(editButton);
        tlBottomRightPanel.add(removeButton);
        tlBottomPanel.add(tlBottomRightPanel);

        tlPanel.add(tlBottomPanel);
        content.add(tlPanel);

        JPanel junctionPanel = new JPanel();
        junctionPanel.setLayout(new BoxLayout(junctionPanel, BoxLayout.Y_AXIS));
        junctionPanel.add(new JLabel("Junctions"));
        JButton convertButton = new JButton("Convert to traffic light");
        convertButton.addActionListener(convertButtonListener);
        JScrollPane junctionScrollPanel = new JScrollPane(junctionList);
        junctionScrollPanel.setMaximumSize(new Dimension(300, 100));
        junctionPanel.add(junctionScrollPanel);
        junctionPanel.add(convertButton);
        junctionList.setVisibleRowCount(5);
        content.add(junctionPanel);
        tlModel.addElement("1");

        // Set scroller
        scrollPane = new JScrollPane(content);
        scrollPane.setSize(AppConstants.APP_LEFT_COLUMN_WIDGET_WIDTH, AppConstants.APP_LEFT_COLUMN_WIDGET_HEIGHT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
        setVisible(true);


        //SimulationScreen.getInstance().traciConnector.conn.socket

    }
    private ActionListener lightGenerateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Junction> idList = new TrafficLightReader().readNetworkFile(networkFile);
            List<String> randomIds = getRandomJunctions(idList);
            trafficLightIDs = randomIds;
            createTrafficLights(randomIds);
            if (randomIds.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Could not find any junctions that could be converted into traffic lights");
            } else {
                //JOptionPane.showMessageDialog(null, "Created " + randomIds.size() + " traffic lights");
            }
        }
    };

    public void generateRandomly() {
        ArrayList<Junction> idList = new TrafficLightReader().readNetworkFile(networkFile);
        List<String> randomIds = getRandomJunctions(idList);
        trafficLightIDs = randomIds;
        createTrafficLights(randomIds);
        readNetworkFile();
    }

    public void triggerGeneration() {
        readNetworkFile();
    }

    public void resetLights() {
        //trafficLightIDs = null;
        tlModel = new DefaultListModel();
        junctionModel = new DefaultListModel();
        CreateMapFrame.getInstance().createMap();
    }

    public void convertToTrafficLight(String id) {

        /*if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Do you want to add traffic light to this junction?")) {
         if (isSimulationRunnig) {
         JOptionPane.showMessageDialog(null, "Please note that the changes will not be reflected during this simulation");
         }
         System.out.println("Converting junction " + id + " to traffic light");
         createTrafficLights(Arrays.asList(id));
         // Refresh data from the network file
         readNetworkFile();
         }*/
        createTrafficLights(Arrays.asList(id));
        // Refresh data from the network file
        readNetworkFile();
    }

    public void removeTrafficLight(String id) {
        removeTrafficLights(Arrays.asList(id));
        JOptionPane.showMessageDialog(null, "Traffic light with ID " + id + " has been removed");
        readNetworkFile();
    }

    private void clearLists() {
        tlModel.clear();
        junctionModel.clear();
        CreateSimulationFrame.getInstance().jListTrafficLights.removeAll();
        CreateSimulationFrame.getInstance().jListJunctions.removeAll();
        System.out.println("Tlight" + tlModel);
        System.out.println("Junc" + junctionModel);
        System.out.println("---");
    }
    private ActionListener convertButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Do you want to transform this junction to a traffic light?")) {
                //System.out.println("Converting junction " + junction.getId() + " to traffic light");
                //createTrafficLights(Arrays.asList(junction.getId()));
                // Refresh data from the network file
                readNetworkFile();
            }
        }
    };

    public void showReport() {
        //TODO: Implement
    }
    private ActionListener editButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = (String) tlList.getSelectedValue();
            try {
                new TrafficLighStateWindow(id);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //new LightModifier(junction.getId()).changeLight();
        }
    };
    private ActionListener removeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            int index = tlList.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "Please select a traffic light to remove");
                return;
            }

            if (isSimulationRunnig) {
                //removeTrafficLights();
            }

        }
    };

    public void showTLOnSummoGUI(String id) throws UnknownHostException, IOException {
        /*if (isSimulationRunnig) {
         Socket socket = new Socket("localhost", 3445);
         new ReadTrafficLightState(socket).goToTrafficLight(id);
         }*/
    }

    /*
     * Populates the junction and traffic light tables from the network file.
     */
    public void readNetworkFile() {
        if (networkFile == null || "".equals(networkFile)) {
            System.out.println("Network file is empy, cannot refresh junction and traffic light list");
        } else {
            System.out.println("Reading network file " + networkFile + ", refreshing junction and traffic light list");
            clearLists();

            ArrayList<Junction> tlList = new TrafficLightReader().readTLogic(networkFile);
            for (final Junction junction : tlList) {
                String t = junction.getType();
                /*if (junction.getType().equals("traffic_light")) {
                 tlModel.addElement(junction.getId());
                 } else {
                 junctionModel.addElement(junction.getId());
                 }*/
                tlModel.addElement(junction.getId());
            }

            ArrayList<Junction> idList = new TrafficLightReader().readNetworkFile(networkFile);
            for (final Junction junction1 : idList) {
                String t = junction1.getType();
                String id = junction1.getId();
                /*if (junction.getType().equals("traffic_light")) {
                 tlModel.addElement(junction.getId());
                 } else {
                 junctionModel.addElement(junction.getId());
                 }*/
                if (findJunction(id)) {
                    junctionModel.addElement(junction1.getId());
                }

            }

            System.out.println("Read traffic:" + tlModel);
            System.out.println("Read junction:" + junctionModel);
        }
    }

    private boolean findJunction(String id) {
        boolean result = true;
        for (int i = 0; i < tlModel.size(); i++) {
            String t = String.valueOf(tlModel.get(i));
            if (t.equals(id)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /*
     * This method receives a list of junctions, filters out all the traffic
     * lights and returns a random subset of junction IDs. This will be used
     * to allow the user to generate random traffic lights for any network.
     * 
     * @param junctions represents the list of junctions in the network
     * 
     * @returns random list of junction IDs with the traffic lights filtered out
     */
    private List<String> getRandomJunctions(List<Junction> junctions) {
        List<String> ids = new ArrayList<String>();
        List<Junction> filteredJunctions = new ArrayList<Junction>();
        for (Junction junction : junctions) {
            /*if (!"traffic_light".equals(junction.getType())) {
                
            }*/
            filteredJunctions.add(junction);
        }

        // Check if we have any proper candidates
        if (filteredJunctions.isEmpty()) {
            return ids;
        }

        // Pick a random number representing the number of junctions that will be
        // converted into traffic lights. There will be at least one, at most every
        // one.
        int pickCount = new Random().nextInt(filteredJunctions.size() - 1) + 1;

        Collections.shuffle(filteredJunctions);

        for (int i = 0; i < pickCount; i++) {
            Junction selectedJunction = filteredJunctions.get(i);
            ids.add(selectedJunction.getId());
        }

        return ids;
    }

    /*
     * This method calls netconverter to convert a list of junctions into traffic lights.
     * 
     * @param sumoPath is the path to the SUMO executables
     * @param ids is the list of junctions that we want to convert into traffic lights
     */
    private void createTrafficLights(List<String> ids) {
        // If there are no junction IDs specified
        if (ids.isEmpty()) {
            System.out.println("Junction list empty, not trying to create traffic lights.");
            return;
        }
        String commaSeparatedList = listToCSV(ids);
        new Connector(ConnectorType.CONNECTOR_NETCONVERT).runCommand("-s " + networkFile + " --tls.set " + commaSeparatedList + " --output-file=" + networkFile);
    }

    /*
     * Give a list of strings, create a single string with the list
     * values separated by commas. Used by netconvert
     */
    private String listToCSV(List<String> list) {
        String commaSeparatedList = "";
        for (int i = 0; i < list.size(); i++) {
            commaSeparatedList = commaSeparatedList + list.get(i);
            if (i < list.size() - 1) {
                commaSeparatedList = commaSeparatedList + ",";
            }
        }
        return commaSeparatedList;
    }

    /*
     * Remove the selected traffic lights from the network file
     * by using netconvert
     */
    private void removeTrafficLights(List<String> ids) {
        String commaSeparatedList = listToCSV(ids);
        new Connector(ConnectorType.CONNECTOR_NETCONVERT).runCommand("-s " + networkFile + " --tls.unset " + commaSeparatedList + " --output-file=" + networkFile);
    }

    class TLListListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            System.out.println("list item: number " + tlList.getSelectedValue());
        }
    }
}
