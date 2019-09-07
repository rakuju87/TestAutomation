package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.CucumberFeatureWrapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.io.IOException;
import static logger.LoggingManager.logMessage;
import drivers.AndroidDriverBuilder;
import appium.AppiumServer;
import drivers.IOSDriverBuilder;

@CucumberOptions(
        features = "src/test/mobile/features",
        glue = {"mobile/pageobjects"},
        tags = {"@mobileDemo"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
public class TestRunner {
    public static WebDriver driver;
    private TestNGCucumberRunner testNGCucumberRunner;
    @BeforeTest
    public void startAppiumServer() throws Exception {
            if (AppiumServer.appium == null || !AppiumServer.appium.isRunning()) {
                AppiumServer.start();
                logMessage("Appium server has been started");
            }
    }
    @AfterTest
    public void stopAppiumServer() throws Exception {
            if (AppiumServer.appium != null || AppiumServer.appium.isRunning()) {
                AppiumServer.stop();
                logMessage("Appium server has been stopped");
            }
    }
    @BeforeMethod
    public void setupMobileDriver() throws IOException {
            driver = new AndroidDriverBuilder().setupDriver("pixel");
        //Can add IOS driver in future
            //driver = new IOSDriverBuilder().setupDriver(model);
        logMessage("pixel" + " driver has been created for execution");
    }

    @AfterMethod
    public void teardownDriver() {
        driver.quit();
        logMessage("Driver has been quit from execution");
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}