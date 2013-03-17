package com.opentrafficsimulation.screen.report;

import com.opentrafficsimulation.gui.utility.AssetUtility;
import com.opentrafficsimulation.utility.data.DBConnector;
import java.awt.Dimension;
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

public class EmissionScreen extends JFrame {

    private String emissionFileName;
    
    public EmissionScreen(String fileName) {
        this.setTitle("Report - Open Street Simulation");
        this.setIconImage(new AssetUtility().getLogo());
        this.setPreferredSize(new Dimension(800, 600));
        this.emissionFileName = fileName;
        init(new EmissionReport());
    }
    private static final long serialVersionUID = -4520562052355452085L;
    public String query = "";

    public void init(EmissionReport tripReport) {


        List<EmissionInfo> l = new EmissionReport().readEmissionReport(emissionFileName);
        query += "INSERT INTO emission (vid,eclass,co2,co,hc,nox,pmx) VALUES";
        for (int i = 0; i < 130; i++) {
            EmissionInfo item = l.get(i);
            query += "('" + item.id;
            query += "','" + item.eclass;
            query += "','" + item.co2;
            query += "','" + item.co;
            query += "','" + item.hc;
            query += "','" + item.nox;
            query += "','" + item.pmx;
           /*query += "','" + item.noise;
            query += "','" + item.route;
            query += "','" + item.type;
            query += "','" + item.waiting;
            query += "','" + item.lane;
            query += "','" + item.pos;
            query += "','" + item.speed;
            query += "','" + item.angle;
            query += "','" + item.x;
            query += "','" + item.y;*/

            if (i == 129) {
                query += "'); ";
            }
            else{
                query += "'), ";
            }
            
            
        }

        System.out.println(query);

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

        JTable table = new JTable(new EmissionTableModel(l));

        JTableHeader th = table.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue("Vehicle Id");
        tc = tcm.getColumn(1);
        tc.setHeaderValue("Eclass");
        tc = tcm.getColumn(2);
        tc.setHeaderValue("Co2");
        tc = tcm.getColumn(3);
        tc.setHeaderValue("Co");
        tc = tcm.getColumn(4);
        tc.setHeaderValue("HC");
        tc = tcm.getColumn(5);
        tc.setHeaderValue("NOX");
        tc = tcm.getColumn(6);
        tc.setHeaderValue("PMX");
        add(new JScrollPane(table));
        pack();
        setVisible(true);
    }
    public String[] columnNames = {"Time", "Id", "Eclass", "Co2", "Co", "HC",
        "Nox", "Pmx","Noise", "Route", "Type", "Waiting", "Lane","POS","Speed", "Angle", "X", "Y"};


}

