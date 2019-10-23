package com.leanplum.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class NewInstallationTest extends CommonTestSteps {

    @Test(description = "Push Notification's open action is New Action")
    public void confirmWithTriggerEveryTwoTimes(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");

        TestStepHelper stepHelper = new TestStepHelper(this);
        MobileDriver<MobileElement> driver = getDriver();

        System.out.println("lets see");

        driver.closeApp();

        driver.removeApp("com.leanplum.rondo");
        
        System.out.println("lets see");
    }
}
