package Tests;

import Pages.LoginPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;

public class LoginTests {

    LoginPage loginPage = new LoginPage();

    @Given("^Iam logged in$")
    public void iamLoggedIn() throws Throwable
        {
        loginPage.loginIfUserisNotLoggedIn();
        }
}
