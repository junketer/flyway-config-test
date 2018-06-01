package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;

public class LeftHandMenuPage extends BasePage {

    private By lefthandMenuItems = By.cssSelector(".MenuItem>a>u");

    private By menuheader = By.className("navbartxt");

//    Click on a menu based on the link text

    public void clickOnAMenu(String menu) throws InterruptedException
        {

        clickOnALefthandMenuItem(lefthandMenuItems, menu);
        waitForElementToBePresent(menuheader);
        Thread.sleep(15000);
         Assert.assertTrue("User is not on the right menu", getTextOfaElement(menuheader).toLowerCase().contains(menu.toLowerCase()));
        }


}
