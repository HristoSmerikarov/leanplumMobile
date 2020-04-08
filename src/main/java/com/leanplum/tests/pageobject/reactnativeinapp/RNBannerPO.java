package com.leanplum.tests.pageobject.reactnativeinapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.inapp.BannerPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNBannerPO extends BannerPO {

    private static final String ANDROID_BANNER_XPATH = "//*[@resource-id='title']/ancestor::*[@class='android.view.View']";
    private static final String ANDROID_BANNER_CLOSE_BUTTON_XPATH = ANDROID_BANNER_XPATH
            + "/*[@resource-id='close-button']";
    private static final String IOS_BANNER_XPATH = "//XCUIElementTypeWebView[@visible='true']";
    private static final String IOS_BANNER_TEXT_XPATH = "(" + IOS_BANNER_XPATH
            + "//XCUIElementTypeOther/XCUIElementTypeStaticText)";
    private static final String IOS_BANNER_CLOSE_BUTTON = IOS_BANNER_XPATH + "//XCUIElementTypeOther[not(*)]";

    @iOSXCUITFindBy(xpath = IOS_BANNER_XPATH)
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH)
    public MobileElement banner;

    @iOSXCUITFindBy(xpath = IOS_BANNER_CLOSE_BUTTON)
    @AndroidFindBy(xpath = ANDROID_BANNER_CLOSE_BUTTON_XPATH)
    public MobileElement bannerCloseButton;

    @iOSXCUITFindBy(xpath = IOS_BANNER_TEXT_XPATH + "[1]")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH + "/*[@resource-id='title']")
    private MobileElement bannerTitleElement;

    @iOSXCUITFindBy(xpath = IOS_BANNER_TEXT_XPATH + "[2]")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH + "/*[@resource-id='message']")
    private MobileElement bannerMessageElement;

    private AppiumDriver<MobileElement> driver;

    public RNBannerPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public boolean verifyBannerLayout(String bannerTitle, String bannerMessage) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clickOnBanner() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCloseButtonPresent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void closeBanner() {
        // TODO Auto-generated method stub

    }
}
