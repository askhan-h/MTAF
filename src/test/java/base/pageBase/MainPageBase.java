package base.pageBase;

import base.utils.log4j.logs;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.TestException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class MainPageBase {

    protected AppiumDriver driver;
    protected FluentWait<AppiumDriver> wait;
    protected Duration defaultTimeout;
    protected Duration defaultPollingInterval;

    public MainPageBase(AppiumDriver appiumDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), this);
        this.driver = appiumDriver;
        defaultTimeout = Duration.ofSeconds(15);
        defaultPollingInterval = Duration.ofSeconds(5);
        wait = new FluentWait<>(driver)
                .withTimeout(defaultTimeout)
                .pollingEvery(defaultPollingInterval)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    protected FluentWait<AppiumDriver> customWait(Duration customTimeOut, Duration customPollingInterval) {
        logs.info("Setting Timeout to : " + customTimeOut.toSeconds() + " seconds");
        logs.info("Setting Polling interval to : " + customPollingInterval.toSeconds() + " seconds");
        return new FluentWait<>(driver)
                .withTimeout(customTimeOut)
                .pollingEvery(customPollingInterval);
    }

    protected FluentWait<AppiumDriver> resetCustomWaitToDefault() {
        logs.info("Resetting the wait values to default");
        return new FluentWait<>(driver)
                .withTimeout(defaultTimeout)
                .pollingEvery(defaultPollingInterval);
    }


    protected void waitForElementToBeClickable(WebElement element) {
        try {
            logs.info("Waiting for element : " + element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String elementName = element.getText().toUpperCase();
            logs.info("Element : " + elementName + " is found and clickable");
        } catch (TimeoutException timeoutException) {
            throw new TimeoutException("Element : " + element + " is either NOT found or clickable");
        }

    }

    protected void waitForElementToBeClickable(WebElement element, String elementName) {
        try {
            logs.info("Waiting for element : " + elementName.toUpperCase());
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logs.info("Element : " + elementName.toUpperCase() + " is found and clickable");
        } catch (TimeoutException timeoutException) {
            throw new TimeoutException("Element : " + element + " is either NOT found or clickable");
        }

    }

    protected void waitForElement(WebElement element) {
        try {
            logs.info("Waiting for element : " + element);
            wait.until(ExpectedConditions.visibilityOf(element));
            String elementName = element.getText().toUpperCase();
            logs.info("Element : " + elementName + " is found");
        } catch (TimeoutException timeoutException) {
            throw new TimeoutException("Element : " + element + " is NOT found");
        }
    }

    protected void waitForElement(WebElement element, String elementName) {
        try {
            logs.info("Waiting for element : " + elementName.toUpperCase());
            wait.until(ExpectedConditions.visibilityOf(element));
            logs.info("Element : " + elementName.toUpperCase() + " is found");
        } catch (TimeoutException timeoutException) {
            throw new TimeoutException("Element : " + elementName.toUpperCase() + " is NOT found ");
        }
    }

    protected void waitForElementTobeDisappeared(WebElement element) {
        logs.info("Waiting for element to be disappeared ");
        wait.until(ExpectedConditions.invisibilityOf(element));
        logs.info("Element disappeared");
    }

    protected void clickElement(WebElement element) {
        waitForElementToBeClickable(element);
        String elementName = element.getText().toUpperCase();
        element.click();
        logs.info("Element : " + elementName + " is clicked ");
    }

    protected void clickElement(WebElement element, String elementName) {
        waitForElementToBeClickable(element, elementName);
        element.click();
        logs.info("Element : " + elementName.toUpperCase() + " is clicked ");
    }

    protected boolean isElementVisible(WebElement element) {
        try {
            waitForElement(element);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException | TimeoutException e) {
            logs.error("Element : " + element + " is NOT visible");
        }

        return false;
    }

    protected void typeText(WebElement element, String text) {
        waitForElementToBeClickable(element);
        String elementText = element.getText().toUpperCase();
        element.sendKeys(text);
        logs.info("Text '" + text + "' is typed on element " + elementText);
    }

    protected void typeText(WebElement element, String text, String elementName) {
        waitForElementToBeClickable(element, elementName);
        element.sendKeys(text);
        logs.info("Text '" + text + "' is typed on element " + elementName.toUpperCase());
    }

    protected boolean assertIfTextContains(WebElement element, String expText) {
        String actualText = null;
        try {
            if (isElementVisible(element)) {
                actualText = element.getText();
                Assert.assertTrue(actualText.contains(expText));
                logs.info("Actual Text : '" + actualText + "'\n " +
                        "                                Expected Text : '" + expText + "'");
                return true;
            }
        } catch (AssertionError e) {
            logs.error("Actual Text : '" + actualText + "' \n " +
                    "                               Expected Text : '" + expText + "'");
        }
        return false;
    }

    protected boolean assertIfTextStartsWith(WebElement element, String expText) {
        String actualText;
        if (isElementVisible(element)) {
            actualText = element.getText();
            if (actualText.startsWith(expText)) {
                logs.info("Actual Text : '" + actualText + "'\n " +
                        "                                Expected Text Starts with: '" + expText + "'");
                return true;
            } else {
                logs.error("Actual Text : '" + actualText + "' \n " +
                        "                               Expected Text Starts with: '" + expText + "'");
            }
        }
        return false;

    }


    /**
     * Method to assert Texts with dynamic contents
     */
    protected boolean assertIfDynamicTextContains(WebElement element, String expText) {
        String actualText = null;
        if (isElementVisible(element)) {
            Pattern regex = Pattern.compile(expText);
            actualText = element.getText();
            Matcher matcher = regex.matcher(actualText);

            if (matcher.matches()) {
                logs.info("Actual Text : '" + actualText + "'\n " +
                        "                                Expected Text : '" + expText + "'");
                return true;
            } else {
                logs.error("Actual Text : '" + actualText + "'\n " +
                        "                                Expected Text : '" + expText + "'");
            }
        }
        return false;
    }

    protected boolean assertIfTextNotEquals(WebElement element, String expText) {
        String actualText = null;
        try {
            if (isElementVisible(element)) {
                actualText = element.getText();
                Assert.assertNotEquals(actualText, expText);
                logs.info("Actual Text : '" + actualText + "' \n " +
                        "                               Expected Text '" + expText + " '");
                return true;
            }
        } catch (AssertionError e) {
            logs.error("Actual Text : '" + actualText + "' \n " +
                    "                               Expected text '" + expText + "'");
        }

        return false;
    }

    protected boolean getElementIfVisible(WebElement element) {
        return isElementVisible(element);
    }

    protected void clickElementIfVisible(WebElement element) {
        if (isElementVisible(element)) {
            String elementName = element.getText();
            element.click();
            logs.info("Element : " + elementName.toUpperCase() + " is clicked");
        }
    }

    protected void ignoreChromeWelcomeScreenIfVisible() {
        try {
            logs.info("Waiting for Default Chrome Welcome screen to ignore if visible");
            WebElement btn_web_AcceptTerms = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.android.chrome:id/terms_accept']")));
            clickElement(btn_web_AcceptTerms);
            WebElement btn_web_NoThanks = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.android.chrome:id/negative_button']")));
            clickElement(btn_web_NoThanks);
        } catch (TimeoutException | NoSuchElementException e) {
            logs.info("No default Chrome Browser Welcome screen is displayed");
        }
    }


    protected void assertToastMsgIfVisible(WebElement element, String expText) {
        try {
            assertIfDynamicTextContains(element, expText);
            waitForElementTobeDisappeared(element);
        } catch (NoSuchElementException ignored) {
        }
    }

    protected void debugMethod() {
    }

    protected void verifyImageOccurrencesSearch(String imagePath) {
        logs.info("Verifying the Image Occurrence");
        try {
            File partialImage = new File(imagePath);
            File fullImage = driver.getScreenshotAs(OutputType.FILE);

            OccurrenceMatchingResult result = driver
                    .findImageOccurrence(fullImage, partialImage, new OccurrenceMatchingOptions()
                            .withEnabledVisualization());
            assertThat(result.getVisualization().length, is(greaterThan(0)));
            Assert.assertNotNull(result.getRect());
            logs.info("Image verified successfully");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WebDriverException e) {
            throw new TestException("Cannot find any occurrences of the partial image in the full image");
        }

    }

    protected String randomText() {
        return RandomStringUtils.randomAlphanumeric(70);

    }

    protected void clearTextField(WebElement element) {
        logs.info("Clearing text field");
        element.clear();
        logs.info("Text field cleared");

    }

    protected enum Action {normal, unspecified, none, go, search, send, next, done, previous}

    protected enum Direction {left, right, up, down}
}
