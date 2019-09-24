package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BasePO {
	@AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
	// @iOSXCUITFindBy(xpath = ""]")
	public MobileElement permissionMessage;

	@AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow\"]")
	public MobileElement allowPermissionButton;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"iOS Push Permission\"]")
	public MobileElement iosPushPermissionButton;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Picker\"]")
	public MobileElement appPicker;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"New App\"]")
	public MobileElement newApp;

	@AndroidFindBy(id = "com.leanplum.rondo:id/userId")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='User ID:']/following-sibling::XCUIElementTypeStaticText")
	public MobileElement userId;

	@AndroidFindBy(id = "com.leanplum.rondo:id/deviceId")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Device ID:']/following-sibling::XCUIElementTypeStaticText")
	public MobileElement deviceId;

	@AndroidFindBy(id = "com.leanplum.rondo:id/app_setup")
	// @iOSXCUITFindBy(xpath = ""]")
	public MobileElement appSetup;

	private MobileDriver<MobileElement> driver;

	public BasePO(MobileDriver<MobileElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public MobileDriver<MobileElement> getDriver() {
		return this.driver;
	}

	public void click(MobileElement element) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(element));
		element.click();
	}

	public boolean verifyText(MobileElement element, String expectedText) {
		return element.getText().equals(expectedText);
	}

	public void allowPermission() {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(permissionMessage));
		click(allowPermissionButton);
	}

	public void allowIosPushPermission() {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(iosPushPermissionButton));
		click(iosPushPermissionButton);

		allowPermission();
	}

	public String getTextFromElement(MobileElement el) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(el));
		if (driver instanceof AndroidDriver) {
			return el.getAttribute("text");
		} else {
			return el.getAttribute("label");
		}
	}
}
