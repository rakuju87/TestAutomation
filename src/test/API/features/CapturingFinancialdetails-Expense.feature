@APIFinanceDetails-Expense
Feature: CapturingFinancialdetails-Expense
  Background:Select the service, environment and schema
    Given I want to execute service "submitApplication" in URL http://localhost:9092/submitUHLApp with schema src/main/resources/RequestSchema.json

  Scenario Outline: Check Capturing Financial details - Expense mandatory field validations
  Then I remove element <ElementRemoved>
  When I post a request
  Then the status code is <statusCode>
  And response status value is <statusValue>
  Examples:
   | ElementRemoved           | statusCode | statusValue |
   | Nothing                  | 200        | SUCCESS     |
   | ExpenseDescription       | 200        | SUCCESS     |
   | IncurredBy               | 400        | ERROR       |
   | ExpenseAmount            | 400        | ERROR       |
   | ExpenseFrequency         | 400        | ERROR       |

  Scenario Outline: Check Capturing Financial details ExpenseDescription
    Then I modify element <Element> with value <value>
    When I post a request
    Then the status code is <statusCode>
    And response status value is <statusValue>
    Examples:
      | Element            | value             | statusCode | statusValue |
      | ExpenseDescription | RegularExpense    | 200        | SUCCESS     |
      | ExpenseDescription | Regular123123     | 200        | SUCCESS     |
      | ExpenseDescription | 3464614646455     | 200        | SUCCESS     |
      | ExpenseDescription | 123!@!@!@         | 400        | ERROR       |
      | ExpenseDescription |                   | 200        | SUCCESS     |

  Scenario Outline: Check Capturing Financial details Amount validations
    Then I modify element <Element> with value <value>
    When I post a request
    Then the status code is <statusCode>
    And response status value is <statusValue>

    Examples:
      | Element            | value              | statusCode | statusValue |
      | ExpenseAmount      | 987654321          | 200        | SUCCESS     |
      | ExpenseAmount      | 789879778.2        | 200        | SUCCESS     |
      | ExpenseAmount      | 1                  | 200        | SUCCESS     |
      | ExpenseAmount      | 999999999999999    | 200        | SUCCESS     |
      | ExpenseAmount      | 92345678912345126  | 400        | ERROR       |
      | ExpenseAmount      | 0.9                | 400        | ERROR       |
      | ExpenseAmount      | -13232             | 400        | ERROR       |
      | ExpenseAmount      | 87987987.99        | 400        | ERROR       |