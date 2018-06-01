package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Utils.WebConnector.currentDriver;

@SuppressWarnings("ALL")
public class BasePage {


    //Open a url
    public void goTourl(String url)
        {

        currentDriver().get(url);
        }

    //    Get current url
    public String getCuirrentUrl()
        {

        return currentDriver().getCurrentUrl();
        }

    //        Navigate back
    public void gobackTopreviousPage()
        {
        currentDriver().navigate().back();
        }

    //    Find element on the page
    public WebElement findElement(By locator)
        {

        return currentDriver().findElement(locator);
        }

    //Find all elements on the page.
    public List<WebElement> findElements(By locator)
        {

        return currentDriver().findElements(locator);
        }

    //    Verify if the element is present
    public boolean isElementPresent(By locator)
        {

        if (findElements(locator).size() == 0) {
            return false;
        } else
            return true;
        }

    //        Is element displayed
    public boolean isElementDisplayed(By locator)
        {
        waitForElementToBePresent(locator);
        ;
        if (findElement(locator).isDisplayed()) {
            return true;
        } else
            return false;
        }

    //    click on an element
    public void click(By locator)
        {
        waitForElementExplicitly(locator);
        currentDriver().findElement(locator).click();
        }

    //        Clear a field of its text
    public void clearField(By locator)
        {
        currentDriver().findElement(locator).clear();
        }

    //Write to a field
    public void input(By locator, String text)
        {

        currentDriver().findElement(locator).sendKeys(text);
        }

    //    Get text of a field
    public String getTextOfaElement(By locator)
        {

        return currentDriver().findElement(locator).getText();

        }

    //        Tick the checkbox ONLY if it is not ticked.
    public void tickCheckboxIfUnticked(By locator)
        {

        WebElement checkbox = currentDriver().findElement(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        }

    //        asserting two objects are equal
    public void assertEqualCondition(String expected, String actual)
        {
        Assert.assertEquals(expected, actual);
        }

    //        Click by value
    public void clickFromList(String value)
        {
        List<WebElement> anchors = currentDriver().findElements(By.tagName("img"));
        System.out.println(anchors.size() + "Total numbe rof images found on the page is ***********");
        for (WebElement anchor : anchors) {
            //    System.out.println(anchor.getText());
            if (anchor.getAttribute("name").equalsIgnoreCase(value)) {
                anchor.click();
            }
        }
        }

    //        Click on a element from list
    public void clickFromList(By locator, By locator1, String value)
        {
        List<WebElement> options = findElement(locator).findElements(locator1);
        for (WebElement option : options) {
            System.out.println("The flights available are**************  " + option.getText());
            if (option.getText().equals(value)) {
                waitForElementExplicitly(option);
                option.click();
                break;
            }
        }
        }

    //        Verify each value in a list
    public void verifyValuesInAList(By locator, By locator1, String value)
        {
        WebElement area = findElement(locator);
        List<WebElement> anchors = area.findElements(locator1);
        for (WebElement anchor : anchors) {
            Assert.assertTrue(anchor.getText().contains(value));
        }


        }

    //        Explicit wait by locator
    public void waitForElementExplicitly(By locator)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        }

