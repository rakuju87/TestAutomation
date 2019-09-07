package mobile.pageobjects;

import helpers.Page;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static logger.LoggingManager.logMessage;

public class HomePage extends Page {

    WebDriver driver;

    @FindBy(xpath = "//a[@title='Log In'][1]")
    @AndroidBy(id = "login_button")
    @iOSBy(xpath = "//XCUIElementTypeButton[contains(@label, 'Log In')]")
    private WebElement eleSignInBtn;

    @FindBy(xpath = "//a[@title='Get Started']")
    @AndroidBy(id = "create_site_button")
    @iOSBy(id = "Sign up for WordPress.com Button")
    private WebElement eleSignUpBtn;

    public HomePage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }
    public void chooseSignInOption() throws Exception {
        Assert.assertEquals(getText(eleSignInBtn), "LOG ON");
        if (isEnabled(eleSignInBtn))
           clickElement(eleSignInBtn);
        logMessage("Clicked on signIn button");
    }


}