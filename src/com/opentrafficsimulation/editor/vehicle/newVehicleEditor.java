package com.opentrafficsimulation.editor.vehicle;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.opentrafficsimulation.connector.Connector;
import com.opentrafficsimulation.connector.utility.ConnectorType;
import com.opentrafficsimulation.editor.road.RoadEditor;
import com.opentrafficsimulation.utility.constants.AppConstants;
import com.opentrafficsimulation.utility.converter.Routes;
import com.opentrafficsimulation.utility.converter.Routes.Vehicle;


public class newVehicleEditor extends JPanel {

	private static final long serialVersionUID = -7292254993717333286L;
	private static newVehicleEditor vehicleEditor = new newVehicleEditor();
	private JRadioButton radioButtonPrivate = new javax.swing.JRadioButton();
	private JRadioButton radioButtonBus = new javax.swing.JRadioButton();
	private JRadioButton radioButtonTaxi = new javax.swing.JRadioButton();
	private JRadioButton radioButtonLorry = new javax.swing.JRadioButton();
	private ButtonGroup vehicleType = new ButtonGroup();
	private JTextField textFieldNoOfVehicle = new  JTextField();

	
	private static String maxSpeed = "100";
	private static List<Vehicle> privateList = new ArrayList<Vehicle>();
	private static List<Vehicle> busList = new ArrayList<Vehicle>();
	private static List<Vehicle> taxiList = new ArrayList<Vehicle>();
	private static List<Vehicle> lorryList = new ArrayList<Vehicle>();
	DefaultListModel model = new DefaultListModel();
	
    private newVehicleEditor() {
		super();
	}
	
	public static newVehicleEditor getInstance() {
		return vehicleEditor;
	}
	
	// Content wrapper
	private JScrollPane scrollPane;
	private JPanel content;
	JList listVehicles = new  JList(model);
	
	
	public void init() {	
		
		// Set size
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Vehicles"));
		setPreferredSize(new Dimension(AppConstants.APP_LEFT_COLUMN_WIDGET_WIDTH,AppConstants.APP_LEFT_COLUMN_WIDGET_HEIGHT));	
		
		// Initialize editor content
		content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		
        JButton buttonAdd = new JButton();
        JScrollPane jScrollPane1 = new  JScrollPane();
        JLabel labelNoOfVehiclesToAdd =new JLabel("No. Vehicles to add:");
        JLabel labelAddedVehicles = new JLabel("Added Vehicles");
        JLabel labelVehicleType = new JLabel("Select Vehicle Type:");
        //JLabel labelNoOfVehicles = new JLabel("No. of Vehicles");
        JButton buttonDelete = new JButton();
        JButton buttonReset = new JButton();
        JButton buttonCreate = new JButton();
        JButton buttonEdit = new JButton("Edit");

        buttonAdd.setText("Add Vehicle/s");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listVehicles);

        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonReset.setText("Reset");
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetActionPerformed(evt);
            }
        });

        buttonCreate.setText("Create Route");
        buttonCreate.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateActionPerformed(evt);
            }
        });

        vehicleType.add(radioButtonPrivate);
        radioButtonPrivate.setText("Private");

        vehicleType.add(radioButtonBus);
        radioButtonBus.setText("Bus");

        vehicleType.add(radioButtonTaxi);
        radioButtonTaxi.setText("Taxi");

        vehicleType.add(radioButtonLorry);
        radioButtonLorry.setText("Lorry");
        
        buttonEdit.setText("Edit");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });
        
