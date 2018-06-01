@regression,@reset

Feature: Commit Date Reset
  As a Schedules user
  I should be able to reset the commit date for a carrier and initiate commit

  @Rsdcarrier
  Scenario Outline: Reset commit date for a RSD carrier
    Given Iam logged in
    And Iam on "ntp7admin" menu
    When I click on "Commit Date Reset" menu
    Then I should be able to reset commit date for "<carrier>" with "<version>" and "<rsd date>"
    And Iam on "schedules" menu
    When I search for "<carrier>" with "<version>" with "Current Level Only" RSD
    And I  edit and  save each itenerary
#    When I click on "Update Process Monitor" menu
    When I click on "Work In Progress" menu
    Then I should be able to initiate commit for "<carrier>" with "<version>" verifying"<rsd date>"
    Examples:
      | carrier | version   | rsd date |
      | F9      | Schedules | 24MAY18  |



