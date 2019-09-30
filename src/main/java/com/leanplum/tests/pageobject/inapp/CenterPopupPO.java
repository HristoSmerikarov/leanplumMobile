package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class CenterPopupPO extends InAppPopupPO {

	private static final String CENTER_POPUP_TITLE_XPATH = "//*[@resource-id='com.leanplum.rondo:id/title_view']";
	private static final String CENTER_POPUP_XPATH = "*[@resource-id='com.leanplum.rondo:id/container_view']";
	private static final String IOS_STATIC_TEXT_XPATH = "XCUIElementTypeStaticText";
	private static final String IOS_BUTTON_XPATH = "XCUIElementTypeButton";
	private static final String IOS_CENTER_POPUP_BUTTON = "//" + IOS_STATIC_TEXT_XPATH + "/following-sibling::"
			+ IOS_STATIC_TEXT_XPATH + "/following-sibling::" + IOS_BUTTON_XPATH;
	private static final String IOS_CENTER_POPUP_XPATH = IOS_CENTER_POPUP_BUTTON + "/ancestor::XCUIElementTypeOther[2]";

	@iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH)
	@AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]/ancestor::" + CENTER_POPUP_XPATH)
	public MobileElement centerPopup;

	@iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH + "//" + IOS_STATIC_TEXT_XPATH + "[1]")
	@AndroidFindBy(xpath = CENTER_POPUP_TITLE_XPATH + "/*[@text]")
	public MobileElement centerPopupTitle;

	@iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_XPATH + "//" + IOS_STATIC_TEXT_XPATH + "[2]")
	@AndroidFindBy(xpath = "//" + CENTER_POPUP_XPATH + "/*[@class='android.widget.TextView']")
	public MobileElement centerPopupMessage;

	@iOSXCUITFindBy(xpath = IOS_CENTER_POPUP_BUTTON)
	@AndroidFindBy(xpath = "//*[@resource-id='com.leanplum.rondo:id/accept_button']")
	public MobileElement centerPopupButton;

	MobileDriver<MobileElement> driver;

	public CenterPopupPO(MobileDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public boolean verifyCenterPopup(String title, String message, String buttonText) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(centerPopup));
		return verifyInAppPopup(
				ImmutableMap.of(centerPopupTitle, title, centerPopupMessage, message, centerPopupButton, buttonText));
	}

	public void clickAcceptButton() {
		centerPopupButton.click();
	}
}
