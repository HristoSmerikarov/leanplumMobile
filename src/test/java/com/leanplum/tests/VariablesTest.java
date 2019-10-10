package com.leanplum.tests;

import java.lang.reflect.Method;
import java.util.Random;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.VariablesPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pushnotification.IOSPushNotification;
import com.leanplum.tests.pushnotification.PushNotifiationType;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class VariablesTest extends CommonTestSteps {

	@Test(description = "Verification of IOS variables")
	public void pushNotWithIOSOptions(Method method) {
		ExtentTestManager.startTest(method.getName(), "Verification of IOS variables");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		String stringVar = "test string var with numbers and cyrilic " + Utils.generateRandomNumberInRange(0, 100)+ " cyrilic words";
		String numVar = Utils.generateRandomNumberInRange(0, 1000);
		Random random = new Random();
		String boolVar = String.valueOf(random.nextBoolean());
		String fileNameVar = "";
		boolean hasImage = true;
		
		//TemporaryAPI.setVars(stringVar, numVar, boolVar, fileNameVar, String.valueOf(hasImage));
		
		// Track event
		 AlertPO alertPO = new AlertPO(driver);
	        stepHelper.acceptAllAlertsOnAppStart(alertPO);
	        
	        VariablesPO variablesPO = new VariablesPO(driver);
	        variablesPO.areValuesCorrect(stringVar, numVar, boolVar, fileNameVar, hasImage);
	}

}
