package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@SuppressWarnings("ALL")
public class SchedulesPage extends BasePage {

    //    Buttons
     private By filterButton = By.xpath("//*[@id='scrollArea']/table/tbody/tr[2]/td[6]/a/img");
    private By menuHeader = By.className("MenuHeader");
    private By schedulesLeftHandMenu = By.className("MenuHeader");
    final String TOPBARFRAMENAME = "topbar";
    final String CARRIERSCHEDULESELECTORFRAME = "ApplicationFrameSet";
    public static By footerAtTheEndofSearchResults = By.cssSelector(".WorkAreaFooter>td");
    public static By searchResultsarea = By.id("scrollArea");
    public static By flightResults = By.cssSelector(".txt>tbody>tr>td>div>a");
    private By rsdLevel = By.name("releaseDate");
    private By currentMenu = By.cssSelector(".WorkTitle>td>strong");
    public static String carrierChoosen;


    //    inputs
    private By carrierSelectorField = By.name("code");

    //Dropdowns
    public static By scheduleVersionDropdown = By.name("scheduleVersion");



    //Select a carrier by name
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
        carrierName(carrier);
        selectScheduleVersion(version);
        selectRsdlevel(rsd);
        clickFilterButton();
        isFooterPresent();
        if (getTextOfaElement(currentMenu).equalsIgnoreCase("Carrier Schedules Selection")) {
            click(By.cssSelector(".txt1>a"));
        }
        verifyValuesInAList(searchResultsarea, flightResults, carrier);
        this.carrierChoosen = carrier;
        System.out.println(carrierChoosen + "IS THE CARRIER CHOSEN");


        }

}
