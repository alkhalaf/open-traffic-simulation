package com.opentrafficsimulation.editor.light;
import it.polito.appeal.traci.protocol.Command;
import it.polito.appeal.traci.protocol.Constants;
import it.polito.appeal.traci.protocol.ResponseContainer;
import it.polito.appeal.traci.protocol.StringList;
import it.polito.appeal.traci.query.Query;

import java.io.IOException;
import java.net.Socket;

public class ReadTrafficLightState extends Query {

	public ReadTrafficLightState(Socket sock) throws IOException {
		super(sock);
	}

	public void readTrafficLightIds() throws IOException {
		Command cmd = makeReadVarCommand(Constants.CMD_GET_TL_VARIABLE, Constants.ID_LIST, "");
		ResponseContainer respc = queryAndVerifySingle(cmd);
		Command resp = respc.getResponse();
		System.out.println("Variable : " + resp.content().readUnsignedByte());
		System.out.println("TL ID : " + resp.content().readStringASCII());

		StringList stringList = new StringList(resp.content(), true);
		for (String s : stringList) {
			System.out.println("S : " + s);
		}
	}

	public String readTLState(String id) throws IOException {
		Command cmd = makeReadVarCommand(Constants.CMD_GET_TL_VARIABLE, Constants.TL_RED_YELLOW_GREEN_STATE, id);
		ResponseContainer respc = queryAndVerifySingle(cmd);
		Command resp = respc.getResponse();
		System.out.println("Variable : " + resp.content().readUnsignedByte());
		System.out.println("TL ID : " + resp.content().readStringASCII());
		System.out.println("Return type : " + resp.content().readUnsignedByte());
		String lightState = resp.content().readStringASCII();
		System.out.println("Light state : " + lightState);
		return lightState;
	}

	public void chageTLState(String id, String state) throws IOException {
		Command cmd = new Command(Constants.CMD_SET_TL_VARIABLE);
		cmd.content().writeUnsignedByte(Constants.DOMVAR_CO2EMISSION);
		cmd.content().writeStringASCII(id);
		cmd.content().writeUnsignedByte(Constants.TYPE_STRING);
		cmd.content().writeStringASCII(state);
		queryAndVerifySingle(cmd);
	}

	/*
	 * Retrieve the position of a junction on the GUI.
	 */
	public Position getJunctionPosition(String id) throws IOException {
		Command cmd = new Command(Constants.CMD_GET_JUNCTION_VARIABLE);
		cmd.content().writeUnsignedByte(Constants.VAR_POSITION);
		cmd.content().writeStringASCII(id);
		ResponseContainer respc = queryAndVerifySingle(cmd);
		Command resp = respc.getResponse();
		System.out.println("Retrieving GUI position of junction " + id);
		System.out.println("Variable : " + resp.content().readUnsignedByte());
		System.out.println("Junction ID : " + resp.content().readStringASCII());
		System.out.println("Return type : " + resp.content().readUnsignedByte());
		Double x = resp.content().readDouble();
		Double y = resp.content().readDouble();
		System.out.println("Junction position is [" + x + "," + y + "]");
		return new Position(x, y);
	}

	/*
	 * Go to the junction in the GUI.
	 */
	public void goToJunction(String id) throws IOException {
		Position position = getJunctionPosition(id);
		Command cmd = new Command(Constants.CMD_SET_GUI_VARIABLE);
		cmd.content().writeUnsignedByte(Constants.VAR_VIEW_OFFSET);
		cmd.content().writeStringASCII("View #0"); // View ID in the GUI - we only use 0
		cmd.content().writeUnsignedByte(Constants.POSITION_2D);
		cmd.content().writeDouble(position.getX());
		cmd.content().writeDouble(position.getY());	
		ResponseContainer respc = queryAndVerifySingle(cmd);
		respc.getResponse();
	}
}
