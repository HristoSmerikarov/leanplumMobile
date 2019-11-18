package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class IPhoneManipulation extends CommonTestSteps {

//	@Test(description = "Push Notification's open action is New Action")
//	public void confirmWithTriggerEveryTwoTimes(Method method) {
//		ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");
//
//		TestStepHelper stepHelper = new TestStepHelper(this);
//		MobileDriver<MobileElement> driver = getDriver();
//
//		driver.removeApp("com.leanplum.Rondo-iOS");
//		
//		System.out.println("stop");
//		
//	}
}
