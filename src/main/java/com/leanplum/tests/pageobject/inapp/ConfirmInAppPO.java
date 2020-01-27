package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ConfirmInAppPO extends InAppPopupPO {

    private static final String CONFIRM_IN_APP_TITLE = "//*[@resource-id='android:id/title_template']";
    private static final String CONFIRM_IN_APP_ALERT_TITLE = "/*[@resource-id='android:id/alertTitle']";
    public static final String CONFIRM_IN_APP = "*[@resource-id='android:id/parentPanel']";
    private static final String IOS_CONFIRM_IN_APP_XPATH = "//XCUIElementTypeAlert[@name]";
    private static final String IOS_CONFIRM_IN_APP_BUTTON = "(" + IOS_CONFIRM_IN_APP_XPATH + "//XCUIElementTypeButton)";
    private static final String ANDROID_CONFIRM_IN_APP_XPATH = CONFIRM_IN_APP_TITLE + CONFIRM_IN_APP_ALERT_TITLE
            + "//ancestor::" + CONFIRM_IN_APP;

    @iOSXCUITFindBy(xpath = IOS_CONFIRM_IN_APP_XPATH)
    @AndroidFindBy(xpath = ANDROID_CONFIRM_IN_APP_XPATH)
    public MobileElement confirmInApp;

    @iOSXCUITFindBy(xpath = IOS_CONFIRM_IN_APP_XPATH
            + "//XCUIElementTypeStaticText/preceding-sibling::XCUIElementTypeStaticText")
    @AndroidFindBy(xpath = CONFIRM_IN_APP_TITLE + CONFIRM_IN_APP_ALERT_TITLE)
    public MobileElement confirmInAppTitle;

    @iOSXCUITFindBy(xpath = IOS_CONFIRM_IN_APP_XPATH
            + "//XCUIElementTypeStaticText/following-sibling::XCUIElementTypeStaticText")
    @AndroidFindBy(xpath = "//" + CONFIRM_IN_APP + "//*[@resource-id='android:id/message']")
    public MobileElement confirmInAppMessage;

    @iOSXCUITFindBy(xpath = IOS_CONFIRM_IN_APP_BUTTON + "[1]")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/button2']")
    public MobileElement confirmCancelButton;

    @iOSXCUITFindBy(xpath = IOS_CONFIRM_IN_APP_BUTTON + "[2]")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/button1']")
    public MobileElement confirmAcceptButton;

    AppiumDriver<MobileElement> driver;

    public ConfirmInAppPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean verifyConfirmInApp(String title, String message, String acceptButtonText, String cancelButtonText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(confirmInApp));
        return verifyInAppPopup(ImmutableMap.of(confirmInAppTitle, title, confirmInAppMessage, message,
                confirmAcceptButton, acceptButtonText, confirmCancelButton, cancelButtonText));
    }

    public boolean verifyConfirmInAppIsAbsent() {
        if (driver instanceof AndroidDriver) {
            return !MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_CONFIRM_IN_APP_XPATH);
        } else {
            return !MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_CONFIRM_IN_APP_XPATH);
        }
    }
}
