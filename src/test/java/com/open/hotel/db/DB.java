package com.open.hotel.db;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.sql.*;
class DB{
    public static void main(String args[]){

        int num = 6;
        int fact = 1;
        while (num>1) {
            fact = fact*num;
            num = num-1;
        }
        System.out.println (fact);
        do {
            if(num==0) {
                fact = 1;
            } else {
                fact = fact*num;
                num--;
            }
        } while (num>1);

        try{
//step1 load the driver class
//            Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("oracle.jdbc.OracleDriver");
//step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","root");

//step3 create the statement object
            Statement stmt=con.createStatement();

//step4 execute query
            ResultSet rs=stmt.executeQuery("SELECT * FROM EMPLOYEES");
  //          rs.next();
            while(rs.next()) {
                String empID = rs.getString("EMPID");
                String empName = rs.getString("EMPNAME");
                String empAge = rs.getString("EMPAGE");
                String empSalary = rs.getString("EMPSALARY");
                String empAddress = rs.getString("EMPADDRESS");
                System.out.println("empID: '" + empID + "' empName: '" + empName + "' empAge: '" + empAge + "' empSalary: '" + empSalary + "' empAddress: '" + empAddress + "'");
            }

//step5 close the connection object
            con.close();

        }catch(Exception e){
            System.out.println(e);}

    }
}
