package com.open.hotel.stepdefinitions;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.dataParsers.CSVData;
import com.open.hotel.services.Payload;
import com.open.hotel.services.RestServices;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Map;

public class RestDefinitions {
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    RestServices restServices = new RestServices();
    Assertions assertions = new Assertions();

    @Given("Customer {string} read the data from CSV {string} from test case id {string} and create the JSON request using JSON request template {string}")
    public void customer_read_the_data_from_CSV_from_test_case_id_and_creat_the_JSON_request_using_JSON_request_template(String customerName, String csvFileName, String testCaseID, String requestTemplate) {
        Map <String, String> data = this.csvData.readData(csvFileName, testCaseID);
        String request = this.payLoad.payLoadPreparation(requestTemplate, data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        VariableManager.getInstance().getVariables().setVar("request", request);
    }

    @When("I submit the JSON {string} request with end point {string}")
    public void i_submit_the_JSON_request_with_end_point(String requestMethod, String endPoint) {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        String request = VariableManager.getInstance().getVariables().getVar("request").toString();
        String response = this.restServices.getResponseFromPostMethod(request, endPoint, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }

    @When("I validate {string} from {string} node in JSON response - JSON path {string}")
    public void i_validate_from_node_in_JSON_response_JSON_path(String expectedValue, String nodeName, String jsonPath) {
       String response = VariableManager.getInstance().getVariables().getVar("response").toString();
       String actualValue = this.restServices.getValueFromJsonResponse(response, jsonPath);
       this.assertions.assertValues(nodeName, expectedValue, actualValue);

    }
}
