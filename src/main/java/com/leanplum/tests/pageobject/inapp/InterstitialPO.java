package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class InterstitialPO extends InAppPopupPO {

    private static final String ANDROID_INTERSTITIAL_XPATH = "//*[@resource-id='com.leanplum.rondo:id/container_view']/ancestor::*[@resource-id='android:id/content']";
    private static final String ANDROID_INTERSTITIAL_ACCEPT_BUTTON_XPATH = ANDROID_INTERSTITIAL_XPATH
            + "//*[@resource-id='com.leanplum.rondo:id/accept_button']";
    private static final String ANDROID_INTERSTITIAL_CLOSE_BUTTON_XPATH = ANDROID_INTERSTITIAL_XPATH
            + "//*[@resource-id='com.leanplum.rondo:id/close_button']";
    private static final String ANDROID_INTERSTITIAL_IMAGE_XPATH = ANDROID_INTERSTITIAL_XPATH
            + "//*[@class='android.widget.ImageView']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_XPATH)
    public MobileElement interstitial;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_CLOSE_BUTTON_XPATH)
    public MobileElement interstitialCloseButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_XPATH
            + "//*[@resource-id='com.leanplum.rondo:id/title_view']/*[@class='android.widget.TextView']")
    public MobileElement interstitialTitleElement;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_IMAGE_XPATH)
    public MobileElement interstitialMessageImage;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_XPATH + "//*[@class='android.widget.TextView' and not(@recource-id)]")
    public MobileElement interstitialMessageElement;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_INTERSTITIAL_ACCEPT_BUTTON_XPATH)
    public MobileElement interstitialAcceptButton;

    AppiumDriver<MobileElement> driver;

    public InterstitialPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean verifyInterstitialLayout(String interstitialTitle, String interstitialMessage, String acceptButton) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(interstitial));
        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_INTERSTITIAL_CLOSE_BUTTON_XPATH)
                && MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_INTERSTITIAL_ACCEPT_BUTTON_XPATH)
                && MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_INTERSTITIAL_IMAGE_XPATH)
                && verifyInAppPopup(ImmutableMap.of(interstitialTitleElement, interstitialTitle,
                        interstitialMessageElement, interstitialMessage, interstitialAcceptButton, acceptButton));
    }

    public void clickAcceptButton() {
        interstitialAcceptButton.click();
    }
}
