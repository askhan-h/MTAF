package base.utils;

import base.utils.log4j.logs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {

    static ExtentReports extentReports;

    public static ExtentReports getExtentReportObject() {

        String path = System.getProperty("user.dir") + "/test-output/extentReport/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Automation Test Results");
        reporter.config().setDocumentTitle("Test Results");

        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Tester", System.getProperty("user.name"));
        logs.info("Test Report will be available on :" + path);
        return extentReports;

    }

}
