package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AlertPO extends InAppPopupPO {

    private static final String ANDROID_ALERT_TITLE_XPATH = "//*[@resource-id='android:id/alertTitle']";
    public static final String CONFIRM_ALERT_BUTTON_XPATH = "//*[@resource-id='android:id/button1']";

    @AndroidFindBy(xpath = CONFIRM_ALERT_BUTTON_XPATH)
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement confirmAlertButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_ALERT_TITLE_XPATH)
    public MobileElement alertPopup;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_ALERT_TITLE_XPATH)
    public MobileElement alertTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/message']")
    public MobileElement alertMessage;

    MobileDriver<MobileElement> driver;

    public AlertPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean verifyAlertLayout(String expectedTitle, String expectedMessage, String confirmAlertButtonText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(alertPopup));

        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, CONFIRM_ALERT_BUTTON_XPATH)
                && verifyInAppPopup(ImmutableMap.of(alertTitle, expectedTitle, alertMessage, expectedMessage, confirmAlertButton, confirmAlertButtonText));
    }
}
