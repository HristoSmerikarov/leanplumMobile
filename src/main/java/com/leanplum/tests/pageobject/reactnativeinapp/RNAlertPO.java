package com.leanplum.tests.pageobject.reactnativeinapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.inapp.AlertPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNAlertPO extends AlertPO {

    private static final String ANDROID_ALERT_TITLE_XPATH = "//*[@resource-id='android:id/alertTitle']";
    private static final String IOS_ALERT_MESSAGE_XPATH = "//XCUIElementTypeAlert[@visible='true']//XCUIElementTypeOther[@visible='true']/XCUIElementTypeStaticText[@visible='true'][2]";
    private static final String IOS_ALERT_TITLE_XPATH = IOS_ALERT_MESSAGE_XPATH
            + "/preceding-sibling::XCUIElementTypeStaticText[@visible='true']";
    private static final String IOS_CONFIRM_ALERT_BUTTON_XPATH = "//XCUIElementTypeAlert[@visible='true']//XCUIElementTypeButton[@visible='true']";
    private static final String CONFIRM_ALERT_BUTTON_XPATH = "//*[@resource-id='android:id/button1']";

    @AndroidFindBy(xpath = CONFIRM_ALERT_BUTTON_XPATH)
    @iOSXCUITFindBy(xpath = IOS_CONFIRM_ALERT_BUTTON_XPATH)
    public MobileElement confirmAlertButton;

    @AndroidFindBy(xpath = ANDROID_ALERT_TITLE_XPATH)
    @iOSXCUITFindBy(xpath = IOS_ALERT_TITLE_XPATH)
    public MobileElement alertPopup;

    @AndroidFindBy(xpath = ANDROID_ALERT_TITLE_XPATH)
    @iOSXCUITFindBy(xpath = IOS_ALERT_TITLE_XPATH)
    private MobileElement alertTitle;

    @iOSXCUITFindBy(xpath = IOS_ALERT_MESSAGE_XPATH)
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/message']")
    private MobileElement alertMessage;

    private AppiumDriver<MobileElement> driver;

    public RNAlertPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public boolean verifyAlertLayout(String expectedTitle, String expectedMessage, String confirmAlertButtonText) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAlertPresent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void dismissAllAlertsOnStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dismissAlert() {
        // TODO Auto-generated method stub

    }

}