/*        org.jdesktop.layout.GroupLayout contentLayout = new org.jdesktop.layout.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentLayout.createSequentialGroup()
                .addContainerGap()
                .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(contentLayout.createSequentialGroup()
                            .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(contentLayout.createSequentialGroup()
                                    .add(1, 1, 1)
                                    .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(radioButtonBus)
                                        .add(radioButtonLorry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(radioButtonPrivate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(radioButtonTaxi)))
                                .add(labelNoOfVehiclesToAdd)
                                .add(labelVehicleType))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, contentLayout.createSequentialGroup()
                            .add(buttonAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(15, 15, 15)))
                    .add(contentLayout.createSequentialGroup()
                        .add(textFieldNoOfVehicle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(labelAddedVehicles))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(buttonReset, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(buttonDelete, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .add(buttonEdit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(buttonCreate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentLayout.createSequentialGroup()
                .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelAddedVehicles)
                    .add(labelVehicleType))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(contentLayout.createSequentialGroup()
                        .add(radioButtonPrivate)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(radioButtonBus)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(radioButtonLorry)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(radioButtonTaxi)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(labelNoOfVehiclesToAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(textFieldNoOfVehicle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(contentLayout.createSequentialGroup()
                        .add(buttonDelete)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonEdit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(buttonReset)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonCreate)
                        .addContainerGap())
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
        
        
		// Set scroller
		scrollPane = new JScrollPane(content);  
		scrollPane.setSize(AppConstants.APP_LEFT_COLUMN_WIDGET_WIDTH, AppConstants.APP_LEFT_COLUMN_WIDGET_HEIGHT);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
		
		setVisible(true);*/
	}
	
	   private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                             
		  
		   	ListSelectionModel selmodel = listVehicles.getSelectionModel();
	        int index = selmodel.getMinSelectionIndex();
	        if (index >= 0) {

	        	String value = (String) model.get(index);
	        	
	        	if (value.contains("Private")) {
	        		privateList.clear();
	        	}
	        	
	        	if (value.contains("Bus")) {
	        		busList.clear();
	        	}
	        	
	        	if (value.contains("Taxi")) {
	        		taxiList.clear();
	        	}

	        	if (value.contains("Lorry")) {
	        		lorryList.clear();
	        	}
	        	
	        	model.remove(index);
	        	
	        } else {
	        	JOptionPane.showMessageDialog(this, "Select a value from the list to delete");
	        }
	   }                                            

	   private void clearList() {
		   privateList.clear();
		   busList.clear();
		   lorryList.clear();
		   taxiList.clear();
		   model.removeAllElements();
		   System.gc();
	   }
	   

	   private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {                                          
	        // TODO add your handling code here:
	    	//JOptionPane.showMessageDialog(null, "you have clicked create");
	    	
//	    	if (radioButtonPrivate.isSelected()) {
//				JOptionPane.showMessageDialog(null, "Prive is selected");
//			}
//	    	else if (radioButtonBus.isSelected()) {
//				JOptionPane.showMessageDialog(null, "Bus is selected");
//				
//			}
//	    	else if (radioButtonLorry.isSelected()) {
//				JOptionPane.showMessageDialog(null, "Lorry is selected");
//				
//			}
//	    	else {
//				JOptionPane.showMessageDialog(null, "Taxi is selected");
//			}

	    	String net=RoadEditor.getInstance().netgenerate_file + ".net.xml";
	    //  generate trip from net
	    	
	    	Connector connector = new Connector(ConnectorType.CONNECTOR_PYTHON);
	    	String Option;
	    	{	
		    	Option = connector.getToolsDir()+"sumolib\\trip\\randomTrips.py -n "+connector.getOutputDir()+net;
		    	//RoadEditor.getInstance().netgenerate_file+ net.XML";
		    	Option +=" -e"+textFieldNoOfVehicle.getText()+ " -l";
		    	Option +=" -o "+ connector.getOutputDir()+ RoadEditor.getInstance().netgenerate_file + ".trips.xml";
		    	connector.runCommand(Option);
		    	
	    	}
	    		
	    //  transfer trip to route file
	    	Connector connectorRouter = new Connector(ConnectorType.CONNECTOR_ROUTER);
	    	String Options1;	
	    	{
		    	Options1 = "-n " +connectorRouter.getOutputDir();
		    	Options1 += net;
		    	Options1 +=" -t "+ connectorRouter.getOutputDir()+ RoadEditor.getInstance().netgenerate_file +".trips.xml";
		    	Options1 +=" -o "+ connectorRouter.getOutputDir()+ RoadEditor.getInstance().netgenerate_file +"Old.rou.xml";
		    	connectorRouter.runCommand(Options1);
	    	}
	    	
	    	boolean isTypeSelected = false;
	    	if (radioButtonPrivate.isSelected()) {
	    		isTypeSelected = true;
	    	} else if (radioButtonBus.isSelected()) {
	    		isTypeSelected = true;
	    	} else if (radioButtonLorry.isSelected()) {
	    		isTypeSelected = true;
	    	} else if (radioButtonTaxi.isSelected()) {
	    		isTypeSelected = true;
	    	}
	    	
	    	if (!isTypeSelected) {
	    		JOptionPane.showMessageDialog(this, "Please Select Vehicle Type to proceed");
	    		return;
	    	}
	    	
	    	model.removeAllElements();
	    	
	        // create JAXB context and instantiate marshaller
	        JAXBContext context;
			try {

				context = JAXBContext.newInstance(Routes.class);
				javax.xml.bind.Unmarshaller m = context.createUnmarshaller();
				Routes bookstore2 = (Routes) m.unmarshal(new FileReader(connectorRouter.getOutputDir()+ RoadEditor.getInstance().netgenerate_file +"Old.rou.xml"));
				List<Vehicle> list = bookstore2.getVehicle();
				
				for (Vehicle vehicle : list) {
					
					vehicle.setMaxSpeed(maxSpeed);
					String vehicleType = "";
					if (radioButtonPrivate.isSelected()) {
						vehicle.setVehicleType("Private");
						privateList.add(vehicle);
					} else if (radioButtonBus.isSelected()) {
						vehicle.setVehicleType("Bus");
						busList.add(vehicle);
					} else if (radioButtonLorry.isSelected()) {
						vehicle.setVehicleType("Lorry");
						lorryList.add(vehicle);
					} else if (radioButtonTaxi.isSelected()) {
						vehicle.setVehicleType("Taxi");
						taxiList.add(vehicle);
					} 
				}

				if (privateList.size() > 0) {
					model.addElement("Private = " + privateList.size());
				}
				
				if (busList.size() > 0) {
					model.addElement("Bus = " + busList.size());
				}
				
				if (lorryList.size() > 0) {
					model.addElement("Lorry = " + lorryList.size());
				}

				if (taxiList.size() > 0) {
					model.addElement("Taxi = " + taxiList.size());
				}
				
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    }                                         

	    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {                                             
	        // TODO add your handling code here:
	    	Connector connector = new Connector(null);
	    	List<Vehicle> commonList = new ArrayList<Vehicle>();
	    	int i = 0;
	    	
	    	for (Vehicle privateVehicle : privateList) {
	    		privateVehicle.setId(Integer.toString(i));
	    		privateVehicle.setDepart(Integer.toString(i) + ".00");
	    		commonList.add(privateVehicle);
	    		i++;
	    	}
	    	
	    	for (Vehicle bus : busList) {
	    		
	    		bus.setId(Integer.toString(i));
	    		bus.setDepart(Integer.toString(i) + ".00");
	    		commonList.add(bus);
	    		i++;
	    	}
	    	
	    	for (Vehicle lorry : lorryList) {
	    	
	    		lorry.setId(Integer.toString(i));
	    		lorry.setDepart(Integer.toString(i) + ".00");
	    		commonList.add(lorry);
	    		i++;
	    	}
	    	
	    	for (Vehicle taxi : taxiList) {
	    		
	    		taxi.setId(Integer.toString(i));
	    		taxi.setDepart(Integer.toString(i) + ".00");
	    		commonList.add(taxi);
	    		i++;
	    	}
	    	
	    	try {
	    		
	    		// create JAXB context and instantiate marshaller
	    		JAXBContext context = JAXBContext.newInstance(Routes.class);
	    		Marshaller m = context.createMarshaller();
	    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    		
	    		Routes route = new Routes();
	    		route.getVehicle().addAll(commonList);
	    		
	    		// Write to System.out
	    		m.marshal(route, System.out);
	    		
	    		// Write to File
	    		m.marshal(route, new File(connector.getOutputDir()+ RoadEditor.getInstance().netgenerate_file +".rou.xml"));
	    		clearList();
	    		
	    		JOptionPane.showMessageDialog(this, "File Created Successfully");
	    		
	    	} catch (JAXBException e) {
	    		System.out.println("Exception Occured");
	    	}
	    }    
	    

	    private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {                                            
	    	clearList();
	    }   
	    
	    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {                                           
	        // TODO add your handling code here:
	    }       
	
}