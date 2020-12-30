Feature: Connect to database and get value and then compare those values

  @InsertAndSelectDataInDB
  Scenario Outline: 101:Connect to database and get value and then compare those values
    Given Insert data into the employee table "<EMPID>" "<EMPNAME>" "<EMPAGE>" "<EMPSALARY>" "<EMPADDRESS>"
    Then Validate employee information which is created in employee db table "<EMPID>" "<EMPNAME>" "<EMPAGE>" "<EMPSALARY>" "<EMPADDRESS>"
    Examples:
      | EMPID | EMPNAME    | EMPAGE | EMPSALARY | EMPADDRESS |
      | 110   | Shashmith5 | 5      | 50000     | INDIA      |