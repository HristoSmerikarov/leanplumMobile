package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class VariablesPO extends BasePO {

	private static final String IOS_VAR_STRING_XPATH = "//XCUIElementTypeStaticText[@name='varString:']";
	private static final String IOS_VAR_NUMBER_XPATH = "//XCUIElementTypeStaticText[@name='varNumber:']";
	private static final String IOS_VAR_BOOL_XPATH = "//XCUIElementTypeStaticText[@name='varBool']";
	private static final String IOS_VAR_FILE_XPATH = "//XCUIElementTypeStaticText[@name='varFile:']";
	private static final String IOS_VAR_FILE_IMAGE_XPATH = "//XCUIElementTypeStaticText[@name='varFile (image):']";
	private static final String FOLLOWING_SIBLING_TEXT_XPATH = "/following-sibling::XCUIElementTypeStaticText";
	private static final String FOLLOWING_SIBLING_IMAGE_XPATH = "/following-sibling::XCUIElementTypeImage";
	private static final String ANDROID_IMAGE_XPATH = "";

	@AndroidFindBy(id = "com.leanplum.rondo:id/varStringLabel")
	@iOSXCUITFindBy(xpath = IOS_VAR_STRING_XPATH)
	public MobileElement stringLabel;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varString")
	@iOSXCUITFindBy(xpath = IOS_VAR_STRING_XPATH + FOLLOWING_SIBLING_TEXT_XPATH)
	public MobileElement stringValue;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varNumberLabel")
	@iOSXCUITFindBy(xpath = IOS_VAR_NUMBER_XPATH)
	public MobileElement numberLabel;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varNumber")
	@iOSXCUITFindBy(xpath = IOS_VAR_NUMBER_XPATH + FOLLOWING_SIBLING_TEXT_XPATH)
	public MobileElement numberValue;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varBoolLabel")
	@iOSXCUITFindBy(xpath = IOS_VAR_BOOL_XPATH)
	public MobileElement boolLabel;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varBool")
	@iOSXCUITFindBy(xpath = IOS_VAR_BOOL_XPATH + FOLLOWING_SIBLING_TEXT_XPATH)
	public MobileElement boolValue;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varFileLabel")
	@iOSXCUITFindBy(xpath = IOS_VAR_FILE_XPATH)
	public MobileElement fileLabel;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varFile")
	@iOSXCUITFindBy(xpath = IOS_VAR_FILE_XPATH + FOLLOWING_SIBLING_TEXT_XPATH)
	public MobileElement fileValue;

	@AndroidFindBy(id = "com.leanplum.rondo:id/varFileImageLabel")
	@iOSXCUITFindBy(xpath = IOS_VAR_FILE_IMAGE_XPATH)
	public MobileElement fileImageLabel;

	@AndroidFindBy(xpath = ANDROID_IMAGE_XPATH)
	@iOSXCUITFindBy(xpath = IOS_VAR_FILE_IMAGE_XPATH + FOLLOWING_SIBLING_IMAGE_XPATH)
	public MobileElement fileImage;

	private AppiumDriver<MobileElement> driver;

	public VariablesPO(AppiumDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public boolean areValuesCorrect(String expectedString, String expectedNumber, String expectedBoolean,
			String expectedFile, boolean hasImage) {

		return getTextFromElement(stringValue).equals(expectedString)
				&& getTextFromElement(numberValue).equals(expectedNumber)
				&& getTextFromElement(boolValue).equals(expectedBoolean)
				&& getTextFromElement(fileValue).equals(expectedFile) && hasImage() == hasImage;
	}

	private boolean hasImage() {
		if (driver instanceof AndroidDriver) {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_IMAGE_XPATH);
		} else {
			return MobileDriverUtils.doesSelectorMatchAnyElements(driver,
					IOS_VAR_FILE_IMAGE_XPATH + FOLLOWING_SIBLING_IMAGE_XPATH);
		}
	}
}
