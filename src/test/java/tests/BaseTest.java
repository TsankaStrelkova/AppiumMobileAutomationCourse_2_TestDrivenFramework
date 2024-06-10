package tests;

import io.appium.java_client.screenrecording.CanRecordScreen;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.AppiumServerManager;
import utils.DriverManager;
import utils.TestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;


public class BaseTest {
    public String testPlatformName;
    static Logger log = (Logger) LogManager.getLogger(LoginTests.class);
    private static AppiumDriverLocalService appiumServer;


    @BeforeSuite
    public void startAppiumServer() {
        System.out.println("Staring server ....");
        AppiumServerManager.Start();
    }

    @Parameters({"platformName"})
    @BeforeClass
    public void beforeClass(String platformName) {
        testPlatformName = platformName;
    }

    // Here is the place to implement start video recording
    // It should be stopped after the test is completed (in @AfterMethod)
    @Parameters({"platformName", "deviceName"})
    @BeforeMethod
    public void setup(String platformName, String deviceName, Method method) throws Exception {
        log.info("Starting test: " + method.getName());
        // Init driver
        DriverManager.initDriver(platformName, deviceName);

        // start video recording
        ((CanRecordScreen) DriverManager.getDriver()).startRecordingScreen();
    }

    @AfterMethod
    public void closeDriver(ITestResult result, Method method) throws Exception {
        // Stop video recording if test is failed. If we like to take vidoe not only for failed tests - remove "if" clause
        if (result.getStatus() == 2) {
            // Stop video recording
            String media = ((CanRecordScreen) DriverManager.getDriver()).stopRecordingScreen();
            Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();


            String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
                    + File.separator + TestUtils.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoDir = new File(dirPath);


            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }


            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
                stream.write(Base64.getDecoder().decode(media));
                stream.close();
                //utils.log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
            } catch (Exception e) {
                //utils.log().error("error during video capture" + e.toString());
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
        // end of code for stop video recording and saving it

        // Quit driver
        DriverManager.quitDriver();
        //System.out.println("Close driver in @AfterMethod");
        log.info("End of the test: " + method.getName() + result.getTestClass().getTestName());
    }

    @AfterSuite
    public void stopAppiumServer() {
        AppiumServerManager.Stop();
    }

}
