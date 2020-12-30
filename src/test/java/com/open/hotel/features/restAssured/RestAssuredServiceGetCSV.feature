
Feature: Get the user information and validate from gorest api site

  @RestAssuredServiceGetCSV
  Scenario: 101:Get the user information and validate user data
    Given Customer "GoRest" Read the data from CSV "GetUser.csv" for testcase ID "tC01"
    When Using Rest Assured I submit the JSON GET request with endpoint "/users/" with parameter "id"
    When I validate "krishna91" from "name" node in JSON response - JSON path "data.name"
    When I validate "Male" from "gender" node in JSON response - JSON path "data.gender"
    When I validate "krishna91@gmail.com" from "email" node in JSON response - JSON path "data.email"
    When I validate "Active" from "status" node in JSON response - JSON path "data.status"
    When I validate "1400" from "id" node in JSON response - JSON path "data.id"