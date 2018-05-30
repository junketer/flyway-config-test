package Utils;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class WebConnector extends BasePage {

    public static WebDriver driver;
    public static String browser = "ie";

    //Initialise the driver/browser
    public static WebDriver currentDriver()
        {

        if (driver == null && browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (driver == null && browser.equalsIgnoreCase("ie")) {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.introduceFlakinessByIgnoringSecurityDomains();
            options.setCapability("ignoreZoomSetting", true);
            options.setCapability("nativeEvents", false);
            options.setCapability("unexpectedAlertBehaviour", "accept");
            options.setCapability("ignoreProtectedModeSettings", true);

//            options.requireWindowFocus();
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
            driver = new InternetExplorerDriver(options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        return driver;
        }

    //Quit the browser after testrunner
    public static void quit()
        {

        currentDriver().quit();
        driver = null;
        }
}
