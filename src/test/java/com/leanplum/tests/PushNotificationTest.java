package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.tests.pushnotification.IOSPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;

public class PushNotificationTest extends CommonTestSteps {

    private static final String LOCAL_TRIGGER = "localTrigger";
    private static final String ATTRIBUTE_NAME = "testAttribute";
    private static final String OPEN_LEANPLUM_URL = "openLeanURL";
    private static final String OUTSIDE_APP_TRIGGER = "outsideAppTrigger";
    private static final String CHANNEL_DISABLED = "channelDisabled";
    private static final String IOS_OPTIONS = "iosoptions";
    private static final String ATTRIBUTE_VALUE = "testAttr" + Utils.generateRandomNumberInRange(0, 100);
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String RONDO_NOTIFICATION_WITH_IMAGE = "Push Notification with image!";
    private static final String END_TRIGGER = "endTrigger";

    /**
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186428">C186428</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186436">C186436</a>
    */
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is New Action")
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

        stepHelper.sendTrackEvent(adHocPO, LOCAL_TRIGGER);

        // Open push notification
        openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

        // Verify alert layout
        stepHelper.verifyCondition("Verification of alert",
                alertPO.verifyAlertLayout("Rondo Alert", "Warning this is a Rondo Alert!!", "Okay, calm down!"));

        // Confirm alert
        stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, "Confirm alert");
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        // Send end event
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        driver.closeApp();
    }

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186429">C186429</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186433">C186433</a>
     */
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is Existing action")
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
        stepHelper.clickElement(centerPopupPO, centerPopupPO.centerPopupAcceptButton, "Accept button");

        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        // Send end event
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        driver.closeApp();
    }

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186430">C186430</a>
     */
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is Open URL")
    public void pushNotOpenURL(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Open URL");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // Track event
        AdHocPO adHocPO = sendState(driver, stepHelper, OPEN_LEANPLUM_URL);

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
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        driver.closeApp();
    }

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186431">C186431</a>
     */
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is Muted Inside App")
    public void pushNotMuteInsideApp(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Muted Inside App");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // Track event
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = new AppSetupPO(driver);
        String userId = alertPO.getTextFromElement(appSetupPO.userId);
        String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
            userId = deviceId;
        }

        stepHelper.sendTrackEvent(adHocPO, OUTSIDE_APP_TRIGGER);

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

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, " Ad-Hoc button");

        // Send end event
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        driver.closeApp();
    }

    /**
    * @see <a href=" https://teamplumqa.testrail.com/index.php?/cases/view/186434">C186434</a>
    */
    @Test(groups = { "android", "pushNotifications" }, description = "Push Notification's with iOS options")
    public void pushNotWithIOSOptions(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's with iOS options");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // Track event
        AdHocPO adHocPO = sendEvent(driver, stepHelper, IOS_OPTIONS);

        // Open notification and confirm that notification is not present
        PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
        IOSPushNotification pushNotification = (IOSPushNotification) pn.initialize(driver, RONDO_PUSH_NOTIFICATION);

        stepHelper.openNotifications(pushNotification);

        stepHelper.waitForNotificationPresence(pushNotification);

        stepHelper.verifyCondition("Verify that notification contains image", pushNotification.doesContainImage());

        stepHelper.verifyCondition("Verify that notification contains subtitle",
                pushNotification.doesContainContent("Push notification with iOS options subtitle"));

        stepHelper.verifyCondition("Verify that notification contains title",
                pushNotification.doesContainContent("Push notification with iOS options title"));

        stepHelper.openPushNotification(pushNotification);

        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        // Send end event
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        driver.closeApp();
    }

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186432">C186432</a>
     */
    @Test(groups = { "android",
            "pushNotifications" }, description = "Push Notification's open action is with disabled notification channel")
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
        stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

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
            AppSetupPO appSetupPO = new AppSetupPO(driver);
            appSetupPO.allowIosPushPermission();
        }
    }

    // Temporary
    private void createApp(MobileDriver<MobileElement> driver) {
        AppSetupPO appSetupPO = new AppSetupPO(driver);
        // MobileDriverUtils
        // .waitForExpectedCondition(driver, ExpectedConditions
        // .visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Always
        // Allow\"]")))
        // .click();

        appSetupPO.appPicker.click();

        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(appSetupPO.newApp)).click();

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
