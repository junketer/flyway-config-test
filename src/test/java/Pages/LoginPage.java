package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;

@SuppressWarnings("All")
public class LoginPage extends BasePage {

    private By signOutButton = By.cssSelector("img[src='images/buttons/signout.gif']");
    private String schedulesUrl = "http://uk01ut05wwutc/TopBar/login.do";
    private By usernameField = By.name("j_username");
    private By passwordField = By.name("j_password");
    private By signInButton =  By.xpath("//*[@src='images/buttons/signin.gif']");

//Navigateing to the schedules url
    public void naviagetToSchedules()
        {
        goTourl(schedulesUrl);
        Assert.assertEquals("http://uk01ut05wwutc/TopBar/login.do", getCuirrentUrl());
        }

//        Check if the user is logged in
    public boolean isUserLoggedin()
        {

        if (getCuirrentUrl().equals("\"http://uk01ut05wwutc/TopBar/\"")) {
            return true;
        }
        return false;
        }
//Do a default login
    public void defaultLogin()
        {


        login("Agauravelly", "Password88");

        }

//Login to the schedules
    public void login(String username, String password)
        {
        naviagetToSchedules();
        assertForAString(schedulesUrl, getCuirrentUrl());
        input(usernameField, username);
        input(passwordField, password);
                click(signInButton);
        expWaitGForUrltoChange("http://uk01ut05wwutc/TopBar/");
        Assert.assertEquals("http://uk01ut05wwutc/TopBar/", getCuirrentUrl());

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
