package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;

public class NtpPage extends BasePage {

    private By designator = By.name("designator");
    private By rsddateField = By.name("rsd");
    private By sumbitButton = By.xpath("//img[@src='/SchedulesWeb/images/buttons/submit.gif']");

// Resettoing the commit date via UI
    public void resetCommitDate(String carrier, String version, String date) throws InterruptedException
        {
        input(designator, carrier);
        selectByValue(SchedulesPage.scheduleVersionDropdown, version);
        input(rsddateField, date);
        click(sumbitButton);
//        confirmAlert();
        Assert.assertEquals("Commit date reset is successful", getTextOfaElement(UpmPage.confirmation));
        Thread.sleep(5000);

        }
}
