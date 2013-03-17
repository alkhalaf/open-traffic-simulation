package com.opentrafficsimulation.editor.road;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.PlainDocument;
//import javax.swing.text.AbstractDocument.Content;

import com.opentrafficsimulation.connector.Connector;
import com.opentrafficsimulation.connector.utility.ConnectorType;
import com.opentrafficsimulation.editor.MainEditor;
import com.opentrafficsimulation.editor.light.LightEditor;
import com.opentrafficsimulation.utility.constants.AppConstants;
import com.opentrafficsimulation.utility.content.AppFont;
import com.opentrafficsimulation.utility.content.AppText;

import java.awt.Color;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class RoadEditor extends JPanel {

    {
        // Set Look & Feel
        try {
            javax.swing.UIManager
                    .setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final long serialVersionUID = 5013628145499136169L;
    // Singleton instance
    private static RoadEditor RoadEditor = new RoadEditor();
    // Content wrapper
    private JScrollPane scrollPane;
    private JPanel content;
    private JPanel contentbuildMap = new JPanel();
    private JPanel contentImportMap = new JPanel();
    // private JPanel contentTabbed = new JPanel();
    private int lane_num = 0, grid_num = 0, arm_num = 0, circle_num = 0,
            iteration_num = 0, max_distance = 0, min_distance = 0;
    private double lane_speed = 0, grid_length = 0, space_radius = 0;
    public String netgenerate_file = "myNet";
    private String osmnet_file = "osmNet.net.xml";
    public String osmFile_path;
    private JFileChooser chooser;
    // private JTabbedPane randomTapped;
    // private JTabbedPane spiderTabbed;
    // private JTabbedPane jTabbedPane1;
    // private JTabbedPane typeTabbed;
    private String choosertitle = "Select OSM file";

    /**
     * @wbp.nonvisual location=46,367
     */
    // private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    /**
     * Singleton constructor
     */
    private RoadEditor() {
        super();

    }

    /**
     * Used for invoking instance
     *
     * @return
     */
    public static RoadEditor getInstance() {
        return RoadEditor;
    }

    /**
     * Initialises an instance of Main GUI
     */
    public void init() {

        // Set size
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(new TitledBorder("Roads"));
        this.setPreferredSize(new java.awt.Dimension(930, 300));

        // Initialize editor content
        content = new JPanel();
        BoxLayout contentLayout = new BoxLayout(content,
                javax.swing.BoxLayout.Y_AXIS);
        content.setLayout(contentLayout);

        content.add(contentbuildMap);
        contentbuildMap.setLayout(new BoxLayout(contentbuildMap,
                javax.swing.BoxLayout.Y_AXIS));
        contentbuildMap.setBorder(new TitledBorder("Build Map"));
        contentbuildMap.setSize(920, 152);

        // ///////////TabbedPane

        // ///////////TabbedPane

        // *****************Common Attribute Box******************

        JLabel lanenum_lb = new JLabel(AppText.LANE_NUMBER);
        lanenum_lb.setFont(AppFont.textFont);
        final JTextField lanenum_tf = new JTextField(3);
        lanenum_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument lanenum_doc = (PlainDocument) lanenum_tf.getDocument();
        lanenum_doc.setDocumentFilter(new MyIntFilter2());

        JLabel lanespeed_lb = new JLabel(AppText.LANE_SPEED);
        lanespeed_lb.setFont(AppFont.textFont);
        final JTextField lanespeed_tf = new JTextField(3);
        lanespeed_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument lanespeed_doc = (PlainDocument) lanespeed_tf
                .getDocument();
        lanespeed_doc.setDocumentFilter(new MyIntFilter2());

        Box comm_attbox = new Box(BoxLayout.X_AXIS);
        comm_attbox.add(lanenum_lb);
        comm_attbox.add(lanenum_tf);
        comm_attbox.add(lanespeed_lb);
        comm_attbox.add(lanespeed_tf);
        contentbuildMap.add(comm_attbox);

        final JTabbedPane typeTabbed = new JTabbedPane();
        contentbuildMap.add(typeTabbed);
        typeTabbed.setSize(910, 130);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel,
                javax.swing.BoxLayout.Y_AXIS));
        typeTabbed.add(gridPanel, "Grid Network");
        gridPanel.setSize(905, 80);

        JPanel spiderPanel = new JPanel();
        spiderPanel.setLayout(new BoxLayout(spiderPanel,
                javax.swing.BoxLayout.Y_AXIS));
        typeTabbed.add(spiderPanel, "Spider Network");
        spiderPanel.setSize(905, 80);

        JPanel randomPanel = new JPanel();
        randomPanel.setLayout(new BoxLayout(randomPanel,
                javax.swing.BoxLayout.Y_AXIS));
        typeTabbed.add(randomPanel, "Random Network");
        randomPanel.setSize(905, 80);

        // **************Grid-network Attribute Box*****************
        JLabel gridnum_lb = new JLabel(AppText.GRID_NUMBER);
        gridnum_lb.setFont(AppFont.textFont);
        final JTextField gridnum_tf = new JTextField();
        gridnum_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument gridnum_doc = (PlainDocument) gridnum_tf.getDocument();
        gridnum_doc.setDocumentFilter(new MyIntFilter2());

        JLabel gridleng_lb = new JLabel(AppText.GRID_LENGTH);
        gridleng_lb.setFont(AppFont.textFont);
        final JTextField gridleng_tf = new JTextField();
        gridleng_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument gridleng_doc = (PlainDocument) gridleng_tf.getDocument();
        gridleng_doc.setDocumentFilter(new MyIntFilter2());

        final Box grid_attbox = new Box(BoxLayout.X_AXIS);
        grid_attbox.setVisible(true);
        grid_attbox.add(gridnum_lb);
        grid_attbox.add(gridnum_tf);
        grid_attbox.add(gridleng_lb);
        grid_attbox.add(gridleng_tf);

        // ************Spider-network Attribute Box***************
        JLabel armnum_lb = new JLabel(AppText.ARM_NUMBER);
        armnum_lb.setFont(AppFont.textFont);
        final JTextField armnum_tf = new JTextField();
        armnum_tf.setMaximumSize(new Dimension(80, 20));

        PlainDocument armnum_doc = (PlainDocument) armnum_tf.getDocument();
        armnum_doc.setDocumentFilter(new MyIntFilter2());

        JLabel circlenum_lb = new JLabel(AppText.CIRCLE_NUMBER);
        circlenum_lb.setFont(AppFont.textFont);
        final JTextField circlenum_tf = new JTextField();
        circlenum_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument circlenum_doc = (PlainDocument) circlenum_tf
                .getDocument();
        circlenum_doc.setDocumentFilter(new MyIntFilter2());

        JLabel spacerad_lb = new JLabel(AppText.SPACE_RADIUS);
        spacerad_lb.setFont(AppFont.textFont);
        final JTextField spacerad_tf = new JTextField();
        spacerad_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument spacerad_doc = (PlainDocument) spacerad_tf.getDocument();
        spacerad_doc.setDocumentFilter(new MyIntFilter2());

        final Box spider_attbox = new Box(BoxLayout.X_AXIS);
        spider_attbox.setVisible(true);
        spider_attbox.add(armnum_lb);
        spider_attbox.add(armnum_tf);
        spider_attbox.add(circlenum_lb);
        spider_attbox.add(circlenum_tf);
        spider_attbox.add(spacerad_lb);
        spider_attbox.add(spacerad_tf);

        // **************Random-network Attribute Box********************
        JLabel iteration_lb = new JLabel(AppText.ITERATION);
        iteration_lb.setFont(AppFont.textFont);
        final JTextField iteration_tf = new JTextField();
        iteration_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument iteration_doc = (PlainDocument) iteration_tf
                .getDocument();
        iteration_doc.setDocumentFilter(new MyIntFilter2());

        JLabel maxdistance_lb = new JLabel(AppText.MAX_DISTANCE);
        maxdistance_lb.setFont(AppFont.textFont);
        final JTextField maxdistance_tf = new JTextField();
        maxdistance_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument maxdistance_doc = (PlainDocument) maxdistance_tf
                .getDocument();
        maxdistance_doc.setDocumentFilter(new MyIntFilter2());

        JLabel mindistance_lb = new JLabel(AppText.MIN_DISTANCE);
        mindistance_lb.setFont(AppFont.textFont);
        final JTextField mindistance_tf = new JTextField();
        mindistance_tf.setMaximumSize(new Dimension(80, 20));
        PlainDocument mindistance_doc = (PlainDocument) mindistance_tf
                .getDocument();
        mindistance_doc.setDocumentFilter(new MyIntFilter2());

        final Box random_attbox = Box.createHorizontalBox();
        random_attbox.setVisible(true);
        random_attbox.add(iteration_lb);
        random_attbox.add(iteration_tf);
        random_attbox.add(maxdistance_lb);
        random_attbox.add(maxdistance_tf);
        random_attbox.add(mindistance_lb);
        random_attbox.add(mindistance_tf);

        gridPanel.add(grid_attbox);
        spiderPanel.add(spider_attbox);
        randomPanel.add(random_attbox);

        // After clicking on the button, java will pass the mapCommand to
        // NETGENERATE and generate a network file
        final JButton createMap = new JButton("Create Map");
        contentbuildMap.add(createMap);
        createMap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (lanenum_tf.getText().length() < 1
                        || lanespeed_tf.getText().length() < 1) {
                    JOptionPane.showMessageDialog(null,
                            "Please set all the values");
                } // if (lanenum_tf.getText().length()>=1 &&
                // lanespeed_tf.getText().length()>=1) {
                else {
                    lane_num = Integer.parseInt(lanenum_tf.getText());
                    lane_speed = Double.parseDouble(lanespeed_tf.getText());
                    String Options;
                    String pattern = "[0-9A-Za-z]*";
                    boolean filenameok;
                    int selection = typeTabbed.getSelectedIndex();

                    if (selection == 0) {
                        if (gridnum_tf.getText().length() >= 1
                                && gridleng_tf.getText().length() >= 1) {

                            grid_num = Integer.parseInt(gridnum_tf.getText());
                            grid_length = Double.parseDouble(gridleng_tf
                                    .getText());

                            if (lane_num >= 1 && lane_num <= 3
                                    && lane_speed >= 10 && lane_speed <= 80
                                    && grid_num >= 2 && grid_num <= 6
                                    && grid_length >= 30 && grid_length <= 80) {

                                netgenerate_file = JOptionPane
                                        .showInputDialog(null,
                                        "Enter name for the XML file(Only letters and digits allowed):");

                                filenameok = netgenerate_file.matches(pattern);
                                if (netgenerate_file.length() >= 1
                                        && filenameok == true) {

                                    boolean result;

                                    Connector connector = new Connector(
                                            ConnectorType.CONNECTOR_NETGENERATE);
                                    Options = " --grid-net";
                                    Options += " --grid-number=" + grid_num;
                                    Options += " --grid-length=" + grid_length;
                                    Options += " -L=" + lane_num + " -S="
                                            + lane_speed;
                                    Options += " --output-file="
                                            + connector.getOutputDir()
                                            + netgenerate_file + ".net.xml";

                                    result = connector.runCommand(Options);

                                    if (!result) {
                                        showEditors();
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Your map has been generated successfully!");
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Sorry, your map gerenation failed. Please try again!");
                                        lanenum_tf.setText("");
                                        lanespeed_tf.setText("");
                                        gridnum_tf.setText("");
                                        gridleng_tf.setText("");
                                    }
                                } else {
                                    JOptionPane
                                            .showMessageDialog(null,
                                            "Please check or input your filename!");
                                }

                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Please check your input!");
                                if (lane_num < 1 || lane_num > 3
                                        || (lanenum_tf.getText() == null)) {
                                    lanenum_tf.setBackground(Color.PINK);
                                }
                                if (lane_speed < 10 || lane_speed > 80
                                        || (lanespeed_tf.getText() == null)) {
                                    lanespeed_tf.setBackground(Color.PINK);
                                }
                                if (grid_num < 2 || grid_num > 6
                                        || (gridnum_tf.getText() == null)) {
                                    gridnum_tf.setBackground(Color.PINK);
                                }
                                if (grid_length < 30 || grid_length > 80
                                        || (gridleng_tf.getText() == null)) {
                                    gridleng_tf.setBackground(Color.PINK);
                                }
                            }
                        }
                    }
                    // ///////////spider
                    // network------------------------------------------

                    if (selection == 1) {

                        if (armnum_tf.getText().length() >= 1
                                && circlenum_tf.getText().length() >= 1
                                && spacerad_tf.getText().length() >= 1) {
                            arm_num = Integer.parseInt(armnum_tf.getText());
                            circle_num = Integer.parseInt(circlenum_tf
                                    .getText());
                            space_radius = Double.parseDouble(spacerad_tf
                                    .getText());

                            if (lane_num >= 1 && lane_num <= 3
                                    && lane_speed >= 10 && lane_speed <= 80
                                    && arm_num >= 3 && arm_num <= 6
                                    && circle_num >= 2 && circle_num <= 6
                                    && space_radius >= 20 && space_radius <= 80) {

                                netgenerate_file = JOptionPane
                                        .showInputDialog(null,
                                        "Enter name for the XML file(Only letters and digits allowed):");
                                filenameok = netgenerate_file.matches(pattern);
                                if (netgenerate_file.length() >= 1
                                        && filenameok == true) {

                                    boolean result;

                                    Connector connector = new Connector(
                                            ConnectorType.CONNECTOR_NETGENERATE);
                                    Options = " --spider-net";
                                    Options += " --spider-arm-number="
                                            + arm_num;
                                    Options += " --spider-circle-number="
                                            + circle_num;
                                    Options += " --spider-space-rad="
                                            + space_radius;
                                    Options += " -L=" + lane_num + " -S="
                                            + lane_speed;
                                    Options += " --output-file="
                                            + connector.getOutputDir()
                                            + netgenerate_file + ".net.xml";
                                    result = connector.runCommand(Options);
                                    if (!result) {
                                        showEditors();
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Your map has been generated successfully!");
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Sorry, your map gerenation failed. Please try again!");
                                        lanenum_tf.setText("");
                                        lanespeed_tf.setText("");
                                        armnum_tf.setText("");
                                        circlenum_tf.setText("");
                                        spacerad_tf.setText("");
                                    }
                                } else {
                                    JOptionPane
                                            .showMessageDialog(null,
                                            "Please check or input your file name!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Please check your input!");
                                if (lane_num < 1 || lane_num > 3) {
                                    lanenum_tf.setBackground(Color.PINK);
                                }
                                if (lane_speed < 10 || lane_speed > 80) {
                                    lanespeed_tf.setBackground(Color.PINK);
                                }
                                if (arm_num < 3 || arm_num > 6) {
                                    armnum_tf.setBackground(Color.PINK);
                                }
                                if (circle_num < 2 || circle_num > 6) {
                                    circlenum_tf.setBackground(Color.PINK);
                                }
                                if (space_radius < 20 || space_radius > 80) {
                                    spacerad_tf.setBackground(Color.PINK);
                                }
                            }
                        }

                    }

                    // /////-------------------Random
                    // Network----------------------------

                    if (selection == 2) {

                        if (iteration_tf.getText().length() >= 1
                                && maxdistance_tf.getText().length() >= 1
                                && mindistance_tf.getText().length() >= 1) {

                            iteration_num = Integer.parseInt(iteration_tf
                                    .getText());
                            max_distance = Integer.parseInt(maxdistance_tf
                                    .getText());
                            min_distance = Integer.parseInt(mindistance_tf
                                    .getText());

                            if (lane_num >= 1 && lane_num <= 3
                                    && lane_speed >= 10 && lane_speed <= 80
                                    && iteration_num >= 10
                                    && iteration_num <= 40
                                    && max_distance >= 40 && max_distance <= 80
                                    && min_distance >= 20 && min_distance <= 80) {

                                netgenerate_file = JOptionPane
                                        .showInputDialog(null,
                                        "Enter name for the XML file(Only letters and digits allowed):");
                                filenameok = netgenerate_file.matches(pattern);
                                if (netgenerate_file.length() >= 1
                                        && filenameok == true) {

                                    boolean result;
                                    Connector connector = new Connector(
                                            ConnectorType.CONNECTOR_NETGENERATE);
                                    Options = " --random-net";
                                    Options += " --rand-iterations="
                                            + iteration_num;
                                    Options += " --rand-max-distance="
                                            + max_distance;
                                    Options += " --rand-min-distance="
                                            + min_distance;
                                    Options += " -L=" + lane_num + " -S="
                                            + lane_speed;
                                    Options += " --output-file="
                                            + connector.getOutputDir()
                                            + netgenerate_file + ".net.xml";
                                    result = connector.runCommand(Options);

                                    if (!result) {
                                        showEditors();
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Your map has been generated successfully!");

                                    } else {
                                        JOptionPane
                                                .showMessageDialog(null,
                                                "Sorry, your map gerenation failed. Please try again!");
                                        lanenum_tf.setText("");
                                        lanespeed_tf.setText("");
                                        iteration_tf.setText("");
                                        maxdistance_tf.setText("");
                                        mindistance_tf.setText("");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Please check your input filename");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Please check your input!");
                                if (lane_num < 1 || lane_num > 3) {
                                    lanenum_tf.setBackground(Color.PINK);
                                }
                                if (lane_speed < 10 || lane_speed > 80) {
                                    lanespeed_tf.setBackground(Color.PINK);
                                }
                                if (iteration_num < 10 || iteration_num > 40) {
                                    iteration_tf.setBackground(Color.PINK);
                                }
                                if (max_distance < 40 || max_distance > 80) {
                                    maxdistance_tf.setBackground(Color.PINK);
                                }
                                if (min_distance < 20 || min_distance > 80) {
                                    mindistance_tf.setBackground(Color.PINK);
                                }
                                if (min_distance > max_distance) {
                                    mindistance_tf.setBackground(Color.PINK);
                                    maxdistance_tf.setBackground(Color.PINK);
                                }
                            }
                        }
                    }

                }

            }
        });

        // ****************************************Import Map
        // Box***********************************

        content.add(contentImportMap);
        contentImportMap.setLayout(new BoxLayout(contentImportMap,
                javax.swing.BoxLayout.X_AXIS));
        contentImportMap.setBorder(new TitledBorder("Import Map"));

        final JButton selectMap = new JButton("Select Map");

        final JTextField filename = new JTextField();
        filename.setMaximumSize(new Dimension(480, 20));

        selectMap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {

                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                FileFilter xmlFilter = new ExtensionFileFilter("OSM File(*.osm.xml", "osm.xml");
                chooser.setFileFilter(xmlFilter);
                chooser.addChoosableFileFilter(xmlFilter);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
                    osmFile_path = chooser.getSelectedFile().getAbsolutePath();

                    filename.setText(osmFile_path);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "You havn't chosen an OSM file!");
                    ;
                }
            }
        });

        contentImportMap.add(selectMap);
        contentImportMap.add(filename);

        final JButton importMap = new JButton("Import");

        importMap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg1) {

                String Options;
                Connector connector = new Connector(
                        ConnectorType.CONNECTOR_NETCONVERT);
                Options = " --osm-files=" + osmFile_path;
                Options += " -o=" + connector.getOutputDir() + osmnet_file;
                connector.runCommand(Options);

                showEditors();
            }
        });

        contentImportMap.add(importMap);

        // Set scroller
        scrollPane = new JScrollPane(content);
        scrollPane.setSize(AppConstants.APP_RIGHT_COLUMN_WIDGET_WIDTH,
                AppConstants.APP_RIGHT_COLUMN_WIDGET_HEIGHT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
        setVisible(true);
        this.setSize(930, 300);

    }

    // added by mahmut on 10/03/13
    public void showEditors() {
        // Added by mahmut on 10.03.13
        // Update traffic light editor
        LightEditor.getInstance().networkFile = new Connector(
                ConnectorType.CONNECTOR_NETGENERATE).getOutputDir()
                + netgenerate_file + ".net.xml";

        // show light and vehicle editor after creating the map by mahmut on
        // 10/03/13
        LightEditor.getInstance().setVisible(true);
        //VehicleEditor.getInstance().setVisible(true);
        MainEditor.getInstance().runSimulation.setVisible(true);

    }
}