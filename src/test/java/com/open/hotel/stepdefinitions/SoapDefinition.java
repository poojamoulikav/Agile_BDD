package com.open.hotel.stepdefinitions;

import com.open.hotel.dataParsers.CSVData;
import com.open.hotel.services.Payload;
import com.open.hotel.services.RestServices;
import com.open.hotel.services.SoapServices;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class SoapDefinition {
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    SoapServices soapServices = new SoapServices();

    @Given("Customer {string} read the data from CSV {string} from test case id {string} and create the XML request using XML request template {string}")
    public void customer_read_the_data_from_CSV_from_test_case_id_and_creat_the_XML_request_using_XML_request_template(String customerName, String csvFileName, String testCaseID, String requestTemplate) {
        Map<String, String> data = this.csvData.readData(csvFileName, testCaseID);
        VariableManager.getInstance().getVariables().setVar("data", data);
        String request = this.payLoad.payLoadPreparation(requestTemplate, data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        VariableManager.getInstance().getVariables().setVar("request", request);
    }

    @When("I submit the XML Post request")
    public void i_submit_the_XML_Post_Request() {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        String request = VariableManager.getInstance().getVariables().getVar("request").toString();
        String response = this.soapServices.getResponseFromPostMethod(request, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }

    @Then("Validating tag {string} of {string} as {string} from XML Response")
    public void Validating_tag_of_as_from_XML_Response(String parentNode, String childNode, String expectedVal) {
        this.soapServices.xmlResponseAssertions(parentNode, childNode, expectedVal);
    }
}
