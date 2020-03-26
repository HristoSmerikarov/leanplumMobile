package com.leanplum.tests;

import java.lang.reflect.Method;

import javax.sound.midi.Soundbank;

import org.openqa.selenium.html5.Location;
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
import com.leanplum.tests.pageobject.inapp.BannerPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Feature;
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

    /**
    * @see <ahref="https://teamplumqa.testrail.com/index.php?/cases/view/186456">C186456</a>
    */
    @Test(description = "Alert message triggered on start")
    public void alertTriggeredOnStartChainedToNewMessage(Method method) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            startStep("Close app");
            driver.closeApp();
            endStep();

            MobileDriverUtils.waitInMs(5000);

            startStep("Launch app");
            driver.launchApp();
            endStep();

            // Verify on app start alert layout
            AlertPO alert = new AlertPO(driver);
            // TODO for Leanplum QA Production
            stepHelper.verifyCondition("Verify on app start alert layout",
                    alert.verifyAlertLayout("Start", "IAM triggered on Start", "OK"));

            // TODO for Leanplum QA Automation
            // stepHelper.verifyCondition("Verify on app start alert layout",
            // alert.verifyAlertLayout("Alert on start", "Alert displayed on app start", "Тук е!"));

            // Confrim alert
            stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    @Test(description = "Confirm message triggered on start or resume app")
    public void alertTriggeredOnStartOrResume(Method method) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            startStep("Close app");
            driver.closeApp();
            endStep();

            MobileDriverUtils.waitInMs(5000);

            startStep("Launch app");
            driver.launchApp();
            endStep();

            // Verify on app start alert layout
            AlertPO alertPO = new AlertPO(driver);
            // TODO for Leanplum QA Production
            stepHelper.verifyCondition("Verify on app start alert layout",
                    alertPO.verifyAlertLayout("Start", "IAM triggered on Start", "OK"));

            // TODO for Leanplum QA Automation
            // stepHelper.verifyCondition("Verify on app start alert layout",
            // alert.verifyAlertLayout("Alert on start", "Alert displayed on app start", "Тук е!"));

            // Confrim alert
            stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");

            // stepHelper.closeAppAndReturnToHome(alertPO);

            stepHelper.backgroundApp(alertPO, 2000);
            // TODO for Leanplum QA Production
            stepHelper.verifyCondition("Verify on app start alert layout",
                    alertPO.verifyAlertLayout("Resume", "IAM triggered on above", "OK"));

            // TODO for Leanplum QA Automation
            // stepHelper.verifyCondition("Verify on app start or resume alert layout", alertPO.verifyAlertLayout(
            // "Alert on start or resume", "Alert displayed on app start or resume", "Известието е тук!"));

            stepHelper.clickElement(alertPO, alertPO.confirmAlertButton, " confirm alert button");

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    // TODO not able to locate banner
    /**
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/186463">C186463</a>
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/186458">C186458</a>
    */
    @Test(description = "Banner message triggered on event with limit per session")

    public void bannerTriggerEventLimitPerSession(Method method) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            // Track event
            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            BannerPO banner = new BannerPO(driver);
            for (int i = 0; i < LIMIT_PER_SESSION; i++) {
                stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);

                // Stability issue
                MobileDriverUtils.waitInMs(500);

                stepHelper.verifyCondition("Verify banner popup layout", banner.verifyBannerLayout(
                        "Center bottom banner", "This banner message is here to remind you something!"));

                stepHelper.clickElement(banner, banner.banner, "banner");

                MobileDriverUtils.waitInMs(500);
            }

            stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);

            startStep("Verify banner is not shown");
            endStep(!MobileDriverUtils.doesSelectorMatchAnyElements(driver, BannerPO.POPUP_CONTAINER_XPATH));

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    /**
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/190770">C190770</a>
    * @see <a href=
    * "https://teamplumqa.testrail.com/index.php?/cases/view/186460">C186460</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "inAppTriggers" }, description = "Confirm in-app on attribute change every two times")
    public void confirmWithTriggerEveryTwoTimes(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

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

    /**
      * @see <a
      href="https://teamplumqa.testrail.com/index.php?/cases/view/186462">C186462</a>
      */
    @Test(description = "Alert in app on exit region")
    public void alertInAppOnExitRegion(Method method) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AlertPO alertPO = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alertPO);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = alertPO.getTextFromElement(appSetupPO.userId);
            String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            startStep("Set user id, if not set");
            if (Strings.isNullOrEmpty(userId)) {
                adHocPO.setUserId(deviceId);
                userId = deviceId;
            }
            endStep();

            if(driver instanceof AndroidDriver) {
                startStep("Send device location: " + MEZDRA[0] + MEZDRA[1]);
                adHocPO.sendDeviceLocation(MEZDRA[0], MEZDRA[1]);
                endStep();
            }else {
                stepHelper.sendDeviceLocation(adHocPO, MEZDRA[0], MEZDRA[1]);
            }

            Response response = TemporaryAPI.getUser(userId);
            System.out.println("Response: " + response.body().prettyPrint());

            startStep("Region is " + response.jsonPath().getString("$.region"));
            endStep();

            driver.terminateApp("com.leanplum.rondo");
            MobileDriverUtils.waitInMs(2000);
            driver.activateApp("com.leanplum.rondo");
            MobileDriverUtils.waitInMs(2000);

            stepHelper.acceptAllAlertsOnAppStart(alertPO);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
            MobileDriverUtils.waitInMs(1000);
            stepHelper.clickElement(appSetupPO, appSetupPO.appSetup, "Ad-Hoc button");

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            if(driver instanceof AndroidDriver) {
                startStep("Send device location: " + INTERPRED[0] + INTERPRED[1]);
                adHocPO.sendDeviceLocation(INTERPRED[0], INTERPRED[1]);
                endStep();
            }else {
                stepHelper.sendDeviceLocation(adHocPO, INTERPRED[0], INTERPRED[1]);
            }
            
            MobileDriverUtils.waitInMs(10000);

            driver.terminateApp("com.leanplum.rondo");
            MobileDriverUtils.waitInMs(2000);
            driver.activateApp("com.leanplum.rondo");
            MobileDriverUtils.waitInMs(2000);

            // Confirm alert
            AlertPO alert = new AlertPO(driver);
            stepHelper.verifyCondition("Verify alert layout",
                    alert.verifyAlertLayout("Exit region", "I'm out!", "Confirm"));

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    /**
    * @see <a
    href="https://teamplumqa.testrail.com/index.php?/cases/view/186461">C186461</a>
    */
    @Test(description = "Alert in app on enter region")
    public void alertInAppOnEnterRegion(Method method) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AlertPO alertPO = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alertPO);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = alertPO.getTextFromElement(appSetupPO.userId);
            String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            startStep("Set user id, if not set");
            if (Strings.isNullOrEmpty(userId)) {
                adHocPO.setUserId(deviceId);
                userId = deviceId;
            }
            endStep();
            
            if(driver instanceof AndroidDriver) {
                startStep("Send device location: " + MEZDRA[0] + MEZDRA[1]);
                adHocPO.sendDeviceLocation(MEZDRA[0], MEZDRA[1]);
                endStep();
            }else {
                stepHelper.sendDeviceLocation(adHocPO, MEZDRA[0], MEZDRA[1]);
            }

            startStep("Restart app");
            driver.closeApp();
            // Wait to change location
            MobileDriverUtils.waitInMs(5000);
            driver.launchApp();
            endStep();

            Response response = TemporaryAPI.getUser(userId);
            System.out.println("Response: " + response.body().prettyPrint());

            startStep("Region is " + response.jsonPath().getString("$.region"));
            endStep();

            if(driver instanceof AndroidDriver) {
                startStep("Send device location: " + VARNA[0] + VARNA[1]);
                adHocPO.sendDeviceLocation(VARNA[0], VARNA[1]);
                endStep();
            }else {
                stepHelper.sendDeviceLocation(adHocPO, VARNA[0], VARNA[1]);
            }

            startStep("Restart app");
            driver.closeApp();
            // Wait to change location
            MobileDriverUtils.waitInMs(5000);
            driver.launchApp();
            endStep();

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