    //        Wait for the presenc eof an element
    public void waitForElementToBePresent(By locator)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }

    //Explicit wait by WebElement
    public void waitForElementExplicitly(WebElement element)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        }


    //        Wait for an element to be not present using webelement
    public void waitForElementToDissappear(WebElement element)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOf(element));
        }

    //        Wait for an element to be not present using locator
    public void waitForElementToDissappear(By locator)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }

    //        Wait for a frame to be available
    public void waitForFrameExplicitly(String frameName)
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        }

    //        Wait for the url to change

    public void expWaitGForUrltoChange(String url)
        {
        {
            WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
            wait.until(ExpectedConditions.urlToBe(url));
        }
        }

        public void waitFortextTobePresent(By locator,String text){
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,text.toLowerCase()));
        }

    //Switch to an frame
    public void switchToFrame(String frameName)
        {
        //        waitForFrameExplicitly(frameName);
        currentDriver().switchTo().frame(frameName);
        }

    //        Switch to main frame
    public void switchbacfromFrame()
        {
        currentDriver().switchTo().defaultContent();
        }


    //Select by visible text
    public void selectByValue(By locator, String tagname, String text) throws InterruptedException
        {
        List<WebElement> options = findElement(locator).findElements(By.tagName(tagname));
        for (WebElement option : options) {
            //            System.out.println(option.getText());
            Thread.sleep(1000);
            if (option.getText().contains(text)) {
                option.click();
                break;
            }

        }
        String selectedText = getSelectionChosenFromDropdown(locator);
        Assert.assertEquals(text, selectedText);

        }


    //        Select by visible text

    public void selectByValue(By locator, String value)
        {

        Select select = new Select(findElement(locator));
        select.selectByVisibleText(value);

        }

    //        Select by Index
    public void selectByIndex(By locator, int index)
        {
        Select select = new Select(findElement(locator));
        select.selectByIndex(index);
        }

    //        verify array list is in ascending order
    public void isAscending(ArrayList<String> al)
        {

        boolean sorted = true;
        for (int i = 1; i < al.size(); i++) {
            if (al.get(i - 1).compareTo(al.get(i)) > 0) {
                sorted = false;
            }
        }
        }
    // Get selected Text from a drop down after selection

    public String getSelectionChosenFromDropdown(By locator)
        {

        Select select = new Select(findElement(locator));
        return select.getFirstSelectedOption().getText().trim();


        }

    //        Use on IE for img tag with attribute src
    public void clickOnImage(String srcword) throws InterruptedException
        {

        List<WebElement> elements = currentDriver().findElements(By.tagName("img"));
        System.out.println("The number of elements found with in the iframe is " + elements.size());
        for (WebElement element : elements) {
            System.out.println(element.getAttribute("src"));

            Thread.sleep(1000);
            if (element.getAttribute("src").contains("srcword")) {
                waitForElementExplicitly(element);
                element.click();
                Thread.sleep(1000);
                break;
            }
        }
        }

    //        Insert today sdate
    public String pickTodaysDate()
        {

        DateFormat dateFormat = new SimpleDateFormat("ddMMMyy");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        System.out.println(date1);
        return date1;

        }

    //        Insert a date after a month
    public String dateAfterAMonth()
        {
        LocalDate currDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMMyy");
        currDate.format(format);
        return currDate.plusMonths(1).format(format).toString();

        }


    //            Assert for a condition
    public void assertForAString(String expStr, String actualStr)
        {
        Assert.assertEquals(expStr, actualStr);
        }

//        Click on each item in a list

    public void clickEachItemInaList(By locator, By locator1, String message)
        {
        waitForElementExplicitly(locator);
        WebElement area = findElement(locator);
        List<WebElement> result = area.findElements(locator1);

        for (int i = 1; i <= result.size(); i++) {
            area = findElement(locator);
            result = area.findElements(locator1);
            System.out.println(result.get(i - 1).getText());
            System.out.println(message + result.get(i - 1).getText());
            waitForElementExplicitly(result.get(i - 1));
            (result.get(i - 1)).click();
        }

        }

    //        Click on a link by text
    public void clickBreadCrumbsByName(String breadcrumb)
        {

        List<WebElement> breadCrumbs = findElements(By.tagName("a"));
        for (WebElement breadCrumb : breadCrumbs) {
            System.out.println(breadCrumb.getText());
            if (breadCrumb.getText().equalsIgnoreCase(breadcrumb)) {
                breadCrumb.click();
                waitForElementToDissappear(breadCrumb);
                break;
            }
        }
        }

//    Clicking on the left hand menu links on schedules tab

    public void clickOnALefthandMenuItem(By locator, String menu)
        {

        List<WebElement> sideMenuLinks = findElements(locator);
        for (WebElement eachmenu : sideMenuLinks) {
            if (eachmenu.getText().equalsIgnoreCase(menu)) {
                eachmenu.click();
                break;
            }
        }

        }

    //        Confirm a js alert
    public void confirmAlert()
        {
        WebDriverWait wait = new WebDriverWait(currentDriver(), 30);
        wait.until(ExpectedConditions.alertIsPresent());
        currentDriver().switchTo().alert().accept();
        }
}
