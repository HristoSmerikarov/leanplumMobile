package com.leanplum.tests.pageobject.nativesdk;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.helpers.Utils.SwipeDirection;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AppInboxMessagePO extends BasePO {

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

    public AppInboxMessagePO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void waitForInboxMessage() {
        MobileDriverUtils.waitForExpectedCondition(driver, 10, ExpectedConditions.visibilityOf(appInboxList));
    }

    public boolean isTitleCorrect(String title) {
        return getTextFromElement(appInboxMessageTitle).equals(title);
    }

    public boolean isSubTitleCorrect(String subtitle) {
        return getTextFromElement(appInboxMessageSubTitle).equals(subtitle);
    }

    public boolean doesContainImage() {
        try {
            return appInboxImage.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void performReadAction() {
        Utils.swipeToElement(driver, performReadActionButton, SwipeDirection.DOWN);
        performReadActionButton.click();
    }
}
