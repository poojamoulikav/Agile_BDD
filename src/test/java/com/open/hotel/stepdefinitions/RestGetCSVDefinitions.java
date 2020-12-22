package com.open.hotel.stepdefinitions;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.dataParsers.CSVData;
import com.open.hotel.dataParsers.TableData;
import com.open.hotel.services.Payload;
import com.open.hotel.services.RestServices;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Map;

public class RestGetCSVDefinitions {
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    RestServices restServices = new RestServices();
    Assertions assertions = new Assertions();
    TableData tableData = new TableData();

    //Get Request Method
    @Given("Customer {string} Read the data from CSV {string} for testcase ID {string}")
    public void customer_read_the_data_from_CSV_from_test_case_id(String customerName, String csvFileName, String testCaseID) {
        Map <String, String> data = this.csvData.readData(csvFileName, testCaseID);
        VariableManager.getInstance().getVariables().setVar("data", data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
    }

    //Get Request Method
    @When("I submit the JSON GET request with endpoint {string} with parameter {string}")
    public void i_submit_the_JSON_GET_request_with_end_point_with_parameter(String endPoint, String parameterVariable) {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        Map <Object, Object> data = (Map<Object, Object>) VariableManager.getInstance().getVariables().getVar("data");
        String idValue = data.get(parameterVariable).toString();
        String fullEndpoint = endPoint + idValue;
        String response = this.restServices.getResponseFromGetMethod(fullEndpoint, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }

    //Get Request Parameterization
    @Given("Customer {string} Read the data from given parameters")
    public void customer_read_the_data_from_test_case_id(String customerName, DataTable dt) {
        Map <String, String> data = this.tableData.convertDataTableValuesToList(dt);
        VariableManager.getInstance().getVariables().setVar("data", data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
    }
}
