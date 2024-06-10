import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MobileGesturesAndroid {

    public static void main(String[] args) throws Exception {
//        AppiumDriver driver = CreateDriverSession.initializeDriver("Android");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//
//        // Click on Views
//        By views = AppiumBy.accessibilityId("Views");
//        WebElement viewsElement = driver.findElement(views);
//
//        System.out.println("Device width: " + getDeviceWidth(driver));
//        System.out.println("Device height: " + getDeviceHeight(driver));
//        int xCoordinateToViews = 10;
//        int yCoordinateToViews = 12*(getDeviceHeight(driver)-(getDeviceHeight(driver)/8))/14;
//
//        System.out.println("Approximate y of Views " + yCoordinateToViews);
//
//        //click(driver,viewsElement);
//        click(driver, xCoordinateToViews, yCoordinateToViews);
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Animation")));
//
//        // Click on Drag and Drop
//        By dragAndDrop = AppiumBy.accessibilityId("Drag and Drop");
//        WebElement dragAndDropElement = driver.findElement(dragAndDrop);
//        dragAndDropElement.click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='Views/Drag and Drop']")));
//
//        // long click on the first element
//        By elForLongClick = AppiumBy.id("io.appium.android.apis:id/drag_dot_1");
//        WebElement longClickElement = driver.findElement(elForLongClick);
//        longClick(driver,longClickElement,1000);
//
//        // Drag fist element , drop on the place where second is
//        By elToDrag = AppiumBy.id("io.appium.android.apis:id/drag_dot_1");
//        By secondElementWhereToDrop = AppiumBy.id("io.appium.android.apis:id/drag_dot_2");
//        WebElement elementToBeDragged = driver.findElement(elToDrag );
//
//        int xCoordinateOfSecond = driver.findElement(secondElementWhereToDrop).getLocation().getX();
//        int yCoordinateOfSecond = driver.findElement(secondElementWhereToDrop).getLocation().getY();
//        System.out.println("X coordinate of second " + xCoordinateOfSecond);
//        System.out.println("Y coordinate of second " + yCoordinateOfSecond);
//
//        int widthOfSecond = driver.findElement(secondElementWhereToDrop).getSize().getWidth();
//        int heightOfSecond = driver.findElement(secondElementWhereToDrop).getSize().getHeight();
//        System.out.println("Width of second " + widthOfSecond);
//        System.out.println("Height of second " + heightOfSecond);
//
//        int middleXCoordinateOfSecond = xCoordinateOfSecond + widthOfSecond/2;
//        int middleYCoordinateOfSecond = yCoordinateOfSecond + heightOfSecond/2;
//        System.out.println("Middle of second " + middleXCoordinateOfSecond  + " , " + middleYCoordinateOfSecond);
//
//        drag(driver, elementToBeDragged, middleXCoordinateOfSecond, middleYCoordinateOfSecond);
//        WebElement resultElement = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_result_text"));
//        System.out.println("The element is dragged and dropped " + resultElement.isDisplayed() + "with text " + resultElement.getText());

//        // Zoom in Zoom out
//        zoomInOutMapsApp();

//        // Swipe up in list
//        swipeOnList();

//        // Swipe on the left and then on the right in gallery) by dimension of gallery
//        swipeOnGallery();

//        // Scroll until bottom in Views
//        scrollUntilBottom();

        // Scroll on right then on left in Views -> Gallery --> Photos
        scrollByElementInGallery();
    }


    public static void zoomInOutMapsApp() throws Exception {
        AppiumDriver driver = CreateDriverSessionForMapsApp.initializeDriver("Android");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.Button[@text = 'SKIP' ]"))).click();

        Thread.sleep(2000);
        zoomIn(driver, 150, 150, 600, 600);
        Thread.sleep(2000);
        zoomOut(driver, 100, 100, 1500, 2000);
    }

    // method to swipe on List
    // In demo app click on View
    public static void swipeOnList() throws Exception {
        AppiumDriver driver = CreateDriverSession.initializeDriver("Android");

        // Click on Views
        By views = AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Views\"]");
        WebElement viewsElement = driver.findElement(views);
        viewsElement.click();

        // Swipe on list
        WebElement listOnTheViewsScreen = driver.findElement(AppiumBy.id("android:id/list"));
        swipe(driver, listOnTheViewsScreen, "up");

    }

    public static void swipeOnGallery() throws Exception {
        AppiumDriver driver = CreateDriverSession.initializeDriver("Android");

        // Click on Views
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        // Click on Gallery , then On Photos
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();


        // Swipe on left by first image in the gallery
//        WebElement element = driver.findElement(AppiumBy.
//                xpath("//*[@resource-id=\"io.appium.android.apis:id/gallery\"]/android.widget.ImageView[1]"));
//        swipe(driver, element, "left");

        // Swipe on the left by all images in the gallery,

        WebElement element = driver.findElement(AppiumBy.id("io.appium.android.apis:id/gallery"));
        swipe(driver, element, "left");
        swipe(driver, element, "right");

        driver.quit();

    }

    public static void scrollUntilBottom() throws Exception {
        AppiumDriver driver = CreateDriverSession.initializeDriver("Android");

        // Click on Views
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        // Scroll by element (by size of list element on Views screen) until end of the screen
        WebElement listOnTheViewsScreen = driver.findElement(AppiumBy.id("android:id/list"));

        boolean canScrollMore = true;
        while (canScrollMore) {
            canScrollMore = scroll(driver, listOnTheViewsScreen, "down");

        }
    }

    public static void scrollByElementInGallery() throws Exception {
        AppiumDriver driver = CreateDriverSession.initializeDriver("Android");

        // Click on Views
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        // Click on Gallery , then On Photos
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();

        // Scroll by gallery element (contains all images)
        WebElement element = driver.findElement(AppiumBy.id("io.appium.android.apis:id/gallery"));


        boolean canScrollMoreOnRight = true;
        while (canScrollMoreOnRight) {
            canScrollMoreOnRight = scroll(driver, element, "right");

        }

        boolean canScrollMoreOnLeft = true;
        while (canScrollMoreOnLeft) {
            canScrollMoreOnLeft = scroll(driver, element, "left");

        }

    }

    // Methods are written using https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
    // long click with element
    public static void longClick(AppiumDriver driver, WebElement element) {
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId()
        ));
    }

    // long click with element, duration
    public static void longClick(AppiumDriver driver, WebElement element, int dur) {
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(), "duration", dur));
    }

    // click method that can be used if driver.

    public static void click(AppiumDriver driver, WebElement element) {
        driver.executeScript("mobile: clickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
    }

    public static void click(AppiumDriver driver, int xCoordinate, int yCoordinate) {
        driver.executeScript("mobile: clickGesture", ImmutableMap.of("x", xCoordinate, "y", yCoordinate));
    }

    public static void drag(AppiumDriver driver, WebElement element, int xCoordinateToDrop, int yCoordinateToDrop) {
        driver.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", xCoordinateToDrop,
                "endY", yCoordinateToDrop
        ));
    }

    public static void zoomIn(AppiumDriver driver, WebElement element) {
        driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "percent", 0.75
        ));
    }


    public static void zoomIn(AppiumDriver driver, int leftCoordinate, int topCoordinate, int widthOfArea, int heightOfArea) {
        driver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
                "left", leftCoordinate,
                "top", topCoordinate,
                "width", widthOfArea,
                "height", heightOfArea,
                "percent", 0.75
        ));
    }

    public static void zoomOut(AppiumDriver driver, int leftCoordinate, int topCoordinate, int widthOfArea, int heightOfArea) {
        driver.executeScript("mobile: pinchCloseGesture", ImmutableMap.of(
                "left", leftCoordinate,
                "top", topCoordinate,
                "width", widthOfArea,
                "height", heightOfArea,
                "percent", 0.75
        ));
    }

    public static void swipe(AppiumDriver driver, WebElement element, String direction) {
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75
        ));
    }

    public static boolean scroll(AppiumDriver driver, WebElement element, String direction) {
        boolean canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 1.0
        ));

        return canScrollMore;
    }

    public static Dimension getDeviceDimension(AppiumDriver driver) {
        return driver.manage().window().getSize();
    }

    public static int getDeviceWidth(AppiumDriver driver) {
        return getDeviceDimension(driver).getWidth();
    }

    public static int getDeviceHeight(AppiumDriver driver) {
        return getDeviceDimension(driver).getHeight();
    }

}
