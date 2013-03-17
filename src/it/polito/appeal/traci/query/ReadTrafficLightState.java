package it.polito.appeal.traci.query;

import it.polito.appeal.traci.protocol.Command;
import it.polito.appeal.traci.protocol.Constants;
import it.polito.appeal.traci.protocol.ResponseContainer;
import it.polito.appeal.traci.protocol.StringList;

import java.io.IOException;
import java.net.Socket;

public class ReadTrafficLightState extends Query {
	
	private String id;
	
	public ReadTrafficLightState(Socket sock, String id) throws IOException {
		super(sock);
		this.id = id;
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
	
	public void readTLState() throws IOException {
		Command cmd = makeReadVarCommand(Constants.CMD_GET_TL_VARIABLE, Constants.TL_RED_YELLOW_GREEN_STATE, id);
		ResponseContainer respc = queryAndVerifySingle(cmd);
		Command resp = respc.getResponse();
		System.out.println("Variable : " + resp.content().readUnsignedByte());
		System.out.println("TL ID : " + resp.content().readStringASCII());
		System.out.println("Return type : " + resp.content().readUnsignedByte());
		System.out.println("Light state : " + resp.content().readStringASCII());
	}
	
	public void chageTLState() throws IOException {
		Command cmd = new Command(Constants.CMD_SET_TL_VARIABLE);
		cmd.content().writeUnsignedByte(Constants.DOMVAR_CO2EMISSION);
		cmd.content().writeStringASCII(id);
		cmd.content().writeUnsignedByte(Constants.TYPE_STRING);
		cmd.content().writeStringASCII("RRRRRRRR");
		ResponseContainer respc = queryAndVerifySingle(cmd);
		Command resp = respc.getResponse();
	}
	
	public static void pp(String[] args) throws IOException {
		System.out.println("Start");
		
		Socket s = new Socket("localhost", 3445);
		ReadTrafficLightState r = new ReadTrafficLightState(s, "2");
		r.readTLState();
		//r.chageTLState();
		//r.readTLState();
		
		System.out.println("End");
	}

}
