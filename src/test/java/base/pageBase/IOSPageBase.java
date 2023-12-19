package base.pageBase;

import base.utils.log4j.logs;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import static java.time.Duration.ofSeconds;

public class IOSPageBase extends MainPageBase {

    @iOSXCUITFindBy(id = "sampleID")
    WebElement sampleElementName;

    public IOSPageBase(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public void scroll(WebElement element, Enum direction) {
        /**
         * Using swipe command instead of scroll so that we use the velocity option to increase the swipe speed.
         * Direction needs to inverted as Swipe works opposite to Scroll
         **/

        switch (direction.toString()) {
            case "left":
                driver.executeScript("mobile: swipe", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "right", "velocity", 2500));
                break;

            case "right":
                driver.executeScript("mobile: swipe", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "left", "velocity", 2500));
                break;

            case "up":
                driver.executeScript("mobile: swipe", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "down", "velocity", 2500));
                break;

            case "down":
                driver.executeScript("mobile: swipe", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "up", "velocity", 2500));
                break;
            default:
                logs.error("In-valid Direction ");
                break;
        }

    }

    public void openSafari() {
        driver.executeScript("mobile: activateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
    }

    public void deepLink(String deeplink) {
        driver.executeScript("mobile: deepLink", ImmutableMap.of("bundleId", "com.apple.mobilesafari", "url", deeplink));
    }

    protected void scrollOnElementUntilElementIsVisible(WebElement elementToScroll, String elementNameToFind, Direction direction) {
        FluentWait<AppiumDriver> wait = customWait(ofSeconds(5), ofSeconds(2));
        int count = 1;
        while (true) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.iOSNsPredicateString("name == '" + elementNameToFind + "' AND visible == true")));
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

}
