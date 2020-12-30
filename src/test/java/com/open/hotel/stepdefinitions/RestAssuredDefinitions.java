package com.open.hotel.stepdefinitions;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.dataParsers.CSVData;
import com.open.hotel.dataParsers.TableData;
import com.open.hotel.services.Payload;
import com.open.hotel.services.RestAssuredServices;
import com.open.hotel.services.RestServices;
import com.open.hotel.threadVariables.VariableManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Map;

public class RestAssuredDefinitions {
    RestAssuredServices restAssuredServices = new RestAssuredServices();

     @When("Using Rest Assured I submit the JSON {string} request with end point {string}")
    public void using_rest_assured_i_submit_the_JSON_request_with_end_point(String requestMethod, String endPoint) {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        String request = VariableManager.getInstance().getVariables().getVar("request").toString();
        String response = this.restAssuredServices.getResponseFromPostMethodRestAssured(request, endPoint, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }

    @When("Using Rest Assured I submit the JSON GET request with endpoint {string} with parameter {string}")
    public void using_rest_assured_i_submit_the_JSON_GET_request_with_end_point_with_parameter(String endPoint, String parameterVariable) {
        String customerName = VariableManager.getInstance().getVariables().getVar("customerName").toString();
        Map <Object, Object> data = (Map<Object, Object>) VariableManager.getInstance().getVariables().getVar("data");
        String idValue = data.get(parameterVariable).toString();
        String fullEndpoint = endPoint + idValue;
        String response = this.restAssuredServices.getResponseFromGetMethodRestAssured(fullEndpoint, customerName);
        VariableManager.getInstance().getVariables().setVar("response", response);
    }
}
