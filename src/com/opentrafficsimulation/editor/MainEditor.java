package com.opentrafficsimulation.editor;

import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

import com.opentrafficsimulation.editor.light.LightEditor;
import com.opentrafficsimulation.editor.road.RoadEditor;
import com.opentrafficsimulation.screen.simulation.SimulationScreen;
import com.opentrafficsimulation.utility.constants.AppConstants;


/**
 * Main Editor Screen
 * @author macromania
 * 
 */
public class MainEditor extends JFrame {

	private static final long serialVersionUID = -1112610253085550963L;

	// Singleton instance
	private static MainEditor mainEditor = new MainEditor();
	
	// Main pane
	private Container pane;

	public Button runSimulation;
		
	/**
	 * Singleton constructor
	 */
	private MainEditor() {

		// Set title
		super("Open Traffic Simulation | Simulation Editor");

		// Set main pane
		this.pane = this.getContentPane();

	}
	
	/**
	 * Used for invoking instance
	 * 
	 * @return
	 */
	public static MainEditor getInstance() {
		return mainEditor;
	}
	
	/**
	 * Initialises an instance
	 */
	public void init() {
		
		runSimulation = new Button();
		runSimulation.setLabel("Run Simulation");
		runSimulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*TraciConnector traciConnector = new TraciConnector();
				traciConnector.runSimulation();*/
				SimulationScreen.getInstance().runSimulation();
			}
		});
		
		pane.add(runSimulation);
		
		// Set default attributes
		setSize(new Dimension(AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		setLocation(AppConstants.APP_DEFAULT_LOCATION_X, AppConstants.APP_DEFAULT_LOCATION_Y);
		pane.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		setLocationRelativeTo(null);
		
		// Hide splash
		
		// Initializing editor
		LightEditor.getInstance().init();
		pane.add(LightEditor.getInstance());
		
		/*VehicleEditorAdd.getInstance().init();
		pane.add(VehicleEditorAdd.getInstance());*/
		
		/*try {
			VehicleEditor.getInstance().init();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pane.add(VehicleEditor.getInstance());*/

		
		
		RoadEditor.getInstance().init();
		pane.add(RoadEditor.getInstance());
		
		// Hide light and vehicle editor before creating the map by mahmut on 10/03/13
		LightEditor.getInstance().setVisible(false);
		//VehicleEditor.getInstance().setVisible(false);
		runSimulation.setVisible(false);
		setVisible(true);
		
		}
}
