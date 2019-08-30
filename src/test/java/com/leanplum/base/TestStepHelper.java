package com.leanplum.base;

import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AndroidPushNotification;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class TestStepHelper {

    private BaseTest test;

    public TestStepHelper(BaseTest test) {
        this.test = test;
    }

    public void clickElement(BasePO page, MobileElement element, String elementDescription) {
        test.startStep("Click " + elementDescription);
        page.click(element);
        test.endStep();
    }

    public void sendEvent(AdHocPO adHocPO, String message) {
        test.startStep("Send track event: " + message);
        adHocPO.sendTrackEvent(message);
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

    public void openAndroidNotifications() {
        test.startStep("Open notifications");
        ((AndroidDriver<MobileElement>) test.getAppiumDriver()).openNotifications();
        test.endStep();
    }

    public void waitForNotificationPresence(AndroidPushNotification pushNotification) {
        test.startStep("Wait for notification presence");
        pushNotification.waitForPresence();
        test.endStep();
    }

    public void openPushNotification(AndroidPushNotification pushNotification) {
        test.startStep("Open push notification");
        pushNotification.open();
        test.endStep();
    }

    public void confirmNotificationAbsence(AndroidPushNotification pushNotification) {
        test.startStep("Confirm notification absence");
        pushNotification.confirmAbsence();
        test.endStep();
    }

    public void clickAndroidKey(AndroidKey key) {
        test.startStep("Press android key: " + key.name());
        ((AndroidDriver<MobileElement>) test.getAppiumDriver()).pressKey(new KeyEvent().withKey(key));
        test.endStep();
    }

}
