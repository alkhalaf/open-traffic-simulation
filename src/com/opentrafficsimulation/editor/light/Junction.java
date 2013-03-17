package com.opentrafficsimulation.editor.light;

/*
 * Represents a junction in the network. The light editor is only
 * concerned about the id and type of the junction.
 */
public class Junction {

	private String id;
	private String type;
	
	/*
	 * Constructor of the Junction class. It requires the id and type
	 * to be specified and will save both in its private fields (called
	 * id and type).
	 */
	public Junction(String id, String type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}
}
