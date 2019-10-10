package com.leanplum.tests;

import java.lang.reflect.Method;

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
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

public class InAppTriggersTest extends CommonTestSteps {

    private static final String TRIGGER_EVENT = "limit";
    private static final String TRIGGER_ATTRIBUTE = "triggerAttribute";
    private static final String ATTRIBUTE_VALUE = "attrValue";
    private static final String CONFIRM_ACCEPT = "Yes, it's here!";
    private static final int LIMIT_PER_SESSION = 2;
    private static final String[] INTERPRED = { "42.670200", "23.350600" };
    private static final String[] MEZDRA = { "43.143500", "23.714600" };
    private static final String[] VARNA = { "43.227300", "27.888400" };

    // //TODO new app needed
    // /**
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186456">C186456</a>
    // */
    // @Test(description = "Alert message triggered on start chained to new Message")
    // public void alertTriggeredOnStartChainedToNewMessage(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Alert message triggered on start chained to new Message");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Verify on app start alert layout
    // AlertPO alert = new AlertPO(driver);
    // stepHelper.verifyCondition("Verify on app start alert layout",
    // alert.verifyAlertLayout("Triggered alert", "Triggered alert with chain", "Yes I see it!"));
    //
    // // Confrim alert
    // stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");
    //
    // // Verify dismissed alert layout
    // stepHelper.verifyCondition("Verify dismissed alert layout",
    // alert.verifyAlertLayout("Dismissed action", "Dismissed alert", "Okay.."));
    //
    // // Confrim alert
    // stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");
    // }
    //
    // @Test(description = "Confirm message triggered on start or resume app chained to new Message")
    // public void alertTriggeredOnStartChainedToNewMessage(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Confirm message triggered on start or resume app chained to new
    // Message");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Verify on app start alert layout
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.verifyCondition("Verify on app start alert layout",
    // alertPO.verifyAlertLayout("Alert shown on start or resume", "You're on start or resume", "Okay, thanks!"));
    //
    // // Confrim alert
    // stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");
    //
    // startStep("Close app");
    // driver.closeApp();
    // endStep();
    //
    // MobileDriverUtils.waitInMs(1000);
    //
    // startStep("Launch app again");
    // driver.launchApp();
    // endStep();
    //
    // stepHelper.verifyCondition("Verify on app start alert layout",
    // alertPO.verifyAlertLayout("Alert shown on start or resume", "You're on start or resume", "Okay, thanks!"));
    //
    // stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");
    // }
    //
    // //TODO not able to locate banner
    // /**
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186463">C186463</a>
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186458">C186458</a>
    // */
    // @Test(description = "Banner message triggered on event with limit per session")
    // public void bannerTriggerEventLimitPerSession(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Banner message triggered on event with limit per session");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
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
    // stepHelper.verifyCondition("Verify banner popup layout", banner.verifyBannerLayout("Center bottom banner",
    // "This banner message is here to remind you something!"));
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
    // }

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/190770">C190770</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186460">C186460</a>
     */
    @Test(description = "Confirm in-app on attribute change every two times")
    public void confirmWithTriggerEveryTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "Confirm in-app on attribute change every two times");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

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
    }

    // //TODO exit/enter region not working through the app
    // /**
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186462">C186462</a>
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
    // * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186461">C186461</a>
    // */
    // @Test(description = "Alert in app on enter region")
    // public void alertInAppOnEnterRegion(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Alert in app on enter region");
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
    // stepHelper.sendDeviceLocation(adHocPO, INTERPRED[0], INTERPRED[1]);
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
    // stepHelper.sendDeviceLocation(adHocPO, VARNA[0], VARNA[1]);
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
    // alert.verifyAlertLayout("Enter region", "I'm in!", "Confirm"));
    // }
}
