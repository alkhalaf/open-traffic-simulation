package com.opentrafficsimulation.editor.light;

import javax.swing.JOptionPane;

public class LightModifier {

	private String id;
	
	public LightModifier(String id) {
		this.id = id;
	}
	
	/*
	 * 
	 */
	public void changeLight() {
		JOptionPane.showMessageDialog(null, "You are changing traffic light " + id);
		
		//TODO: Implement actual Traci call to SUMO to change the traffic light
	}
	
}
