package com.leanplum.base;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.tests.pushnotification.PushNotificationUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;

public class TestStepHelper {

    private BaseTest test;
    private static final Logger logger = LoggerFactory.getLogger(TestStepHelper.class);

    public TestStepHelper(BaseTest test) {
        this.test = test;
    }

    public void clickElement(BasePO page, MobileElement element, String elementDescription) {
        test.startStep("Click " + elementDescription);
        page.click(element);
        test.endStep();
    }

    public void sendTrackEvent(AdHocPO adHocPO, String message) {
        test.startStep("Send track event: " + message);
        adHocPO.sendTrackEvent(message);
        test.endStep();
    }

    public void sendTrackEventWithParameters(AdHocPO adHocPO, String message, String paramKey, String paramValue) {
        test.startStep("Send track event: " + message);
        adHocPO.sendTrackEventWithParameter(message, paramKey, paramValue);
        test.endStep();
    }

    public void sendDeviceLocation(AdHocPO adHocPO, String latitude, String longitude) {
        test.startStep("Send location coordinates: " + latitude + " and " + longitude);
        adHocPO.sendDeviceLocation(latitude, longitude);
        test.endStep();
    }

    public void sendStateEvent(AdHocPO adHocPO, String state) {
        test.startStep("Advance to state: " + state);
        adHocPO.sendStateEvent(state);
        test.endStep();
    }

    public void sendUserAttribute(AdHocPO adHocPO, String attributeName, String attributeValue) {
        test.startStep("Send user attribute: " + attributeName + " with value: " + attributeValue);
        adHocPO.sendUserAttribute(attributeName, attributeValue);
        test.endStep();
    }

    public void verifyCondition(String conditionDescription, boolean condition) {
        test.startStep(conditionDescription);
        test.endStep(condition);
    }

    //TODO
    //Clear all push notifications before
    public void openNotifications() {
        test.startStep("Open notifications");
        PushNotificationUtils pushNotificationUtils = new PushNotificationUtils(test.getDriver());
        pushNotificationUtils.openNotifications();
        test.endStep();
    }

    public void waitForNotificationPresence(PushNotification pushNotification) {
        test.startStep("Wait for notification presence");
        pushNotification.waitForPresence();
        test.endStep();
    }

    public void openPushNotification(PushNotification pushNotification) {
        test.startStep("Open push notification");
        pushNotification.view();
        test.endStep();
    }

    public void confirmNotificationAbsence(PushNotification pushNotification) {
        test.startStep("Confirm notification absence");
        test.endStep(pushNotification.isAbsent());
    }

    public void clickAndroidKey(AndroidKey key) {
        test.startStep("Press android key: " + key.name());
        ((AndroidDriver<MobileElement>) test.getDriver()).pressKey(new KeyEvent().withKey(key));
        test.endStep();
    }

    public void backgroundApp(BasePO basePage, long millis) {
        	basePage.getDriver().runAppInBackground(Duration.ofMillis(millis));
    }

    public void resumeUserSession(BasePO basePage) {
            basePage.getDriver().launchApp();
    }

    public void closeAppAndReturnToHome(BasePO basePage) {
        if (basePage.getDriver() instanceof IOSDriver) {
            basePage.getDriver().closeApp();
        } else {
            basePage.getDriver().closeApp();
            clickAndroidKey(AndroidKey.HOME);
        }
    }

    public void acceptAllAlertsOnAppStart(AlertPO page) {
        try {
            MobileDriverUtils.waitForExpectedCondition(test.getDriver(), 10,
                    ExpectedConditions.visibilityOf(page.alertPopup));
        } catch (Exception e) {
            System.out.println("Exception found: " + System.currentTimeMillis());
            System.out.println("Is alert present: " + page.isAlertPresent());
            logger.info("No alerts were detected on app start");
        }

        while (page.isAlertPresent()) {
            clickElement(page, page.confirmAlertButton, "Confirm Alert button");
            MobileDriverUtils.waitInMs(500);
        }
    }

    public void endTest() {
        test.getDriver().closeApp();
    }

    public void uninstallApp() {
        test.getDriver().closeApp();
        test.getDriver().removeApp("com.leanplum.rondo");
    }

    public void installApp() {
        if (test.getDriver() instanceof AppiumDriver) {
            test.getDriver().installApp("C:\\Leanplum-Mobile-Automation\\resources\\RondoApp-debug.apk");
        } else {
            // TODO find way to fresh install from TestFlight
        }
    }
}
