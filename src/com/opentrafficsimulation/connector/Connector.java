package com.opentrafficsimulation.connector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.opentrafficsimulation.connector.utility.ConnecterConverter;
import com.opentrafficsimulation.connector.utility.ConnectorType;

/**
 * Connects to SUMO to send/receive parameters
 *
 * @author macromania
 *
 */
public class Connector {

    private static final String TOOLS_DIRECTORY = "\\tools\\";
    private static final String OUTPUT_DIRECTORY = "\\output\\";
    private static final String INPUT_DIRECTORY = "\\input\\";
    private static final String WORKING_DIRECTORY = "user.dir";
    private ConnectorType type;
    private String workingDirectory;

    public Connector(ConnectorType type) {
        this.type = type;
        workingDirectory = System.getProperty(WORKING_DIRECTORY);
    }

    public boolean runCommand(String options) {
        boolean result = false;
        System.out.println("Currently working in: " + workingDirectory + TOOLS_DIRECTORY);
        System.out.println("Requested program: " + this.type.toString());


        String processName = ConnecterConverter.getConnecterName(type);
        String processDir = workingDirectory + TOOLS_DIRECTORY + processName;
        System.out.println("Requested command: " + processName + " " + options);

        try {

            //Process process = Runtime.getRuntime().e
            Process process = Runtime.getRuntime().exec(processName + " " + options);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            int output = 0;
            while ((line = br.readLine()) != null) {
                output++;
                System.out.println(line);
            }
            
            if (output > 0) {
                result = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    public String getToolsDir() {
        return workingDirectory + TOOLS_DIRECTORY;
    }

    public String getInputDir() {
        return workingDirectory + INPUT_DIRECTORY;
    }

    public String getOutputDir() {
        return workingDirectory + OUTPUT_DIRECTORY;
    }
}
