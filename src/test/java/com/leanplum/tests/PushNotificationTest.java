package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.appiumdriver.TestConfig;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.tests.pushnotification.AndroidPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.ios.IOSDriver;
import io.restassured.response.Response;

public class PushNotificationTest extends CommonTestSteps {

	private static final String LOCAL_TRIGGER = "localTrigger";
	private static final String ATTRIBUTE_NAME = "testAttribute";
	private static final String OPEN_LEANPLUM_URL = "openLeanURL";
	private static final String OUTSIDE_APP_TRIGGER = "outsideAppTrigger";
	private static final String CHANNEL_DISABLED = "channelDisabled";
	private static final String ATTRIBUTE_VALUE = "testAttr" + Utils.generateRandomNumberInRange(0, 100);
	private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
	private static final String RONDO_NOTIFICATION_WITH_IMAGE = "Push Notification with image!";
	private static final String END_TRIGGER = "endTrigger";

	@Test(description = "Push Notification's open action is New Action")
	public void pushNotOpenActionWNewAction(Method method) {
		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Track event
		AlertPO alertPO = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();

		AdHocPO adHocPO = new AdHocPO(driver);
		stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

		stepHelper.sendEvent(adHocPO, LOCAL_TRIGGER);

		// Open push notification
		openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

		// Verify alert layout
		stepHelper.verifyCondition("Verification of alert",
				alertPO.verifyAlertLayout("Rondo Alert", "Warning this is a Rondo Alert!!", "Okay, calm down!"));

		// Confirm alert
		stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, "Confirm alert");
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		// Send end event
		stepHelper.sendEvent(adHocPO, END_TRIGGER);

