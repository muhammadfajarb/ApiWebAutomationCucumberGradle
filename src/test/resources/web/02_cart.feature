@web
Feature: Cart

  @positive-test
  Scenario: Adding items to the cart
    Given user is on front page
    And user click login menu
    And user input username with "qwertytest123"
    And user input password with "123"
    And user click login button
    And user is on homepage
    When user click add to cart button on following items:
      | item_name         |
      | Samsung galaxy s6 |
      | Nokia lumia 1520  |
      | Sony xperia z5    |
    And user go to cart page
    Then cart should contain the following items:
      | item_name         |
      | Samsung galaxy s6 |
      | Nokia lumia 1520  |
      | Sony xperia z5    |
    And total price cart is correct
