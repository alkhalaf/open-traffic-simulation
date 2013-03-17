package com.opentrafficsimulation.utility.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class queryExample {

    private queryExample(){
        
    }
    public void query() {

        /*Statement stmt;
        DBConnector db = new DBConnector();
        db.connector();

        String query = "SELECT * from vehicles";
        try {
            stmt = db.con.createStatement(); // here is the problem
            ResultSet rs = stmt.executeQuery(query);  // here is the problem 

            System.out.println("Database Data:");
            while (rs.next()) {
                String id = rs.getString("ID");
                String type = rs.getString("Name");
                String speed = rs.getString("Color");

                System.out.println("ID: " + id + "Name: " + type + "Color: " + speed);

            }
        } catch (SQLException ex) {
            ex.getMessage();
        }*/
    }
    private static queryExample example = new queryExample();

    public static queryExample getInstance() {
        return example;

    }
}
