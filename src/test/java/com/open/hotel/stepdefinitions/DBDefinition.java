package com.open.hotel.stepdefinitions;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.db.DBConnections;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBDefinition {
	DBConnections dbConnections = new DBConnections();
	Assertions assertions = new Assertions();

	@Given("Insert data into the employee table {string} {string} {string} {string} {string}")
	public void insert_data_into_the_employee_table(String empID, String empName, String empAge, String empSalary, String empAddress) {
		String insertQuery = "Insert into EMPLOYEES (EMPID, EMPNAME, EMPAGE, EMPSALARY, EMPADDRESS) values ('"+ empID + "', '"+ empName + "', '"+ empAge + "', '" + empSalary + "', '" + empAddress + "')";
		dbConnections.insertQuery(insertQuery);
	}

	@Then("Validate employee information which is created in employee db table {string} {string} {string} {string} {string}")
	public void validate_employee_information_which_is_created_in_employee_db_table(String empID, String empName, String empAge, String empSalary, String empAddress) throws SQLException {
		String selectQuery = "Select * from EMPLOYEES where EMPID = '"+ empID + "'";
		ResultSet rs = dbConnections.selectQuery(selectQuery);
		ResultSetMetaData meta = rs.getMetaData();
		Map map = new HashMap();
		while (rs.next()) {
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i);
				String value = rs.getString(key);
				map.put(key, value);
			}
		}
		System.out.println(map);
		assertions.assertValues("EMPID", empID, map.get("EMPID").toString());
		assertions.assertValues("EMPNAME", empName, map.get("EMPNAME").toString());
		assertions.assertValues("EMPAGE", empAge, map.get("EMPAGE").toString());
		assertions.assertValues("EMPSALARY", empSalary, map.get("EMPSALARY").toString());
		assertions.assertValues("EMPADDRESS", empAddress, map.get("EMPADDRESS").toString());
	}
}