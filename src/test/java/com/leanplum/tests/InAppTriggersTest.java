package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.BannerPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class InAppTriggersTest extends CommonTestSteps {

    private static final String TRIGGER_EVENT = "limit";
    private static final int LIMIT_PER_SESSION = 2;

    @Test(description = "Alert message triggered on start chained to new Message")
    public void alertTriggeredOnStartChainedToNewMessage(Method method) {
        ExtentTestManager.startTest(method.getName(), "Alert message triggered on start chained to new Message");

        TestStepHelper stepHelper = new TestStepHelper(this);
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        // Verify on app start alert layout
        AlertPO alert = new AlertPO(driver);
        stepHelper.verifyCondition("Verify on app start alert layout",
                alert.verifyAlertLayout("Triggered alert", "Triggered alert with chain", "Yes I see it!"));

        // Confrim alert
        stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");

        // Verify dismissed alert layout
        stepHelper.verifyCondition("Verify dismissed alert layout",
                alert.verifyAlertLayout("Dismissed action", "Dismissed alert", "Okay.."));

        // Confrim alert
        stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert button");
    }

    @Test(description = "Banner message triggered on event with limit per session")
    public void bannerTriggerEventLimitPerSession(Method method) {
        ExtentTestManager.startTest(method.getName(), "Banner message triggered on event with limit per session");

        TestStepHelper stepHelper = new TestStepHelper(this);
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        AlertPO alert = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alert);

        // Track event
        AdHocPO adHocPO = sendEvent(driver, stepHelper, TRIGGER_EVENT);

        BannerPO banner = new BannerPO(driver);
        for (int i = 0; i < LIMIT_PER_SESSION; i++) {
            stepHelper.verifyCondition("Verify banner popup layout", banner.verifyBannerLayout("Center bottom banner",
                    "This banner message is here to remind you something!"));

            stepHelper.clickElement(banner, banner.bannerCloseButton, "banner close icon");
            
            stepHelper.sendEvent(adHocPO, TRIGGER_EVENT);
        }
        
        startStep("Verify banner is not shown");
        endStep(!MobileDriverUtils.doesSelectorMatchAnyElements(driver, BannerPO.POPUP_CONTAINER_XPATH));
    }

}
