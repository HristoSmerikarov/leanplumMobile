package com.leanplum.tests.pageobject.reactnativeinapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.inapp.CenterPopupPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNCenterPopupPO extends CenterPopupPO {

    private static final String CENTER_POPUP_TITLE_XPATH = "//*[@resource-id='com.leanplum.rondo:id/title_view']";
    private static final String CENTER_POPUP_XPATH = "*[@resource-id='com.leanplum.rondo:id/container_view']";
    private static final String IOS_STATIC_TEXT_XPATH = "XCUIElementTypeStaticText";
    private static final String IOS_CENTER_POPUP_XPATH = "//XCUIElementTypeButton[@visible='true']/following-sibling::XCUIElementTypeOther";
    private static final String IOS_CENTER_POPUP_ACCEPT_BUTTON_XPATH = IOS_CENTER_POPUP_XPATH
            + "/XCUIElementTypeButton[@name]";
    private static final String IOS_CENTER_POPUP_CLOSE_BUTTON_XPATH = IOS_CENTER_POPUP_XPATH
            + "/preceding-sibling::XCUIElementTypeButton[1]";

    @iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH)
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]/ancestor::" + CENTER_POPUP_XPATH)
    private MobileElement centerPopup;

    @iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH + "//" + IOS_STATIC_TEXT_XPATH + "[1]")
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]")
    private MobileElement centerPopupTitle;

    @iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH + "//" + IOS_STATIC_TEXT_XPATH + "[2]")
    @AndroidFindBy(xpath = "//" + CENTER_POPUP_XPATH + "/*[@class='android.widget.TextView']")
    private MobileElement centerPopupMessage;

    @iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_ACCEPT_BUTTON_XPATH)
    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/accept_button']")
    public MobileElement centerPopupAcceptButton;

    @iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_CLOSE_BUTTON_XPATH)
    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/accept_button']")
    public MobileElement centerPopupCloseButton;

    private AppiumDriver<MobileElement> driver;

    public RNCenterPopupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public boolean verifyCenterPopup(String title, String message, String buttonText) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clickAcceptButton() {
        // TODO Auto-generated method stub

    }
}
