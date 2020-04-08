package com.leanplum.tests.campaigncomposer;

import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class AppInboxCampaign extends CommonTestSteps {

    @Parameters({ "id" })
    @Test(groups = { "release" }, description = "Verification of automatically created campaign with App Inbox message")
    public void appInboxCampaignComposer(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            String deviceId = getDeviceId(appSetupPO);
            setUserId(driver, appSetupPO, userId);

            AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, "inAppVerified");
            MobileDriverUtils.waitInMs(5000);

            Response newsfeedIdResponse = TemporaryAPI.getNewsfeedMessages(deviceId);

            Set<String> newsfeedIds = getNewsfeedMessageIds(newsfeedIdResponse);
            System.out.println("NEWSFEED IDs: " + newsfeedIds);
            for (String newsfeedId : newsfeedIds) {
                TemporaryAPI.deleteNewsfeedMessage(deviceId, userId, newsfeedId);
            }

            driver.closeApp();
            MobileDriverUtils.waitInMs(15000);
            driver.launchApp();

            AlertPO alert = AlertPO.initialize(driver, sdk);
            MobileDriverUtils.waitInMs(5000);
            stepHelper.dismissAllAlertsOnAppStart(alert);

            AppInboxMessagePO appInbox = AppInboxMessagePO.initialize(driver, sdk);
            stepHelper.clickElement(appInbox, appInbox.appinbox, "App Inbox button");

            startStep("Verify app inbox message title is correct");
            endStep(appInbox.isTitleCorrect("Rondo App Inbox message"));

            startStep("Verify app inbox message subtitle is correct");
            endStep(appInbox.isSubTitleCorrect("App inbox subtitle"));

            startStep("Verify app inbox message does contain image");
            endStep(appInbox.doesContainImage());

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, "inAppVerified");
            MobileDriverUtils.waitInMs(5000);
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    private Set<String> getNewsfeedMessageIds(Response response) {
        return new JSONObject(response.body().asString()).getJSONArray("response").getJSONObject(0)
                .getJSONObject("newsfeedMessages").keySet();
    }
}
