package Tests;

import Pages.TopBarPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class TopBarTests {

    TopBarPage tp = new TopBarPage();

    @And("^Iam on \"([^\"]*)\" menu$")
    public void iamOnMenu(String arg0) throws Throwable
        {
        switch (arg0.toLowerCase()) {
            case "schedules":
                tp.navigateToschedules();
                break;

            case "ntp7admin":
                tp.clickNtpAdmin();
                break;
        }
        }
}

