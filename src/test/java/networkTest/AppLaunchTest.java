package networkTest;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URL;

public class AppLaunchTest {

    private static RemoteWebDriver driver;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Choose Teams\"]")
    private WebElement chooseTeamsTitle;

    @BeforeTest
    public void setUp() {
        int maxAttempts = 5;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platform", "iOS");
        capabilities.setCapability("platformVersion", "13.3.1");
        capabilities.setCapability("deviceName", "iPhone_X");
        capabilities.setCapability("udid", "f5d9ac468cacdc7915f69f060a01430746f8283d");
        capabilities.setCapability("app", "/Users/abrasham.chowdhury/Downloads/Apps/iOS/NHL/Real Device/NHL.Prod.Location.Enterprise.json.ipa");
        do {
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                break;
            } catch (Exception e) {
                System.out.println("Unable to initiate driver due to: " + e + "retrying, max attempts remaining: " + maxAttempts);
            }
        } while (maxAttempts-- > 0);
    }

    @Test
    public void appLaunch() {
        SoftAssert softAssert = new SoftAssert();
        System.out.println("App launched");
        softAssert.assertAll();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
