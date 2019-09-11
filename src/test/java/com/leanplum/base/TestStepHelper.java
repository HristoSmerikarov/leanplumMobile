package com.leanplum.base;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.PushNotificationUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

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
        ((AndroidDriver<MobileElement>) test.getDriver()).openNotifications();
        test.endStep();
    }

    public void waitForAndroidNotificationPresence(PushNotificationUtils pushNotification) {
        test.startStep("Wait for notification presence");
        pushNotification.waitForPresence();
        test.endStep();
    }

    public void openAndroidPushNotification(PushNotificationUtils pushNotification) {
        test.startStep("Open push notification");
        pushNotification.open();
        test.endStep();
    }

    public void confirmAndroidNotificationAbsence(PushNotificationUtils pushNotification) {
        test.startStep("Confirm notification absence");
        test.endStep(pushNotification.confirmAbsence());
    }

    public void clickAndroidKey(AndroidKey key) {
        test.startStep("Press android key: " + key.name());
        ((AndroidDriver<MobileElement>) test.getDriver()).pressKey(new KeyEvent().withKey(key));
        test.endStep();
    }

    public void acceptAllAlertsOnAppStart(AlertPO page) {
        try {
            MobileDriverUtils.waitForExpectedCondition(test.getDriver(), 10,
                    ExpectedConditions.visibilityOf(page.alertPopup));
        } catch (Exception e) {
            logger.info("No alerts were detected on app start");
        }
        while (MobileDriverUtils.doesSelectorMatchAnyElements(test.getDriver(),
                AlertPO.CONFIRM_ALERT_BUTTON_XPATH)) {
            clickElement(page, page.confirmAlertButton, "Confirm Alert button");
            MobileDriverUtils.waitInMs(500);
        }
    }
}
