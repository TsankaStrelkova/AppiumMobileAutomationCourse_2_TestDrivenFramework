package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import extentreports.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utils.DriverManager;
import utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestListener implements ITestListener {
    ExtentReports reports;
    ExtentTest test;


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("On test Start");
        reports = ExtentManager.getReporterInstance();
        test = reports.createTest(result.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("On test Success");
        test.log(Status.PASS, " Test case " + result.getMethod().getMethodName() + " is PASS");
        test.log(Status.INFO, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("On test Failure");

        String testName = result.getName();

        test.log(Status.FAIL, " Test case " + result.getMethod().getMethodName() + " is FAIL");
        test.log(Status.INFO, result.getThrowable().fillInStackTrace());


        File file = null;
        try {
            file = DriverManager.getDriver().getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Map<String, String> params = new HashMap<String, String>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator + params.get("platformName")
                + "_" + params.get("deviceName") + File.separator + TestUtils.getCurrentDateAndTime() + File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file, new File(imagePath));
            Reporter.log("This is the sample screenshot");
            Reporter.log("<a href='" + completeImagePath + "'> <img src='" + completeImagePath + "' height='400' width='400'/> </a>");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("On START");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("On FINISH");
        if (reports != null) {
            reports.flush();
        }
    }

    @Override
    public boolean isEnabled() {
        return ITestListener.super.isEnabled();
    }
}
