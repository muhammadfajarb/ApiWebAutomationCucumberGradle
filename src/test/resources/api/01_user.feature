@api
Feature: API User

  @positive-test
  Scenario: GET request to get list users
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    When a "GET" request is sent to "user"
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "getListUsersSchema.json"

  @boundary-test
  Scenario: GET request to check boundaries parameter max limit is 50 and max page is 999
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request query parameter is set to:
      | Key   | Value |
      | limit | 51    |
      | page  | 1000  |
    When a "GET" request is sent to "user"
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response body should contains:
      | Key   | Value |
      | limit | 50    |
      | page  | 999   |
    And the response should match the JSON schema "getListUsersSchema.json"

  @positive-test
  Scenario: POST request to create a new user
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request body is set to:
      | Key       | Value               |
      | title     | mr                  |
      | firstName | Muhammad            |
      | lastName  | Fajar B             |
      | email     | fajarb@example.com  |
      | picture   | https://img.freepik.com/premium-vector/account-icon-user-icon-vector-graphics_292645-552.jpg?w=200  |
    When a "POST" request is sent to "user/create"
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "postUserSchema.json"
    And the response body should contains:
      | Key       | Value               |
      | title     | mr                  |
      | firstName | Muhammad            |
      | lastName  | Fajar B             |
      | email     | fajarb@example.com  |
      | picture   | https://img.freepik.com/premium-vector/account-icon-user-icon-vector-graphics_292645-552.jpg?w=200  |
    And response body "id" is saved in variable "idUser" for next scenario testing

  Scenario: GET request to get single user
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    When a "GET" request is sent to "user/" with path parameter from "idUser" variable
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "getSingleUserSchema.json"

  @positive-test
  Scenario: POST request to create a new user (without title and picture)
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request body is set to:
      | Key       | Value                       |
      | firstName | Muhammad                    |
      | lastName  | Fajar B                     |
      | email     | muhammadfajarb@example.com  |
    When a "POST" request is sent to "user/create"
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "postUserWithoutTitleAndPictureSchema.json"
    And the response body should contains:
      | Key       | Value                       |
      | firstName | Muhammad                    |
      | lastName  | Fajar B                     |
      | email     | muhammadfajarb@example.com  |
    And response body "id" is saved in variable "idUser2" for next scenario testing

  @positive-test
  Scenario: PUT request to update user first and last name
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request body is set to:
      | Key       | Value |
      | firstName | Fajar |
      | lastName  | B     |
    When a "PUT" request is sent to "user/" with path parameter from "idUser" variable
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response body should contains:
      | Key       | Value |
      | firstName | Fajar |
      | lastName  | B     |
    And the response should match the JSON schema "putUserSchema.json"

  @negative-test
  Scenario: PUT request to check that email cannot be updated
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request body is set to:
      | Key       | Value             |
      | email     | change@email.com  |
    When a "PUT" request is sent to "user/" with path parameter from "idUser" variable
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response body should contains:
      | Key       | Value               |
      | email     | fajarb@example.com  |
    And the response should match the JSON schema "putUserSchema.json"

  @negative-test
  Scenario: POST request to create a new user with existing email
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    And the request body is set to:
      | Key       | Value               |
      | title     | mr                  |
      | firstName | Exist               |
      | lastName  | Email               |
      | email     | fajarb@example.com  |
    When a "POST" request is sent to "user/create"
    Then the status code is 400
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response body should contains:
      | Key         | Value               |
      | error       | BODY_NOT_VALID      |
      | data.email  | Email already used  |

  @positive-test
  Scenario Outline: DELETE request to delete user with specific id
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    When a "DELETE" request is sent to "user/" with path parameter from "<id_user>" variable
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "deleteUserSchema.json"
    Examples:
      | id_user |
      | idUser  |
      | idUser2 |
