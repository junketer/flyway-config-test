@iteneraryedit,@commit

Feature: As a schedule suser
  I should be able to edit and save an itenerary

  @ite
  Scenario Outline: Click on save
    Given Iam logged in
    And Iam on "schedules" menu
    When I search for "<carrier>" with "<version>" with "Current Level Only" RSD
    And I  edit and  save each itenerary
    Then there should be no errors

    Examples:
      | carrier | version    |
      | ZZ      | Training 2 |
