package Tests;

import Utils.WebConnector;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:target/cucumber-html-report",
        "json:target/cucumber.json"},
        features = "src/test/resources",
        tags = {"@commit"}, monochrome = true)

public class TestRunner {

//    Closes the browser and all instances after all the test runs are complted or after executing test runner class.


        @AfterClass
    public static void quitBrowser()
        {
        WebConnector.quit();
        }

}
