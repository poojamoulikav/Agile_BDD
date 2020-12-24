
Feature: Convert temperature from celsius to Fahrenheit

  @SoapServicePostCSV
  Scenario: 101:Read data from csv and create XML request and send request and get response and validate it
    Given Customer "tempconvert" read the data from CSV "FahrenheitToCelsius.csv" from test case id "tc01" and create the XML request using XML request template "FahrenheitToCelsius"
    When I submit the XML Post request
    Then Validating tag "FahrenheitToCelsiusResponse" of "FahrenheitToCelsiusResult" as "51.1111111111111" from XML Response
