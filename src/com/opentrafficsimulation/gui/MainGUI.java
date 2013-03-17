package com.opentrafficsimulation.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.opentrafficsimulation.editor.MainEditor;
import com.opentrafficsimulation.utility.constants.AppConstants;
import com.opentrafficsimulation.utility.content.AppFont;
import com.opentrafficsimulation.utility.content.AppSpacers;
import com.opentrafficsimulation.utility.content.AppText;

/**
 * Main GUI window It is designed to be used as singleton * 
 * @author macromania
 * 
 */
public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1408412131327836260L;
	
	// Singleton instance
	private static MainGUI mainGUI = new MainGUI();

	// Main pane
	private Container pane;
	
	// Main panel
	private JPanel welcomePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	

	/**
	 * Singleton constructor
	 */
	private MainGUI() {

		// Set title
		super("Open Traffic Simulation");

		// Set main pane
		this.pane = this.getContentPane();
		
	}

	/**
	 * Used for invoking instance
	 * 
	 * @return
	 */
	public static MainGUI getInstance() {
		return mainGUI;
		
	}

	/**
	 * Initialises an instance
	 */
	public void init() {
		// Set default attributes
		// A new  comment
		setSize(new Dimension(AppConstants.APP_WIDTH, AppConstants.APP_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(AppConstants.APP_DEFAULT_LOCATION_X, AppConstants.APP_DEFAULT_LOCATION_Y);
		pane.setLayout(new FlowLayout(FlowLayout.LEFT,50,50));
		setLocationRelativeTo(null);
		//setTitle("Traffic Simulator");
		// Welcome text panel
		welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
		
		// Welcome
		JLabel label = new JLabel(AppText.WELCOME);
		label.setFont(AppFont.titleFont);
		welcomePanel.add(label);
		
		// Add space
		welcomePanel.add(AppSpacers.createVerticalSpacer(10));
			
		// Simple description of app
		label = new JLabel(AppText.DUMMY_TEXT);
		label.setFont(AppFont.textFont);
		welcomePanel.add(label);
		pane.add(welcomePanel);
				
		// Button panel
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		// Create simulation
		JButton createSimulationButton = new JButton("Create Simulation");
		createSimulationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainGUI.setVisible(false);
				MainEditor.getInstance().init();
			}
		});
		createSimulationButton.setFocusable(false);
		buttonPanel.add(createSimulationButton);	
		
		buttonPanel.add(AppSpacers.createHorizontalSpacer(10));
				
		// Open template simulation
		/*JButton openTemplateButton = new JButton("Open Template");
		openTemplateButton.setFocusable(false);
		buttonPanel.add(openTemplateButton);*/
		
		pane.add(buttonPanel);
		
		  
		setVisible(true);
	}


}
