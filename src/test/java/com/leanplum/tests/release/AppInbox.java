package com.leanplum.tests.release;

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
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class AppInbox extends CommonTestSteps {

    protected String description;
    private static final String APP_INBOX_EVENT = "appInboxAutomation";

    /**
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186442">C186442</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186443">C186443</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186444">C186444</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "release" })
    public void appInboxRelease(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();
            
            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String deviceId = getDeviceId(appSetupPO);
            String userId = "automationUser";
            setUserId(appSetupPO, userId);

            Response newsfeedIdResponse = TemporaryAPI.getNewsfeedMessages(deviceId);

            Set<String> newsfeedIds = getNewsfeedMessageIds(newsfeedIdResponse);
            newsfeedIds.forEach(newsfeedId -> TemporaryAPI.deleteNewsfeedMessage(deviceId, userId, newsfeedId));

            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, APP_INBOX_EVENT);

            driver.closeApp();
            MobileDriverUtils.waitInMs(30000);
            driver.launchApp();

            AlertPO alert = new AlertPO(driver);
            MobileDriverUtils.waitInMs(5000);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            AppInboxMessagePO appInbox = new AppInboxMessagePO(driver);
            stepHelper.clickElement(appInbox, appInbox.appinbox, "App Inbox button");

            startStep("Wait for app inbox message");
            appInbox.waitForInboxMessage();
            endStep();

            startStep("Verify app inbox message title is correct");
            endStep(appInbox.isTitleCorrect("You've reached new milestone!"));

            startStep("Verify app inbox message subtitle is correct");
            endStep(appInbox.isSubTitleCorrect("You've reached new milestone!"));

            startStep("Verify app inbox message does contain image");
            endStep(appInbox.doesContainImage());

            startStep("Perform read action");
            appInbox.performReadAction();
            endStep();

            // Verify alert layout
            StarRatingPO starRating = new StarRatingPO(driver);
            stepHelper.verifyCondition("Verify star rating popup layout",
                    starRating.verifyStarRating("Survey question", 5, "I hate it!", "I love it!"));

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

    private String getDeviceId(AppSetupPO appSetupPO) {
        return appSetupPO.getTextFromElement(appSetupPO.deviceId);
    }
}
