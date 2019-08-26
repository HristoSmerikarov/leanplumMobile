package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AlertPO extends BasePO {

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/alertTitle']")
    public MobileElement alertPopup;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/alertTitle']")
    public MobileElement alertTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='android:id/message']")
    public MobileElement alertMessage;

    AppiumDriver<MobileElement> driver;

    public AlertPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean isAlertCorrect(String expectedTitle, String expectedMessage) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(alertPopup));
        
        String actualTitle = alertTitle.getAttribute("text");
        String actualMessage = alertMessage.getAttribute("text");

        return actualTitle.equals(expectedTitle) && actualMessage.equals(expectedMessage);
    }
}
