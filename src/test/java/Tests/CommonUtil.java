package Tests;

import Utils.WebConnector;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CommonUtil {

    //Take a screenshot if the Scenario is failed!

    @After()
    public void tearDown(Scenario scenario) throws InterruptedException
        {
        if (scenario.isFailed()) {
            scenario.write("Failed Scenario: " + scenario.getName());
            //             Take a screenshot
            final byte[] screenshot = ((TakesScreenshot) WebConnector.currentDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }

        }


    //    @After
//    public void quit()
//        {
//        WebConnector.currentDriver().quit();
//        }
}
