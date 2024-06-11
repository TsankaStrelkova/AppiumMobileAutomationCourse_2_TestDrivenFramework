package utils.deepLinks;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.properties.ConfigPropertyManager;

import java.time.Duration;
import java.util.HashMap;

public class DeepLinks {
    public static void OpenAppWith(String url) throws Exception {
        AppiumDriver driver = DriverManager.getDriver();

        String platformName = ConfigPropertyManager.getPlatformName();

        switch(platformName){
            case "Android":
                HashMap<String, String> deepUrl = new HashMap<>();
                deepUrl.put("url", url);
                deepUrl.put("package", "com.swaglabsmobileapp");
                driver.executeScript("mobile: deepLink", deepUrl);
                break;
            case "iOS":
                By urlBtn = AppiumBy.accessibilityId("CapsuleNavigationBar?isSelected=true");
                //         By urlFld = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' && name CONTAINS 'URL'");
                By openBtn = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");
                ((InteractsWithApps) driver).activateApp("com.apple.mobilesafari");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).click();
                // Uni code character for Enter button is "\uE007"
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).sendKeys("" + url + "\uE007");
                wait.until(ExpectedConditions.visibilityOfElementLocated(openBtn)).click();
                break;
        }
    }
}
