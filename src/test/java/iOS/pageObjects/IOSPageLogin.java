package iOS.pageObjects;

import base.pageBase.IOSPageBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class IOSPageLogin extends IOSPageBase {

    @iOSXCUITFindBy(id = "sampleID")
    WebElement sampleElementName;

    public IOSPageLogin(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public void sampleMethod() {
        waitForElement(sampleElementName);
    }
}
