package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MobileBrowserPO extends BasePO {

	private static final String BREADCRUMB_BACK_TO_RONDO_XPATH = "//XCUIElementTypeButton[@label='Return to Rondo-iOS']";

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeApplication[@label='Safari']")
	@AndroidFindBy(id = "com.android.chrome:id/action_bar_root")
	public MobileElement browser;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label='Address']")
	@AndroidFindBy(id = "com.android.chrome:id/url_bar")
	public MobileElement browserUrlBar;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@label='Network connection in progress']")
	@AndroidFindBy(xpath = "//*[@class='android.widget.ProgressBar']")
	public MobileElement browserProgressBar;

	private MobileDriver<MobileElement> driver;

	public MobileBrowserPO(MobileDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}

	public boolean isCorrectURLOpened(String url) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(browser));
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(browserUrlBar));
		return isUrlCorrect(url);
	}

	public void goBack() {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
		} else {
			driver.findElement(By.xpath(BREADCRUMB_BACK_TO_RONDO_XPATH)).click();
		}
	}

	private boolean isUrlCorrect(String url) {
		if (driver instanceof AndroidDriver) {
			return browserUrlBar.getAttribute("text").contains(url);
		} else {
			return browserUrlBar.getAttribute("value").contains(url);
		}
	}
}
