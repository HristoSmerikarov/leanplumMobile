package com.leanplum.tests.pageobject;

import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BasePO {

    protected static final String ANDROID_BANNER_XPATH = "//*[@resource-id='title']/ancestor::*[@class='android.view.View']";
    protected static final String IOS_BANNER_XPATH = "//XCUIElementTypeWebView[@visible='true']";

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_setup")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Setup\"]")
    public MobileElement appSetup;

    @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Adhoc\"]")
    public MobileElement adhoc;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_inbox")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Inbox\"]")
    public MobileElement appinbox;

    @iOSXCUITFindBy(xpath = IOS_BANNER_XPATH)
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH)
    public MobileElement banner;

    public static final String POPUP_CONTAINER_XPATH = "//*[@resource-id='com.leanplum.rondo:id/container_view']";

    private AppiumDriver<MobileElement> driver;

    protected BasePO(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public AppiumDriver<MobileElement> getDriver() {
        return this.driver;
    }

    public void click(MobileElement element) {
        long startTime = System.currentTimeMillis();
        element.click();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("I spent " + elapsedTime + " seconds to click");
    }

    public boolean verifyText(MobileElement element, String expectedText) {
        return element.getText().equals(expectedText);
    }

    public String getTextFromElement(MobileElement el) {
        if (driver instanceof AndroidDriver) {
            return el.getAttribute("text");
        } else {
            String value = el.getAttribute("value");
            return value != null ? value : el.getAttribute("label");
        }
    }

    /**
     * Confirms that all texts displayed in app are correct
     * 
     * @param elementTextMap
     * @return
     */
    public boolean verifyInAppPopup(AppiumDriver<MobileElement> driver, Map<MobileElement, String> elementTextMap) {
        for (Entry<MobileElement, String> entry : elementTextMap.entrySet()) {
            String actual;
            if (driver instanceof AndroidDriver) {
                actual = entry.getKey().getAttribute("text");
            } else {
                actual = entry.getKey().getAttribute("label");
            }
            String expected = entry.getValue();
            System.out.println("Actual: " + actual);
            System.out.println("Expected: " + expected);

            if (!actual.equalsIgnoreCase(expected)) {
                return false;
            }
        }
        return true;
    }
}
