package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RichInterstitialPO extends InAppPopupPO {

	private static final String RICH_INTERSTITIAL_XPATH = "//*[@resource-id='view']";
	private static final String RICH_INTERSTITIAL_ANDROID_CLOSE_BUTTON_XPATH = RICH_INTERSTITIAL_XPATH
			+ "/*[@resource-id='close-button']";
	private static final String IOS_RICH_INTERSTITIAL_XPATH = "//XCUIElementTypeWebView[@visible='true']";
	private static final String IOS_RICH_INTERSTITIAL_TEXT_XPATH = "("+IOS_RICH_INTERSTITIAL_XPATH
			+ "//XCUIElementTypeStaticText)";

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_XPATH)
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH)
	public MobileElement richInterstitial;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_XPATH+"//XCUIElementTypeOther[not(*)]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='close-button']")
	public MobileElement richInterstitialCloseButton;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH+"[1]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='title']")
	public MobileElement richInterstitialTitle;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH+"[2]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='message']")
	public MobileElement richInterstitialMessage;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH+"[3]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-1']")
	public MobileElement richInterstitialLeftButton;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH+"[4]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-2']")
	public MobileElement richInterstitialRightButton;

	MobileDriver<MobileElement> driver;

	public RichInterstitialPO(MobileDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
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
