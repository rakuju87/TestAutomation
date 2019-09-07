package mobile.stepdefs;

import cucumber.api.java.en.Given;
import mobile.pageobjects.HomePage;
import Runner.TestRunner;

public class HomePageSteps extends TestRunner{

    @Given("I click on login button")
    public void testSignInOption() throws Exception {
        HomePage homePage = new HomePage(driver);
        homePage.chooseSignInOption();
    }
}
