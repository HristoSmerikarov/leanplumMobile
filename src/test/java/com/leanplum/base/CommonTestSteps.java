package com.leanplum.base;

import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class CommonTestSteps extends BaseTest{

    public AdHocPO sendEvent(MobileDriver<MobileElement> driver, TestStepHelper stepHelper, String message) {
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendTrackEvent(adHocPO, message);

        return adHocPO;
    }
    
    public AdHocPO sendEventWithParameters(MobileDriver<MobileElement> driver, TestStepHelper stepHelper, String message, String paramKey, String paramValue) {
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendTrackEventWithParameters(adHocPO, message, paramKey, paramValue);

        return adHocPO;
    }
    
    public AdHocPO sendUserAttribute(MobileDriver<MobileElement> driver, TestStepHelper stepHelper, String attributeName, String attributeValue) {
        AlertPO alertPO = new AlertPO(driver);
        stepHelper.acceptAllAlertsOnAppStart(alertPO);

        AdHocPO adHocPO = new AdHocPO(driver);
        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

        stepHelper.sendUserAttribute(adHocPO, attributeName, attributeValue);

        return adHocPO;
    }
    
    public boolean verifyCorrectURLIsOpened(MobileDriver<MobileElement> driver, String url) {
        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
        return mobileBrowserPO.isCorrectURLOpened(url);
    }
}
