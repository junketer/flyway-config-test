package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;

public class TopBarPage extends BasePage {

    private By homeButton = By.name("Home");
    private By schedulesButton = By.name("schedules");
    private By referenceButton = By.name("reference");
    private By productsButton = By.name("products");
    private By staffActivityButton = By.name("staffActivity");
    private By ntpButton = By.name("NTP& admin");
    private By adminButton = By.name("Admin");

    final String TOPBARFRAMENAME = "topbar";
    final String CARRIERSCHEDULESELECTORFRAME = "ApplicationFrameSet";

    //    Switch to the top bar frame
    public void switchToTopBar()
        {

        switchToFrame(TOPBARFRAMENAME);
        }

//        Switch to the application frameset

    public void switchToApplicationFrameset()
        {

        switchToFrame(CARRIERSCHEDULESELECTORFRAME);

        }


    //        Click on Schedules button
    public void clickSchedules()
        {

        click(schedulesButton);
        Assert.assertTrue("User is still not on SChedules", findElement(schedulesButton).getAttribute("src").contains("schedules_on.gif"));
        switchbacfromFrame();
        switchToApplicationFrameset();

        }

    //        Verify if the user is on schedules menu
    public boolean isUseronSchedulesMenu()
        {
        switchToTopBar();
        if (findElement(schedulesButton).getAttribute("src").contains("schedules_off.gif")) {
            return false;
        } else
            return true;
        }

    //        Navigate to schedules menu
    public void navigateToschedules()
        {
        if (!isUseronSchedulesMenu()) {
            clickSchedules();

        }
        }


    //        Click on Home button
    public void clickHome()
        {
        switchToTopBar();
        click(homeButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }

    //        Click on Reference button
    public void clickReference()
        {
        switchToTopBar();
        click(referenceButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }
//        Click on Products button

    public void clickProducts()
        {
        switchToTopBar();
        click(productsButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }
//        Click on Staff Activity button

    public void clickStaffActivity()
        {
        switchToTopBar();
        click(staffActivityButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }
//        Click on NTP& admin  button

    public void clickNtpAdmin()
        {
        switchToTopBar();
        click(ntpButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }
//        Click on Admin button

    public void clickAdmin()
        {
        switchToTopBar();
        click(adminButton);
        switchbacfromFrame();
        switchToApplicationFrameset();

        }

}
