package com.opentrafficsimulation.editor.light;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.opentrafficsimulation.connector.TraciConnector;
import com.opentrafficsimulation.screen.simulation.SimulationScreen;

/*
 * This class makes it possible for the user to view and set the state
 * of a particular traffic light.
 */
public class TrafficLighStateWindow extends JFrame {

    private static final long serialVersionUID = 1504275692564132699L;
    // ReadTrafficLightState reader;
    JLabel stateLabel = new JLabel();
    String id;
    TrafficLighStateWindow ttt = null;

    /*
     * Creates the window that holds the traffic light state and the buttons
     * that can be used to change the traffic light state.
     */
    public TrafficLighStateWindow(String id) throws UnknownHostException, IOException {
        System.out.println("Creating TrafficLighStateWindow");
        this.id = id;
        setSize(500, 120);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        refreshState();
        JPanel panel = new JPanel();
        panel.add(stateLabel);
        JButton red = new JButton("Make Red");
        red.addActionListener(generateListener(generateLightState("r", stateLabel.getText().length())));
        JButton green = new JButton("Make Green");
        green.addActionListener(generateListener(generateLightState("G", stateLabel.getText().length())));
        panel.add(red);
        panel.add(green);
        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
        System.out.println("TrafficLighStateWindow should appear now");
        pack();
        ttt = this;
    }

    private String generateLightState(String state, int count) {
        String st = stateLabel.getText();
        int l = st.length();

        String result = "";

        for (int i = 0; i < l; i++) {
            result += state;
        }

        return result;// + st.substring(l, st.length());
    }

    /*
     * Refreshes the state of the traffic light using sumo.
     */
    private void refreshState() {
        System.out.println("Refreshing TL state from Traci for " + id);
        try {
            String state = TraciConnector.RTL.readTLState(id);
            stateLabel.setText(state);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not read traffic light state");
            e.printStackTrace();
        }
    }

    /*
     * Generates action listener for each invividual button.
     */
    private ActionListener generateListener(final String state) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Changing TL " + id + " to " + state);
                //TraciConnector.RTL.chageTLState(id, state);
                TraciConnector.CHGSTATE = state;
                TraciConnector.CHGID = id;
                TraciConnector.TLID = id;
                ttt.setVisible(false);
            }
        };
    }
}