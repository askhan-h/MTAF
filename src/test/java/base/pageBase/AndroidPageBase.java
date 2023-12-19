package base.pageBase;

import base.utils.log4j.logs;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import static java.time.Duration.ofSeconds;


public class AndroidPageBase extends MainPageBase {

    @AndroidFindBy(id = "sampleID")
    WebElement sampleElementName;

    public AndroidPageBase(AppiumDriver appiumDriver) {

        super(appiumDriver);
    }

    public void scroll(WebElement element, Enum direction) {
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", direction.toString(), "percent", 0.75, "speed", 1500));

    }

    public void openChrome() {
        driver.executeScript("mobile: activateApp", ImmutableMap.of("appId", "com.android.chrome"));

    }

    public void deepLink(String deeplink) {
        driver.executeScript("mobile: deepLink", ImmutableMap.of("url", deeplink, "package", "com.android.chrome"));
    }

    public void editorAction(Enum key) {
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", key.toString()));
    }

    protected void scrollOnElementUntilElementIsVisible(WebElement elementToScroll, String elementNameToFind, Direction direction) {
        FluentWait<AppiumDriver> wait = customWait(ofSeconds(5), ofSeconds(2));
        int count = 1;
        while (true) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text=\"" + elementNameToFind + "\" and @displayed='true' ]")));
                if (element.isDisplayed()) {
                    break;
                }
            } catch (NoSuchElementException | TimeoutException ignored) {
            }
            scroll(elementToScroll, direction);
            logs.info("Swiping until element is found");
            if (count == 15) {
                logs.info("Max Swipe count reached ");
                break;
            }
            count++;
        }
        logs.info("Element " + elementNameToFind + " is found ");
        resetCustomWaitToDefault();
    }


    protected void performKeyboardAction(Action action) {
        while (true) {
            logs.info("Check if Keyboard is shown");
            if (((AndroidDriver) driver).isKeyboardShown()) {
                logs.info("Keyboard is shown");
                editorAction(action);
                logs.info(action.toString().toUpperCase() + " button is pressed");
                break;
            }
        }

    }
}