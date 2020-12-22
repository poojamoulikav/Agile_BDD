package com.open.hotel.stepdefinitions;

import com.open.hotel.dataParsers.ExcelData;
import com.open.hotel.services.Payload;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.Map;

public class RestPostAndGetExcelDefinitions {

    ExcelData excelData = new ExcelData();
    Payload payLoad = new Payload();


    //Post Request
    @Given("Customer {string} read the data from Excel {string} and sheet name {string} for test case id {string} and create the JSON request using JSON request template {string}")
    public void Customer_Read_Data_From_Excel_And_Prepare_Json_Request(String customerName, String excelFileName, String sheetName, String testCaseID, String requestTemplate) throws IOException {
        Map<String, String> data = this.excelData.readData(excelFileName, sheetName, "TestCaseID" , testCaseID);
        VariableManager.getInstance().getVariables().setVar("data", data);
        String request = this.payLoad.payLoadPreparation(requestTemplate, data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
        VariableManager.getInstance().getVariables().setVar("request", request);
    }

    //Get Request Method
    @Given("Customer {string} read the data from Excel {string} and sheet name {string} for test case id {string}")
    public void customer_read_the_data_from_CSV_from_test_case_id(String customerName, String excelFileName, String sheetName, String testCaseID) throws IOException {
        Map <String, String> data = this.excelData.readData(excelFileName, sheetName, "TestCaseID", testCaseID);
        VariableManager.getInstance().getVariables().setVar("data", data);
        VariableManager.getInstance().getVariables().setVar("customerName", customerName);
    }

}
