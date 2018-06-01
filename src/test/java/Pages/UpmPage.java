package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@SuppressWarnings("All")
public class UpmPage extends BasePage {

    private By upmLink = By.linkText("Update Process Monitor");

    private By menuheaderCurrentlyIn = By.id("Overview");
    private By wipheader = By.cssSelector(".navbartxt>font");
    private By checkBoxToClick = By.cssSelector("#scrollArea>#printTable>tbody>tr:nth-child(6)>td:nth-child(9)>.txt1>tbody>tr>td>input");
    private By initiateCommitButton = By.xpath("//img[@title='Commit chosen Carriers']");
    public static By confirmation = By.id("errorsMessages");


    public void confirmInUpm(String exp)
        {
        Assert.assertTrue("User is not in the " + exp + "menu", getTextOfaElement(menuheaderCurrentlyIn).contains(exp));
        }


    public void confirmOnWipMenu(String exp)
        {
        Assert.assertTrue("User is not in the " + exp + "menu", getTextOfaElement(wipheader).contains(exp));
        }

//        Initiate commit for a non RSD carrier
    public void initiateCommit(String carrier, String version) throws InterruptedException
        {

        waitForElementToBePresent(SchedulesPage.searchResultsarea);
        List<WebElement> carriersInCommit = findElements(By.cssSelector(".dataRow>#colCarrier"));
        System.out.println(carriersInCommit.size() + "Number of carriers found ");
        List<WebElement> carrierVersion = findElements(By.cssSelector(".dataRow>#colVersion'"));
        for (int i = 0; i < carriersInCommit.size(); i++) {
            System.out.println(carriersInCommit.get(i).getText());
            System.out.println(carrierVersion.get(i).getText());
            if ((carriersInCommit.get(i).getText().startsWith(carrier)) && (carrierVersion.get(i).getText().equalsIgnoreCase(version))) {
                System.out.println("Found the carrier ** " + carriersInCommit.get(i).getText() + "With version **" + carrierVersion.get(i).getText() + "at the index of ***" + i);
                waitForElementToBePresent(By.cssSelector("tr[class=\"dataRow\"][valign=\"top\"]:nth-child(" + (i + 3) + ")>td:nth-child(9)>table>tbody>tr>td>input[name='checkingRequiredInd']"));
                WebElement chkBox = findElement(By.cssSelector("tr[class=\"dataRow\"][valign=\"top\"]:nth-child(" + (i + 3) + ")>td:nth-child(9)>table>tbody>tr>td>input[name='checkingRequiredInd']"));
                if (chkBox.isDisplayed() && (!chkBox.isSelected())) {
                    chkBox.click();
                    clickCommit();
                    break;

                }
                if (!chkBox.isDisplayed()) {
                    Assert.fail("There are changes to be completed hence checkbox is not available");
                } else {
                    Assert.fail("Carrier is not initiated for the Commit");
                }
            }


        }
        waitForElementToBePresent(confirmation);
        Assert.assertEquals("Commit started for: " + carrier + ".", getTextOfaElement(confirmation));

        }


//Initiating commit for a RSD carrie rby verifying the RSD date on hover of the check box

    public void initCommitRsdCarrier(String carrier, String version, String Rsd)
        {

        waitForElementToBePresent(SchedulesPage.searchResultsarea);
        List<WebElement> carriersInCommit = findElements(By.cssSelector(".dataRow>#colCarrier"));
        System.out.println(carriersInCommit.size() + "Number of carriers found ");
        List<WebElement> carrierVersion = findElements(By.cssSelector(".dataRow>#colVersion'"));
        for (int i = 0; i < carriersInCommit.size(); i++) {
            System.out.println(carriersInCommit.get(i).getText());
            System.out.println(carrierVersion.get(i).getText());
            if ((carriersInCommit.get(i).getText().startsWith(carrier)) && (carrierVersion.get(i).getText().equalsIgnoreCase(version))) {
                System.out.println("Found the carrier ** " + carriersInCommit.get(i).getText() + "With version **" + carrierVersion.get(i).getText() + "at the index of ***" + i);
                waitForElementToBePresent(By.cssSelector("tr[class=\"dataRow\"][valign=\"top\"]:nth-child(" + (i + 3) + ")>td:nth-child(9)>table>tbody>tr>td>input[name='checkingRequiredInd']"));
                WebElement chkBox = findElement(By.cssSelector("tr[class=\"dataRow\"][valign=\"top\"]:nth-child(" + (i + 3) + ")>td:nth-child(9)>table>tbody>tr>td>input[name='checkingRequiredInd']"));
                Assert.assertTrue("RSD date is not same", findElement(By.cssSelector("tr[class=\"dataRow\"][valign=\"top\"]:nth-child(" + (i + 3) + ")>td:nth-child(9)>table>tbody>tr>td>input[name='checkingRequiredInd']")).getAttribute("id").equalsIgnoreCase(Rsd));
                if (chkBox.isDisplayed() && (!chkBox.isSelected())) {
                    chkBox.click();
                    clickCommit();
                    break;

                }
                if (!chkBox.isDisplayed()) {
                    Assert.fail("There are changes to be completed hence checkbox is not available");
                } else {
                    Assert.fail("Carrier is not initiated for the Commit");
                }
            }


        }
        waitForElementToBePresent(confirmation);
        Assert.assertEquals("Commit started for: " + carrier + ".", getTextOfaElement(confirmation));

        }

    public void clickCommit()
        {

        click(initiateCommitButton);
        }


//    public void clickOnAMenu(String menu)
//        {
//
//        clickOnALefthandMenuItem(lefthandMenuItems, menu);
//        }
}
