package com.leanplum.tests.classic;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.RichInterstitialPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class InApp extends CommonTestSteps {

    @Parameters({ "id" })
    @Test(groups = { "release" })
    public void confirmWithTriggerEveryTwoTimes(Method method, String id) {

        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(driver, appSetupPO, userId);

            // Track event
            AdHocPO adHocPO = sendEvent(driver, stepHelper, "richInterstitial");

            // Verify rich interstitial
            RichInterstitialPO richInterstitial = RichInterstitialPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify rich interstitial popup layout", richInterstitial.verifyRichInterstitial(
                    "There is a new update!", "Do you want to download it?", "Accept", "Decline", true));

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

}
