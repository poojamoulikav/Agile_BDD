
Feature: Create the user in a gorest api site

  @RestServiceGetExcel
  Scenario: 101:Read data from excel and create JSON request and send request and get response and validate it
    Given Customer "GoRest" read the data from Excel "TestData.xlsx" and sheet name "GetUser" for test case id "tc01"
    When I submit the JSON GET request with endpoint "/users/" with parameter "ID"
    When I validate "krishna94" from "name" node in JSON response - JSON path "data.name"
    When I validate "Male" from "gender" node in JSON response - JSON path "data.gender"
    When I validate "krishna94@gorest.in" from "email" node in JSON response - JSON path "data.email"
    When I validate "Active" from "status" node in JSON response - JSON path "data.status"
