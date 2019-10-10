package com.leanplum.tests.pushnotification;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class IOSPushNotification implements PushNotification {

	private static final String PUSH_NOTIFICATION_XPATH = "//XCUIElementTypeScrollView";
	private static final String PUSH_NOTIFICATION_MESSAGE_XPATH = PUSH_NOTIFICATION_XPATH + "[contains(@label,'%s')]";
	private static final String CONTENT_IN_PUSH_NOTIFICATION_XPATH = PUSH_NOTIFICATION_XPATH
			+ "[contains(@label,'%s') and contains(@label,'%s')]";

	private MobileDriver<MobileElement> driver;
	private String message;

	/**
	 * Push notification identified by message since several push notifications from
	 * same app can be displayed
	 * 
	 * @param driver
	 * @param message
	 */
	public IOSPushNotification(MobileDriver<MobileElement> driver, String message) {
		this.driver = driver;
		this.message = message;
	}

	@Override
	public boolean doesContainImage() {
		return doesContainContent("Attachment");
	}

	public boolean doesContainContent(String content) {
		String pictureInPushNotificationFormattedXpath = String.format(CONTENT_IN_PUSH_NOTIFICATION_XPATH, message, content);
		return MobileDriverUtils.doesSelectorMatchAnyElements(driver, pictureInPushNotificationFormattedXpath);
	}

	@Override
	public void waitForPresence() {
		String formattedNotificationXpath = String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message);

		MobileDriverUtils.waitForExpectedCondition(driver, 30,
				ExpectedConditions.visibilityOfElementLocated(By.xpath(formattedNotificationXpath)));
	}

	@Override
	public boolean isAbsent() {
		try {
			MobileDriverUtils.waitForExpectedCondition(driver, 10, ExpectedConditions
					.visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))));
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public void view() {
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions
				.visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))));
		swipeOpen();
	}

	@Override
	public void dismiss() {
		// TODO
	}

	@Override
	public void openNotifications() {
		System.out.println("Is unlocked " + !((IOSDriver) driver).isDeviceLocked());
		if (!((IOSDriver) driver).isDeviceLocked()) {
			((IOSDriver) driver).lockDevice();
		}
		MobileDriverUtils.waitInMs(15000);
		System.out.println("Is locked " + ((IOSDriver) driver).isDeviceLocked());
		if (((IOSDriver) driver).isDeviceLocked()) {
			((IOSDriver) driver).unlockDevice();
		}
		MobileDriverUtils.swipeTopToBottom(driver);
	}

	private void swipeOpen() {
		MobileElement pushNotification = (MobileElement) MobileDriverUtils.waitForExpectedCondition(driver,
				ExpectedConditions
						.visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))));

		Point pushNotificationPoint = pushNotification.getLocation();
		Dimension pushNotDimension = pushNotification.getSize();
		int yMid = pushNotificationPoint.getY() + (pushNotDimension.height / 2);
		int xCenter = pushNotDimension.width / 2;
		PointOption left = PointOption.point(xCenter, yMid);
		PointOption right = PointOption.point(driver.manage().window().getSize().width, yMid);

		new TouchAction(driver).press(left).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(right)
				.perform();
	}
}
