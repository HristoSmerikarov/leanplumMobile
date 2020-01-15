package com.leanplum.tests;

import java.lang.reflect.Method;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Listeners;
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
import com.leanplum.tests.pageobject.inapp.BannerPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.tests.pushnotification.PushNotification;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
@Feature("In-App triggers test")
public class InAppTriggersTest extends CommonTestSteps {

    private static final String TRIGGER_EVENT = "limit";
    private static final String TRIGGER_ATTRIBUTE = "triggerAttribute";
    private static final String ATTRIBUTE_VALUE = "attrValue";
    private static final String CONFIRM_ACCEPT = "Yes, it's here!";
    private static final int LIMIT_PER_SESSION = 2;
    private static final String[] INTERPRED = { "42.670200", "23.350600" };
    private static final String[] MEZDRA = { "43.143500", "23.714600" };
    private static final String[] VARNA = { "43.227300", "27.888400" };

    // /**
    // * @see <ahref="https://teamplumqa.testrail.com/index.php?/cases/view/186456">C186456</a>
    // */
    // @Test(description = "Alert message triggered on start")
    // public void alertTriggeredOnStartChainedToNewMessage(Method method) {
    // try {
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Verify on app start alert layout
    // AlertPO alert = new AlertPO(driver);
    // stepHelper.verifyCondition("Verify on app start alert layout",
    // alert.verifyAlertLayout("Alert on start", "Alert displayed on app start", "Тук е!"));
    //
    // // Confrim alert
    // stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // endStep(e.toString(), false);
    // }
    // endTest();
    // }
    //
    // @Test(description = "Confirm message triggered on start or resume app")
    // public void alertTriggeredOnStartOrResume(Method method) {
    // try {
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Verify on app start alert layout
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.verifyCondition("Verify on app start alert layout",
    // alertPO.verifyAlertLayout("Alert on start", "Alert displayed on app start", "Тук е!"));
    //
    // // Confrim alert
    // stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");
    //
    // // stepHelper.closeAppAndReturnToHome(alertPO);
    //
    // stepHelper.backgroundApp(alertPO, 2000);
    //
    // stepHelper.verifyCondition("Verify on app start or resume alert layout", alertPO.verifyAlertLayout(
    // "Alert on start or resume", "Alert displayed on app start or resume", "Известието е тук!"));
    //
    // stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // endStep(e.toString(), false);
    // }
    // endTest();
    // }
    //
    // TODO not able to locate banner
    // /**
    // * @see <a href=
    // * "https://teamplumqa.testrail.com/index.php?/cases/view/186463">C186463</a>
    // * @see <a href=
    // * "https://teamplumqa.testrail.com/index.php?/cases/view/186458">C186458</a>
    // */
    // @Test(description = "Banner message triggered on event with limit per session")
    // public void bannerTriggerEventLimitPerSession(Method method) {
    // try {
    // MobileDriver<MobileElement> driver = getDriver();
    // TestStepHelper stepHelper = new TestStepHelper(this);
    //
    // AlertPO alert = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alert);
    //
    // // Track event
    // AdHocPO adHocPO = new AdHocPO(driver);
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    //
    // BannerPO banner = new BannerPO(driver);
    // for (int i = 0; i < LIMIT_PER_SESSION; i++) {
    // stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);
    //
    // stepHelper.verifyCondition("Verify banner popup layout", banner.verifyBannerLayout(
    // "Center bottom banner", "This banner message is here to remind you something!"));
    //
    // stepHelper.clickElement(banner, banner.banner, "banner");
    //
    // MobileDriverUtils.waitInMs(100);
    // }
    //
    // stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);
    //
    // startStep("Verify banner is not shown");
    // endStep(!MobileDriverUtils.doesSelectorMatchAnyElements(driver, BannerPO.POPUP_CONTAINER_XPATH));
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // endStep(e.toString(), false);
    // }
    // endTest();
    // }

    /**
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/190770">C190770</a>
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/186460">C186460</a>
    */
    @Test(groups = { "android", "ios",
            "inAppTriggers" }, description = "Confirm in-app on attribute change every two times")
    public void confirmWithTriggerEveryTwoTimes(Method method) {

        AppiumDriver<MobileElement> driver = initTest();

        startTest();
        
        try {
            TestStepHelper stepHelper = new TestStepHelper(this);

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            // Track event
            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // First trigger
            stepHelper.sendUserAttribute(adHocPO, TRIGGER_ATTRIBUTE,
                    ATTRIBUTE_VALUE + Utils.generateRandomNumberInRange(0, 100));

            stepHelper.verifyCondition("Verify confirm popup layout is not present",
                    !MobileDriverUtils.doesSelectorMatchAnyElements(driver, ConfirmInAppPO.CONFIRM_IN_APP));

            // Second trigger
            stepHelper.sendUserAttribute(adHocPO, TRIGGER_ATTRIBUTE,
                    ATTRIBUTE_VALUE + Utils.generateRandomNumberInRange(0, 100));

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            stepHelper.verifyCondition("Verify confirm popup layout", confirmInApp.verifyConfirmInApp("ConfirmTitle",
                    "This message appears when attribute changes every 2 times", CONFIRM_ACCEPT, "No!"));

            stepHelper.clickElement(confirmInApp, confirmInApp.confirmAcceptButton,
                    "Confirm popup accept button - " + CONFIRM_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    // //TODO exit/enter region not working through the app
    // /**
    // * @see <a
    // href="https://teamplumqa.testrail.com/index.php?/cases/view/186462">C186462</a>
    // */
    // @Test(description = "Alert in app on exit region")
    // public void alertInAppOnExitRegion(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Alert in app on exit region");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // AppSetupPO appSetupPO = new AppSetupPO(driver);
    // String userId = alertPO.getTextFromElement(appSetupPO.userId);
    // String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
    // AdHocPO adHocPO = new AdHocPO(driver);
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    //
    // startStep("Set user id, if not set");
    // if (Strings.isNullOrEmpty(userId)) {
    // adHocPO.setUserId(deviceId);
    // userId = deviceId;
    // }
    // endStep();
    //
    // stepHelper.sendDeviceLocation(adHocPO, MEZDRA[0], MEZDRA[1]);
    //
    // // Wait to change location
    // MobileDriverUtils.waitInMs(5000);
    //
    // Response response = TemporaryAPI.getUser(userId);
    // System.out.println("Response: " + response.body().prettyPrint());
    //
    // startStep("Region is " + response.jsonPath().getString("$.region"));
    // endStep();
    //
    // // Go to interpred
    // stepHelper.sendDeviceLocation(adHocPO, INTERPRED[0], INTERPRED[1]);
    //
    // // Wait to change location
    // MobileDriverUtils.waitInMs(5000);
    //
    // response = TemporaryAPI.getUser(userId);
    // System.out.println("Response: " + response.body().prettyPrint());
    //
    // startStep("Region is " + response.jsonPath().getString("$.region"));
    // endStep();
    //
    // // Confirm alert
    // AlertPO alert = new AlertPO(driver);
    // stepHelper.verifyCondition("Verify alert layout",
    // alert.verifyAlertLayout("Exit region", "I'm out!", "Confirm"));
    // }
    //
    // /**
    // * @see <a
    // href="https://teamplumqa.testrail.com/index.php?/cases/view/186461">C186461</a>
    // */
    // @Test(description = "Alert in app on enter region")
    // public void alertInAppOnEnterRegion(Method method) {
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // AppSetupPO appSetupPO = new AppSetupPO(driver);
    // String userId = alertPO.getTextFromElement(appSetupPO.userId);
    // String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
    // AdHocPO adHocPO = new AdHocPO(driver);
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    //
    // startStep("Set user id, if not set");
    // if (Strings.isNullOrEmpty(userId)) {
    // adHocPO.setUserId(deviceId);
    // userId = deviceId;
    // }
    // endStep();
    //
    // // stepHelper.sendDeviceLocation(adHocPO, INTERPRED[0], INTERPRED[1]);
    //
    // stepHelper.sendDeviceLocation(adHocPO, VARNA[0], VARNA[1]);
    //
    // // stepHelper.sendDeviceLocation(adHocPO, MEZDRA[0], MEZDRA[1]);

    // MobileDriverUtils.waitInMs(5000);
    //
    // startStep("Close app and wait 5 seconds");
    // driver.closeApp();
    // endStep();
    //
    // MobileDriverUtils.waitInMs(45000);
    //
    // startStep("Launch app and wait 45 seconds for the location to change in User info");
    // driver.launchApp();
    // // MobileDriverUtils.waitInMs(45000);
    // endStep();
    //
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    // userId = alertPO.getTextFromElement(appSetupPO.userId);
    //
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    // startStep("Set user id, if not set");
    // if (Strings.isNullOrEmpty(userId)) {
    // adHocPO.setUserId(deviceId);
    // userId = deviceId;
    // }
    // endStep();
    //
    // MobileDriverUtils.waitInMs(5000);
    //
    // Response response = TemporaryAPI.getUser(userId);
    // System.out.println("Response: " + response.body().prettyPrint());

    //////////////////////////////////////////////////
    //
    // Response response = TemporaryAPI.getUser(userId);
    // System.out.println("Response: " + response.body().prettyPrint());
    //
    // // startStep("City is " + response.jsonPath().getString("$..city"));
    // // endStep();
    //
    // // Go to interpred
    // stepHelper.sendDeviceLocation(adHocPO, VARNA[0], VARNA[1]);
    // MobileDriverUtils.waitInMs(45000);
    //
    // startStep("Close app and wait 10 seconds");
    // driver.closeApp();
    // endStep();
    //
    // MobileDriverUtils.waitInMs(5000);
    //
    // startStep("Launch app and wait 45 seconds for the location to change in User info");
    // driver.launchApp();
    // endStep();
    //
    // System.out.println("i'm in");
    //
    // response = TemporaryAPI.getUser(userId);
    // System.out.println("Response: " + response.body().prettyPrint());
    //
    // // startStep("City is " + response.jsonPath().getString("$..city"));
    // // endStep();
    //
    // startStep("Accept alert on start");
    // AlertPO alert = new AlertPO(driver);
    // alert.click(alert.confirmAlertButton);
    // endStep();
    //
    // // Confirm alert
    // stepHelper.verifyCondition("Verify alert layout",
    // alert.verifyAlertLayout("Enter region", "I'm in!", "Confirm"));
    // }
}
