package com.leanplum.base;

import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class CommonTestSteps extends BaseTest{

    public AdHocPO sendMessage(AndroidDriver<MobileElement> driver, TestStepHelper stepHelper, String message) {
        BasePO basePO = new BasePO(driver);
        stepHelper.clickElement(basePO, basePO.confirmAlertButton, "Confirm Alert button");

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(basePO, basePO.confirmAlertButton, "Ad-Hoc button");

        stepHelper.sendEvent(adHocPO, message);

        return adHocPO;
    }
    
    public AdHocPO sendUserAttribute(AndroidDriver<MobileElement> driver, TestStepHelper stepHelper, String attributeName, String attributeValue) {
        BasePO basePO = new BasePO(driver);
        stepHelper.clickElement(basePO, basePO.confirmAlertButton, "Confirm Alert button");

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(basePO, basePO.confirmAlertButton, "Ad-Hoc button");

        stepHelper.sendUserAttribute(adHocPO, attributeName, attributeValue);

        return adHocPO;
    }
}
