package com.leanplum.tests.helpers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileDriverUtils {

	private static final int WAIT_TAIMEOUT = 60;

	public static boolean doesSelectorMatchAnyElements(WebDriver driver, String xpathSelector) {
		turnOffImplicitWaits(driver);
		boolean matchAnyElements = !driver.findElements(By.xpath(xpathSelector)).isEmpty();
		turnOnImplicitWaits(driver);
		return matchAnyElements;
	}

	public static <T> T waitForExpectedCondition(MobileDriver<MobileElement> driver, int waitTimeout,
			ExpectedCondition<T> expectedCondition) {
		WebDriverWait wait = new WebDriverWait(driver, waitTimeout);
		turnOffImplicitWaits(driver);
		T result = null;
		result = wait.until(expectedCondition);
		turnOnImplicitWaits(driver);
		return result;
	}

	public static <T> T waitForExpectedCondition(MobileDriver<MobileElement> driver,
			ExpectedCondition<T> expectedCondition) {
		return waitForExpectedCondition(driver, WAIT_TAIMEOUT, expectedCondition);
	}

	public static void waitInMs(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void swipeTopToBottom(MobileDriver<MobileElement> driver) {
		Dimension screenSize = driver.manage().window().getSize();

		int yMargin = 5;
		int xMid = screenSize.width / 2;
		PointOption top = PointOption.point(xMid, yMargin);
		PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

		new TouchAction(driver).press(top).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1500))).moveTo(bottom)
				.perform();
	}

	public static void swipeBottomToTop(MobileDriver<MobileElement> driver) {
		Dimension screenSize = driver.manage().window().getSize();

		int yMargin = 5;
		int xMid = screenSize.width / 2;
		PointOption top = PointOption.point(xMid, yMargin);
		PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

		new TouchAction(driver).press(bottom).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(top)
				.perform();
	}

	/**
	 * Turn off Implicit Waits
	 */
	private static void turnOffImplicitWaits(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * Turn on Implicit Waits
	 */
	private static void turnOnImplicitWaits(WebDriver driver) {
		turnOnImplicitWaits(60, driver);
	}

	/**
	 * Turn on Implicit Waits
	 */
	private static void turnOnImplicitWaits(int secondsToWait, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(secondsToWait, TimeUnit.SECONDS);
	}
}
