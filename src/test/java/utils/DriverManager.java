package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.properties.ConfigPropertyManager;

import static utils.AppiumServerManager.port;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverManager {
    protected static AppiumDriver driver;
    protected static Properties props;
    protected static String testedPlatformName;

    public static void initDriver() throws Exception {
        props = new Properties();
        String path = "src/test/resources/config.properties";
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        DesiredCapabilities cap = new DesiredCapabilities();
        URL url;
        try {
            // in case when appium server is started by the console
            //url = new URL(props.getProperty("appiumURL")+ ":" + AppiumServerManager.getPort());

            // in the case when appium server is programmatically started
            url = new URL(props.getProperty("appiumURL")+ ":" + AppiumServerManager.getPort());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        String platformName = props.getProperty("platformName");
        testedPlatformName = platformName;
        cap.setCapability("platformName", props.getProperty("platformName"));

        switch (platformName) {
            case "Android":
                // capability to wait for a new command before quit and end session, extend it if you need to debug something
                cap.setCapability("appium:newCommandTimeout", "60");

                String appUrlAndroid = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + props.getProperty("androidApp");
                cap.setCapability("appium:automationName", props.getProperty("androidAutomationName"));
                //cap.setCapability("appium:appPackage","com.saucelabs.mydemoapp.android");
                //cap.setCapability("appium:appActivity", props.getProperty("androidAppActivity"));
                cap.setCapability("appium:avd", props.getProperty("androidDeviceName"));
                cap.setCapability("appium:appWaitActivity", props.getProperty("androidAppWaitActivity"));
                //cap.setCapability("appium:deviceName", props.getProperty("androidDeviceName"));
                cap.setCapability("appium:app", appUrlAndroid);
                //cap.setCapability("appium:avd", deviceName);
                cap.setCapability("appium:avdReadyTimeout", 240000);
                cap.setCapability("appium:avdLaunchTimeout", 240000);


                driver = new AndroidDriver(url, cap);
                break;

            case "iOS":
                String appUrlIOS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "My Demo App.app";
                cap.setCapability("appium:automationName", props.getProperty("iOSAutomationName"));
                cap.setCapability("appium:app", appUrlIOS);
                cap.setCapability("appium:bundleId", props.getProperty("iOSBundleId"));
                cap.setCapability("appium:deviceName", props.getProperty("iOSDeviceName"));
                cap.setCapability("appium:udid", props.getProperty("iOSUdid"));
                driver = new IOSDriver(url, cap);
                break;
            default:
                throw new Exception("Invalid platform");
        }

    }

    public static void initDriver(String platformName, String deviceName) throws Exception {
        testedPlatformName = platformName;


        try {
            URL url = new URL(ConfigPropertyManager.getAppiumURL(port));
            if (platformName.equals("Android")) {
                driver = new AndroidDriver(url, new CapabilitiesManager().getCaps(platformName,deviceName));
                System.out.println("INITIALIZE DRIVER FOR ANDROID");
            } else {
                driver = new IOSDriver(url, new CapabilitiesManager().getCaps(platformName,deviceName));
                System.out.println("INITIALIZE DRIVER FOR IOS");
            }
        } catch (Exception e) {
            System.out.println("NOT ABLE TO INITIALIZE DRIVER " + e.getMessage());
        }

    }

        public static AppiumDriver getDriver() throws Exception {
        if (driver == null) initDriver();
        return driver;
    }


    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static String getTestedPlatformName() {
        return testedPlatformName;
    }

    public static String getProperty(String propertyKey) {
        return props.getProperty(propertyKey);
    }
}
