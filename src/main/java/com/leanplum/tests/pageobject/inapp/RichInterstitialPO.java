package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RichInterstitialPO extends InAppPopupPO {

    private static final String RICH_INTERSTITIAL_XPATH = "//*[@resource-id='view']";
    private static final String RICH_INTERSTITIAL_ANDROID_CLOSE_BUTTON_XPATH = RICH_INTERSTITIAL_XPATH
            + "/*[@resource-id='close-button']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH)
    public MobileElement richInterstitial;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='close-button']")
    public MobileElement richInterstitialCloseButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='title']")
    public MobileElement richInterstitialTitle;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='message']")
    public MobileElement richInterstitialMessage;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-1']")
    public MobileElement richInterstitialLeftButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-2']")
    public MobileElement richInterstitialRightButton;

    AppiumDriver<MobileElement> driver;

    public RichInterstitialPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean verifyRichInterstitial(String title, String message, String leftButtonText, String rightButtonText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(richInterstitial));
        
        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, RICH_INTERSTITIAL_ANDROID_CLOSE_BUTTON_XPATH)
                && verifyInAppPopup(ImmutableMap.of(richInterstitialTitle, title, richInterstitialMessage, message,
                        richInterstitialLeftButton, leftButtonText, richInterstitialRightButton, rightButtonText));
    }

    public void clickLeftButton() {
        richInterstitialLeftButton.click();
    }

    public void clickRightButton() {
        richInterstitialRightButton.click();
    }
}
