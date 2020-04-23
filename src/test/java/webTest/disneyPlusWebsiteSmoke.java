package webTest;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;

public class disneyPlusWebsiteSmoke {

    private static WebDriver driver;

    @BeforeTest
    public void setup() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "/Users/abrasham.chowdhury/Downloads/chromedriver");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("browserName","chrome");
        capabilities.setCapability("browserVersion","80.0.3987.132");
        capabilities.setCapability("platform","MAC");
        capabilities.setCapability("platformName","MAC");
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.get("https://www.disneyplus.com");
    }

    @Test
    public void launchDisneyWeb() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.disneyplus.com/");
        Thread.sleep(10000);
        System.out.println("Test in progress..");
        Thread.sleep(10000);
        System.out.println("Test in progress..");
        Thread.sleep(10000);
        System.out.println("Test finished");
        softAssert.assertAll();
    }

    @AfterTest
    public void tearDown() {
        if(driver != null) {
            driver.close();
            driver.quit();
        }
    }

}
