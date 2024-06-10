package utils;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.properties.ConfigPropertyManager;

import java.io.File;

public class CapabilitiesManager {
    public DesiredCapabilities getCaps() {
        try {
            StringBuilder appNameBuilder = new StringBuilder(System.getProperty("user.dir"))
                    .append(File.separator).append("src")
                    .append(File.separator).append("test")
                    .append(File.separator).append("resources")
                    .append(File.separator).append("app")
                    .append(File.separator);

            DesiredCapabilities caps = new DesiredCapabilities();

            // Android capabilities
            if (ConfigPropertyManager.isAndroid()) {
                caps.setCapability("appium:avd", ConfigPropertyManager.getAndroidAvdName());
                //caps.setCapability("appium:platformVersion", ConfigPropertyManager.getAndroidVersion());
                caps.setCapability("appium:adbExecTimeout", 500000);
                caps.setCapability("appium:avdLaunchTimeout", 500000);
                caps.setCapability("appium:newCommandTimeout", 240);
                //caps.setCapability("appium:noReset", false);
                //caps.setCapability("appium:fullReset", true);
                //caps.setCapability("appium:recreateChromeDriverSessions", true);
                caps.setCapability("appium:automationName", ConfigPropertyManager.getAndroidAutomationName());
                //caps.setCapability("appium:appPackage", ConfigPropertyManager.getAndroidAppPackage());
                caps.setCapability("appium:appWaitActivity", ConfigPropertyManager.getAndroidAppWaitActivity());
                caps.setCapability("appium:app", appNameBuilder.append("mda-2.0.1-22.apk").toString());
                caps.setCapability("appium:avdReadyTimeout", 240000);
                caps.setCapability("appium:avdLaunchTimeout", 240000);

                return caps;
            }

            // iOS capabilities
            caps.setCapability("platformName", ConfigPropertyManager.getPlatformName());
            caps.setCapability("appium:automationName", ConfigPropertyManager.get_iOSAutomationName());
            caps.setCapability("appium:deviceName", ConfigPropertyManager.get_iOSDeviceName());
            caps.setCapability("appium:platformVersion", ConfigPropertyManager.get_iOSPlatformVersion());
            caps.setCapability("appium:app", appNameBuilder.append("My Demo App.app").toString());
            caps.setCapability("appium:bundleId", ConfigPropertyManager.getBundleId());
            caps.setCapability("appium:fullReset", false);
            caps.setCapability("appium:wdaLaunchTimeout", 60000);
            // Capability to show logs
            if (ConfigPropertyManager.getShowXcodeLog()) {
                caps.setCapability("appium:showXcodeLog", "true");
            }
            return caps;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public DesiredCapabilities getCaps(String platformName, String deviceName) {
        try {
            StringBuilder appNameBuilder = new StringBuilder(System.getProperty("user.dir"))
                    .append(File.separator).append("src")
                    .append(File.separator).append("test")
                    .append(File.separator).append("resources")
                    .append(File.separator).append("app")
                    .append(File.separator);

            DesiredCapabilities caps = new DesiredCapabilities();

            // Android capabilities
            if (platformName.equals("Android")) {
                caps.setCapability("platformName", platformName);
                caps.setCapability("appium:deviceName", deviceName);
                caps.setCapability("appium:avd", deviceName);
                // capability to wait for a new command before quit and end session, extend it if you need to debug something
                caps.setCapability("appium:newCommandTimeout", "60");
                caps.setCapability("appium:automationName", ConfigPropertyManager.getAndroidAutomationName());
                caps.setCapability("appium:appWaitActivity", ConfigPropertyManager.getAndroidAppWaitActivity());

                caps.setCapability("appium:appWaitForLaunch", false);
                caps.setCapability("appium:avdReadyTimeout", 240000);
                caps.setCapability("appium:avdLaunchTimeout", 240000);
                caps.setCapability("appium:app", appNameBuilder.append("mda-2.0.1-22.apk").toString());


                return caps;
            } else {
                // iOS capabilities
                caps.setCapability("platformName", ConfigPropertyManager.getPlatformName());
                caps.setCapability("appium:automationName", ConfigPropertyManager.get_iOSAutomationName());
                //caps.setCapability("appium:deviceName", ConfigPropertyManager.get_iOSDeviceName());
                caps.setCapability("appium:deviceName", deviceName);
                caps.setCapability("appium:platformVersion", ConfigPropertyManager.get_iOSPlatformVersion());
                caps.setCapability("appium:app", appNameBuilder.append("My Demo App.app").toString());
                caps.setCapability("appium:bundleId", ConfigPropertyManager.getBundleId());
                caps.setCapability("appium:fullReset", false);
                caps.setCapability("appium:wdaLaunchTimeout", 60000);
                // Capability to show logs
                if (ConfigPropertyManager.getShowXcodeLog()) {
                    caps.setCapability("appium:showXcodeLog", "true");
                }
            }
            return caps;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
