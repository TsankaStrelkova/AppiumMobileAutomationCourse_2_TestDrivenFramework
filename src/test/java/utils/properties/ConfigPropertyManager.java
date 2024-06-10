package utils.properties;

public class ConfigPropertyManager extends BasePropertyManager {
    private static final ConfigPropertyManager instance;

    static {
        instance = new ConfigPropertyManager();
        instance.loadProperties("config.properties");
    }

    public static String getPlatformName() {
        return instance.getProperty("platformName");
    }

    public static void switchToAndroid() {
        instance.setProperty("platformName", "Android");
    }

    public static void switchToiOS() {
        instance.setProperty("platformName", "iOS");
    }

    public static Boolean isAndroid() {
        return "Android".equals(instance.getProperty("platformName"));
    }

    public static Boolean isIOS() {
        return "iOS".equals(instance.getProperty("platformName"));
    }

    public static Integer getExplicitWaitDuration() {
        return Integer.parseInt(instance.getProperty("explicitWait"));
    }

    public static String getAppiumURL(Integer port) {
        return instance.getProperty("appiumURL") + ":" + port;
    }

    public static String getAndroidAvdName() {
        return instance.getProperty("androidAvdName");
    }

    public static String getAndroidVersion() {
        return instance.getProperty("androidVersion");
    }

    public static String getAndroidAutomationName() {
        return instance.getProperty("androidAutomationName");
    }

    public static String getAndroidAppPackage() {
        return instance.getProperty("androidAppPackage");
    }

    public static String getAndroidAppActivity() {
        return instance.getProperty("androidAppActivity");
    }

    public static String getAndroidAppWaitActivity()  {
        return instance.getProperty("androidAppWaitActivity");
    }

    public static String get_iOSAutomationName() {
        return instance.getProperty("iOSAutomationName");
    }

    public static String get_iOSDeviceName() {
        return instance.getProperty("iOSDeviceName");
    }

    public static String get_iOSPlatformVersion() {
        return instance.getProperty("iOSPlatformVersion");
    }

    public static String getBundleId() {
        return instance.getProperty("bundleId");
    }

    public static Boolean getShowXcodeLog() {
        return "true".equals(instance.getProperty("showXcodeLog"));
    }

    public static Boolean getShowAppiumLogInTheConsole() {
        return "true".equals(instance.getProperty("showAppiumLogInTheConsole"));
    }

    public static String getAppiumLogLevel() {
        return instance.getProperty("appiumLogLevel");
    }
}
