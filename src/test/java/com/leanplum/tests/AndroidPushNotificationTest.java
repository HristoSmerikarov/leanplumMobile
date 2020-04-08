package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;

@Listeners({ TestListener.class })
public class AndroidPushNotificationTest extends CommonTestSteps {

    private static final String OUTSIDE_APP_TRIGGER = "outsideAppTrigger";
    private static final String CHANNEL_DISABLED = "channelDisabled";
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String END_TRIGGER = "endTrigger";

    /**
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/186431">C186431</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "android",
            "pushNotifications" }, description = "Push Notification's open action is Muted Inside App")
    public void pushNotMuteInsideApp(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
            String userId = appSetupPO.getUserId();
            String deviceId = appSetupPO.getDeviceId();
            NAdHocPO adHocPO = new NAdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            if (Strings.isNullOrEmpty(userId)) {
                adHocPO.setUserId(deviceId);
                userId = deviceId;
            }

            stepHelper.sendTrackEvent(adHocPO, OUTSIDE_APP_TRIGGER);

            // Open notification and confirm that notification is not present
            PushNotification pushNotification = PushNotifiationType.ANDROID.initialize(driver, RONDO_PUSH_NOTIFICATION);

            Utils.swipeTopToBottom(driver);

            stepHelper.confirmNotificationAbsence(pushNotification);

            Utils.swipeBottomToTop(driver);

            stepHelper.stopAppSession(adHocPO);

            // Technical wait to actually background the app
            MobileDriverUtils.waitInMs(5000);

            startStep("Track event " + OUTSIDE_APP_TRIGGER + " with API call");
            TemporaryAPI.track(userId, OUTSIDE_APP_TRIGGER);
            endStep();

            MobileDriverUtils.waitInMs(15000);

            Utils.swipeTopToBottom(driver);
            stepHelper.waitForNotificationPresence(pushNotification);
            stepHelper.openPushNotification(pushNotification);

            // Confirm on resume app
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, " Ad-Hoc button");

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

            stepHelper.closeAppAndReturnToHome(adHocPO);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    /**
    * @see <a
    href="https://teamplumqa.testrail.com/index.php?/cases/view/186432">C186432</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "android",
            "pushNotifications" }, description = "Push Notification's open action is with disabled notification channel")
    public void pushNotWithDisabledChannel(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            AdHocPO adHocPO = sendEvent(driver, stepHelper, CHANNEL_DISABLED);

            // Open notification and confirm that notification is not present
            PushNotification pushNotification = PushNotifiationType.ANDROID.initialize(driver, RONDO_PUSH_NOTIFICATION);

            stepHelper.openNotifications();

            stepHelper.confirmNotificationAbsence(pushNotification);

            // Confirm on resume app
            stepHelper.clickAndroidKey(AndroidKey.BACK);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

            stepHelper.closeAppAndReturnToHome(adHocPO);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
