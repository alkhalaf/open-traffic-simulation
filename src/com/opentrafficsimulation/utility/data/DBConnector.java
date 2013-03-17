package com.opentrafficsimulation.utility.data;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    public DBConnector() {
        
    }
    public Connection con = null;

    public Connection connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql2.freesqldatabase.com/sql24688", "sql24688", "vZ4*gH7%");

            if (con == null) {
                System.out.println("Connection counld not be esstablished");
            } else {
                System.out.println("Connection established");
            }
            return con;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error" + e);
        }
        return null;
    }
    /*private static DBConnector connector = new DBConnector();

    public static DBConnector getInstance() {
        return connector;

    }*/
    
}
