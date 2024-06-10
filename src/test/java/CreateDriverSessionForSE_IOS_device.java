import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class CreateDriverSessionForSE_IOS_device {
    public static AppiumDriver initializeDriverForSE(String platformName) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        cap.setCapability("platformName", platformName);
        // capability to wait for a new command before quit and end session, extend it if you need to debug somethog
        cap.setCapability("appium:newCommandTimeout", "60");

        switch(platformName){
            case "iOS":
                String appUrlIOS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "UIKitCatalog-iphonesimulator.app";
                cap.setCapability("appium:automationName", "XCUITest");
                cap.setCapability( "appium:app", appUrlIOS);
                cap.setCapability("appium:bundleId", "com.example.apple-samplecode.UICatalog");
                cap.setCapability("appium:udid", "37D273BC-0E0B-4E63-AB16-FF0ED4D1462A");
                return new IOSDriver(url, cap);
            default:
                throw new Exception("Invalid platform");
        }

    }


}
