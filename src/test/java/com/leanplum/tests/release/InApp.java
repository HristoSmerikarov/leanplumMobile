package com.leanplum.tests.release;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.json.JSONObject;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppInboxMessagePO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.RichInterstitialPO;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.testng.TestInstanceParameter;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class InApp extends CommonTestSteps {

    /**
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186442">C186442</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186443">C186443</a>
    * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186444">C186444</a>
    */
    @Parameters({ "id" })
    @Test(groups = { "release" })
    public void confirmWithTriggerEveryTwoTimes(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();
            
            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "automationUser";
            setUserId(appSetupPO, userId);

            // Track event
            AdHocPO adHocPO = sendEvent(driver, stepHelper, "richInterstitial");
            
         // Verify rich interstitial
            RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
            stepHelper.verifyCondition("Verify rich interstitial popup layout",
                    richInterstitial.verifyRichInterstitial("There is a new update!", "Искаш ли да го свалиш?",
                            "Accept", "Decline", true));
            
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

}
