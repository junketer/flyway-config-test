package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IteneraryPage extends BasePage {

    private By iteneraryResults = By.cssSelector(".txt1>a");
    private By saveButton = By.xpath("//img[@src='/SchedulesWeb/images/buttons/save.gif']");
    private By cancelButton = By.cssSelector("img[src='/SchedulesWeb/images/buttons/cancel.gif']");
    private By editButton = By.xpath("//img[@src='/SchedulesWeb/images/buttons/edit.gif']");
    private By completeButton = By.xpath("//img[@src='/SchedulesWeb/images/buttons/complete.gif']");

    //    Edit and Save on each itenerary
    public void editAndSaveItenerary() throws InterruptedException
        {
        waitForElementExplicitly(SchedulesPage.searchResultsarea);
        WebElement area = findElement(SchedulesPage.searchResultsarea);
        List<WebElement> flights = area.findElements(SchedulesPage.flightResults);
        for (int i = 1; i <= 10; i++) {
            area = findElement(SchedulesPage.searchResultsarea);
            flights = area.findElements(SchedulesPage.flightResults);
            System.out.println("Clicked the Flight number ****" + flights.get(i - 1).getText());
            waitForElementExplicitly(flights.get(i - 1));
            if(i>8){
                area.sendKeys(Keys.ARROW_DOWN);
            }
            flights.get(i - 1).click();
            editEachItenarary();
            if (i <10) {
                clickOnRouteSummary();
                waitForElementToBePresent(SchedulesPage.searchResultsarea);
            } else {

                waitForElementExplicitly(completeButton);
                isElementDisplayed(completeButton);
                click(completeButton);
            }
        }
        }

//        Click and edit each itenerary

    public void editEachItenarary() throws InterruptedException
        {

        waitForElementExplicitly(SchedulesPage.searchResultsarea);
        WebElement area = findElement(SchedulesPage.searchResultsarea);
        List<WebElement> itenaries = area.findElements(iteneraryResults);
//        for (int i = 1; i <= itenaries.size(); i++) {
//            area = findElement(SchedulesPage.searchResultsarea);
//            itenaries = area.findElements(iteneraryResults);
////            System.out.println(itenaries.get(i - 1).getText());
//            System.out.println("Clicked the itenary number ****" + itenaries.get(i - 1).getText());
            waitForElementExplicitly(itenaries.get(0));
            (itenaries.get(0)).click();
            clickOnEdit();
            clickSave();
            Thread.sleep(3000);

//        }

        }

    //    Click on the edit button
    public void clickOnEdit()
        {
        click(editButton);
        }

    //Click on the save button
    public void clickSave()
        {
        click(saveButton);
        }

//Click on Route Sumamry

    public void clickOnRouteSummary()
        {
        clickBreadCrumbsByName("route summary");

        }
}
