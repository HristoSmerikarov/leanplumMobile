package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leanplum.base.BaseTest;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AndroidPushNotification;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.restassured.response.Response;

public class PushNotificationTest extends BaseTest {

    private static final String LOCAL_TRIGGER = "localTrigger";
    private static final String ATTRIBUTE_NAME = "testAttribute";
    private static final String OPEN_LEANPLUM_URL = "openLeanURL";
    private static final String OUTSIDE_APP_TRIGGER = "outsideAppTrigger";
    private static final String CHANNEL_DISABLED = "channelDisabled";
    private static final String ATTRIBUTE_VALUE = "testAttr" + Utils.generateRandomNumberInRange(0, 100);
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String END_TRIGGER = "endTrigger";

    @BeforeMethod
    public void setUpApp() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        driver.resetApp();
    }

    @Test(description = "Push Notification's open action is New Action")
    public void pushNotOpenActionWNewAction(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        BasePO basePO = new BasePO(driver);
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(LOCAL_TRIGGER);
        step("Send track evetn: " + LOCAL_TRIGGER);

        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openNotificationsAndOpenByMessage(driver, pushNotification);

        AlertPO alertPO = new AlertPO(driver);
        step("Verification of alert", alertPO.verifyAlertLayout("Rondo Alert123", "Warning this is a Rondo Alert!!"));

        alertPO.click(alertPO.confirmAlertButton);
        step("Confirm alert");

        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: " + END_TRIGGER);

        ExtentTestManager.endTest();
    }

    @Test(description = "Push Notification's open action is Existing action")
    public void pushNotOpenActionWExistingAction(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Existing action");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        BasePO basePO = new BasePO(driver);
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendUserAttribute(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        step("Update user attribute" + ATTRIBUTE_NAME + " to " + ATTRIBUTE_VALUE);

        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openAndroidNotifications(driver);
        step("Open notifications");

        pushNotification.waitForPresence();
        step("Wait for notification is visualized");

        step("Verify that notification contains image", pushNotification.doesContainImage());

        pushNotification.open();
        step("Open notifications");

        CenterPopupPO centerPopupPO = new CenterPopupPO(driver);
        step("Verify center popup",
                centerPopupPO.verifyCenterPopup("Rondo Center Popup", "Rondo is ready!", "Start now!"));

        centerPopupPO.clickAcceptButton();
        step("Click accept button");

        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: " + END_TRIGGER);
    }

    @Test(description = "Push Notification's open action is Open URL")
    public void pushNotOpenURL(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Open URL");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        BasePO basePO = new BasePO(driver);
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(OPEN_LEANPLUM_URL);
        step("Send track evetn: " + OPEN_LEANPLUM_URL);

        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openNotificationsAndOpenByMessage(driver, pushNotification);

        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
        step("Verify opened URL is correct", mobileBrowserPO.isCorrectURLOpened("http://www.leanplum.com/"));

        // Confirm on resume app
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        step("Click Back");

        basePO.click(basePO.confirmAlertButton);
        step("Confirm alert");

        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: " + END_TRIGGER);
    }

    @Test(description = "Push Notification's open action is Muted Inside App")
    public void pushNotMuteInsideApp(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is Muted Inside App");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        BasePO basePO = new BasePO(driver);
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(OUTSIDE_APP_TRIGGER);
        step("Send track evetn: " + OUTSIDE_APP_TRIGGER);

        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openAndroidNotifications(driver);
        step("Open notifications");

        step("Verify notification is absent", pushNotification.confirmAnbsence());

        driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
        step("Click Home");

        Response response = TemporaryAPI.post("9f405fddfcf1352a", "outsideAppTrigger");
        System.out.println("Response: " + response.body().asString());

        openNotificationsAndOpenByMessage(driver, pushNotification);

        step("Verify opened URL is correct", verifyCorrectURLIsOpened(driver));

        // Confirm on resume app
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        step("Click Back");
        // Start app alert
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm start alert");
        // Resume app alert
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm resume alert");

        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: " + END_TRIGGER);
    }

    @Test(description = "Push Notification's open action is with disabled notification channel")
    public void pushNotWithDisabledChannel(Method method) {
        ExtentTestManager.startTest(method.getName(),
                "Push Notification's open action is with disabled notification channel");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        BasePO basePO = new BasePO(driver);
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
                .click();
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(CHANNEL_DISABLED);
        step("Send track evetn: " + CHANNEL_DISABLED);

        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openAndroidNotifications(driver);
        step("Open notifications");

        step("Verify notification is absent", pushNotification.confirmAnbsence());

        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        step("Click Back");

        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: " + END_TRIGGER);
    }

    private void openAndroidNotifications(AndroidDriver<MobileElement> driver) {
        driver.openNotifications();
    }

    private void openNotificationsAndOpenByMessage(AndroidDriver<MobileElement> driver,
            AndroidPushNotification pushNotification) {
        openAndroidNotifications(driver);
        step("Open notifications");

        pushNotification.waitForPresence();
        step("Wait for presence of notification");

        pushNotification.open();
        step("Open notification");
    }

    private boolean verifyCorrectURLIsOpened(AndroidDriver<MobileElement> driver) {
        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
        return mobileBrowserPO.isCorrectURLOpened("http://www.leanplum.com/");
    }

}
