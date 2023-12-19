package base.appiumController;

import base.utils.log4j.logs;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.simulator.PasteboardSyncState;
import io.appium.java_client.ios.options.wda.XcodeCertificate;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class IOSAppiumFactory {

    public static IOSDriver driver;
    public static Properties prop;
    public static InputStream input;

    private static String getPropValue(String key) throws IOException {
        input = Files.newInputStream(Paths.get("src/test/java/iOS/iOSDeviceDetails.properties"));
        prop = new Properties();
        prop.load(input);
        return prop.getProperty(key);
    }

    @BeforeTest()
    public void iOSDeviceSetup() throws IOException {
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        logs.info("*** Initializing XCUITestOptions Options ***  ");
        xcuiTestOptions
                .setApp(getPropValue("appPath")) //Used when running on iOS Simulators
                .setNoReset(Boolean.parseBoolean(getPropValue("noReset")))
                .setBundleId("bundle.id")
                .setUdid(getPropValue("udid"))
                .setDeviceName(getPropValue("deviceName"))
                .setPlatformVersion(getPropValue("platformVersion"))
                .setAutoAcceptAlerts(true)
                .setSimulatorPasteboardAutomaticSync(PasteboardSyncState.OFF)
                .setXcodeCertificate(new XcodeCertificate(getPropValue("xcodeOrgId")))
                .setNewCommandTimeout(Duration.ofMinutes(2));

        driver = new IOSDriver(new URL(getPropValue("appiumURL")), xcuiTestOptions);

        logs.info("Device Name : " + xcuiTestOptions.getCapability("deviceName"));
        logs.info("Device UDID : " + xcuiTestOptions.getCapability("udid"));
        logs.info("Platform Version : " + xcuiTestOptions.getCapability("platformVersion"));
        logs.info("No Reset : " + xcuiTestOptions.getCapability("noReset"));
        logs.info("Bundle ID : " + xcuiTestOptions.getCapability("bundleId"));
        logs.info("Appium URL : " + getPropValue("appiumURL"));

        logs.info("*** XCUITestOptions Initialized ***  \n");

    }

    public String captureScreenShot(ITestResult result) {
        String testName = result.getName();
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destDir = "test-output/extentReport/iOS/failedImages";

        new File(destDir).mkdirs();
        String destFile = testName + ".png";
        logs.info("Screenshot saved on : " + destDir + "/" + destFile);
        try {
            FileHandler.copy(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = File.separator + destDir + File.separator + destFile;
        return filePath;
    }

    @AfterTest
    public void quiteDriver() {
        if (null != driver) {
            driver.quit();
            logs.info("Driver Killed");
        }
    }
}
