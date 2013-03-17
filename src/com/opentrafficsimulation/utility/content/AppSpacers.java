package com.opentrafficsimulation.utility.content;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Indicates spacers used to be in GUI
 * @author macromania
 *
 */
public class AppSpacers {

	/**
	 * Creates an invisible component to create a vertical space
	 * between other components in BoxLayout
	 * @param height
	 * @return
	 */
	public static Component createVerticalSpacer(int height){
		
		return Box.createVerticalStrut(height);
	}
	
	/**
	 * Creates an invisible component to create a horizontal space
	 * between other components in BoxLayout
	 * @param width
	 * @return
	 */
	public static Component createHorizontalSpacer(int width){
		
		return Box.createHorizontalStrut(width);
	}
	
	/**
	 * Creates a horizontal seperator
	 * between other components in BoxLayout
	 * @param width
	 * @return
	 */
	public static Component createHorizontalSeperator(){
		
		return new JSeparator(SwingConstants.HORIZONTAL);
	}
	
	/**
	 * Creates a vertical seperator
	 * between other components in BoxLayout
	 * @param width
	 * @return
	 */
	public static Component createVerticalSeperator(){
		
		return new JSeparator(SwingConstants.VERTICAL);
	}
}
