/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opentrafficsimulation.editor.light;

/**
 *
 * @author macromania
 */
/*
 * Represents GUI position.
 */
public class Position {
	private Double x;
	private Double y;
    
	public Position(Double x, Double y) {
		this.x = x;
		this.y =y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}
}
