package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RichInterstitialPO extends InAppPopupPO {

	private static final String RICH_INTERSTITIAL_XPATH = "//*[@resource-id='view']";
	private static final String RICH_INTERSTITIAL_ANDROID_CLOSE_BUTTON_XPATH = RICH_INTERSTITIAL_XPATH
			+ "/*[@resource-id='close-button']";
	private static final String IOS_RICH_INTERSTITIAL_XPATH = "//XCUIElementTypeWebView[@visible='true']";
	private static final String IOS_RICH_INTERSTITIAL_TEXT_XPATH = "(" + IOS_RICH_INTERSTITIAL_XPATH
			+ "//XCUIElementTypeStaticText)";
	private static final String IOS_RICH_INTERSTITIAL_CLOSE_BUTTON_XPATH = IOS_RICH_INTERSTITIAL_XPATH
			+ "//XCUIElementTypeOther[not(*)]";

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_XPATH)
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH)
  private MobileElement richInterstitial;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_XPATH + "//XCUIElementTypeOther[not(*)]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='close-button']")
	public MobileElement richInterstitialCloseButton;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH + "[1]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "/*[@resource-id='title']")
  private MobileElement richInterstitialTitle;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH + "[2]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='message']")
  private MobileElement richInterstitialMessage;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH + "[3]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-1']")
	public MobileElement richInterstitialLeftButton;

	@iOSXCUITFindBy(xpath = IOS_RICH_INTERSTITIAL_TEXT_XPATH + "[4]")
	@AndroidFindBy(xpath = RICH_INTERSTITIAL_XPATH + "//*[@resource-id='button-2']")
	public MobileElement richInterstitialRightButton;

	private AppiumDriver<MobileElement> driver;

	public RichInterstitialPO(AppiumDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public boolean verifyRichInterstitial(String title, String message, String leftButtonText, String rightButtonText, boolean hasCloseButton) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(richInterstitial));

		return hasCloseButton==isCloseButtonPresent()
				&& verifyInAppPopup(ImmutableMap.of(richInterstitialTitle, title, richInterstitialMessage, message,
						richInterstitialLeftButton, leftButtonText, richInterstitialRightButton, rightButtonText));
	}

	public void clickLeftButton() {
		richInterstitialLeftButton.click();
	}

	public void clickRightButton() {
		richInterstitialRightButton.click();
	}

	private boolean isCloseButtonPresent() {
		if (driver instanceof AndroidDriver) {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver, RICH_INTERSTITIAL_ANDROID_CLOSE_BUTTON_XPATH);
		} else {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_RICH_INTERSTITIAL_CLOSE_BUTTON_XPATH);
		}
	}
}
