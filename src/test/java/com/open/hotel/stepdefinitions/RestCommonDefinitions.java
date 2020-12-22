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

public class RestCommonDefinitions {
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    RestServices restServices = new RestServices();
    Assertions assertions = new Assertions();
    TableData tableData = new TableData();

    //Common for both Post and Get Request validations
    @When("I validate {string} from {string} node in JSON response - JSON path {string}")
    public void i_validate_from_node_in_JSON_response_JSON_path(String expectedValue, String nodeName, String jsonPath) {
        String response = VariableManager.getInstance().getVariables().getVar("response").toString();
        String actualValue = this.restServices.getValueFromJsonResponse(response, jsonPath);
        this.assertions.assertValues(nodeName, expectedValue, actualValue);

    }

    //Common for both Post and Get Request validations
    @When("I validate {string} node name in JSON response - JSON path {string}")
    public void i_validate_node_name_in_JSON_response_JSON_path(String nodeName, String jsonPath) {
        Map <Object, Object> data = (Map<Object, Object>) VariableManager.getInstance().getVariables().getVar("data");
        String expectedValue = data.get(nodeName).toString();
        String response = VariableManager.getInstance().getVariables().getVar("response").toString();
        String actualValue = this.restServices.getValueFromJsonResponse(response, jsonPath);
        this.assertions.assertValues(nodeName, expectedValue, actualValue);
    }

}
