package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

@Listeners({ TestListener.class })
public class PushNotificationTest extends CommonTestSteps {

    private static final String LOCAL_TRIGGER = "localTrigger";
    private static final String ATTRIBUTE_NAME = "testAttribute";
    private static final String OPEN_LEANPLUM_URL = "openLeanURL";
    private static final String ATTRIBUTE_VALUE = "testAttr" + Utils.generateRandomNumberInRange(0, 100);
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String RONDO_NOTIFICATION_WITH_IMAGE = "Push Notification with image!";
    private static final String END_TRIGGER = "endTrigger";

    /**
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186429">C186429</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186433">C186433</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is Existing action")
    public void pushNotOpenActionWExistingAction(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Send attribute
            AdHocPO adHocPO = sendUserAttribute(driver, stepHelper, ATTRIBUTE_NAME, ATTRIBUTE_VALUE);

            // Open notifications and verify layout
            PushNotifiationType pn = determinePushNotification(driver);
            PushNotification pushNotification = pn.initialize(driver, RONDO_NOTIFICATION_WITH_IMAGE);

            stepHelper.openNotifications();

            stepHelper.waitForNotificationPresence(pushNotification);

            stepHelper.verifyCondition("Verify that notification contains image", pushNotification.doesContainImage());

            stepHelper.openPushNotification(pushNotification);

            // Verify center popup
            CenterPopupPO centerPopupPO = CenterPopupPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify center popup",
                    centerPopupPO.verifyCenterPopup("Rondo Center Popup", "Rondo is ready!", "Start now!"));

            // Click center popup accept button
            startStep("Click accept on center popup message");
            centerPopupPO.clickAcceptButton();
            endStep();

            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

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
     * @see <a href=
     *      “https://teamplumqa.testrail.com/index.php?/cases/view/186428“>C186428</a>
     * @see <a href=
     *      “https://teamplumqa.testrail.com/index.php?/cases/view/186436”>C186436</a>
     */
    @Parameters({ "id" })
    @Test(groups = {
            "android”, “ios”, “pushNotifications" }, description = "Push Notification’s open action is New Action")
    public void pushNotOpenActionWNewAction(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            AdHocPO adHocPO = sendEvent(driver, stepHelper, LOCAL_TRIGGER);

            PushNotifiationType pn = determinePushNotification(driver);

            // Open push notification
            openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

            // Verify alert layout
            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verification of alert",
                    alertPO.verifyAlertLayout("Rondo Alert", "Warning this is a Rondo Alert!!", "Okay, calm down!"));

            // Confirm alert
            stepHelper.dismissAlert(alertPO);
            MobileDriverUtils.waitInMs(500);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

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
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186430">C186430</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "pushNotifications" }, description = "Push Notification's open action is Open URL")
    public void pushNotOpenURL(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track state
            AdHocPO adHocPO = sendState(driver, stepHelper, OPEN_LEANPLUM_URL);

            PushNotifiationType pn = determinePushNotification(driver);
            openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

            MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
            stepHelper.verifyCondition("Verify opened URL is correct",
                    mobileBrowserPO.isCorrectURLOpened("leanplum.com"));

            // Confirm on resume app
            startStep("Go back to Rondo app");
            mobileBrowserPO.goBack();
            endStep();

            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_TRIGGER);

            stepHelper.closeAppAndReturnToHome(adHocPO);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    private PushNotifiationType determinePushNotification(AppiumDriver<MobileElement> driver) {
        if (driver instanceof AndroidDriver) {
            return PushNotifiationType.ANDROID;
        } else {
            return PushNotifiationType.IOS;
        }
    }
}