		driver.closeApp();
	}

	@Test(description = "Push Notification's open action is Existing action")
	public void pushNotOpenActionWExistingAction(Method method) {
		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Existing action");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Send attribute
		AdHocPO adHocPO = sendUserAttribute(driver, stepHelper, ATTRIBUTE_NAME, ATTRIBUTE_VALUE);

		// Open notifications and verify layout
		PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();

		PushNotification pushNotification = pn.initialize(driver, RONDO_NOTIFICATION_WITH_IMAGE);

		stepHelper.openNotifications(pushNotification);

		stepHelper.waitForNotificationPresence(pushNotification);

		stepHelper.verifyCondition("Verify that notification contains image", pushNotification.doesContainImage());

		stepHelper.openPushNotification(pushNotification);

		// Verify center popup
		CenterPopupPO centerPopupPO = new CenterPopupPO(driver);
		stepHelper.verifyCondition("Verify center popup",
				centerPopupPO.verifyCenterPopup("Rondo Center Popup", "Rondo is ready!", "Start now!"));

		// Click center popup accept button
		stepHelper.clickElement(centerPopupPO, centerPopupPO.centerPopupButton, "Accept button");

		AlertPO alertPO = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		// Send end event
		stepHelper.sendEvent(adHocPO, END_TRIGGER);

		driver.closeApp();
	}

	@Test(description = "Push Notification's open action is Open URL")
	public void pushNotOpenURL(Method method) {
		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Open URL");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Track event
		AdHocPO adHocPO = sendEvent(driver, stepHelper, OPEN_LEANPLUM_URL);

		PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
		openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

		MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
		stepHelper.verifyCondition("Verify opened URL is correct", mobileBrowserPO.isCorrectURLOpened("leanplum.com"));

		// Confirm on resume app
		startStep("Go back to Rondo app");
		mobileBrowserPO.goBack();
		endStep();

		AlertPO alertPO = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		// Send end event
		stepHelper.sendEvent(adHocPO, END_TRIGGER);

		driver.closeApp();
	}

	@Test(description = "Push Notification's open action is Muted Inside App")
	public void pushNotMuteInsideApp(Method method) {
		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Muted Inside App");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Track event
		AlertPO alertPO = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		String userId = alertPO.getTextFromElement(alertPO.userId);
		String deviceId = alertPO.getTextFromElement(alertPO.deviceId);
		AdHocPO adHocPO = new AdHocPO(driver);
		stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

		if (Strings.isNullOrEmpty(userId)) {
			adHocPO.setUserId(deviceId);
			userId = deviceId;
		}

		stepHelper.sendEvent(adHocPO, OUTSIDE_APP_TRIGGER);

		// Open notification and confirm that notification is not present
		PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
		PushNotification pushNotification = pn.initialize(driver, RONDO_PUSH_NOTIFICATION);

		MobileDriverUtils.swipeTopToBottom(driver);

		stepHelper.confirmNotificationAbsence(pushNotification);

		stepHelper.pauseUserSession(adHocPO);
		TemporaryAPI.track(userId, OUTSIDE_APP_TRIGGER);

		MobileDriverUtils.waitInMs(5000);

		stepHelper.resumeUserSession(adHocPO);
		MobileDriverUtils.swipeTopToBottom(driver);
		stepHelper.waitForNotificationPresence(pushNotification);
		stepHelper.openPushNotification(pushNotification);

		// Confirm on resume app
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		// Uncomment if needed
		// // Start app alert
		// stepHelper.clickElement(adHocPO, adHocPO.confirmAlertButton, " start alert
		// confirmation");
		//
		// // Resume app alert
		// stepHelper.clickElement(adHocPO, adHocPO.confirmAlertButton, " resume
		// alert confirmation");

		stepHelper.clickElement(adHocPO, adHocPO.adhoc, " Ad-Hoc button");

		// Send end event
		stepHelper.sendEvent(adHocPO, END_TRIGGER);

		driver.closeApp();
	}

	// Only for Android
	@Test(description = "Push Notification's open action is with disabled notification channel")
	public void pushNotWithDisabledChannel(Method method) {
		ExtentTestManager.startTest(method.getName(),
				"Push Notification's open action is with disabled notification channel");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Track event
		AdHocPO adHocPO = sendEvent(driver, stepHelper, CHANNEL_DISABLED);

		// Open notification and confirm that notification is not present
		PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
		PushNotification pushNotification = pn.initialize(driver, RONDO_PUSH_NOTIFICATION);

		stepHelper.openNotifications(pushNotification);

		stepHelper.confirmNotificationAbsence(pushNotification);

		// Confirm on resume app
		stepHelper.clickAndroidKey(AndroidKey.BACK);

		stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

		// Send end event
		stepHelper.sendEvent(adHocPO, END_TRIGGER);

		driver.closeApp();
		// stepHelper.clickAndroidKey(AndroidKey.HOME);
	}

	private void openNotificationsAndOpenByMessage(TestStepHelper stepHelper, PushNotification pushNotification) {
		stepHelper.openNotifications(pushNotification);
		stepHelper.waitForNotificationPresence(pushNotification);
		stepHelper.openPushNotification(pushNotification);
	}

	private void allowIOSPushPermission(MobileDriver<MobileElement> driver, PushNotifiationType pn) {
		if (pn == PushNotifiationType.IOS) {
			BasePO basePO = new BasePO(driver);
			basePO.allowIosPushPermission();
		}
	}

	// Temporary
	private void createApp(MobileDriver<MobileElement> driver) {
		BasePO basePO = new BasePO(driver);
		// MobileDriverUtils
		// .waitForExpectedCondition(driver, ExpectedConditions
		// .visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Always
		// Allow\"]")))
		// .click();

		basePO.appPicker.click();

		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.newApp)).click();

		driver.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys("testRondo");
		driver.findElement(By.xpath("//XCUIElementTypeTextField[2]"))
				.sendKeys("app_ve9UCNlqI8dy6Omzfu1rEh6hkWonNHVZJIWtLLt6aLs");
		driver.findElement(By.xpath("//XCUIElementTypeTextField[3]"))
				.sendKeys("prod_D5ECYBLrRrrOYaFZvAFFHTg1JyZ2Llixe5s077Lw3rM");
		driver.findElement(By.xpath("//XCUIElementTypeTextField[4]"))
				.sendKeys("dev_cKF5HMpLGqhbovlEGMKjgTuf8AHfr2Jar6rrnNhtzQ0");

		driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Create\"]"));

		MobileDriverUtils
				.waitForExpectedCondition(driver, ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name=\"testRondo\"]")))
				.click();

		driver.resetApp();
	}
}
