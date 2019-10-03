package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.html5.Location;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

public class AndroidManipulation extends CommonTestSteps {

    private static final String LOCAL_TRIGGER = "localTrigger";

    @Test(description = "Push Notification's open action is New Action")
    public void confirmWithTriggerEveryTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        // // Track event
        // AlertPO alertPO = new AlertPO(driver);
        // stepHelper.acceptAllAlertsOnAppStart(alertPO);

        // createApp(driver);

        // PushNotifiationType pn = PushNotifiationType.valueOfEnum(getTestConfig().getOS()).get();
        // allowIOSPushPermission(driver, pn);

        System.out.println("Driver: " + driver.location().toString());

        Response response = TemporaryAPI.getUser("9f405fddfcf1352a");
        System.out.println("Response: " + response.body().prettyPrint());
        
        driver.setLocation(new Location(43.123456, 24.123456, 638.39996));
        
        System.out.println("Driver: " + driver.location().toString());
        
        response = TemporaryAPI.getUser("9f405fddfcf1352a");
        System.out.println("Response: " + response.body().prettyPrint());
    }
}
