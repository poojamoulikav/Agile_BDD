Feature: Get the user information and validate from gorest api site

  @RestServiceGetCSVIterations
  Scenario Outline: 101:Get the user information and validate user data
    Given Customer "GoRest" Read the data from CSV "GetUser.csv" for testcase ID "<TestcaseID>"
    When I submit the JSON GET request with endpoint "/users/" with parameter "id"
    When I validate "name" node name in JSON response - JSON path "data.name"
    When I validate "gender" node name in JSON response - JSON path "data.gender"
    When I validate "email" node name in JSON response - JSON path "data.email"
    When I validate "status" node name in JSON response - JSON path "data.status"
    When I validate "id" node name in JSON response - JSON path "data.id"
    Examples:
      | TestcaseID |
      | tc01       |
      | tc02       |