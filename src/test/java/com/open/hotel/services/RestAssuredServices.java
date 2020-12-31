package com.open.hotel.services;

import com.open.hotel.config.Config;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.open.hotel.logger.LoggerClass;
import com.open.hotel.threadVariables.VariableManager;
import static com.jayway.restassured.RestAssured.given;

public class RestAssuredServices {

    org.apache.log4j.Logger log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), VariableManager.getInstance().getVariables().getVar("testCaseID").toString());

    public String getResponseFromGetMethodRestAssured(String endPoint, String customerName) {
        String responseString = null;
        try {
            String region = Config.properties.getProperty("Environment");
            String url = Config.properties.getProperty("EndPointURL_" + region + "_" + customerName);
            log.info("end point url - " + url + endPoint);
            RestAssured.baseURI= url;
            RequestSpecification specification = RestAssured.given();
            if (url != null){
                String header = Config.properties.getProperty("Header_" + region + "_" + customerName);
                String[] headerValues = header.split(";");
                for(int i = 0; i < headerValues.length; ++i) {
                    String[] values = headerValues[i].split(":");
                    specification.header(values[0],values[1]);
                }
            }
            Response response = specification.get(endPoint);
            int statusCode = response.getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed : HTTP error code :" +  statusCode);
            }
            responseString = response.body().asString();
            log.info("Response  - " + responseString);
        }catch(Exception var1) {
            log.info("response failed " + var1 + System.lineSeparator());
        }
        return responseString;
    }

    public String getResponseFromPostMethodRestAssured(String jsonRequest, String endPoint, String customerName) {
        String responseString = null;
        try {
            String region = Config.properties.getProperty("Environment");
            String url = Config.properties.getProperty("EndPointURL_" + region + "_" + customerName);
            log.info("end point url - " + url + endPoint);
            RequestSpecification specification = given();
            if (url != null){
                String header = Config.properties.getProperty("Header_" + region + "_" + customerName);
                String[] headerValues = header.split(";");
                for(int i = 0; i < headerValues.length; ++i) {
                    String[] values = headerValues[i].split(":");
                    specification.header(values[0],values[1]);
                }
            }
            Response response = given().spec(specification).body(jsonRequest).when().post(url + endPoint);

            int statusCode = response.getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed : HTTP error code :" +  statusCode);
            }
            responseString = response.asString();
            log.info("Response  - " + responseString);
        }catch(Exception var1) {
            log.info("response failed " + var1.getMessage() + System.lineSeparator());
        }
        return responseString;
    }

}
