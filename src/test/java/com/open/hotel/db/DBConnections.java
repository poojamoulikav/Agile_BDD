package com.open.hotel.db;

import com.open.hotel.config.Config;
import com.open.hotel.security.Security;

import java.sql.*;

public class DBConnections {

    public ResultSet selectQuery(String query) throws SQLException {
        ResultSet rs = null;
        String hostName = Config.properties.getProperty("HostName");
        String port = Config.properties.getProperty("Port");
        String service = Config.properties.getProperty("Service");
        String username = Config.properties.getProperty("UserName");
        String password = Config.properties.getProperty("Password");
        Security security = new Security();
        String pwd = security.decryptPassword(password);
        String connectionString = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + service;
        System.out.println(connectionString);
        Connection con = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(connectionString, username, pwd);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e);
            new RuntimeException("Not able to connect to database");
        }
        //con.close();
        return rs;
    }

    public void insertQuery(String query) {
        String hostName = Config.properties.getProperty("HostName");
        String port = Config.properties.getProperty("Port");
        String service = Config.properties.getProperty("Service");
        String username = Config.properties.getProperty("UserName");
        String password = Config.properties.getProperty("Password");
        Security security = new Security();
        String pwd = security.decryptPassword(password);
        String connectionString = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + service;
        System.out.println(connectionString);

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(connectionString, username, pwd);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            new RuntimeException("Not able to connect to database");
        }
    }
}
