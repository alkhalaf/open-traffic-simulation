/*   
    Copyright (C) 2011 ApPeAL Group, Politecnico di Torino

    This file is part of TraCI4J.

    TraCI4J is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TraCI4J is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TraCI4J.  If not, see <http://www.gnu.org/licenses/>.
*/

<<<<<<< HEAD
package it.polito.appeal.traci;

import it.polito.appeal.traci.protocol.RoadmapPosition;
import it.polito.appeal.traci.query.ChangeEdgeStateQuery;
import it.polito.appeal.traci.query.ChangeLaneStateQuery;
import it.polito.appeal.traci.query.CloseQuery;
import it.polito.appeal.traci.query.MultiVehiclePositionQuery;
import it.polito.appeal.traci.query.RetrieveEdgeStateQuery;
import it.polito.appeal.traci.query.RoadmapQuery;
import it.polito.appeal.traci.query.SimStepQuery;
import it.polito.appeal.traci.query.SubscribeVehiclesLifecycle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
=======
package it.polito.appeal.traci;

import it.polito.appeal.traci.protocol.RoadmapPosition;
import it.polito.appeal.traci.query.ChangeEdgeStateQuery;
import it.polito.appeal.traci.query.ChangeLaneStateQuery;
import it.polito.appeal.traci.query.CloseQuery;
import it.polito.appeal.traci.query.MultiVehiclePositionQuery;
import it.polito.appeal.traci.query.RetrieveEdgeStateQuery;
import it.polito.appeal.traci.query.RoadmapQuery;
import it.polito.appeal.traci.query.SimStepQuery;
import it.polito.appeal.traci.query.SubscribeVehiclesLifecycle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
>>>>>>> origin/abdalla
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Models a TCP/IP connection to a local or remote SUMO server via the TraCI
 * protocol.
 * <p>
 * It runs a SUMO instance as a subprocess with a given configuration file and a
 * random seed, and provides methods and objects to advance to the next
 * simulation step, to retrieve vehicles' info, to set vehicles' routes and to
 * get roads' info.
 * <p>
 * To use it, create an instance and call {@link #runServer()}, that will start
 * the subprocess. From there, you can use all the other methods to interact
 * with the simulator.
 * <p>
 * The method {@link #nextSimStep()} will advance SUMO by a time step (one
 * second). The methods
 * {@link #addVehicleLifecycleObserver(VehicleLifecycleObserver)} and
 * {@link #removeVehicleLifecycleObserver(VehicleLifecycleObserver)} allow you
 * to register and unregister objects that will be notified whenever a vehicle
 * enters or exits the simulation.
 * <p>
 * The executable path must be specified via the system property specified in
 * {@link #SUMO_EXE_PROPERTY}.
 * <p>
 * At simulation end, one should call {@link #close()} to gracefully close the
 * simulator and free any resources.
 * 
 * @author Enrico Gueli &lt;enrico.gueli@gmail.com&gt;
 * 
<<<<<<< HEAD
 */
public class SumoTraciConnection {

	/**
	 * The system property name to get the executable path and name to run.
	 */
	public static final String SUMO_EXE_PROPERTY = "it.polito.appeal.traci.sumo_exe";

	/**
	 * Reads an InputStream object and logs each row in the containing class's
	 * logger.
	 * 
	 * @author Enrico
	 * 
	 */
	private static class StreamLogger implements Runnable {
		final InputStream stream;
		final String prefix;

		public StreamLogger(InputStream stream, String prefix) {
			this.stream = stream;
			this.prefix = prefix;
		}

		public void run() {
			StringBuilder buf = new StringBuilder();
			InputStreamReader isr = new InputStreamReader(stream);
			try {
				int ch;
				while((ch = isr.read()) != -1) {
					if(log.isInfoEnabled()) {
						if(ch != '\r' && ch != '\n')
							buf.append((char)ch);
						else {
							log.info(prefix + buf.toString());
							buf = new StringBuilder();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Logger log = Logger.getLogger(SumoTraciConnection.class);

	private String configFile;
	private int randomSeed;
	private int remotePort;
	private Socket socket;
	
	private int currentSimStep;
	private Process sumoProcess;
=======
 */
public class SumoTraciConnection {

	/**
	 * The system property name to get the executable path and name to run.
	 */
	public static final String SUMO_EXE_PROPERTY = "it.polito.appeal.traci.sumo_exe";

	/**
	 * Reads an InputStream object and logs each row in the containing class's
	 * logger.
	 * 
	 * @author Enrico
	 * 
	 */
	private static class StreamLogger implements Runnable {
		final InputStream stream;
		final String prefix;

		public StreamLogger(InputStream stream, String prefix) {
			this.stream = stream;
			this.prefix = prefix;
		}

		public void run() {
			StringBuilder buf = new StringBuilder();
			InputStreamReader isr = new InputStreamReader(stream);
			try {
				int ch;
				while((ch = isr.read()) != -1) {
					if(log.isInfoEnabled()) {
						if(ch != '\r' && ch != '\n')
							buf.append((char)ch);
						else {
							log.info(prefix + buf.toString());
							buf = new StringBuilder();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Logger log = Logger.getLogger(SumoTraciConnection.class);

	private String configFile;
	private int randomSeed;
	private int remotePort;
	private Socket socket;
	
	private int currentSimStep;
	private Process sumoProcess;
>>>>>>> origin/abdalla
	private final Set<String> activeVehicles = new HashSet<String>();
	private final Set<VehicleLifecycleObserver> vehicleLifecycleObservers = new HashSet<VehicleLifecycleObserver>();
	private final Map<String, Vehicle> vehicles = new HashMap<String, Vehicle>();
	private final Set<String> teleporting = new HashSet<String>();

<<<<<<< HEAD
	private static final int CONNECT_RETRIES = 9;

	private Point2D geoOffset;

	private Map<String, Lane> cachedLanes;

	private CloseQuery closeQuery;

	private boolean readInternalLinks = false;

	private SumoHttpRetriever httpRetriever;

	private boolean getVehiclesEdgeAtSimStep;
	
	private List<String> args = new ArrayList<String>();
	
	/**
	 * Constructor for the object.
	 * 
	 * @param configFile
	 *            the file name of the SUMO XML configuration file
	 * @param randomSeed
	 *            the random seed for SUMO (passed with the --srand option); if
	 *            different to -1, it overrides the value specified in the
	 *            config file or, if absent, the system time
	 * @param useGeoOffset
	 *            if true, scans the network description file specified in the
	 *            config file to search the latitude/longitude of a
	 *            georeferenced map
	 */
	public SumoTraciConnection(String configFile, int randomSeed,
			boolean useGeoOffset) {
		this.randomSeed = randomSeed;
		this.configFile = configFile;

		if (useGeoOffset)
=======
	private static final int CONNECT_RETRIES = 9;

	private Point2D geoOffset;

	private Map<String, Lane> cachedLanes;

	private CloseQuery closeQuery;

	private boolean readInternalLinks = false;

	private SumoHttpRetriever httpRetriever;

	private boolean getVehiclesEdgeAtSimStep;
	
	private List<String> args = new ArrayList<String>();
	
	/**
	 * Constructor for the object.
	 * 
	 * @param configFile
	 *            the file name of the SUMO XML configuration file
	 * @param randomSeed
	 *            the random seed for SUMO (passed with the --srand option); if
	 *            different to -1, it overrides the value specified in the
	 *            config file or, if absent, the system time
	 * @param useGeoOffset
	 *            if true, scans the network description file specified in the
	 *            config file to search the latitude/longitude of a
	 *            georeferenced map
	 */
	public SumoTraciConnection(String configFile, int randomSeed,
			boolean useGeoOffset) {
		this.randomSeed = randomSeed;
		this.configFile = configFile;

		if (useGeoOffset)
>>>>>>> origin/abdalla
			geoOffset = lookForGeoOffset(configFile);
		
	}
	
	public SumoTraciConnection(SocketAddress sockAddr) throws IOException,
			InterruptedException {
		
		socket = new Socket();
		if (log.isDebugEnabled())
			log.debug("Connecting to remote TraCI server at " + socket.toString());

		int waitTime = 500; // milliseconds
		for (int i = 0; i < CONNECT_RETRIES; i++) {

			try {
				socket.connect(sockAddr);
				log.info("Connection to SUMO established.");
				break;
			} catch (ConnectException ce) {
				log.debug("Server not ready, retrying in " + waitTime
						+ "ms");
				Thread.sleep(waitTime);
				waitTime *= 2;
			}
		}

		if (!socket.isConnected()) {
			log.error("Couldn't connect to server");
			throw new IOException("can't connect to SUMO server");
		}
<<<<<<< HEAD
	}
=======
	}
>>>>>>> origin/abdalla

	/**
	 * Adds a custom option to the SUMO command line before executing it.
	 * 
	 * @param option
	 *            the option name, in long form (e.g. &quot;no-warnings&quot;
	 *            instead of &quot;W&quot;) and without initial dashes
	 * @param value
	 *            the option value, or <code>null</code> if the option has no
	 *            value
	 */
	public void addOption(String option, String value) {
		args.add("--" + option);
		if (value != null)
			args.add(value);
	}
<<<<<<< HEAD
	
	/**
	 * Runs a SUMO instance and tries to connect at it.
	 * 
	 * @throws IOException
	 *             if something wrong occurs while starting SUMO or connecting
	 *             at it.
	 */
	public void runServer() throws IOException {
		retrieveFromURLs();		
		
		findAvailablePort();

		runSUMO();

		/*
		 * wait for simulator's loading before connecting.
		 */
		int waitTime = 500; // milliseconds
		try {
			for (int i = 0; i < CONNECT_RETRIES; i++) {
				/*
				 * first, check that the SUMO process is still alive.
				 */
				try {
					int retVal = sumoProcess.exitValue();
					throw new IOException(
							"SUMO process terminated unexpectedly with value "
									+ retVal);
				} catch (IllegalThreadStateException e) {
					// it's alive, go ahead.
				}

				socket = new Socket();
				if (log.isDebugEnabled())
					log.debug("Connecting to local port " + remotePort);

				try {
					socket.connect(new InetSocketAddress(InetAddress
							.getLocalHost(), remotePort));
					log.info("Connection to SUMO established.");
					break;
				} catch (ConnectException ce) {
					log.debug("Server not ready, retrying in " + waitTime
							+ "ms");
					Thread.sleep(waitTime);
					waitTime *= 2;
				}
			}

			if (!socket.isConnected()) {
				log.error("Couldn't connect to server");
				throw new IOException("can't connect to SUMO server");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		currentSimStep = 0;

		subscribeVehiclesLifecycle();

		closeQuery = new CloseQuery(socket);
	}

	private void retrieveFromURLs() throws IOException {
		if (configFile.startsWith("http://")) {
			
			httpRetriever = new SumoHttpRetriever(configFile);

			log.info("Downloading config file from " + configFile);
			try {
				httpRetriever.fetchAndParse();
			} catch (SAXException e) {
				throw new IOException(e);
			}
			
			configFile = httpRetriever.getConfigFileName();
		}
			
	}

	private void runSUMO() throws IOException {
		final String sumoEXE = System.getProperty(SUMO_EXE_PROPERTY);
		if (sumoEXE == null)
			throw new RuntimeException("System property " + SUMO_EXE_PROPERTY
					+ " must be set");
=======
	
	/**
	 * Runs a SUMO instance and tries to connect at it.
	 * 
	 * @throws IOException
	 *             if something wrong occurs while starting SUMO or connecting
	 *             at it.
	 */
	public void runServer() throws IOException {
		retrieveFromURLs();		
		
		findAvailablePort();

		runSUMO();

		/*
		 * wait for simulator's loading before connecting.
		 */
		int waitTime = 500; // milliseconds
		try {
			for (int i = 0; i < CONNECT_RETRIES; i++) {
				/*
				 * first, check that the SUMO process is still alive.
				 */
				try {
					int retVal = sumoProcess.exitValue();
					throw new IOException(
							"SUMO process terminated unexpectedly with value "
									+ retVal);
				} catch (IllegalThreadStateException e) {
					// it's alive, go ahead.
				}

				socket = new Socket();
				if (log.isDebugEnabled())
					log.debug("Connecting to local port " + remotePort);

				try {
					socket.connect(new InetSocketAddress(InetAddress
							.getLocalHost(), remotePort));
					log.info("Connection to SUMO established.");
					break;
				} catch (ConnectException ce) {
					log.debug("Server not ready, retrying in " + waitTime
							+ "ms");
					Thread.sleep(waitTime);
					waitTime *= 2;
				}
			}

			if (!socket.isConnected()) {
				log.error("Couldn't connect to server");
				throw new IOException("can't connect to SUMO server");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		currentSimStep = 0;

		subscribeVehiclesLifecycle();

		closeQuery = new CloseQuery(socket);
	}

	private void retrieveFromURLs() throws IOException {
		if (configFile.startsWith("http://")) {
			
			httpRetriever = new SumoHttpRetriever(configFile);

			log.info("Downloading config file from " + configFile);
			try {
				httpRetriever.fetchAndParse();
			} catch (SAXException e) {
				throw new IOException(e);
			}
			
			configFile = httpRetriever.getConfigFileName();
		}
			
	}

	private void runSUMO() throws IOException {
		final String sumoEXE = System.getProperty(SUMO_EXE_PROPERTY);
		if (sumoEXE == null)
			throw new RuntimeException("System property " + SUMO_EXE_PROPERTY
					+ " must be set");
>>>>>>> origin/abdalla

		args.add(0, sumoEXE);
		
		args.add("-c");
		args.add(configFile);
		args.add("--remote-port");
		args.add(Integer.toString(remotePort));
		
		if (randomSeed != -1) {
			args.add("--seed");
			args.add(Integer.toString(randomSeed));
<<<<<<< HEAD
		}

		if (log.isDebugEnabled())
			log.debug("Executing SUMO with cmdline " + args);

		String[] argsArray = new String[args.size()];
		args.toArray(argsArray);
		sumoProcess = Runtime.getRuntime().exec(argsArray);

		// String logProcessName = SUMO_EXE.substring(SUMO_EXE.lastIndexOf("\\")
		// + 1);

		StreamLogger errStreamLogger = new StreamLogger(sumoProcess.getErrorStream(), "SUMO-err:");
		StreamLogger outStreamLogger = new StreamLogger(sumoProcess.getInputStream(), "SUMO-out:");
		new Thread(errStreamLogger, "StreamLogger-SUMO-err").start();
		new Thread(outStreamLogger, "StreamLogger-SUMO-out").start();
	}

	private void findAvailablePort() throws IOException {
		ServerSocket testSock = new ServerSocket(0);
		remotePort = testSock.getLocalPort();
		testSock.close();
		testSock = null;
=======
		}

		if (log.isDebugEnabled())
			log.debug("Executing SUMO with cmdline " + args);

		String[] argsArray = new String[args.size()];
		args.toArray(argsArray);
		sumoProcess = Runtime.getRuntime().exec(argsArray);

		// String logProcessName = SUMO_EXE.substring(SUMO_EXE.lastIndexOf("\\")
		// + 1);

		StreamLogger errStreamLogger = new StreamLogger(sumoProcess.getErrorStream(), "SUMO-err:");
		StreamLogger outStreamLogger = new StreamLogger(sumoProcess.getInputStream(), "SUMO-out:");
		new Thread(errStreamLogger, "StreamLogger-SUMO-err").start();
		new Thread(outStreamLogger, "StreamLogger-SUMO-out").start();
	}

	private void findAvailablePort() throws IOException {
		ServerSocket testSock = new ServerSocket(0);
		remotePort = testSock.getLocalPort();
		testSock.close();
		testSock = null;
>>>>>>> origin/abdalla
	}

	/**
	 * Closes the connection, quits the simulator, frees any stale
	 * resource and makes all {@link Vehicle} instances inactive.
	 * 
	 * @throws InterruptedException
	 *             if the current thread was interrupted while waiting for SUMO
	 *             to close.
<<<<<<< HEAD
	 */
	public void close() throws InterruptedException {
		for (Vehicle v : vehicles.values())
			v.alive = false;
		
		/*
		 * Unlike other command methods that instantiate a new TraCIQuery
		 * instance in the method itself, for CloseQuery we must instantiate the
		 * corresponding CloseQuery object before the effective execution, since
		 * it may occur in a remote JiST server after the RMI connection is
		 * closed. This would make the loading of the CloseQuery class
		 * impossible at this point.
		 */
		if (socket != null) {
			if (closeQuery != null) {
=======
	 */
	public void close() throws InterruptedException {
		for (Vehicle v : vehicles.values())
			v.alive = false;
		
		/*
		 * Unlike other command methods that instantiate a new TraCIQuery
		 * instance in the method itself, for CloseQuery we must instantiate the
		 * corresponding CloseQuery object before the effective execution, since
		 * it may occur in a remote JiST server after the RMI connection is
		 * closed. This would make the loading of the CloseQuery class
		 * impossible at this point.
		 */
		if (socket != null) {
			if (closeQuery != null) {
>>>>>>> origin/abdalla
				try {
					closeQuery.doCommand();
				} catch (IOException e) {
					/*
					 * do nothing, because probably the connection is already
					 * messed up and the only logical thing to do is to convince
					 * ourselves that the connection is definitely closed.
					 */
<<<<<<< HEAD
				}
				closeQuery = null;
			}
			socket = null;
		}
		
		if (sumoProcess != null) {
			sumoProcess.waitFor();
			sumoProcess = null;
		}
		
		if (httpRetriever != null)
			httpRetriever.close();
		
		vehicles.clear();
		vehicleLifecycleObservers.clear();
		cachedLanes = null;
=======
				}
				closeQuery = null;
			}
			socket = null;
		}
		
		if (sumoProcess != null) {
			sumoProcess.waitFor();
			sumoProcess = null;
		}
		
		if (httpRetriever != null)
			httpRetriever.close();
		
		vehicles.clear();
		vehicleLifecycleObservers.clear();
		cachedLanes = null;
>>>>>>> origin/abdalla
	}
	
	/**
	 * Closes the connection, eating the {@link InterruptedException} it may
	 * throw, hoping that Murphy's Law doesn't notice all this ugly thing.
	 */
	private void closeAndDontCareAboutInterruptedException() {
		try {
			close();
		} catch (InterruptedException e) {
			/*
			 * please, please, please, Murphy's law, stay away from here...
			 */
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns <code>true</code> if the connection was closed by the user, or if
	 * an {@link IOException} was thrown after the connection was made.
	 * @see #close()
	 */
	public boolean isClosed() {
		return socket == null || socket.isClosed();
<<<<<<< HEAD
	}

	/**
	 * Returns the boundaries of the network.
	 * 
	 * @return the boundaries of the network
	 * 
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
=======
	}

	/**
	 * Returns the boundaries of the network.
	 * 
	 * @return the boundaries of the network
	 * 
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
>>>>>>> origin/abdalla
	public Rectangle2D queryBounds() throws IOException {
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
			Map<String, Lane> lanes = getLanesMap();
			Rectangle2D boundsAll = null;
			for (Lane r : lanes.values()) {
				Rectangle2D bounds = (Rectangle2D)r.getBoundingBox().clone();
				if (boundsAll == null)
					boundsAll = bounds;
				else
					boundsAll = boundsAll.createUnion(bounds);
			}

			return boundsAll;
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * the geographical coordinates (latitude/longitude) of the bottom-left
	 * corner, if the network description file is derived from a GIS map.
	 * 
	 * @return the geo coordinates of the bottom-left corner, or null if the
	 *         network description file didn't specify info about the geographic
	 *         map
	 */
	public Point2D getGeoOffset() {
		return geoOffset;
	}

	private void subscribeVehiclesLifecycle() throws IOException {
		(new SubscribeVehiclesLifecycle(socket)).doCommand();
=======
		}
	}

	/**
	 * the geographical coordinates (latitude/longitude) of the bottom-left
	 * corner, if the network description file is derived from a GIS map.
	 * 
	 * @return the geo coordinates of the bottom-left corner, or null if the
	 *         network description file didn't specify info about the geographic
	 *         map
	 */
	public Point2D getGeoOffset() {
		return geoOffset;
	}

	private void subscribeVehiclesLifecycle() throws IOException {
		(new SubscribeVehiclesLifecycle(socket)).doCommand();
>>>>>>> origin/abdalla
	}

	/**
	 * Advance the SUMO simulation by a step. The method will also call
	 * {@link VehicleLifecycleObserver} instances for vehicle enter and exit.
	 * 
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 * 
	 * @throws IllegalStateException
	 *             if the method is called when the connection is closed
<<<<<<< HEAD
	 */
=======
	 */
>>>>>>> origin/abdalla
	public void nextSimStep() throws IOException, IllegalStateException {
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			currentSimStep++;
			SimStepQuery ssQuery = new SimStepQuery(socket, currentSimStep);
			ssQuery.doCommand();
	
			Set<String> departed = ssQuery.getDepartedVehicles();
			Set<String> arrived = ssQuery.getArrivedVehicles();
			Set<String> teleportStarting = ssQuery.getTeleportStartingVehicles();
			Set<String> teleportEnding = ssQuery.getTeleportEndingVehicles();
	
=======
			currentSimStep++;
			SimStepQuery ssQuery = new SimStepQuery(socket, currentSimStep);
			ssQuery.doCommand();
	
			Set<String> departed = ssQuery.getDepartedVehicles();
			Set<String> arrived = ssQuery.getArrivedVehicles();
			Set<String> teleportStarting = ssQuery.getTeleportStartingVehicles();
			Set<String> teleportEnding = ssQuery.getTeleportEndingVehicles();
	
>>>>>>> origin/abdalla
	
			for (String id : departed) {
				vehicles.put(id, new Vehicle(id, socket, this));
	
	//			if (log.isDebugEnabled())
	//				log.debug("Vehicle " + id + " created");
			}
			for (String id : arrived) {
	//			if (log.isDebugEnabled())
	//				log.debug("Vehicle " + id + " destroyed");
				vehicles.get(id).alive = false;
				vehicles.remove(id);
			}
			
			for (String id : teleportStarting) {
				teleporting.add(id);
				vehicles.get(id).teleport = true;
			}
			for (String id : teleportEnding) {
				vehicles.get(id).teleport = false;
				teleporting.remove(id);
			}
	
			activeVehicles.addAll(departed);
			activeVehicles.removeAll(arrived);
	
<<<<<<< HEAD
			updateVehiclesPosition();
			
			if (getVehiclesEdgeAtSimStep) {
				updateVehiclesEdge();
=======
			updateVehiclesPosition();
			
			if (getVehiclesEdgeAtSimStep) {
				updateVehiclesEdge();
>>>>>>> origin/abdalla
			}
	
			notifyArrived(arrived);
			notifyDeparted(departed);
			notifyTeleportStarting(teleportStarting);
			notifyTeleportEnding(teleportEnding);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
		}
	}

	private void updateVehiclesPosition() throws IOException {
<<<<<<< HEAD
		MultiVehiclePositionQuery mvpQuery = 
			new MultiVehiclePositionQuery(socket, activeVehicles);
		
		Map<String, Point2D> vehiclesPosition = mvpQuery
				.getVehiclesPosition2D();

		for(Map.Entry<String, Point2D> entry : vehiclesPosition.entrySet()) {
			String id = entry.getKey();
			Point2D pos = entry.getValue();
			
			if(!activeVehicles.contains(id) || !vehicles.containsKey(id))
				throw new IllegalStateException("should never happen");
			
			vehicles.get(id).setPosition(pos);
=======
		MultiVehiclePositionQuery mvpQuery = 
			new MultiVehiclePositionQuery(socket, activeVehicles);
		
		Map<String, Point2D> vehiclesPosition = mvpQuery
				.getVehiclesPosition2D();

		for(Map.Entry<String, Point2D> entry : vehiclesPosition.entrySet()) {
			String id = entry.getKey();
			Point2D pos = entry.getValue();
			
			if(!activeVehicles.contains(id) || !vehicles.containsKey(id))
				throw new IllegalStateException("should never happen");
			
			vehicles.get(id).setPosition(pos);
>>>>>>> origin/abdalla
		}
	}

	private void updateVehiclesEdge() throws IOException {
<<<<<<< HEAD
		Map<String, RoadmapPosition> vehiclesPos = getAllVehiclesRoadmapPos();
		for ( Vehicle v : vehicles.values() ) {
			String name = v.getID();
			if (vehiclesPos.containsKey(name)) {
				v.setCurrentRoadmapPos(vehiclesPos.get(name));
			}
		}
	}

	protected void notifyDeparted(Set<String> created) {
		for (String id : created) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleDeparted(id);
		}
	}

	protected void notifyArrived(Set<String> destroyed) {
		for (String id : destroyed) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleArrived(id);
		}
	}
=======
		Map<String, RoadmapPosition> vehiclesPos = getAllVehiclesRoadmapPos();
		for ( Vehicle v : vehicles.values() ) {
			String name = v.getID();
			if (vehiclesPos.containsKey(name)) {
				v.setCurrentRoadmapPos(vehiclesPos.get(name));
			}
		}
	}

	protected void notifyDeparted(Set<String> created) {
		for (String id : created) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleDeparted(id);
		}
	}

	protected void notifyArrived(Set<String> destroyed) {
		for (String id : destroyed) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleArrived(id);
		}
	}
>>>>>>> origin/abdalla

	protected void notifyTeleportStarting(Set<String> starting) {
		for (String id : starting) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleTeleportStarting(id);
		}
	}

	protected void notifyTeleportEnding(Set<String> ending) {
		for (String id : ending) {
			for (VehicleLifecycleObserver observer : vehicleLifecycleObservers)
				observer.vehicleTeleportEnding(id);
		}
	}
<<<<<<< HEAD

	/**
	 * Returns a set containing which vehicles are currently circulating.
	 * Vehicles are identified by their string ID.
	 */
	public Set<String> getActiveVehicles() {
		return activeVehicles;
	}

	/**
	 * Returns the current simulation step number.
	 */
	public int getCurrentSimStep() {
		return currentSimStep;
	}

	/**
	 * Allows a {@link VehicleLifecycleObserver}-implementing object to be
	 * notified about vehicles' creation and destruction.
	 */
	public void addVehicleLifecycleObserver(VehicleLifecycleObserver observer) {
		vehicleLifecycleObservers.add(observer);
	}

	/**
	 * Prevents a {@link VehicleLifecycleObserver}-implementing object to be
	 * notified about vehicles' creation and destruction.
	 */
	public void removeVehicleLifecycleObserver(VehicleLifecycleObserver observer) {
		vehicleLifecycleObservers.remove(observer);
	}

	/**
	 * Returns a {@link Vehicle} object with the given ID.
	 * 
	 * @param id
	 *            the internal ID of the vehicle
	 * @return the corresponding Vehicle object
	 * @throws IllegalArgumentException
	 *             if the given ID doesn't match any active vehicle
	 */
	public Vehicle getVehicle(String id) {
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		if (vehicles.containsKey(id))
			return vehicles.get(id);
		else
			throw new IllegalArgumentException("Vehicle ID " + id
					+ " does not exist");
	}

	/**
	 * Returns a collection of {@link Lane} objects, representing the entire
	 * traffic network.
	 * <p>
	 * NOTE: this command can require some time to complete.
	 * @return a collection of {@link Lane}s
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public Collection<Lane> queryLanes() throws IOException {
=======

	/**
	 * Returns a set containing which vehicles are currently circulating.
	 * Vehicles are identified by their string ID.
	 */
	public Set<String> getActiveVehicles() {
		return activeVehicles;
	}

	/**
	 * Returns the current simulation step number.
	 */
	public int getCurrentSimStep() {
		return currentSimStep;
	}

	/**
	 * Allows a {@link VehicleLifecycleObserver}-implementing object to be
	 * notified about vehicles' creation and destruction.
	 */
	public void addVehicleLifecycleObserver(VehicleLifecycleObserver observer) {
		vehicleLifecycleObservers.add(observer);
	}

	/**
	 * Prevents a {@link VehicleLifecycleObserver}-implementing object to be
	 * notified about vehicles' creation and destruction.
	 */
	public void removeVehicleLifecycleObserver(VehicleLifecycleObserver observer) {
		vehicleLifecycleObservers.remove(observer);
	}

	/**
	 * Returns a {@link Vehicle} object with the given ID.
	 * 
	 * @param id
	 *            the internal ID of the vehicle
	 * @return the corresponding Vehicle object
	 * @throws IllegalArgumentException
	 *             if the given ID doesn't match any active vehicle
	 */
	public Vehicle getVehicle(String id) {
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		if (vehicles.containsKey(id))
			return vehicles.get(id);
		else
			throw new IllegalArgumentException("Vehicle ID " + id
					+ " does not exist");
	}

	/**
	 * Returns a collection of {@link Lane} objects, representing the entire
	 * traffic network.
	 * <p>
	 * NOTE: this command can require some time to complete.
	 * @return a collection of {@link Lane}s
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public Collection<Lane> queryLanes() throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			if (cachedLanes == null) {
				log.info("Retrieving lanes...");
				Set<Lane> lanes = (new RoadmapQuery(socket)).queryLanes(readInternalLinks);
				log.info("... done, " + lanes.size() + " roads read");

				cachedLanes = new HashMap<String, Lane>();
				for (Lane r : lanes) {
					cachedLanes.put(r.externalID, r);
				}
				cachedLanes = Collections.unmodifiableMap(cachedLanes);
			}


=======
			if (cachedLanes == null) {
				log.info("Retrieving lanes...");
				Set<Lane> lanes = (new RoadmapQuery(socket)).queryLanes(readInternalLinks);
				log.info("... done, " + lanes.size() + " roads read");

				cachedLanes = new HashMap<String, Lane>();
				for (Lane r : lanes) {
					cachedLanes.put(r.externalID, r);
				}
				cachedLanes = Collections.unmodifiableMap(cachedLanes);
			}


>>>>>>> origin/abdalla
			return cachedLanes.values();
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
=======
		}
>>>>>>> origin/abdalla
	}

	/**
	 * @return a {@link Map} whose keys are the lane IDs and the values are the
	 *         corresponding {@link Lane} describing the lane.
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
<<<<<<< HEAD
	 */
	public Map<String, Lane> getLanesMap() throws IOException {
=======
	 */
	public Map<String, Lane> getLanesMap() throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			queryLanes();
=======
			queryLanes();
>>>>>>> origin/abdalla
			return cachedLanes;
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * Returns the length of a single road given its string ID.
	 * 
	 * @param roadID
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 * @throws NullPointerException
	 *             if the ID doesn't match any road
	 */
	public double getRoadLength(String roadID) throws IOException {
=======
		}
	}

	/**
	 * Returns the length of a single road given its string ID.
	 * 
	 * @param roadID
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 * @throws NullPointerException
	 *             if the ID doesn't match any road
	 */
	public double getRoadLength(String roadID) throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			Lane r = getLane(roadID);
=======
			Lane r = getLane(roadID);
>>>>>>> origin/abdalla
			return r.getLength();
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * Returns a {@link Lane} object matching the given ID
	 * 
	 * @param roadID
	 * @return the requested {@link Lane} object, or null if such road doesn't
	 *         exist.
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public Lane getLane(String roadID) throws IOException {
=======
		}
	}

	/**
	 * Returns a {@link Lane} object matching the given ID
	 * 
	 * @param roadID
	 * @return the requested {@link Lane} object, or null if such road doesn't
	 *         exist.
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public Lane getLane(String roadID) throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			if (cachedLanes == null)
				queryLanes();

=======
			if (cachedLanes == null)
				queryLanes();

>>>>>>> origin/abdalla
			return cachedLanes.get(roadID);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	@SuppressWarnings("serial")
	private static class DataFoundException extends SAXException {
		public DataFoundException(String string) {
			super(string);
		}
	};

	/**
	 * Since SUMO can't yet provide geographical info about the map via TraCI,
	 * and it's needed to geo-localize city maps (e.g. with Google Maps), this
	 * method tries to read the geographic offset from an external file. This
	 * information is contained in the network description file whose path is
	 * specified in the config file. So this method will look there.
	 * <p>
	 * NOTE: I marked this as "deprecated" since it may not work with the HTTP
	 * retrieval feature. Testing needed.
	 * @param configFile
	 */
	@Deprecated
	private static Point2D lookForGeoOffset(String configFile) {
		try {
			ContentHandler sumoConfHandler = new DefaultHandler() {

				boolean doRead = false;

				@Override
				public void startElement(String namespaceURI, String localName,
						String qName, Attributes atts) throws SAXException {
					if (localName.equals("net-file"))
						doRead = true;
				}

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (doRead) {
						throw new DataFoundException(new String(ch, start,
								length));
					}
				}
			};

			String netFile = null;

			XMLReader xmlReader = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");

			xmlReader.setContentHandler(sumoConfHandler);
			try {
				xmlReader
						.parse(new InputSource(new FileInputStream(configFile)));
				return null;
			} catch (DataFoundException dfe) {
				netFile = dfe.getMessage();
			}

			String basePath = configFile.substring(0, configFile
					.lastIndexOf(File.separatorChar));
			String absNetFile = basePath + File.separatorChar + netFile;

			ContentHandler netDescHandler = new DefaultHandler() {

				boolean doRead = false;

				@Override
				public void startElement(String namespaceURI, String localName,
						String qName, Attributes atts) throws SAXException {
					if (localName.equals("net-offset"))
						doRead = true;
				}

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (doRead) {
						throw new DataFoundException(new String(ch, start,
								length));
					}
				}
			};

			xmlReader = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");

			xmlReader.setContentHandler(netDescHandler);
			try {
				xmlReader
						.parse(new InputSource(new FileInputStream(absNetFile)));
				return null;
			} catch (DataFoundException dfe) {
				String geoOffsetStr = dfe.getMessage();
				String[] geoOffsetStrFields = geoOffsetStr.split(",");
				return new Point2D.Float(Float
						.parseFloat(geoOffsetStrFields[0]), Float
						.parseFloat(geoOffsetStrFields[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Point2D.Float(0, 0);
		}

	}

	/**
	 * Returns whether the inner-junction links will be read.
	 */
	public boolean isReadInternalLinks() {
		return readInternalLinks;
	}

	/**
	 * Enables or disable reading of internal, inner-junction links. If such
	 * feature is changed, the lanes will be re-read from SUMO.
	 * @param readInternalLinks
	 */
	public void setReadInternalLinks(boolean readInternalLinks) {
		if (this.readInternalLinks != readInternalLinks) {
			cachedLanes = null;
		}
		
		this.readInternalLinks = readInternalLinks;
	}

	/**
	 * Sets the maximum speed (in m/s) of a certain lane. Note that it doesn't
	 * affect subsequent rerouting (for that, use e.g.
	 * {@link #changeEdgeTravelTime(int, int, String, float)}).
	 * 
	 * @param laneID
	 * @param vmax
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public void changeLaneMaxVelocity(String laneID, float vmax) throws IOException {
=======
		}
	}

	@SuppressWarnings("serial")
	private static class DataFoundException extends SAXException {
		public DataFoundException(String string) {
			super(string);
		}
	};

	/**
	 * Since SUMO can't yet provide geographical info about the map via TraCI,
	 * and it's needed to geo-localize city maps (e.g. with Google Maps), this
	 * method tries to read the geographic offset from an external file. This
	 * information is contained in the network description file whose path is
	 * specified in the config file. So this method will look there.
	 * <p>
	 * NOTE: I marked this as "deprecated" since it may not work with the HTTP
	 * retrieval feature. Testing needed.
	 * @param configFile
	 */
	@Deprecated
	private static Point2D lookForGeoOffset(String configFile) {
		try {
			ContentHandler sumoConfHandler = new DefaultHandler() {

				boolean doRead = false;

				@Override
				public void startElement(String namespaceURI, String localName,
						String qName, Attributes atts) throws SAXException {
					if (localName.equals("net-file"))
						doRead = true;
				}

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (doRead) {
						throw new DataFoundException(new String(ch, start,
								length));
					}
				}
			};

			String netFile = null;

			XMLReader xmlReader = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");

			xmlReader.setContentHandler(sumoConfHandler);
			try {
				xmlReader
						.parse(new InputSource(new FileInputStream(configFile)));
				return null;
			} catch (DataFoundException dfe) {
				netFile = dfe.getMessage();
			}

			String basePath = configFile.substring(0, configFile
					.lastIndexOf(File.separatorChar));
			String absNetFile = basePath + File.separatorChar + netFile;

			ContentHandler netDescHandler = new DefaultHandler() {

				boolean doRead = false;

				@Override
				public void startElement(String namespaceURI, String localName,
						String qName, Attributes atts) throws SAXException {
					if (localName.equals("net-offset"))
						doRead = true;
				}

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (doRead) {
						throw new DataFoundException(new String(ch, start,
								length));
					}
				}
			};

			xmlReader = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");

			xmlReader.setContentHandler(netDescHandler);
			try {
				xmlReader
						.parse(new InputSource(new FileInputStream(absNetFile)));
				return null;
			} catch (DataFoundException dfe) {
				String geoOffsetStr = dfe.getMessage();
				String[] geoOffsetStrFields = geoOffsetStr.split(",");
				return new Point2D.Float(Float
						.parseFloat(geoOffsetStrFields[0]), Float
						.parseFloat(geoOffsetStrFields[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Point2D.Float(0, 0);
		}

	}

	/**
	 * Returns whether the inner-junction links will be read.
	 */
	public boolean isReadInternalLinks() {
		return readInternalLinks;
	}

	/**
	 * Enables or disable reading of internal, inner-junction links. If such
	 * feature is changed, the lanes will be re-read from SUMO.
	 * @param readInternalLinks
	 */
	public void setReadInternalLinks(boolean readInternalLinks) {
		if (this.readInternalLinks != readInternalLinks) {
			cachedLanes = null;
		}
		
		this.readInternalLinks = readInternalLinks;
	}

	/**
	 * Sets the maximum speed (in m/s) of a certain lane. Note that it doesn't
	 * affect subsequent rerouting (for that, use e.g.
	 * {@link #changeEdgeTravelTime(int, int, String, float)}).
	 * 
	 * @param laneID
	 * @param vmax
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public void changeLaneMaxVelocity(String laneID, float vmax) throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			ChangeLaneStateQuery clsq = new ChangeLaneStateQuery(socket, laneID);
=======
			ChangeLaneStateQuery clsq = new ChangeLaneStateQuery(socket, laneID);
>>>>>>> origin/abdalla
			clsq.changeMaxVelocity(vmax);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * Sets the travel time of a given edge in the specified time frame.
	 * Subsequent rerouting of vehicles (either with {@link Vehicle#reroute()}
	 * or {@link Vehicle#setEdgeTravelTime(String, Number)}) will be affected by this
	 * setting, if they don't have another specified travel time for this edge.
	 * 
	 * @param begin
	 * @param end
	 * @param edgeID
	 * @param travelTime
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public void changeEdgeTravelTime(int begin, int end, String edgeID, double travelTime) throws IOException {
=======
		}
	}

	/**
	 * Sets the travel time of a given edge in the specified time frame.
	 * Subsequent rerouting of vehicles (either with {@link Vehicle#reroute()}
	 * or {@link Vehicle#setEdgeTravelTime(String, Number)}) will be affected by this
	 * setting, if they don't have another specified travel time for this edge.
	 * 
	 * @param begin
	 * @param end
	 * @param edgeID
	 * @param travelTime
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public void changeEdgeTravelTime(int begin, int end, String edgeID, double travelTime) throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			ChangeEdgeStateQuery cesq = new ChangeEdgeStateQuery(socket, edgeID);
=======
			ChangeEdgeStateQuery cesq = new ChangeEdgeStateQuery(socket, edgeID);
>>>>>>> origin/abdalla
			cesq.changeGlobalTravelTime(begin, end, travelTime);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * Returns the globally-specified travel time of an edge in the current
	 * time step.
	 * @param edgeID
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public double getEdgeTravelTime(String edgeID) throws IOException {
=======
		}
	}

	/**
	 * Returns the globally-specified travel time of an edge in the current
	 * time step.
	 * @param edgeID
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	public double getEdgeTravelTime(String edgeID) throws IOException {
>>>>>>> origin/abdalla
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
			return getEdgeTravelTime(edgeID, currentSimStep);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}

	/**
	 * Returns the globally-specified travel time of an edge in a given time
	 * step.
	 * @param edgeID
	 * @param time
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	private double getEdgeTravelTime(String edgeID, int time) throws IOException {
		try {
			RetrieveEdgeStateQuery resq = new RetrieveEdgeStateQuery(socket, edgeID);
=======
		}
	}

	/**
	 * Returns the globally-specified travel time of an edge in a given time
	 * step.
	 * @param edgeID
	 * @param time
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
	 *             This will close the connection.
	 */
	private double getEdgeTravelTime(String edgeID, int time) throws IOException {
		try {
			RetrieveEdgeStateQuery resq = new RetrieveEdgeStateQuery(socket, edgeID);
>>>>>>> origin/abdalla
			return resq.getGlobalTravelTime(time);
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}
=======
		}
	}
>>>>>>> origin/abdalla

	/**
	 * Returns the {@link RoadmapPosition} of all vehicles.
	 * @return
	 * @throws IOException
	 *             if something wrong happened while sending the TraCI command.
<<<<<<< HEAD
	 *             This will close the connection.	 */
=======
	 *             This will close the connection.	 */
>>>>>>> origin/abdalla
	public Map<String, RoadmapPosition> getAllVehiclesRoadmapPos() throws IOException {
		if (isClosed())
			throw new IllegalStateException("connection is closed");
		
		try {
<<<<<<< HEAD
			Set<String> vehicleIDs = new HashSet<String>();
			for (Vehicle v : vehicles.values()) {
				vehicleIDs.add(v.getID());
			}
			MultiVehiclePositionQuery mvpq = new MultiVehiclePositionQuery(socket, 
					vehicleIDs);
			
=======
			Set<String> vehicleIDs = new HashSet<String>();
			for (Vehicle v : vehicles.values()) {
				vehicleIDs.add(v.getID());
			}
			MultiVehiclePositionQuery mvpq = new MultiVehiclePositionQuery(socket, 
					vehicleIDs);
			
>>>>>>> origin/abdalla
			return mvpq.getVehiclesPositionRoadmap();
		}
		catch (IOException e) {
			closeAndDontCareAboutInterruptedException();
			throw e;
<<<<<<< HEAD
		}
	}
	
	public void setGetVehiclesEdgeAtSimStep(boolean state) {
		getVehiclesEdgeAtSimStep = state;
	}
}

=======
		}
	}
	
	public void setGetVehiclesEdgeAtSimStep(boolean state) {
		getVehiclesEdgeAtSimStep = state;
	}
}

>>>>>>> origin/abdalla
