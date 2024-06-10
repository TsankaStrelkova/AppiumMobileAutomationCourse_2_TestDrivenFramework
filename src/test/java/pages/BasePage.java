package pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.LoginTests;
import utils.DriverManager;
import utils.TestUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePage {
    // Logger needed for implementation of Log4j2 logging
    // See where it is used
    static Logger log = (Logger) LogManager.getLogger(LoginTests.class);

    public WebDriver getDriver() throws Exception {
        return DriverManager.getDriver();
    }

    public void waitForVisibility(WebElement e) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(WebElement e) {
        log.info("Click on element");
        try {
            waitForVisibility(e);
            e.click();
        } catch (Exception ex) {
            log.error("Not able to click due to:" + ex.getMessage());
        }
    }

    // The method above can be improved in the following matter
    // where in the message is mentioned understandable information about the element we are clicking on
    public void click(WebElement e, String message) {
        log.info("Click on " + message );
        try {
            waitForVisibility(e);
            e.click();
        } catch (Exception ex) {
            log.error("Not able to click due to:" + ex.getMessage());
        }
    }


    public void sendKeys(WebElement e, String txt) throws Exception {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public String getAttribute(WebElement e, String attributeName) throws Exception {
        waitForVisibility(e);
        return e.getAttribute(attributeName);
    }

    public String getText(WebElement e) throws Exception {
        waitForVisibility(e);
        String textInTheElement = null;
        if (DriverManager.getTestedPlatformName().equals("iOS")) {
            textInTheElement = getAttribute(e, "label");
        } else if (DriverManager.getTestedPlatformName().equals("Android")) {
            textInTheElement = getAttribute(e, "text");
        }
        return textInTheElement;
    }

    public boolean isElementDisplayed(WebElement e) throws Exception {
        return e.isDisplayed();
    }

    public void closeApp() throws Exception {
        switch (DriverManager.getTestedPlatformName()) {
            case "Android":
                String androidAppId = DriverManager.getProperty("androidAppPackage");
                ((InteractsWithApps) DriverManager.getDriver()).terminateApp(androidAppId);
                break;
            case "iOS":
                String iOSAppId = DriverManager.getProperty("iOSBundleId");
                ((InteractsWithApps) DriverManager.getDriver()).terminateApp(iOSAppId);
        }
    }

    public void launchApp() throws Exception {
        switch (DriverManager.getTestedPlatformName()) {
            case "Android":
                String androidAppId = DriverManager.getProperty("androidAppPackage");
                ((InteractsWithApps) DriverManager.getDriver()).activateApp(androidAppId);
                break;
            case "iOS":
                String iOSAppId = DriverManager.getProperty("iOSBundleId");
                ((InteractsWithApps) DriverManager.getDriver()).activateApp(iOSAppId);
        }
    }

    // Scroll methods for iOS
    // scroll with size of the page in given as parameter direction
    public static void scroll(AppiumDriver driver, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        driver.executeScript("mobile: scroll", params);
    }

    // scroll down with direction with the size of the given as parameter element (use elements that are big, as list, table)
    // here is used direction strategy + parent element
    public static void scroll(AppiumDriver driver, String direction, WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        params.put("elementID", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: scroll", params);
    }

    // scroll to the element with given name. Used strategy is name
    // name is the accessibility id of the child element, to which scrolling is performed.
    // The same result can be achieved by setting predicateString argument to 'name == accessibilityId'.
    // Has no effect if element is not a container
    public static void scroll(AppiumDriver driver, WebElement parentElement, String accessIdChild) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) parentElement).getId());
        params.put("name", accessIdChild);
        driver.executeScript("mobile: scroll", params);
    }

    // scroll to the element with given predicateString . Used strategy is name
    // predicateString: the NSPredicate locator of the child element,
    // to which the scrolling should be performed. Has no effect if element is not a container
    public static void scrollToChildWithPredicate(AppiumDriver driver, WebElement parentElement, String predicateString) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) parentElement).getId());
        params.put("predicateString", predicateString);
        driver.executeScript("mobile: scroll", params);
    }

    // scroll to element when we know its accessibility ID using strategy toVisible
    public static void scrollToElementWithAccessibilityID(AppiumDriver driver, Boolean visible, String accessibilityId) {
        WebElement element = driver.findElement(AppiumBy.accessibilityId(accessibilityId));
        Map<String, Object> params = new HashMap<>();
        params.put("toVisible", visible);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: scroll", params);
    }

    public static void scrollToElement(AppiumDriver driver, Boolean visible, WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("toVisible", visible);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: scroll", params);
    }

    // Scroll method for Android
    public static boolean scrollAndroid(AppiumDriver driver, WebElement element, String direction) {
        boolean canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 1.0
        ));

        return canScrollMore;
    }

    public WebElement scrollToElement(String text) throws Exception {
        return getDriver().findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().text(\"" + text + "\"));"));
    }


}
