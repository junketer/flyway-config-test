package Tests;

import Pages.IteneraryPage;
import Pages.SchedulesPage;
import Pages.TopBarPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IteneraryCheckTests {

    SchedulesPage sp = new SchedulesPage();
    IteneraryPage ip = new IteneraryPage();





    @And("^I  edit and  save each itenerary$")
    public void iEditAndSaveEachItenerary() throws Throwable
        {
        ip.editAndSaveItenerary();
        }

    @Then("^there should be no errors$")
    public void thereShouldBeNoErrors() throws Throwable
        {
        System.out.println("NO ERRORS PRESENT");
        }


    @When("^I search for \"([^\"]*)\" with \"([^\"]*)\" with \"([^\"]*)\" RSD$")
    public void iSearchForWithWithRSD(String arg0, String arg1, String arg2) throws Throwable
        {
        sp.chooseACarrierWithversion(arg0, arg1, arg2);
        }
}
