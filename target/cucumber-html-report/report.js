$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("CommitDateReset.feature");
formatter.feature({
  "line": 3,
  "name": "Commit Date Reset",
  "description": "As a Schedules user\r\nI should be able to reset the commit date for a carrier and initiate commit",
  "id": "commit-date-reset",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@regression,"
    },
    {
      "line": 1,
      "name": "@reset"
    }
  ]
});
formatter.scenarioOutline({
  "line": 8,
  "name": "Reset commit date for a RSD carrier",
  "description": "",
  "id": "commit-date-reset;reset-commit-date-for-a-rsd-carrier",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 7,
      "name": "@Rsdcarrier"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "Iam logged in",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "Iam on \"ntp7admin\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "I click on \"Commit Date Reset\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I should be able to reset commit date for \"\u003ccarrier\u003e\" with \"\u003cversion\u003e\" and \"\u003crsd date\u003e\"",
  "keyword": "Then "
});
formatter.step({
  "line": 13,
  "name": "Iam on \"schedules\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "I search for \"\u003ccarrier\u003e\" with \"\u003cversion\u003e\" with \"Current Level Only\" RSD",
  "keyword": "When "
});
formatter.step({
  "line": 15,
  "name": "I  edit and  save each itenerary",
  "keyword": "And "
});
formatter.step({
  "comments": [
    {
      "line": 16,
      "value": "#    When I click on \"Update Process Monitor\" menu"
    }
  ],
  "line": 17,
  "name": "I click on \"Work In Progress\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "I should be able to initiate commit for \"\u003ccarrier\u003e\" with \"\u003cversion\u003e\" verifying\"\u003crsd date\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "line": 19,
  "name": "",
  "description": "",
  "id": "commit-date-reset;reset-commit-date-for-a-rsd-carrier;",
  "rows": [
    {
      "cells": [
        "carrier",
        "version",
        "rsd date"
      ],
      "line": 20,
      "id": "commit-date-reset;reset-commit-date-for-a-rsd-carrier;;1"
    },
    {
      "cells": [
        "F9",
        "Schedules",
        "24MAY18"
      ],
      "line": 21,
      "id": "commit-date-reset;reset-commit-date-for-a-rsd-carrier;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 21,
  "name": "Reset commit date for a RSD carrier",
  "description": "",
  "id": "commit-date-reset;reset-commit-date-for-a-rsd-carrier;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@reset"
    },
    {
      "line": 7,
      "name": "@Rsdcarrier"
    },
    {
      "line": 1,
      "name": "@regression,"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "Iam logged in",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "Iam on \"ntp7admin\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "I click on \"Commit Date Reset\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I should be able to reset commit date for \"F9\" with \"Schedules\" and \"24MAY18\"",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 13,
  "name": "Iam on \"schedules\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "I search for \"F9\" with \"Schedules\" with \"Current Level Only\" RSD",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 15,
  "name": "I  edit and  save each itenerary",
  "keyword": "And "
});
formatter.step({
  "comments": [
    {
      "line": 16,
      "value": "#    When I click on \"Update Process Monitor\" menu"
    }
  ],
  "line": 17,
  "name": "I click on \"Work In Progress\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "I should be able to initiate commit for \"F9\" with \"Schedules\" verifying\"24MAY18\"",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "Then "
});
formatter.match({
  "location": "LoginTests.iamLoggedIn()"
});
formatter.result({
  "duration": 7191872848,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ntp7admin",
      "offset": 8
    }
  ],
  "location": "TopBarTests.iamOnMenu(String)"
});
formatter.result({
  "duration": 7413667885,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Commit Date Reset",
      "offset": 12
    }
  ],
  "location": "Upmtests.iClickOnMenu(String)"
});
formatter.result({
  "duration": 15988069276,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "F9",
      "offset": 43
    },
    {
      "val": "Schedules",
      "offset": 53
    },
    {
      "val": "24MAY18",
      "offset": 69
    }
  ],
  "location": "NtpTests.iShouldBeAbleToResetCommitDateForWithAnd(String,String,String)"
});
formatter.result({
  "duration": 7209356448,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "schedules",
      "offset": 8
    }
  ],
  "location": "TopBarTests.iamOnMenu(String)"
});
formatter.result({
  "duration": 2154858192,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "F9",
      "offset": 14
    },
    {
      "val": "Schedules",
      "offset": 24
    },
    {
      "val": "Current Level Only",
      "offset": 41
    }
  ],
  "location": "IteneraryCheckTests.iSearchForWithWithRSD(String,String,String)"
});
formatter.result({
  "duration": 9469603176,
  "status": "passed"
});
formatter.match({
  "location": "IteneraryCheckTests.iEditAndSaveEachItenerary()"
});
formatter.result({
  "duration": 99528396542,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Work In Progress",
      "offset": 12
    }
  ],
  "location": "Upmtests.iClickOnMenu(String)"
});
formatter.result({
  "duration": 26228390716,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "F9",
      "offset": 41
    },
    {
      "val": "Schedules",
      "offset": 51
    },
    {
      "val": "24MAY18",
      "offset": 72
    }
  ],
  "location": "NtpTests.iShouldBeAbleToInitiateCommitForWithVerifying(String,String,String)"
});
formatter.result({
  "duration": 2345912631,
  "error_message": "java.lang.AssertionError: RSD date is not same\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat Pages.UpmPage.initCommitRsdCarrier(UpmPage.java:87)\r\n\tat Tests.NtpTests.iShouldBeAbleToInitiateCommitForWithVerifying(NtpTests.java:22)\r\n\tat ✽.Then I should be able to initiate commit for \"F9\" with \"Schedules\" verifying\"24MAY18\"(CommitDateReset.feature:18)\r\n",
  "status": "failed"
});
formatter.write("Failed Scenario: Reset commit date for a RSD carrier");
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 237494544,
  "status": "passed"
});
});