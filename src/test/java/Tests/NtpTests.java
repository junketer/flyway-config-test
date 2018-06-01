package Tests;

import Pages.NtpPage;
import Pages.UpmPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

public class NtpTests {

    NtpPage ntpPage = new NtpPage();
    UpmPage upm = new UpmPage();

    @Then("^I should be able to reset commit date for \"([^\"]*)\" with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iShouldBeAbleToResetCommitDateForWithAnd(String arg0, String arg1, String arg2) throws Throwable
        {
        ntpPage.resetCommitDate(arg0, arg1, arg2);
        }

    @Then("^I should be able to initiate commit for \"([^\"]*)\" with \"([^\"]*)\" verifying\"([^\"]*)\"$")
    public void iShouldBeAbleToInitiateCommitForWithVerifying(String arg0, String arg1, String arg2) throws Throwable
        {
        upm.initCommitRsdCarrier(arg0, arg1, arg2);
        }
}
