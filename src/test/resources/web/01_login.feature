@web
Feature: Login

  @positive-test
  Scenario: Login using valid username and password
    Given user is on front page
    And user click login menu
    And user input username with "qwertytest123"
    And user input password with "123"
    When user click login button
    Then user is on homepage

  @negative-test
  Scenario: Login using invalid password
    Given user is on front page
    And user click login menu
    And user input username with "qwertytest123"
    And user input password with "wrongpassword"
    When user click login button
    Then user see error message "Wrong password."

  @negative-test
  Scenario: Login using invalid username
    Given user is on front page
    And user click login menu
    And user input username with "wrongusername"
    And user input password with "password"
    When user click login button
    Then user see error message "User does not exist."

  @boundary-test
  Scenario: Login with blank username or password
    Given user is on front page
    And user click login menu
    And user input username with ""
    And user input password with ""
    When user click login button
    Then user see error message "Please fill out Username and Password."