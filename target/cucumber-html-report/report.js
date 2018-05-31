$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Commit.feature");
formatter.feature({
  "line": 2,
  "name": "Initiate manual commit",
  "description": "As a schedules user\r\nI should be able to initiate a commit manually for a carrier.",
  "id": "initiate-manual-commit",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@regression"
    }
  ]
});
formatter.scenarioOutline({
  "line": 8,
  "name": "Initiate the commit",
  "description": "",
  "id": "initiate-manual-commit;initiate-the-commit",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 7,
      "name": "@initcommit"
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
  "name": "Iam on \"schedules\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "I search for \"\u003ccarrier\u003e\" with \"\u003cversion\u003e\" with \"Current Level Only\" RSD",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I  edit and  save each itenerary",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "I click on \"Update Process Monitor\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "I click on \"Work In Progress\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 15,
  "name": "I should be able to initiate commit for \"\u003ccarrier\u003e\" with \"\u003cversion\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "line": 17,
  "name": "",
  "description": "",
  "id": "initiate-manual-commit;initiate-the-commit;",
  "rows": [
    {
      "cells": [
        "carrier",
        "version"
      ],
      "line": 18,
      "id": "initiate-manual-commit;initiate-the-commit;;1"
    },
    {
      "cells": [
        "ZZ",
        "Training 2"
      ],
      "line": 19,
      "id": "initiate-manual-commit;initiate-the-commit;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 19,
  "name": "Initiate the commit",
  "description": "",
  "id": "initiate-manual-commit;initiate-the-commit;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@regression"
    },
    {
      "line": 7,
      "name": "@initcommit"
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
  "name": "Iam on \"schedules\" menu",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "I search for \"ZZ\" with \"Training 2\" with \"Current Level Only\" RSD",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "I  edit and  save each itenerary",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "I click on \"Update Process Monitor\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "I click on \"Work In Progress\" menu",
  "keyword": "When "
});
formatter.step({
  "line": 15,
  "name": "I should be able to initiate commit for \"ZZ\" with \"Training 2\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "Then "
});
formatter.match({
  "location": "LoginTests.iamLoggedIn()"
});
formatter.result({
  "duration": 5597084249,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "schedules",
      "offset": 8
    }
  ],
  "location": "IteneraryCheckTests.iamOnMenu(String)"
});
formatter.result({
  "duration": 1724671980,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "ZZ",
      "offset": 14
    },
    {
      "val": "Training 2",
      "offset": 24
    },
    {
      "val": "Current Level Only",
      "offset": 42
    }
  ],
  "location": "IteneraryCheckTests.iSearchForWithWithRSD(String,String,String)"
});
formatter.result({
  "duration": 4305916011,
  "status": "passed"
});
formatter.match({
  "location": "IteneraryCheckTests.iEditAndSaveEachItenerary()"
});
formatter.result({
  "duration": 25261048729,
  "error_message": "org.openqa.selenium.JavascriptException: Error executing JavaScript\nBuild info: version: \u00273.11.0\u0027, revision: \u0027e59cfb3\u0027, time: \u00272018-03-11T20:26:55.152Z\u0027\nSystem info: host: \u0027UK03-101883\u0027, ip: \u002710.3.21.158\u0027, os.name: \u0027Windows 7\u0027, os.arch: \u0027amd64\u0027, os.version: \u00276.1\u0027, java.version: \u00271.8.0_171\u0027\nDriver info: org.openqa.selenium.ie.InternetExplorerDriver\nCapabilities {acceptInsecureCerts: false, browserName: internet explorer, browserVersion: 11, javascriptEnabled: true, pageLoadStrategy: normal, platform: WINDOWS, platformName: WINDOWS, proxy: Proxy(), se:ieOptions: {browserAttachTimeout: 0, elementScrollBehavior: 0, enablePersistentHover: true, ie.browserCommandLineSwitches: , ie.ensureCleanSession: false, ie.fileUploadDialogTimeout: 3000, ie.forceCreateProcessApi: false, ignoreProtectedModeSettings: true, ignoreZoomSetting: true, initialBrowserUrl: http://localhost:25054/, nativeEvents: true, requireWindowFocus: false}, setWindowRect: true, timeouts: {implicit: 0, pageLoad: 300000, script: 30000}, unhandledPromptBehavior: accept}\nSession ID: 9bdeba7a-0bb8-49cc-a994-e48eb32e2f4b\r\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\r\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\r\n\tat org.openqa.selenium.remote.http.W3CHttpResponseCodec.createException(W3CHttpResponseCodec.java:187)\r\n\tat org.openqa.selenium.remote.http.W3CHttpResponseCodec.decode(W3CHttpResponseCodec.java:122)\r\n\tat org.openqa.selenium.remote.http.W3CHttpResponseCodec.decode(W3CHttpResponseCodec.java:49)\r\n\tat org.openqa.selenium.remote.HttpCommandExecutor.execute(HttpCommandExecutor.java:158)\r\n\tat org.openqa.selenium.remote.service.DriverCommandExecutor.execute(DriverCommandExecutor.java:83)\r\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:545)\r\n\tat org.openqa.selenium.remote.RemoteWebElement.execute(RemoteWebElement.java:279)\r\n\tat org.openqa.selenium.remote.RemoteWebElement.isDisplayed(RemoteWebElement.java:320)\r\n\tat org.openqa.selenium.support.ui.ExpectedConditions.isInvisible(ExpectedConditions.java:1405)\r\n\tat org.openqa.selenium.support.ui.ExpectedConditions.access$400(ExpectedConditions.java:44)\r\n\tat org.openqa.selenium.support.ui.ExpectedConditions$46.apply(ExpectedConditions.java:1393)\r\n\tat org.openqa.selenium.support.ui.ExpectedConditions$46.apply(ExpectedConditions.java:1389)\r\n\tat org.openqa.selenium.support.ui.FluentWait.until(FluentWait.java:248)\r\n\tat Pages.BasePage.waitForElementToDissappear(BasePage.java:188)\r\n\tat Pages.BasePage.clickBreadCrumbsByName(BasePage.java:363)\r\n\tat Pages.IteneraryPage.clickOnRouteSummary(IteneraryPage.java:80)\r\n\tat Pages.IteneraryPage.editAndSaveItenerary(IteneraryPage.java:30)\r\n\tat Tests.IteneraryCheckTests.iEditAndSaveEachItenerary(IteneraryCheckTests.java:36)\r\n\tat ✽.And I  edit and  save each itenerary(Commit.feature:12)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "Update Process Monitor",
      "offset": 12
    }
  ],
  "location": "Upmtests.iClickOnMenu(String)"
});
formatter.result({
  "status": "skipped"
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
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "ZZ",
      "offset": 41
    },
    {
      "val": "Training 2",
      "offset": 51
    }
  ],
  "location": "Upmtests.iShouldBeAbleToInitiateCommitForWith(String,String)"
});
formatter.result({
  "status": "skipped"
});
formatter.write("Failed Scenario: Initiate the commit");
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 204490297,
  "status": "passed"
});
});