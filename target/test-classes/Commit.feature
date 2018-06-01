@regression
Feature: Initiate manual commit
  As a schedules user
  I should be able to initiate a commit manually for a carrier.


  @initcommit
  Scenario Outline:Initiate the commit
    Given Iam logged in
    And Iam on "schedules" menu
    When I search for "<carrier>" with "<version>" with "Current Level Only" RSD
    And I  edit and  save each itenerary
#    When I click on "Update Process Monitor" menu
    When I click on "Work In Progress" menu
    Then I should be able to initiate commit for "<carrier>" with "<version>"

    Examples:
      | carrier | version    |
      | ZZ      | Training 2 |
#      | F9      | Schedules  |

    
