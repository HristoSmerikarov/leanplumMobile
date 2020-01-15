package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BannerPO extends InAppPopupPO {

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
    public MobileElement bannerTitleElement;

    @iOSXCUITFindBy(xpath = IOS_BANNER_TEXT_XPATH + "[2]")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH + "/*[@resource-id='message']")
    public MobileElement bannerMessageElement;

    private AppiumDriver<MobileElement> driver;

    public BannerPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean verifyBannerLayout(String bannerTitle, String bannerMessage) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(banner));

		return isCloseButtonPresent() && verifyInAppPopup(
				ImmutableMap.of(bannerTitleElement, bannerTitle, bannerMessageElement, bannerMessage));
	}

    public void clickOnBanner() {
        banner.click();
    }

    public boolean isCloseButtonPresent() {
        if (driver instanceof AndroidDriver) {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_BANNER_CLOSE_BUTTON_XPATH);
        } else {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_BANNER_CLOSE_BUTTON);
        }
    }
}
