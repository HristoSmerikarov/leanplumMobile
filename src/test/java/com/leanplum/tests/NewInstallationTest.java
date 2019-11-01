package com.leanplum.tests;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class NewInstallationTest extends CommonTestSteps {

    private static final String IN_APP_LIMIT_STATE = "inapplimit";
    private static final String RONDO_PUSH_NOTIFICATION = "Push notification";

    @BeforeMethod
    public void installApp() {
        File rondoAppFile = new File("./resources/RondoApp-debug.apk");
        System.out.println(rondoAppFile.getAbsolutePath());

        if (this.getDriver().isAppInstalled("com.leanplum.rondo")) {
            this.getDriver().removeApp("com.leanplum.rondo");
        }

        this.getDriver().installApp(rondoAppFile.getAbsolutePath());

        this.getDriver().launchApp();
    }

    @Test(description = "In-app with limit two times ever")
    public void inAppWithLimitTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "In-app with limit two times ever");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // Track state
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = new AppSetupPO(driver);
        String userId = alertPO.getTextFromElement(appSetupPO.userId);
        String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
            userId = deviceId;
        }

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
        for (int i = 0; i < 2; i++) {
            stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

            stepHelper.verifyCondition("Verify confirm popup is present",
                    confirmInApp.verifyConfirmInApp("In-App with Limit", "2 times ever", "Виждам го!", "Не!"));

            stepHelper.clickElement(confirmInApp, confirmInApp.confirmAcceptButton, "Accept in-app");
        }

        // Trigger third time the state
        stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

        stepHelper.verifyCondition("Verify confirm popup is present", confirmInApp.verifyConfirmInAppIsAbsent());

        // Restart app to verify new session does not affect the limit
        driver.closeApp();

        MobileDriverUtils.waitInMs(5000);

        driver.launchApp();

        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        for (int i = 0; i < 2; i++) {
            stepHelper.sendTrackEvent(adHocPO, IN_APP_LIMIT_STATE);

            stepHelper.verifyCondition("Verify confirm popup is present", confirmInApp.verifyConfirmInAppIsAbsent());
        }
    }

    @Test(description = "Push Notification's on user first open app")
    public void pushNotificationOnUserFirstStartApp(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's on user first open app");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // Track state
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        String userId = "RondoTestUser" + Utils.generateRandomNumberInRange(0, 100);

        startStep("Set User ID: " + userId);
        adHocPO.setUserId(userId);
        endStep();

        PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();

        // Open push notification
        openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));

        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        stepHelper.closeAppAndReturnToHome(adHocPO);
    }

    // TODO Uncomment when new app is created
    // /**
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186428">C186428</a>
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186436">C186436</a>
    // */
    // @Test(groups = { "android", "ios",
    // "pushNotifications" }, description = "Push Notification's open action is New Action")
    // public void pushNotOpenActionWNewAction(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Track event
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
    //
    // // Open push notification
    // openNotificationsAndOpenByMessage(stepHelper, pn.initialize(driver, RONDO_PUSH_NOTIFICATION));
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
    // }
}
