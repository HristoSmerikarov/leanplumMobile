package com.leanplum.tests.release;

import java.lang.reflect.Method;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.AndroidPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PushNotification extends CommonTestSteps {
    @Parameters({ "id" })
    @Test(groups = { "release" }, description = "Verification of automatically created Push Notification message")
    public void pushNotOpenActionWExistingAction(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "automationUser";
            setUserId(appSetupPO, userId);

            // Send attribute
            TemporaryAPI.sendMessage(userId, "5718727950598144");

            // Open notifications and verify layout
            AndroidPushNotification pushNotification = (AndroidPushNotification) PushNotifiationType.ANDROID
                    .initialize(driver, "You've reached new milestone!");

            stepHelper.openNotifications();

            stepHelper.waitForNotificationPresence(pushNotification);

            stepHelper.verifyCondition("Verify that notification contains image", pushNotification.doesContainImage());

            stepHelper.openPushNotification(pushNotification);

            // Verify center popup
            AlertPO alert = new AlertPO(driver);
            stepHelper.verifyCondition("Verify alert layout", alert.verifyAlertLayout("Congrats!",
                    "You will acuire your present on next purchase.", "Thank you!"));

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
