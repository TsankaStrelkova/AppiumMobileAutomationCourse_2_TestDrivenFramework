import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class CreateDriverSession {
    public static void main(String[] args) throws Exception {
        //initializeDriver("Android");
        initializeDriver("iOS");
    }

    public static AppiumDriver initializeDriver(String platformName) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        cap.setCapability("platformName", platformName);
        // capability to wait for a new command before quit and end session, extend it if you need to debug somethog
        cap.setCapability("appium:newCommandTimeout", "60");

        switch(platformName){
            case "Android":
                String appUrlAndroid = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator +"app" + File.separator +"ApiDemos-debug.apk";
                cap.setCapability("appium:automationName", "UiAutomator2");
                cap.setCapability("appium:deviceName","Pixel_6_Pro_API_34");

                // if you open specific app package and app activity or in some cases when Appium can't retrieve
                // manifest.xml appPackage and appActivity
                //cap.setCapability("appium:appPackage", "io.appium.android.apis");
                //cap.setCapability("appium:appActivity", "io.appium.android.apis.ApiDemos");

                // if you open the app (usual case) and Appium is able to take appPackage and appActivity from manifest.xml
                cap.setCapability( "appium:app", appUrlAndroid);

                // capabilities needed for automatic launch of Android virtual device
                cap.setCapability("appium:avd", "Pixel_6_Pro_API_34");
                cap.setCapability("appium:avdReadyTimeout", 240000);
                cap.setCapability("appium:avdLaunchTimeout", 240000);

                return new AndroidDriver(url,cap);
            case "iOS":
                String appUrlIOS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "UIKitCatalog-iphonesimulator.app";
                cap.setCapability("appium:automationName", "XCUITest");
                cap.setCapability( "appium:app", appUrlIOS);
                cap.setCapability("appium:bundleId", "com.example.apple-samplecode.UICatalog");
                cap.setCapability("appium:deviceName", "iPhone 15");
                cap.setCapability("appium:udid", "A996BD59-545C-42F5-955B-A83D08384CE3");
                return new IOSDriver(url, cap);
            default:
                throw new Exception("Invalid platform");
        }

    }


}
