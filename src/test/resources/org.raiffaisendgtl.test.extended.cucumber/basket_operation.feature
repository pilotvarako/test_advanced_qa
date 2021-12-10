Feature: Basket operations

  Scenario: Add and remove items from basket
    Given number of goods in the amount of 3 units
    When add items to basket
    And remove items from the basket
    Then operations completed correctly
