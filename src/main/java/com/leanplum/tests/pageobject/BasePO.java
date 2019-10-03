package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePO {

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

	public String getTextFromElement(MobileElement el) {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(el));
		if (driver instanceof AndroidDriver) {
			return el.getAttribute("text");
		} else {
			return el.getAttribute("label");
		}
	}
}
