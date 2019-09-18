package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AppInboxMessagePO extends BasePO {

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/title']")
    // @iOSXCUITFindBy(xpath = "com.leanplum.rondo:id/trackName")
    public MobileElement appInboxMessageByTitle;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/subtitle']")
    // @iOSXCUITFindBy(xpath = "com.leanplum.rondo:id/trackName")
    public MobileElement appInboxMessageBySubTitle;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/image']")
    // @iOSXCUITFindBy(xpath = "com.leanplum.rondo:id/trackName")
    public MobileElement appInboxImage;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/button']")
    // @iOSXCUITFindBy(xpath = "com.leanplum.rondo:id/trackName")
    public MobileElement performReadActionButton;

    private MobileDriver<MobileElement> driver;

    public AppInboxMessagePO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean isTitleCorrect(String title) {
        return MobileDriverUtils
                .waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(appInboxMessageByTitle))
                .getAttribute("text").equals(title);
    }

    public boolean isSubTitleCorrect(String subtitle) {
        return MobileDriverUtils
                .waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(appInboxMessageBySubTitle))
                .getAttribute("text").equals(subtitle);
    }

    public boolean doesContainImage() {
        try {
            MobileDriverUtils.waitForExpectedCondition(driver, 5, ExpectedConditions.visibilityOf(appInboxImage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void performReadAction() {
        performReadActionButton.click();
    }
}
