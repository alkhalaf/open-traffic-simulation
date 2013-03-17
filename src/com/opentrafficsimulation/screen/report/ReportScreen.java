package com.opentrafficsimulation.screen.report;

import com.opentrafficsimulation.gui.utility.AssetUtility;
import com.opentrafficsimulation.utility.data.DBConnector;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ReportScreen extends JFrame {

    private String fileName;
    public String[] columnNames = {"id", "depart", "departLane", "departPos", "departSpeed", "departDelay",
        "arrival", "arrivalLane"};

    public ReportScreen(String fileName) {
        this.setTitle("Report - Open Street Simulation");
        this.setIconImage(new AssetUtility().getLogo());
        this.setPreferredSize(new Dimension(800, 600));
        this.fileName = fileName;
        init(new TripReport());
    }
    private static final long serialVersionUID = -4520562052355452085L;
    public String query = "";

    public void init(TripReport tripReport) {

        /*List<TripInfo> l = new TripReport().readTripReport(fileName);
         JTable table = new JTable(new TripTableModel(l));*/

        List<TripInfo> l = new TripReport().readTripReport(fileName);


        query += "INSERT INTO vehicleTrips (vid,depart,departlane,departpos,departspeed,departdelay,arrival) VALUES";
        for (int i = 0; i < l.size(); i++) {
            TripInfo item = l.get(i);
            query += "('" + item.id;
            query += "','" + item.depart;
            query += "','" + item.departLane;
            query += "','" + item.departPos;
            query += "','" + item.departSpeed;
            query += "','" + item.departDelay;
            query += "','" + item.arrival;
            
            if (i == l.size()-1) {
                query += "'); ";
            }
            else{
                query += "'), ";
            }
            
            
        }

        //System.out.println(query);

        SwingWorker worker = new SwingWorker<String, Object>() {
            @Override
            public String doInBackground() throws SQLException {
                // DB Connection test
                System.out.println("inserting vehicle data to db");
                DBConnector db = new DBConnector();
                try {

                    db.connector();
                    Statement st = db.con.createStatement();
                    st.execute(query);

                    System.out.println("Data saved.");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    db.con.close();
                }
                return "running";
            }

            @Override
            public void done() {
                System.out.println("completed.");
            }
        };

        worker.execute();

        JTable table = new JTable(new TripTableModel(l));

        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue("id");
        tc = tcm.getColumn(1);
        tc.setHeaderValue("depart");
        tc = tcm.getColumn(2);
        tc.setHeaderValue("departLane");
        tc = tcm.getColumn(3);
        tc.setHeaderValue("departPos");
        tc = tcm.getColumn(4);
        tc.setHeaderValue("departSpeed");
        tc = tcm.getColumn(5);
        tc.setHeaderValue("departDelay");
        tc = tcm.getColumn(6);
        tc.setHeaderValue("arrival");
        add(new JScrollPane(table));
        pack();
        setVisible(true);
    }
    /*public static void main(String[] args) {
     new ReportScreen().init(new TripReport());
     }*/
}
