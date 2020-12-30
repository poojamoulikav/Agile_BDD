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

public class RestPostCSVDefinitions {
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    RestServices restServices = new RestServices();
    Assertions assertions = new Assertions();
    TableData tableData = new TableData();

    //Post Request
    @Given("Customer {string} read the data from CSV {string} from test case id {string} and create the JSON request using JSON request template {string}")
    public void customer_read_the_data_from_CSV_from_test_case_id_and_creat_the_JSON_request_using_JSON_request_template(String customerName, String csvFileName, String testCaseID, String requestTemplate) {
        Map <String, String> data = this.csvData.readData(csvFileName, testCaseID);
        VariableManager.getInstance().getVariables().setVar("data", data);
        String request = this.payLoad.payLoadPreparation(requestTemplate, data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        VariableManager.getInstance().getVariables().setVar("request", request);
    }

    //Post Request
    @When("I submit the JSON {string} request with end point {string}")
    public void i_submit_the_JSON_request_with_end_point(String requestMethod, String endPoint) {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        String request = VariableManager.getInstance().getVariables().getVar("request").toString();
        String response = this.restServices.getResponseFromPostMethod(request, endPoint, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }

    //Post Parametrization
    @Given("Customer {string} Create JSON request using JSON template {string} with parameters")
    public void customer_create_json_request_using_json_template_with_parameters(String customerName, String requestTemplate, DataTable dt) {
        Map <String, String> data = this.tableData.convertDataTableValuesToList(dt);
        VariableManager.getInstance().getVariables().setVar("data", data);
        String request = this.payLoad.payLoadPreparation(requestTemplate, data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        VariableManager.getInstance().getVariables().setVar("request", request);
    }
}