package com.open.hotel.stepdefinitions;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.dataParsers.CSVData;
import com.open.hotel.dataParsers.ExcelData;
import com.open.hotel.dataParsers.TableData;
import com.open.hotel.services.Payload;
import com.open.hotel.services.RestServices;
import com.open.hotel.services.SoapServices;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoapDefinition {

    TableData tableData = new TableData();
    CSVData csvData = new CSVData();
    Payload payLoad = new Payload();
    RestServices restServices = new RestServices();
    ExcelData excelData = new ExcelData();
    Assertions assertions = new Assertions();
    SoapServices soapServices = new SoapServices();

    //////////////////////CSV XML////////////////////////////////////////////////////////////
    @Given("Customer {string} Read data from {string} CSV file with testcase ID {string} and Create XML request using XML template {string}")
    public void CSV_Create_XML_request(String customer, String csvFileName, String testCaseID, String template) {
        VariableManager.getInstance().getVariables().setVar("customerName", customer);
        VariableManager.getInstance().getVariables().setVar("csvFileName", csvFileName);
        Map<String, String> data = this.csvData.readData(csvFileName, testCaseID);
        String requestPayLoad = this.payLoad.payLoadPreparation(template, data);
        VariableManager.getInstance().getVariables().setVar("requestPayLoad", requestPayLoad);
    }

    @When("I submit the xml request")
    public void I_submit_the_xml_request(){
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        String requestPayLoad = VariableManager.getInstance().getVariables().getVar("requestPayLoad").toString();
        String response = this.soapServices.sendSoapPostRequest(requestPayLoad, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);

    }

    @Then("Validating tag {string} of {string} as {string} from XML Response")
    public void Validating_tag_of_as_from_XML_Response(String parentNode, String childNode, String expectedVal) {
        String response = VariableManager.getInstance().getVariables().getVar("response").toString();
        String actualValue = this.soapServices.xmlResponseAssertions(response, parentNode, childNode, expectedVal);
        this.assertions.assertValues(childNode, expectedVal, actualValue);
    }


    @Given("Customer {string} Read the data from excel {string} and sheet {string} for testcase ID {string} and Create the XML request using XML template {string}")
    public void Create_XML_request(String customerName, String excelFileName, String sheetName, String testCaseID, String template) throws IOException {
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        Map<String, String> data = this.excelData.readData(excelFileName, sheetName, "TestCaseid", testCaseID);
        String requestPayLoad = this.payLoad.payLoadPreparation(template, data);
        VariableManager.getInstance().getVariables().setVar("requestPayLoad", requestPayLoad);

    }

    ////Cucumber parameterization for XML ////////////////////////////////////
    @Given("Customer {string} Create XML request using XML template {string}")
    public void Create_XML_request_using_XML_template(String customerName, String template, DataTable dt) {
        HashMap<String, String> testData = this.tableData.convertDataTableValuesToList(dt);
        VariableManager.getInstance().getVariables().setVar("testData", testData);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        String requestPayLoad = this.payLoad.payLoadPreparation(template, testData);
        VariableManager.getInstance().getVariables().setVar("requestPayLoad", requestPayLoad);
    }
}
