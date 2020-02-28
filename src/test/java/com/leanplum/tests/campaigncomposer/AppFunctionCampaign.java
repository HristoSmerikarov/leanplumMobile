package com.leanplum.tests.campaigncomposer;

import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONObject;
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
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class AppFunctionCampaign extends CommonTestSteps {

    @Parameters({ "id" })
    @Test(groups = { "release" }, description = "Verification of automatically created campaign with App Function open URL message")
    public void appFunctionCampaignComposer(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(appSetupPO, userId);

            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendTrackEvent(adHocPO, "openUrl");
            MobileDriverUtils.waitInMs(5000);
            
            MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
            startStep("Verify correct URL is opened");
            endStep(mobileBrowserPO.isCorrectURLOpened("leanplum.com"));

            // Confirm on resume app
            startStep("Go back to Rondo app");
            mobileBrowserPO.goBack();
            endStep();

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            stepHelper.sendUserAttribute(adHocPO, "autoAppFunct", "attrb"+Utils.generateRandomNumberInRange(0, 100));
            
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
