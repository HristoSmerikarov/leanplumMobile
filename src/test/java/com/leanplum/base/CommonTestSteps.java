package com.leanplum.base;

import java.io.File;
import java.net.MalformedURLException;

import com.google.common.base.Strings;
import com.leanplum.tests.helpers.TestAppUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.tests.pushnotification.PushNotification;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class CommonTestSteps extends BaseTest {

    protected AppiumDriver<MobileElement> initiateTest() throws MalformedURLException {
        AppiumDriver<MobileElement> driver = initTest();

        startTest();

        setupApp(driver);

        return driver;
    }

    protected AppiumDriver<MobileElement> initiateTestWithFreshInstallation() throws MalformedURLException {
        AppiumDriver<MobileElement> driver = initTest();

        startTest();

        File rondoAppFile = new File("./resources/RondoApp-debug.apk");

        if (driver.isAppInstalled("com.leanplum.rondo")) {
            driver.removeApp("com.leanplum.rondo");
        }

        driver.installApp(rondoAppFile.getAbsolutePath());

        driver.launchApp();

        setupApp(driver);

        return driver;
    }

    private void setupApp(AppiumDriver<MobileElement> driver) {
        TestStepHelper stepHelper = new TestStepHelper(this);

        AlertPO alert = AlertPO.initialize(driver, sdk);
        stepHelper.dismissAllAlertsOnAppStart(alert);

        TestAppUtils appUtils = new TestAppUtils();
        appUtils.selectAppAndEnv(driver);
    }

    protected void setUserId(AppiumDriver<MobileElement> driver, AppSetupPO appSetupPO, String userId) {
        AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
        adHocPO.click(adHocPO.adhoc);

        adHocPO.setUserId(userId);

        appSetupPO.click(appSetupPO.appSetup);
    }

    protected AdHocPO sendEvent(AppiumDriver<MobileElement> driver, TestStepHelper stepHelper, String message) {
        AlertPO alertPO = AlertPO.initialize(driver, sdk);
        stepHelper.dismissAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
        String userId = appSetupPO.getUserId();
        String deviceId = appSetupPO.getDeviceId();
        AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
        }

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendTrackEvent(adHocPO, message);

        return adHocPO;
    }

    protected AdHocPO sendEventWithParameters(AppiumDriver<MobileElement> driver, TestStepHelper stepHelper,
            String message, String paramKey, String paramValue) {
        AlertPO alertPO = AlertPO.initialize(driver, sdk);
        stepHelper.dismissAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
        String userId = appSetupPO.getUserId();
        String deviceId = appSetupPO.getDeviceId();
        AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
        }

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
        stepHelper.sendTrackEventWithParameters(adHocPO, message, paramKey, paramValue);

        return adHocPO;
    }

    protected AdHocPO sendState(AppiumDriver<MobileElement> driver, TestStepHelper stepHelper, String state) {
        AlertPO alertPO = AlertPO.initialize(driver, sdk);
        stepHelper.dismissAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
        String userId = appSetupPO.getUserId();
        String deviceId = appSetupPO.getDeviceId();
        AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
        }

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendStateEvent(adHocPO, state);

        return adHocPO;
    }

    protected AdHocPO sendUserAttribute(AppiumDriver<MobileElement> driver, TestStepHelper stepHelper,
            String attributeName, String attributeValue) {
        AlertPO alertPO = AlertPO.initialize(driver, sdk);
        stepHelper.dismissAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
        String userId = appSetupPO.getUserId();
        String deviceId = appSetupPO.getDeviceId();
        AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
        }

        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendUserAttribute(adHocPO, attributeName, attributeValue);

        return adHocPO;
    }

    protected void openNotificationsAndOpenByMessage(TestStepHelper stepHelper, PushNotification pushNotification) {
        stepHelper.openNotifications();
        stepHelper.waitForNotificationPresence(pushNotification);
        stepHelper.openPushNotification(pushNotification);
    }

    public boolean verifyCorrectURLIsOpened(AppiumDriver<MobileElement> driver, String url) {
        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
        return mobileBrowserPO.isCorrectURLOpened(url);
    }

    /**
     * On Android there is a chance not to have userId, logic to set one is added
     *
     * @param appSetupPO
     * @return
     */
    public String getUserId(AppSetupPO appSetupPO) {
        String userId = appSetupPO.getUserId();

        if (userId == null || userId.isEmpty()) {
            String deviceId = getDeviceId(appSetupPO);

            NAdHocPO adHocPO = new NAdHocPO(getDriver());
            adHocPO.click(adHocPO.adhoc);

            adHocPO.setUserId(deviceId);

            appSetupPO.clickAppStatus();
        }

        return userId;
    }

    public String getDeviceId(AppSetupPO appSetupPO) {
        return appSetupPO.getDeviceId();
    }
}
