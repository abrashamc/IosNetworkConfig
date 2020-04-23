package networkTest;

import java.net.MalformedURLException;
import java.net.URL;
import com.google.common.collect.ImmutableMap;
import data.AppiumData;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

class ReconfigureWifiTest extends AppiumData {

    private static RemoteWebDriver driver;
    private final static String SETTINGS = "com.apple.Preferences";
    private final static By WiFI_SWITCH = By.xpath("//XCUIElementTypeSwitch[@name='Wi-Fi']");
    private final static String SELECTED_WIFI = "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeImage";
    private final static String WIFI_NETWORK_NAME = "King's Landing";
    private final static By WiFi = MobileBy.AccessibilityId("Wi-Fi");
    private final static By WiFi_2 = By.xpath("//XCUIElementTypeButton[@name=\"Wi-Fi\"]");
    private final static By SETTINGS_ICON = By.xpath("//*[@name='Home screen icons']//*[@name='Settings']");

    @BeforeTest
    public void setUp() {
        int maxAttempts = 5;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platform", "iOS");
        capabilities.setCapability("platformVersion", "13.3.1");
        capabilities.setCapability("deviceName", "iPhone_X");
        capabilities.setCapability("udid", "f5d9ac468cacdc7915f69f060a01430746f8283d");
//        capabilities.setCapability("wdaLocalPort", "8100");
//        capabilities.setCapability("automationName", "XCUITest");
//        capabilities.setCapability("launchTimeout", "20000");
//        capabilities.setCapability("clearSystemFiles", "true");
        capabilities.setCapability("app", SystemBundles.Settings.getBundleId());
        do {
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                break;
            } catch (Exception e) {
                System.out.println("Unable to initiate driver due to: " + e + "retrying, max attempts remaining: " + maxAttempts);
            }
        } while (maxAttempts-- > 0);
    }

    public void getWifiPage(int maxAttempts, int explicitWait) {
        WebDriverWait wait = new WebDriverWait(driver, explicitWait);
            do{
                try {
                    driver.findElement(WiFi_2).click();
                } catch (Exception e) {
                    System.out.println("Unable to click Wifi in Settings page due to: " + e + "\nTrying through Home Screen Quick Actions");
                    ImmutableMap<String, String> pressHome = ImmutableMap.of("name", "home");
                    ((JavascriptExecutor)driver).executeScript("mobile: pressButton", pressHome);
                    ((JavascriptExecutor)driver).executeScript("mobile: pressButton", pressHome);
                    WebElement settingsIcon = wait.until(ExpectedConditions.presenceOfElementLocated(SETTINGS_ICON));
                    ((JavascriptExecutor)driver).executeScript("mobile: touchAndHold", ImmutableMap.of(
                            "element", ((RemoteWebElement)settingsIcon).getId(),
                            "duration", 2.0
                    ));
                    driver.findElement(WiFi_2).click();
                }
            } while (!wait.until(ExpectedConditions.presenceOfElementLocated(WiFI_SWITCH)).isDisplayed() && --maxAttempts > 0);
    }

    public ButtonStatus getBtnStatus(int maxAttempts) {
        do {
            try {
                int buttonValue = Integer.parseInt(driver.findElement(WiFI_SWITCH).getAttribute(Attributes.value.toString()));
                if (buttonValue == 1) {
                    return ButtonStatus.ON;
                } else if (buttonValue == 0) {
                    return ButtonStatus.OFF;
                }
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Button status couldn't be fetched due to:\n" + e + "\nMax attempts remaining: " + maxAttempts);
               maxAttempts--;
            }
        } while (maxAttempts > 0);
        return ButtonStatus.INVALID;
    }

    public boolean checkIfWiFiSelected(String wifiName, int maxAttempts, int explicitWait) {
        WebDriverWait wait = new WebDriverWait(driver, explicitWait);
        boolean checkmarkFound = false;
        do {
            try {
                if ("checkmark".equalsIgnoreCase(wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(String.format(SELECTED_WIFI, wifiName)))).getAttribute(Attributes.name.toString()))){
                    checkmarkFound = true;
                    return true;
                }
            } catch (Exception e) {
                if (getBtnStatus(3) == ButtonStatus.OFF) {
                    System.out.println("Wifi switch is turned off, needs to be toggled on..");
                    break; // <-------- CHECK THIS
                } else if (getBtnStatus(3) == ButtonStatus.ON) {
                    System.out.println("Different wifi network selected, need to pick correct wifi");
                    break;
                } else {
                    System.out.println("Unable to verify 'checkmark' for '" + wifiName + "', due to:\n" + e +
                            " Re-verifying, max attempts remaining: " + maxAttempts);
                }
            }
        } while (maxAttempts-- > 0 || checkmarkFound);
        return false;
    }

    public void toggleWiFiButtonOnAndOff(String defaultWifiName, int maxAttempts) {
            System.out.println("Attempting to connect to '" + defaultWifiName + "' by toggling wifi button ON and OFF");
            do {
                System.out.println("Trying to switch off wifi");
                try {
                    driver.findElement(WiFI_SWITCH).click();
                    if (getBtnStatus(maxAttempts).equals(ButtonStatus.OFF)) {
                        System.out.println("Successfully turned OFF wifi");
                        break;
                    } else {
                        System.out.println("Failed to turn OFF wifi, maybe it was already OFF, retrying..");
                    }
                } catch (WebDriverException e_1) {
                    System.out.println("Unable to turn off wifi due to:\n" + e_1 + "\nTrying again, max attempts remaining: " + maxAttempts);
                }
            } while (maxAttempts-- > 0 && !getBtnStatus(maxAttempts).equals(ButtonStatus.OFF));

            System.out.println("Trying to turn ON wifi now");
            do {
                try {
                    driver.findElement(WiFI_SWITCH).click();
                    if (getBtnStatus(maxAttempts).equals(ButtonStatus.ON)) {
                        System.out.println("Successfully turned ON wifi");
                        break;
                    } else {
                        System.out.println("Failed to turn ON wifi. Trying again..");
                    }
                } catch (WebDriverException e_2) {
                    System.out.println("Unable to turn ON wifi due to:\n" + e_2 +
                            "\nTrying again, max attempts remaining: " + maxAttempts);
                }
            } while (maxAttempts-- > 0 && getBtnStatus(maxAttempts).equals(ButtonStatus.ON));
    }

    public void selectWifi(String wifiName, int maxAttempts, int explicitWait) {
        while (!checkIfWiFiSelected(wifiName, 2, 10) && maxAttempts-- > 0) {
                System.out.println(wifiName + " is not set as current wifi/locator not found");
                try {
                    WebDriverWait wait = new WebDriverWait(driver, explicitWait);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id(wifiName))).click();
                    break;
                } catch (Exception e_4) {
                    System.out.println(wifiName + " wifi could not be selected due to:\n" + e_4 +
                            "\nTrying again, max attempts remaining: " + maxAttempts);
                }
            }
        System.out.println(wifiName + " is set as current wifi");
    }

    @Test
    public void revertWifiNetwork() {
        getWifiPage(3, 30);
        if (checkIfWiFiSelected(WIFI_NETWORK_NAME, 2, 10)) {
            System.out.println(WIFI_NETWORK_NAME + " is preset network, script execution not necessary");
            throw new SkipException(WIFI_NETWORK_NAME + " is preset network, no need to execute script");
        }
        toggleWiFiButtonOnAndOff(WIFI_NETWORK_NAME, 5);
        selectWifi(WIFI_NETWORK_NAME, 3 ,30);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
