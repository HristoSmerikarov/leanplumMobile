package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ConfirmInAppPO extends InAppPopupPO {

    private static final String CONFIRM_IN_APP_TITLE = "//*[@resource-id='android:id/title_template']";
    private static final String CONFIRM_IN_APP_ALERT_TITLE = "/*[@resource-id='android:id/alertTitle']";
    public static final String CONFIRM_IN_APP = "*[@resource-id='android:id/parentPanel']";
    
    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CONFIRM_IN_APP_TITLE + CONFIRM_IN_APP_ALERT_TITLE + "//ancestor::" + CONFIRM_IN_APP)
    public MobileElement confirmInApp;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CONFIRM_IN_APP_TITLE + CONFIRM_IN_APP_ALERT_TITLE)
    public MobileElement confirmInAppTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//" + CONFIRM_IN_APP + "//*[@resource-id='android:id/message']")
    public MobileElement confirmInAppMessage;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/button2']")
    public MobileElement confirmCancelButton;

    // @iOSXCUITFindBy(xpath = "")
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
}
