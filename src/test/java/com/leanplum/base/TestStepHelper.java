package com.leanplum.base;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.PushNotification;

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

    public void openNotifications(PushNotification pushNotification) {
        test.startStep("Open notifications");
        pushNotification.openNotifications();
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

    public void pauseUserSession(BasePO basePage) {
        if (basePage.getDriver() instanceof IOSDriver) {
            basePage.getDriver().closeApp();
        } else {
            clickAndroidKey(AndroidKey.HOME);
        }
    }

    public void resumeUserSession(BasePO basePage) {
        if (basePage.getDriver() instanceof IOSDriver) {
            basePage.getDriver().launchApp();
        }
    }

    public void acceptAllAlertsOnAppStart(AlertPO page) {
        try {
            System.out.println("Trying to find: " + System.currentTimeMillis());
            MobileDriverUtils.waitForExpectedCondition(test.getDriver(), 5,
                    ExpectedConditions.visibilityOf(page.alertPopup));
        } catch (Exception e) {
            System.out.println("Exception found: " + System.currentTimeMillis());
            System.out.println("Is alert present: " + page.isAlertPresent());
            // System.out.println("No alerts were detected on app start");
            logger.info("No alerts were detected on app start");
        }

        while (page.isAlertPresent()) {
            clickElement(page, page.confirmAlertButton, "Confirm Alert button");
            MobileDriverUtils.waitInMs(500);
        }
    }
}
