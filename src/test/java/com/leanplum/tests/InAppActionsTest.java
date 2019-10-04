package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;

public class InAppActionsTest extends CommonTestSteps {

	@Test(description = "Open URL action")
	public void openUrlAction(Method method) {
		ExtentTestManager.startTest(method.getName(), "Open URL action");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

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
	}
}
