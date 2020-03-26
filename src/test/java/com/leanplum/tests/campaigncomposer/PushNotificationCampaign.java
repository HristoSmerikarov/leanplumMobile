package com.leanplum.tests.campaigncomposer;

import java.lang.reflect.Method;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.AndroidPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PushNotificationCampaign extends CommonTestSteps {
    @Parameters({ "id" })
    @Test(groups = {
            "release" }, description = "Verification of automatically created campaign with Push Notification message")
    public void pushNotCampaign(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "automationUser";
            setUserId(appSetupPO, userId);

            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // Exit campaign, if entered before
            stepHelper.sendTrackEvent(adHocPO, "pushVerified");
            MobileDriverUtils.waitInMs(5000);

            stepHelper.sendUserAttribute(adHocPO, "automation",
                    "attrbValue" + Utils.generateRandomNumberInRange(0, 10));

            // Open notifications and verify layout
            AndroidPushNotification pushNotification = (AndroidPushNotification) PushNotifiationType.ANDROID
                    .initialize(driver, "Rondo Push Notification from Campaign");

            stepHelper.openNotifications();

            stepHelper.waitForNotificationPresence(pushNotification);

            stepHelper.verifyCondition("Rondo Push Notification from Campaign", pushNotification.doesContainImage());

            stepHelper.openPushNotification(pushNotification);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, "pushVerified");

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
