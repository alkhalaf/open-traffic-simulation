package com.opentrafficsimulation.connector;

import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.Vehicle;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.opentrafficsimulation.editor.light.LightEditor;
import com.opentrafficsimulation.editor.light.ReadTrafficLightState;
import com.opentrafficsimulation.editor.light.TrafficLighStateWindow;
import com.opentrafficsimulation.screen.simulation.SimulationScreen;

public class TraciConnector {

    public SumoTraciConnection conn;
    // Required for LightEditor
    public static ReadTrafficLightState RTL;
    public static String TLID = null;
    public static String CHGID = null;
    public static String CHGSTATE = null;
    public static String GOTO_JUNCTION = null;

    public void runSimulation(String conf) {
        System.out.println("Running a new simulation using config file " + conf);

        BasicConfigurator.configure();

        conn = new SumoTraciConnection(
                conf, // config file
                12345, // random seed
                false // look for geolocalization info in the map
                );

        try {
            conn.runServer();

            RTL = new ReadTrafficLightState(conn.socket);

            LightEditor.getInstance().isSimulationRunnig = true;
            LightEditor.getInstance().PORT_NUMBER = SimulationScreen.getInstance().portNumber;



            //System.out.println("Map bounds are: " + conn.queryBounds());

            for (int i = 0; i < SimulationScreen.SIMULATION_TIME; i++) {
                int time = conn.getCurrentSimStep();

                if (CHGID != null) {
                    RTL.chageTLState(CHGID, CHGSTATE);
                    CHGID = null;
                }

                if (TLID != null) {
                    //new TrafficLighStateWindow(TLID);
                    SimulationScreen.getInstance().edt.openEditor(TLID);
                    TLID = null;
                }

                if (GOTO_JUNCTION != null) {
                    RTL.goToJunction(GOTO_JUNCTION);
                    GOTO_JUNCTION = null;
                }

                conn.nextSimStep();

            }

            conn.close();
        } catch (Exception e) {
            LightEditor.getInstance().isSimulationRunnig = false;
            e.printStackTrace();
        }
    }
}
