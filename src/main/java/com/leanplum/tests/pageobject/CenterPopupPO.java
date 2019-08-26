package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CenterPopupPO extends BasePO {

    private static final String CENTER_POPUP_TITLE = "//*[@resource-id='com.leanplum.rondo:id/title_view']";
    private static final String CENTER_POPUP = "*[@resource-id='com.leanplum.rondo:id/container_view']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE + "/*[@text]/ancestor::" + CENTER_POPUP)
    public MobileElement centerPopup;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = CENTER_POPUP_TITLE + "/*[@text]")
    public MobileElement centerPopupTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//" + CENTER_POPUP + "/*[@class='android.widget.TextView']")
    public MobileElement centerPopupMessage;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/accept_button']")
    public MobileElement centerPopupButton;

    AppiumDriver<MobileElement> driver;

    public CenterPopupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean isCenterPopup(String title, String message, String buttonText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(centerPopup));

        return centerPopupTitle.getAttribute("text").equals(title)
                && centerPopupMessage.getAttribute("text").equals(title)
                && centerPopupButton.getAttribute("text").equals(buttonText);
    }

    public void clickAcceptButton() {
        centerPopupButton.click();
    }
}
