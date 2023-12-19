package base.utils.testng;


import base.appiumController.IOSAppiumFactory;
import base.utils.AppiumServer;
import base.utils.ExtentReporter;
import base.utils.log4j.logs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.*;
import org.testng.annotations.IConfigurationAnnotation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.Duration;

public class IOSTestngListeners extends IOSAppiumFactory implements ITestListener, IInvokedMethodListener, ISuiteListener, IAnnotationTransformer {

    ExtentReports extentReports = ExtentReporter.getExtentReportObject();
    ExtentTest test;
    private boolean hasFailures = false;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        synchronized (this) {
            if (hasFailures) {
                throw new SkipException("Skipping this test due to previous test failure");
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod() && !iTestResult.isSuccess()) {
            synchronized (this) {
                hasFailures = true;
            }
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logs.info("Test *** " + iTestResult.getMethod().getMethodName() + " *** has started");
        test = extentReports.createTest(iTestResult.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        test.log(Status.PASS, "Test Passed");
        //captureScreenShot(iTestResult);
        logs.info("Test *** " + iTestResult.getMethod().getMethodName() + " *** has passed successfully");

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        test.fail(iTestResult.getThrowable());
        test.addScreenCaptureFromPath(captureScreenShot(iTestResult), iTestResult.getMethod().getMethodName());
        logs.error("Test *** " + iTestResult.getMethod().getMethodName() + " *** has failed." + "\nCause: " + iTestResult.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onStart(ISuite iSuite) {
        try {
            AppiumServer.startServer();
            iOSDeviceSetup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logs.info("Test Suit *** " + iSuite.getName() + " *** has started");

    }

    @Override
    public void onFinish(ISuite iSuite) {
        logs.info("Test Suit *** " + iSuite.getName() + " *** has ended");
//        AppiumServer.stopServer();
        quiteDriver();
        extentReports.flush();

    }

    /**
     * Disabling @BeforeTest and @AfterTest when running from Test Suites
     */
    @Override
    public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (annotation.getBeforeTest() || annotation.getAfterTest()) {
            annotation.setEnabled(false);
        }

    }

}
