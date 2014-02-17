@persisted
Feature: Registration
  In order to create a user base
  As a new user
  I should be able to register for an account


  Scenario Outline: For Valid Users
    Given I am at the "registration" page
    When I fill in "#email" with "<email>"
    And I fill in "#username" with "<username>"
    And I fill in "#pass" with "<pass>"
    And I fill in "#pass-confirm" with "<pass-confirm>"
    And I click on "input.btn"
    Then I should see "Welcome to bitshelf.io"

  Examples:
    | email               | username  | pass     | pass-confirm |
    | testuser1@email.com | testuser1 | 12345678 | 12345678     | 
    | testuser2@thing.com | testuser2 | abcdefgh | abcdefgh     | 
    | testuser3@other.com | testuser3 | abcd1234 | abcd1234     | 

  Scenario Outline: For Invalid Users
    Given I am at the "registration" page
    When I fill in "Email" with "<email>"
    And I fill in "Username" with "<username>"
    And I fill in "Password" with "<pass>"
    And I fill in "Password Confirmation" with "<pass-confirm>"
    And I click on "Create Account"
    Then I should see errors

  Examples:
    | email               | username  | pass     | pass-confirm |
    | testuser1@email.com |           | 12345678 | 12345678     | 
    |                     | testuser2 | abcdefgh | abcdefgh     | 
    | testuser3@other.com | testuser3 | abcd1234 |              | 
    | testuser3@other.com | testuser3 | abcd1234 | 1234abcd     | 
