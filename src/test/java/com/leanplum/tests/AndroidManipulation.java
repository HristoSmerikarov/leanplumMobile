package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.html5.Location;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

public class AndroidManipulation extends CommonTestSteps {

    private static final String LOCAL_TRIGGER = "localTrigger";
    private static final String[] interpred = {"42.670200", "23.350600"};
    private static final String[] mezdra = {"43.143500", "23.714600"};

    @Test(description = "Android manipulations for test purposes")
    public void androidManipulation(Method method) {
        ExtentTestManager.startTest(method.getName(), "Android manipulations for test purposes");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AppSetupPO appSetupPO = new AppSetupPO(driver);
        String userId = alertPO.getTextFromElement(appSetupPO.userId);
        String deviceId = alertPO.getTextFromElement(appSetupPO.deviceId);
        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        if (Strings.isNullOrEmpty(userId)) {
            adHocPO.setUserId(deviceId);
            userId = deviceId;
        }

        adHocPO.sendDeviceLocation(interpred[0], interpred[1]);
        //adHocPO.sendDeviceLocation(mezdra[0], mezdra[1]);
       // MobileDriverUtils.waitInMs(5000);
        Response response = TemporaryAPI.getUser(userId);
        System.out.println("Response: " + response.body().prettyPrint());
//
//        adHocPO.sendDeviceLocation(mezdra[0], mezdra[1]);
//        response = TemporaryAPI.getUser(userId);
//        System.out.println("Response: " + response.body().prettyPrint());
        
        
//        MobileDriverUtils.waitInMs(30000);
//        adHocPO.sendDeviceLocation(interpred[0], interpred[1]);
//        response = TemporaryAPI.getUser(userId);
//        System.out.println("Response: " + response.body().prettyPrint());
    }
}
