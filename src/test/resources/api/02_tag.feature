@api
Feature: API Tag

  @positive-test
  Scenario: GET request to get list tags
    Given the request header is set to:
      | Key           | Value                     |
      | app-id        | 64a5752eaa283520ff3bac5a  |
      | Content-Type  | application/json          |
    When a "GET" request is sent to "tag"
    Then the status code is 200
    And the response header should contains:
      | Key           | Value                           |
      | Content-Type  | application/json; charset=utf-8 |
    And the response should match the JSON schema "getListTagsSchema.json"
