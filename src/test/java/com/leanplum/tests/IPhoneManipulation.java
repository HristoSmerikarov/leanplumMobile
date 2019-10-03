package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class IPhoneManipulation extends CommonTestSteps {

	@Test(description = "Push Notification's open action is New Action")
	public void confirmWithTriggerEveryTwoTimes(Method method) {
		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		// Track event
		AlertPO alertPO = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alertPO);

		((IOSDriver) driver).lockDevice();

		MobileDriverUtils.waitInMs(20000);

		((IOSDriver) driver).unlockDevice();

		MobileDriverUtils.swipeTopToBottom(driver);

	}
}
