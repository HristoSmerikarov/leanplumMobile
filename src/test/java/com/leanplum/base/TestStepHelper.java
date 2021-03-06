package com.leanplum.base;

import java.time.Duration;

import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.tests.pushnotification.PushNotificationUtils;

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

    public void sendTrackEventWithParameters(AdHocPO adHocPO, String eventName, String paramKey, String paramValue) {
        test.startStep("Send track event: " + eventName);
        adHocPO.sendTrackEventWithParameter(eventName, paramKey, paramValue);
        test.endStep();
    }

    public void sendDeviceLocation(AdHocPO adHocPO, String latitude, String longitude) {
        test.startStep("Send location coordinates: " + latitude + " and " + longitude);
        System.out.println("SEND COORDINATES: "+latitude + " and " + longitude);
        adHocPO.getDriver().setLocation(new Location(Double.valueOf(latitude), Double.valueOf(longitude), 40.0));
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
    
    public void stopAppSession(BasePO basePage) {
        if (basePage.getDriver() instanceof IOSDriver) {
            basePage.getDriver().closeApp();
        } else {
            clickAndroidKey(AndroidKey.HOME);
        }
    }

    public void closeAppAndReturnToHome(BasePO basePage) {
        if (basePage.getDriver() instanceof IOSDriver) {
            basePage.getDriver().closeApp();
        } else {
            clickAndroidKey(AndroidKey.HOME);
        }
    }

    public void acceptAllAlertsOnAppStart(AlertPO page) {
        try {
            MobileDriverUtils.waitForExpectedCondition(page.getDriver(), 10,
                    ExpectedConditions.visibilityOf(page.alertPopup));
        } catch (Exception e) {
            logger.info("No alerts were detected on app start");
        }

        while (page.isAlertPresent()) {
            clickElement(page, page.confirmAlertButton, "Confirm Alert button");
            MobileDriverUtils.waitInMs(500);
        }
    }
}
