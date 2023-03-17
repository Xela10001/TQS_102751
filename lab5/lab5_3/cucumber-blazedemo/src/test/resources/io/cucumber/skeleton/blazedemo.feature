Feature: Blazedemo Feature

  This feature tests the flight booking process on Blazedemo website.
  It ensures that the user can select the desired flight, fill in the passenger details, and complete the booking successfully.

  Scenario: Purchase a flight
    Given I am on the homepage
    When I select the departure city as 'Portland' and arrival city as 'Berlin'
    And I click on the Find Flights button
    And I select the flight 4 from the list
    And I fill in the passenger details
    And I confirm my booking
    Then I should see the confirmation page with the price 555 USD
    And I should be on the confirmation page
    And I close the browser
