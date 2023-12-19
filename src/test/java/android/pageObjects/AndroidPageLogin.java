package android.pageObjects;

import base.constants.AndroidConstants;
import base.pageBase.AndroidPageBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import static java.time.Duration.ofSeconds;

public class AndroidPageLogin extends AndroidPageBase {

    @AndroidFindBy(id = "sampleID")
    WebElement sampleElementName;

    public AndroidPageLogin(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public void sampleMethod() {
    waitForElement(sampleElementName);
    }
}
