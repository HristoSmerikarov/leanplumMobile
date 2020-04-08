package com.leanplum.tests.pageobject.reactnativesdk;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.AppInboxMessagePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNAppInboxMessagePO extends AppInboxMessagePO {

    private static final String IOS_IN_APP_XPATH = "//XCUIElementTypeCell";

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_inbox")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Inbox\"]")
    public MobileElement appinbox;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/listview']")
    @iOSXCUITFindBy(xpath = IOS_IN_APP_XPATH)
    private MobileElement appInboxList;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/title']")
    @iOSXCUITFindBy(xpath = IOS_IN_APP_XPATH + "//XCUIElementTypeStaticText[@label][1]")
    private MobileElement appInboxMessageTitle;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/subtitle']")
    @iOSXCUITFindBy(xpath = IOS_IN_APP_XPATH + "//XCUIElementTypeStaticText[@label][2]")
    private MobileElement appInboxMessageSubTitle;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/image']")
    @iOSXCUITFindBy(xpath = IOS_IN_APP_XPATH + "//XCUIElementTypeImage")
    private MobileElement appInboxImage;

    @AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/button']")
    @iOSXCUITFindBy(xpath = IOS_IN_APP_XPATH + "//XCUIElementTypeButton[@label='Perform Read Action']")
    private MobileElement performReadActionButton;

    private AppiumDriver<MobileElement> driver;

    public RNAppInboxMessagePO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public void waitForInboxMessage() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isTitleCorrect(String title) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSubTitleCorrect(String subtitle) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doesContainImage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void performReadAction() {
        // TODO Auto-generated method stub

    }

}
