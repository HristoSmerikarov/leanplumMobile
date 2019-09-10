package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;

public class InAppActionsTest extends CommonTestSteps {

    @Test(description = "Open URL action")
    public void confirmWithTriggerEveryTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "Confirm in-app on attribute change every two times");

        TestStepHelper stepHelper = new TestStepHelper(this);
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        AlertPO alert = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alert);

        // Track event
        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        // First trigger
        stepHelper.sendEvent(adHocPO, "request");

        verifyCorrectURLIsOpened(driver, "http://www.leanplum.com/");
        
        stepHelper.clickAndroidKey(AndroidKey.BACK);
        
        stepHelper.sendEvent(adHocPO, "end");
    }
}
