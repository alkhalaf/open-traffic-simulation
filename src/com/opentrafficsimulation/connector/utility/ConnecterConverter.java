package com.opentrafficsimulation.connector.utility;

/**
 * Converts ConnectorType into program name
 * @author macromania
 *
 */
public class ConnecterConverter {

	public static String getConnecterName(ConnectorType type) {
		String result = "";

		if (type == ConnectorType.CONNECTOR_SUMO) {
			result = "sumo";
		} 
		else if (type == ConnectorType.CONNECTOR_SUMO_GUI) {
			result = "sumo-gui";
		}
		else if (type == ConnectorType.CONNECTOR_NETGENERATE) {
			result = "netgenerate";
		}
		else if (type == ConnectorType.CONNECTOR_NETCONVERT) {
			result = "netconvert";
		}
		else if (type == ConnectorType.CONNECTOR_ROUTER) {
			result = "duarouter";
		}
		else if (type == ConnectorType.CONNECTOR_PYTHON) {
			result = "python";
		}
		else
		{
			result = "cmd";
		}

		return result;
	}
}