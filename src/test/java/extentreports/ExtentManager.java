package extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    public static ExtentReports extentReports;


    public static Date date = new Date();
    static String myReportsPath = date.toString().replace(" ", "_").replace(":", "_");

    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public static ExtentReports getReporterInstance() {
        if (extentReports == null) {
            // Create reporter
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("./reports/"+ myReportsPath + ".html");

            // Configure reporter
            extentSparkReporter.config().setReportName("Test Report");
            extentSparkReporter.config().setDocumentTitle("Test document title");

            // Create extent reports
            extentReports = new ExtentReports();

            // Configure reports
            extentReports.setSystemInfo("QA", "Tsanka Strelkova");
            extentReports.setSystemInfo("Env", "Staging");

            // Attach reporter to the reports
            extentReports.attachReporter(extentSparkReporter);
        }
        return extentReports;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = getReporterInstance().createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
}
