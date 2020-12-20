
Feature: Get the user information and validate from gorest api site

  @RestServiceGetCSV
  Scenario: 101:Get the user information and validate user data
    Given Customer "GoRest" Read the data from CSV "GetUser.csv" for testcase ID "TC01"
    When I submit the JSON GET request with endpoint "/users/" with parameter "ID"
    When I validate "krishna90" from "name" node in JSON response - JSON path "data.name"
    When I validate "Male" from "gender" node in JSON response - JSON path "data.gender"
    When I validate "krishna90@gmail.com" from "email" node in JSON response - JSON path "data.email"
    When I validate "Active" from "status" node in JSON response - JSON path "data.status"
    When I validate "1348" from "id" node in JSON response - JSON path "data.id"