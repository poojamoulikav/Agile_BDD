Feature: Create the user in a gorest api site

  @RestServicePostCSVIterations
  Scenario Outline: 101:Read data from csv and create JSON request and send request and get response and validate it
    Given Customer "GoRest" read the data from CSV "CreateUser.csv" from test case id "<TestcaseID>" and create the JSON request using JSON request template "goRest_CreateUser_Template"
    When I submit the JSON "POST" request with end point "/users/"
    When I validate "name" node name in JSON response - JSON path "data.name"
    When I validate "gender" node name in JSON response - JSON path "data.gender"
    When I validate "email" node name in JSON response - JSON path "data.email"
    When I validate "status" node name in JSON response - JSON path "data.status"
    Examples:
      | TestcaseID |
      | tc01       |
    #  | tc02       |
          #  | tc03       |
    #  | tc04       |
     # | tc05       |