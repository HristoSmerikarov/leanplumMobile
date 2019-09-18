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

public class CenterPopupPO extends InAppPopupPO {

    private static final String CENTER_POPUP_TITLE_XPATH = "//*[@resource-id='com.leanplum.rondo:id/title_view']";
    private static final String CENTER_POPUP_XPATH = "*[@resource-id='com.leanplum.rondo:id/container_view']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]/ancestor::" + CENTER_POPUP_XPATH)
    public MobileElement centerPopup;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]")
    public MobileElement centerPopupTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//" + CENTER_POPUP_XPATH + "/*[@class='android.widget.TextView']")
    public MobileElement centerPopupMessage;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/accept_button']")
    public MobileElement centerPopupButton;

    MobileDriver<MobileElement> driver;

    public CenterPopupPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean verifyCenterPopup(String title, String message, String buttonText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(centerPopup));
        return verifyInAppPopup(ImmutableMap.of(centerPopupTitle, title, centerPopupMessage, message,
                        centerPopupButton, buttonText));
    }

    public void clickAcceptButton() {
        centerPopupButton.click();
    }
}
