package com.leanplum.tests.classic;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.tests.pageobject.nativesdk.AppSetupPO;
import com.leanplum.tests.pageobject.nativesdkinapp.RichInterstitialPO;
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

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "automationUser";
            setUserId(appSetupPO, userId);

            // Track event
            NAdHocPO adHocPO = sendEvent(driver, stepHelper, "richInterstitial");
            
         // Verify rich interstitial
            RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
            stepHelper.verifyCondition("Verify rich interstitial popup layout",
                    richInterstitial.verifyRichInterstitial("There is a new update!", "Do you want to download it?",
                            "Accept", "Decline", true));
            
        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

}
