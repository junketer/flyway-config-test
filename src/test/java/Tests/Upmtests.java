package Tests;

import Pages.UpmPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Upmtests {

    UpmPage up = new UpmPage();

    @When("^I click on \"([^\"]*)\" menu$")
    public void iClickOnMenu(String arg0) throws Throwable
        {
        up.clickOnAMenu(arg0);
        }

    @Then("^I should be in the \"([^\"]*)\" screen$")
    public void iShouldBeInTheScreen(String arg0) throws Throwable
        {
        if (arg0.contains("Update Process")) {
            up.confirmInUpm(arg0);
        } else {
            if (arg0.contains("Work in Progress")) {
                up.confirmOnWipMenu(arg0);
            }
        }
        }


    @Then("^I should be able to initiate commit for \"([^\"]*)\" with \"([^\"]*)\"$")
    public void iShouldBeAbleToInitiateCommitForWith(String arg0, String arg1) throws Throwable
        {
        up.initiateCommit(arg0, arg1);

        }
}
