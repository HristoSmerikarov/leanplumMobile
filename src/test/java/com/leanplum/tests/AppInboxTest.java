package com.leanplum.tests;

import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class AppInboxTest extends CommonTestSteps {

    protected String description;
    private static final String APP_INBOX_EVENT = "appinbox";

    /**
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186442">C186442</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186443">C186443</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186444">C186444</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios", "appinbox" })
    public void confirmWithTriggerEveryTwoTimes(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();
            
            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String deviceId = getDeviceId(appSetupPO);
            String userId = getUserId(appSetupPO);

            AdHocPO adHocPO = new AdHocPO(driver);

            if (Strings.isNullOrEmpty(userId)) {
                stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
                adHocPO.setUserId(deviceId);
                userId = deviceId;
            }
            
            startStep("Get newsfeed messages");
            Response newsfeedIdResponse = TemporaryAPI.getNewsfeedMessages(deviceId);
            endStep();
            
            
            Set<String> newsfeedIds = getNewsfeedMessageIds(newsfeedIdResponse);
            startStep("Delete newsfeed messages with IDs: "+newsfeedIds);
            for(String newsfeedId : newsfeedIds) {
                TemporaryAPI.deleteNewsfeedMessage(deviceId, userId, newsfeedId);
            }
            endStep();

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, APP_INBOX_EVENT);

            startStep("Restart app");
            driver.closeApp();
            MobileDriverUtils.waitInMs(30000);
            driver.launchApp();
            endStep();
            
            AlertPO alert = new AlertPO(driver);
            MobileDriverUtils.waitInMs(5000);
            stepHelper.acceptAllAlertsOnAppStart(alert);
            
            startStep("Wait for device Id to appear");
            MobileDriverUtils.waitForExpectedCondition(driver, 30, ExpectedConditions.textToBePresentInElement(appSetupPO.deviceId, deviceId));
            endStep();

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
            stepHelper.verifyCondition("Verify alert layout", alert.verifyAlertLayout("AlertMessage",
                    "Alert message after opening app inbox message", "It's here!"));

            // Confrim alert
            stepHelper.clickElement(alert, alert.confirmAlertButton, "It's here!");

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

    /**
    * On Android there is a chance not to have userId, logic to set one is added
    *
    * @param appSetupPO
    * @return
    */
    private String getUserId(AppSetupPO appSetupPO) {
        String userId = appSetupPO.getTextFromElement(appSetupPO.userId);

        if (userId == null || userId.isEmpty()) {
            String deviceId = getDeviceId(appSetupPO);

            AdHocPO adHocPO = new AdHocPO(getDriver());
            adHocPO.click(adHocPO.adhoc);

            adHocPO.setUserId(deviceId);

            appSetupPO.click(appSetupPO.appSetup);
        }

        return userId;
    }

    private String getDeviceId(AppSetupPO appSetupPO) {
        return appSetupPO.getTextFromElement(appSetupPO.deviceId);
    }
}
