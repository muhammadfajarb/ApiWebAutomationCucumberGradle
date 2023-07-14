@web
Feature: Checkout

  @positive-test
  Scenario: checkout single item
    Given user is on front page
    And user click login menu
    And user input username with "qwertytest123"
    And user input password with "123"
    And user click login button
    And user is on homepage
    And user go to cart page
    When user click place order button
    And place order form is displayed
    And fill the form with following information:
    | input_id  | value             |
    | name      | Muhammad Fajar B  |
    | country   | Indonesia         |
    | city      | Makassar          |
    | card      | 0123456789        |
    | month     | 01                |
    | year      | 2023              |
    And purchase button is clicked
    Then the transaction is successful