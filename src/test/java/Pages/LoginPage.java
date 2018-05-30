package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;

@SuppressWarnings("All")
public class LoginPage extends BasePage {

    private By signOutButton = By.cssSelector("img[src='images/buttons/signout.gif']");
    //    private String schedulesUrl = "http://uk01ut05wwutc/TopBar/login.do";
    private By usernameField = By.name("j_username");
    private By passwordField = By.name("j_password");
    private By signInButton = By.xpath("//*[@src='images/buttons/signin.gif']");

    //Navigateing to the schedules url
    public void naviagetToSchedules()
        {
        goTourl(System.getProperty("baseUrl"));
        Assert.assertEquals(System.getProperty("baseUrl") + "login.do", getCuirrentUrl());
        }

    //        Check if the user is logged in
    public boolean isUserLoggedin()
        {

        if (getCuirrentUrl().equals(System.getProperty("baseUrl"))) {
            return true;
        }
        return false;
        }

    //Do a default login
    public void defaultLogin()
        {


        login(System.getProperty("username"), System.getProperty("password"));

        }

    //Login to the schedules
    public void login(String username, String password)
        {
        naviagetToSchedules();
        input(usernameField, username);
        input(passwordField, password);
        click(signInButton);
        expWaitGForUrltoChange(System.getProperty("baseUrl"));
        Assert.assertEquals(System.getProperty("baseUrl"), getCuirrentUrl());

        }

    //        Log in if the user is not logge din already
    public void loginIfUserisNotLoggedIn()
        {
        if (!isUserLoggedin()) {
            defaultLogin();
        } else {
            System.out.println("User is logge din already");
        }
        }


}
