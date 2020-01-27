package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class WebInterstitialPO extends InAppPopupPO {

	private static final String WEB_INTERSTITIAL_CLOSE_BUTTON = "//*[@resource-id='com.leanplum.rondo:id/close_button']";
	private static final String IOS_WEB_INTERSTITIAL_CLOSE_BUTTON = "//XCUIElementTypeOther[@visible='true']/XCUIElementTypeButton";
	private static final String WEB_INTERSTITIAL_CONTENT_XPATH = "//*[@class='android.webkit.WebView']//ancestor::*[@resource-id='android:id/content']";

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeWebView[@visible='true']")
	@AndroidFindBy(xpath = WEB_INTERSTITIAL_CONTENT_XPATH)
	public MobileElement webInterstitialWindow;

	@iOSXCUITFindBy(xpath = IOS_WEB_INTERSTITIAL_CLOSE_BUTTON)
	@AndroidFindBy(xpath = WEB_INTERSTITIAL_CONTENT_XPATH + WEB_INTERSTITIAL_CLOSE_BUTTON)
	public MobileElement webInterstitialCloseButton;

	private AppiumDriver<MobileElement> driver;

	public WebInterstitialPO(AppiumDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public boolean verifyWebInterstitial() {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(webInterstitialWindow));
		return isWebInterstitialCloseButtonPresent();
	}

	public void closeWebInterstitial() {
		webInterstitialCloseButton.click();
	}

	public boolean isWebInterstitialCloseButtonPresent() {
		if (driver instanceof AndroidDriver) {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver, WEB_INTERSTITIAL_CLOSE_BUTTON);
		} else {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_WEB_INTERSTITIAL_CLOSE_BUTTON);
		}
	}
}
