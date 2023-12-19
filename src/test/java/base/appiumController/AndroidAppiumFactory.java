package base.appiumController;

import base.utils.log4j.logs;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
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

public class AndroidAppiumFactory {

    public static AndroidDriver driver;
    public static Properties prop;
    public static InputStream input;

    public static String getPropValue(String key) throws IOException {
        input = Files.newInputStream(Paths.get("src/test/java/android/androidDeviceDetails.properties"));
        prop = new Properties();
        prop.load(input);
        return prop.getProperty(key);
    }

    public static String getAPKFileName() {
        File directory = new File(System.getProperty("user.dir") + "/src/main/resources/apps/Android");

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".apk")) {
                        logs.info("apk file name : " + file.getName());
                        return file.getName();
                    }
                }
            }
        }

        return null;
    }

    @BeforeTest()
    public void androidDeviceSetup() throws IOException {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        logs.info("*** Initializing uiAutomator2 Options ***");
        uiAutomator2Options
//                .setPlatformName("android")
//                .setAutomationName("uiautomator2")
                .setDeviceName(getPropValue("deviceName"))
                .setUdid(getPropValue("udid"))
                .setPlatformVersion(getPropValue("platformVersion"))
                .setApp(System.getProperty("user.dir") + "/src/main/resources/apps/Android/" + getAPKFileName())
                .setUnlockType("pin")
                .setUnlockKey(getPropValue("unlockKey"))
                // .setAppActivity("")
                .setAppActivity("app.activity")
                .setAppPackage("app.package")
                .setFullReset(Boolean.parseBoolean(getPropValue("fullReset")))
                .setNoReset(Boolean.parseBoolean(getPropValue("noReset")))
                .autoGrantPermissions()
                .setNewCommandTimeout(Duration.ofMinutes(3));

        driver = new AndroidDriver(new URL(getPropValue("appiumURL")), uiAutomator2Options);

        logs.info("Device Name : " + uiAutomator2Options.getCapability("deviceName"));
        logs.info("Device UDID : " + uiAutomator2Options.getCapability("udid"));
        logs.info("Platform Version : " + uiAutomator2Options.getCapability("platformVersion"));
        logs.info("Full Reset : " + uiAutomator2Options.getCapability("fullReset"));
        logs.info("No Reset : " + uiAutomator2Options.getCapability("noReset"));
        logs.info("App Package : " + uiAutomator2Options.getCapability("appPackage"));
        logs.info("Appium URL : " + getPropValue("appiumURL"));

        logs.info("*** uiAutomator2 Initialized *** \n");
    }

    public String captureScreenShot(ITestResult result) {
        String testName = result.getName();
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destDir = "test-output/extentReport/android/failedImages";

        new File(destDir).mkdirs();
        String destFile = testName + ".png";
        logs.info("Screenshot saved on : " + destDir + "/" + destFile);
        try {
            FileHandler.copy(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return File.separator + destDir + File.separator + destFile;

    }

    @AfterTest
    public void quiteDriver() {
        if (null != driver) {
            driver.quit();
            logs.info("Driver Killed");
        }
    }

}
