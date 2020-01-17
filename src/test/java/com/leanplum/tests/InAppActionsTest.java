package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class InAppActionsTest extends CommonTestSteps {

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186445">C186445</a>
     */
    @Parameters({"id"})
    @Test(groups = { "android", "ios", "openAction" }, description = "Open URL action")
    public void openUrlAction(Method method, String id) {

        try {
        AppiumDriver<MobileElement> driver = initTest();

        startTest();
       
            TestStepHelper stepHelper = new TestStepHelper(this);

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            // Track event
            AdHocPO adHocPO = new AdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // First trigger
            stepHelper.sendStateEvent(adHocPO, "request");

            MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
            startStep("Verify correct URL is opened");
            endStep(mobileBrowserPO.isCorrectURLOpened("leanplum.com"));

            // Confirm on resume app
            startStep("Go back to Rondo app");
            mobileBrowserPO.goBack();
            endStep();

            stepHelper.acceptAllAlertsOnAppStart(alert);

            stepHelper.sendTrackEvent(adHocPO, "end");

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }
}
