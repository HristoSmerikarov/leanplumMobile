package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.IOSPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class IosPushNotificationTest extends CommonTestSteps {

    private static final String IOS_OPTIONS = "iosoptions";
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String END_TRIGGER = "endTrigger";

    /**
     * @see <a
     *      href=" https://teamplumqa.testrail.com/index.php?/cases/view/186434">C186434</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "ios", "pushNotifications" }, description = "Push Notification's with iOS options")
    public void pushNotWithIOSOptions(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            // TODO CHANGE TO ADVANCE TO STATE WITH PARAMETER
            AdHocPO adHocPO = sendState(driver, stepHelper, IOS_OPTIONS);

            // Open notification and confirm that notification is not present
            IOSPushNotification pushNotification = (IOSPushNotification) PushNotifiationType.IOS.initialize(driver,
                    RONDO_PUSH_NOTIFICATION);

            stepHelper.openNotifications();

            stepHelper.waitForNotificationPresence(pushNotification);

            stepHelper.verifyCondition("Verify that notification contains image", pushNotification.doesContainImage());

            stepHelper.verifyCondition("Verify that notification contains subtitle",
                    pushNotification.doesContainContent("Push notification with iOS options subtitle"));

            stepHelper.verifyCondition("Verify that notification contains title",
                    pushNotification.doesContainContent("Push notification with iOS options title"));

            stepHelper.openPushNotification(pushNotification);

            AlertPO alert = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alert);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
