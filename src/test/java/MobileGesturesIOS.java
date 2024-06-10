import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MobileGesturesIOS {
    public static void main(String[] args) throws Exception {
        AppiumDriver driver = CreateDriverSessionForSE_IOS_device.initializeDriverForSE("iOS");

//        WebElement tableOnTheScreen = driver.findElement(AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeTable\""));
//        swipe(driver, tableOnTheScreen, "up");

//        swipe(driver, "up");

//        // Scroll down with direction strategy
//        scroll(driver,"down");

//        // Scroll down with size of element (for example table, list)
//        WebElement tableElement = driver.findElement(AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeTable\""));
//        scroll(driver,"down" , tableElement);
//
//        // Scroll to child named Toolbars
//        WebElement parentElement = driver.findElement(AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeTable\""));
//        scroll(driver,parentElement, "Toolbars");
//
//        // Scroll to child element with stringPredicate
//        scrollToChildWithPredicate(driver, parentElement,  "label == \"Alert Views\"");
//
//        // Scroll to element with accessibility id using toVisible strategy
//        scrollToElementWithAccessibilityID(driver, true, "Steppers");

//        // Zoom in then zoom out in Maps app
//        zoomMaps();

//        // In UICatalog go tap on Steppers
//        WebElement elementToTap = driver.findElement(AppiumBy.accessibilityId("Steppers"));
//        tap(driver, elementToTap,0,0);
//
//        // Touch and hold - in UICatalog go to Steppers and then touch and hold  on first + for 3 sec
//        WebElement elementToTouch = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Increment\"`][1]"));
//        touchAndHold(driver,elementToTouch, 3);

//
//        // Select picker wheel value 90 on Picker view
//        driver.findElement(AppiumBy.accessibilityId("Picker View")).click();
//        WebElement redColorPickerWheel = driver.findElement(AppiumBy.accessibilityId("Red color component value"));
//        int value = Integer.valueOf(redColorPickerWheel.getAttribute("value"));
//
//        while (value< 90) {
//            System.out.println("Value before select " + redColorPickerWheel.getAttribute("value"));
//            selectPickerWheel(driver, redColorPickerWheel, "next", 0.1f);
//            System.out.println("Value after select " + redColorPickerWheel.getAttribute("value"));
//            value = Integer.valueOf(redColorPickerWheel.getAttribute("value"));
//        }

        // Working with sliders
        // To move on a specific position we can use sendKeys and give as parameter values between 0 and 1
        driver.findElement(AppiumBy.accessibilityId("Sliders")).click();
        WebElement sliderOne = driver.findElement(AppiumBy.xpath("//XCUIElementTypeCell//XCUIElementTypeSlider"));
        sliderOne.sendKeys("0.9");
    }

    public static void zoomMaps() throws Exception {
        AppiumDriver driver = CreateDriverSessionForMapsApp.initializeDriver("iOS");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == \"Not Now\"`]"))).click();
        zoom(driver, 5, 2.2f);
        Thread.sleep(1000);
        System.out.println("Next is zoom out");
        WebElement areaToZoom = driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`name == \"ChromeViewController.Viewport\"`]"));
        //areaToZoom.click();
        zoom(driver, areaToZoom, 0.01f, -2.0f);

    }

    // All methods below are implemented using https://appium.readthedocs.io/en/latest/en/writing-running-appium/ios/ios-xctest-mobile-gestures/

    // Implementation of swipe using element (we swipe using the size of element). Sometimes when we use an element that is small (for example a cell in a list or table)
    // instead of swipe a click is performed
    // To avoid this effect please choose bigger elements as whole list container or table
    //This gesture performs a simple "swipe" gesture on the particular screen element or on the application element, which is usually the whole screen. This method does not accept coordnates and siply emulates single swipe with one finger. It might be useful for such cases like album pagination, switching views, etc. More advanced cases may require to call "mobile: dragFromToForDuration", where one can supply coordinates and duration.
    //
    //Supported arguments
    //direction: Either 'up', 'down', 'left' or 'right'. The parameter is mandatory
    //element: The internal element identifier (as hexadecimal hash string) to swipe on. Application element will be used instead if this parameter is not provided
    public static void swipe(AppiumDriver driver, WebElement element, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        params.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: swipe", params);
    }

    //This gesture performs a simple "swipe" gesture on the particular screen element or on the application element, which is usually the whole screen.
    // This method does not accept coordnates and siply emulates single swipe with one finger. It might be useful for such cases like album pagination, switching views, etc. More advanced cases may require to call "mobile: dragFromToForDuration", where one can supply coordinates and duration.
    //
    //Supported arguments
    //direction: Either 'up', 'down', 'left' or 'right'. The parameter is mandatory

    public static void swipe(AppiumDriver driver, String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        driver.executeScript("mobile: swipe", params);
    }

    // different strategies to scroll (direction, element, ...)

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

    // zoom with elementId, scale and velocity
    // for zoom out it is better to use non-mandatory elementId parameter that gives the area on which the operation is going to be performed
    // for zoom out scale should be 0-1 and velocity should be negative
    // scale: Pinch scale of type float. Use a scale between 0 and 1 to "pinch close" or zoom out and a scale greater than 1 to "pinch open" or zoom in. Mandatory parameter
    // velocity: The velocity of the pinch in scale factor per second (float value). Mandatory parameter
    public static void zoom(AppiumDriver driver, WebElement element, Float scale, Float velocity) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("scale", scale);
        params.put("velocity", velocity);
        driver.executeScript("mobile: pinch", params);
    }

    // zoom with scale and velocity - good for zoom in, sometimes doesn't work for zoom out
    // for zoom out it is better to include and elementId parameter that gives the area on which the operation is going to be performed
    // for zoom out scale should be 0-1 and velocity should be negative
    public static void zoom(AppiumDriver driver, float scale, float velocity) {
        Map<String, Object> params = new HashMap<>();
        params.put("scale", scale);
        params.put("velocity", velocity);
        driver.executeScript("mobile: pinch", params);
    }


    // Performs long press gesture on the given element or on the screen.
    //
    //Supported arguments
    //elementId: The internal element identifier (as hexadecimal hash string) to long tap on
    //duration: The float duration of press action in seconds. Mandatory patameter
    //x: Screen x long tap coordinate of type float. Mandatory parameter only if element is not set
    //y: Screen y long tap coordinate of type float. Mandatory parameter only if element is not set

    public static void touchAndHold(AppiumDriver driver, WebElement element, int duration) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("duration", duration);
        driver.executeScript("mobile: touchAndHold", params);
    }

    //Performs tap gesture by coordinates on the given element or on the screen.
    //
    //Supported arguments
    //element: The internal element identifier (as hexadecimal hash string) to long tap on. x and y tap coordinates will be calulated relatively to the current element position on the screen if this argument is provided. Otherwise they should be calculated relatively to screen borders.
    //x: x tap coordinate of type float. Mandatory parameter
    //y: y tap coordinate of type float. Mandatory parameter

    public static void tap(AppiumDriver driver, WebElement element, int x, int y) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) element).getId());
        params.put("x", 0);
        params.put("y", 0);
        driver.executeScript("mobile: tap", params);
    }

    //Performs selection of the next or previous picker wheel value. This might be useful if these values are populated dynamically, so you don't know which one to select or value selection does not work because of XCTest bug.
    //
    //Supported arguments
    //element: PickerWheel's internal element id (as hexadecimal hash string) to perform value selection on. The element must be of type XCUIElementTypePickerWheel. Mandatory parameter
    //order: Either next to select the value next to the current one from the target picker wheel or previous to select the previous one. Mandatory parameter
    //offset: The value in range [0.01, 0.5]. It defines how far from picker wheel's center the click should happen. The actual distance is calculated by multiplying this value to the actual picker wheel height. Too small offset value may not change the picker wheel value and too high value may cause the wheel to switch two or more values at once.
    // Usually the optimal value is located in range [0.15, 0.3]. 0.2 by default

    public static void selectPickerWheel(AppiumDriver driver, WebElement element, String order, float offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("offset", offset);
        params.put("elementId", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);
    }
}
