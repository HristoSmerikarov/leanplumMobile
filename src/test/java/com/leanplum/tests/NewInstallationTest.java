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
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class NewInstallationTest extends CommonTestSteps {

    private static final String IN_APP_LIMIT_STATE = "inapplimit";
    private static final String RONDO_PUSH_NOTIFICATION = "Push notification on first start";
    private static String userID = "";

    @Parameters({ "id" })
    @Test(description = "Push Notification's on user first open app", alwaysRun = true)
    public void pushNotificationOnUserFirstStartApp(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTestWithFreshInstallation();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track state
            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            if (userID.equals("")) {
                userID = "RondoTestUser" + Utils.generateRandomNumberInRange(0, 1000);
            }

            startStep("Set User ID: " + userID);
            adHocPO.setUserId(userID);
            endStep();

            // Sleep for 5 seconds to wait for UserId change
            MobileDriverUtils.waitInMs(5000);

            // Open push notification
            PushNotification pushNotification = PushNotifiationType.ANDROID.initialize(driver, RONDO_PUSH_NOTIFICATION);
            openNotificationsAndOpenByMessage(stepHelper, pushNotification);

            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            stepHelper.closeAppAndReturnToHome(adHocPO);
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    @Parameters({ "id" })
    @Test(description = "In-app with limit two times ever", dependsOnMethods = "pushNotificationOnUserFirstStartApp")
    public void inAppWithLimitTwoTimes(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTestWithFreshInstallation();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track state
            AlertPO alertPO = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            if (userID.equals("")) {
                userID = "RondoTestUser" + Utils.generateRandomNumberInRange(0, 1000);
            }

            startStep("Set User ID: " + userID);
            adHocPO.setUserId(userID);
            endStep();

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            ConfirmInAppPO confirmInApp = ConfirmInAppPO.initialize(driver, sdk);
            for (int i = 0; i < 2; i++) {
                stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

                stepHelper.verifyCondition("Verify confirm popup is present",
                        confirmInApp.verifyConfirmInApp("In-App with Limit", "2 times ever", "Виждам го!", "Не!"));

                startStep("Click accept on confirm message");
                confirmInApp.acceptConfirmMessage();
                endStep();
            }

            // Trigger third time the state
            stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

            stepHelper.verifyCondition("Verify confirm popup is present", confirmInApp.verifyConfirmInAppIsAbsent());

            // Restart app to verify new session does not affect the limit
            driver.closeApp();

            MobileDriverUtils.waitInMs(5000);

            driver.launchApp();

            stepHelper.dismissAllAlertsOnAppStart(alertPO);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            for (int i = 0; i < 2; i++) {
                stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

                stepHelper.verifyCondition("Verify confirm popup is present",
                        confirmInApp.verifyConfirmInAppIsAbsent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    // // TODO Uncomment when new app is created
    // /**
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186428">C186428</a>
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186436">C186436</a>
    // */
    // @Parameters({ "id" })
    // @Test(groups = { "android", "ios",
    // "pushNotifications" }, description = "Push Notification's open action is New Action")
    // public void pushNotOpenActionWNewAction(Method method) {
    // try {
    // AppiumDriver<MobileElement> driver = initiateTest();
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    //
    // // Track event
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // // Open push notification
    // PushNotification pushNotification = PushNotifiationType.ANDROID.initialize(driver, RONDO_PUSH_NOTIFICATION);
    // openNotificationsAndOpenByMessage(stepHelper, pushNotification);
    //
    // // Verify alert layout
    // stepHelper.verifyCondition("Verification of alert",
    // alertPO.verifyAlertLayout("Rondo Alert", "Warning this is a Rondo Alert!!", "Okay, calm down!"));
    //
    // // Confirm alert
    // stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, "Confirm alert");
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // stepHelper.closeAppAndReturnToHome(alertPO);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // endStep(e.toString(), false);
    // }
    // endTest();
    // }
}
