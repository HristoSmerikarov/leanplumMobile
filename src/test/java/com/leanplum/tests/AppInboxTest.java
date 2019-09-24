package com.leanplum.tests;

import java.lang.reflect.Method;
import java.util.Set;

import org.testng.annotations.Test;

import org.json.JSONObject;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

public class AppInboxTest extends CommonTestSteps {

    private static final String APP_INBOX_EVENT = "appinbox";

    @Test(description = "App Inbox message verifiation")
    public void confirmWithTriggerEveryTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "App Inbox message verifiation");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        AlertPO alert = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alert);

        BasePO basePage = new BasePO(driver);
        String deviceId = getDeviceId(basePage);
        String userId = getUserId(basePage);

        Response newsfeedIdResponse = TemporaryAPI.getNewsfeedMessages(deviceId);

        Set<String> newsfeedIds = getNewsfeedMessageIds(newsfeedIdResponse);
        newsfeedIds.forEach(newsfeedId -> {
            TemporaryAPI.deleteNewsfeedMessage(deviceId, userId, newsfeedId);
        });

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendEvent(adHocPO, APP_INBOX_EVENT);

        driver.closeApp();
        MobileDriverUtils.waitInMs(30000);
        driver.launchApp();

        stepHelper.acceptAllAlertsOnAppStart(alert);

        AppInboxMessagePO appInbox = new AppInboxMessagePO(driver);
        stepHelper.clickElement(appInbox, appInbox.appinbox, "App Inbox button");

        startStep("Wait for app inbox message");
        appInbox.waitForInboxMessage();
        endStep();

        startStep("Verify app inbox message title is correct");
        endStep(appInbox.isTitleCorrect("Update your profile!"));

        startStep("Verify app inbox message subtitle is correct");
        endStep(appInbox.isSubTitleCorrect("Please add more info.."));

        startStep("Verify app inbox message does contain image");
        endStep(appInbox.doesContainImage());

        startStep("Perform read action");
        appInbox.performReadAction();
        endStep();

        // Verify alert layout
        alert = new AlertPO(driver);
        stepHelper.verifyCondition("Verify alert layout",
                alert.verifyAlertLayout("AlertMessage", "Alert message after opening app inbox message", "It's here!"));

        // Confrim alert
        stepHelper.clickElement(alert, alert.confirmAlertButton, "It's here!");

        driver.closeApp();
    }

    private Set<String> getNewsfeedMessageIds(Response response) {
        return new JSONObject(response.body().asString()).getJSONArray("response").getJSONObject(0)
                .getJSONObject("newsfeedMessages").keySet();
    }

    private String getUserId(BasePO basePage) {
        String userId = basePage.userId.getAttribute("text");

        if (userId == null || userId.isEmpty()) {
            String deviceId = getDeviceId(basePage);

            AdHocPO adHocPO = new AdHocPO(getDriver());
            adHocPO.click(adHocPO.adhoc);

            adHocPO.setUserId(deviceId);
        }

        basePage.click(basePage.appSetup);

        return userId;
    }

    private String getDeviceId(BasePO basePage) {
        String deviceId = basePage.deviceId.getAttribute("text");
        return deviceId;
    }
}
