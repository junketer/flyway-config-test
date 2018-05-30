package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@SuppressWarnings("ALL")
public class SchedulesPage extends BasePage {

    //    Buttons
    private By schedulesButton = By.cssSelector("img[name='schedules']");
    private By filterButton = By.xpath("//*[@id='scrollArea']/table/tbody/tr[2]/td[6]/a/img");


    private By schedulesLeftHandMenu = By.className("MenuHeader");
    final String TOPBARFRAMENAME = "topbar";
    final String CARRIERSCHEDULESELECTORFRAME = "ApplicationFrameSet";
    public static By footerAtTheEndofSearchResults = By.cssSelector(".WorkAreaFooter>td");
    public static By searchResultsarea = By.id("scrollArea");
    public static By flightResults = By.cssSelector(".txt>tbody>tr>td>div>a");
    private By rsdLevel = By.name("releaseDate");
    private By currentMenu=By.cssSelector(".WorkTitle>td>strong");
    public static String carrierChoosen;


    //    inputs
    private By carrierSelectorField = By.name("code");

    //Dropdowns
    private By scheduleVersionDropdown = By.id("scheduleVersionNameId");
    LoginPage lp = new LoginPage();


//    Checks if the user is on the schedules menu

    public boolean isUseronSchedulestab()
        {
        lp.naviagetToSchedules();
        return isElementPresent(schedulesLeftHandMenu);
        }

//        Seitch to the frame topbar

    public void switchToTopBar()
        {

        switchToFrame(TOPBARFRAMENAME);
        }

//        Switch to the application frameset

    public void switchToApplicationFrameset()
        {

        switchToFrame(CARRIERSCHEDULESELECTORFRAME);

        }

    //        Click the schedules menu based on user login
    public void clickSchedules() throws InterruptedException
        {
        if (!isElementPresent(carrierSelectorField)) {
            switchbacfromFrame();
            switchToTopBar();
            Thread.sleep(5000);
            String src = findElement(schedulesButton).getAttribute("src");
            System.out.println("The status of the schedule stab is ************* " + src);
            if (src.contains("schedules_on.gif")&& (!isElementPresent(carrierSelectorField))) {
                click(schedulesButton);
                System.out.println("User is on schedules tab");
                click(schedulesButton);
            } else {

                Thread.sleep(2000);
                click(schedulesButton);
                Thread.sleep(5000);
                System.out.println(findElement(schedulesButton).getAttribute("src") + "IS THE STATUS OF THE SCHEDULES TAB");
                Assert.assertTrue(findElement(schedulesButton).getAttribute("src").contains("schedules_on.gif"));

            }


            switchbacfromFrame();
            switchToApplicationFrameset();
        }
        }

    //Select a carrie rby name
    public void carrierName(String carrier)
        {
        input(carrierSelectorField, carrier);

        }

    //Select a schedule version form the dropdown
    public void selectScheduleVersion(String version) throws InterruptedException
        {

        selectByValue(scheduleVersionDropdown, version);
        }

    public void selectRsdlevel(String rsd)
        {

        selectByValue(rsdLevel, rsd);
        }

    //        Verify if footer is present
    public void isFooterPresent()
        {
        waitForElementExplicitly(footerAtTheEndofSearchResults);
        isElementDisplayed(footerAtTheEndofSearchResults);
        }

    //        Click filter
    public void clickFilterButton()
        {
        click(filterButton);
        }

    //        Choose a carrier base don schedule version
    public void chooseACarrierWithversion(String carrier, String version, String rsd) throws InterruptedException
        {
        switchbacfromFrame();
        Thread.sleep(2000);
        switchToApplicationFrameset();
        carrierName(carrier);
        selectScheduleVersion(version);
        selectRsdlevel(rsd);
        clickFilterButton();
        isFooterPresent();
        if(getTextOfaElement(currentMenu).equalsIgnoreCase("Carrier Schedules Selection")){
            click(By.cssSelector(".txt1>a"));
        }
        verifyValuesInAList(searchResultsarea, flightResults, carrier);
        this.carrierChoosen = carrier;
        System.out.println(carrierChoosen + "IS THE CARRIER CHOSEN");


        }

}
