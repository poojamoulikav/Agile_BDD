Feature: send xml request and get response

  @SoapApiExcelPostRequest
  Scenario: 103:send xml request for scenario 1 and get response
    Given Customer "tempconvert" Read the data from excel "TestData.xlsx" and sheet "Fahrenheit" for testcase ID "TC01" and Create the XML request using XML template "FharenheitToCensius_Request"
    When I submit the xml request
    Then Validating tag "FahrenheitToCelsiusResponse" of "FahrenheitToCelsiusResult" as "10" from XML Response
