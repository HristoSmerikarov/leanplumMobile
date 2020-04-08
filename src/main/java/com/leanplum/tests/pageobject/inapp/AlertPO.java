package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NAlertPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNAlertPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AlertPO extends BasePO {

    public AlertPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends AlertPO> AlertPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NAlertPO(driver);
        } else {
            return new RNAlertPO(driver);
        }
    }

    public abstract boolean verifyAlertLayout(String expectedTitle, String expectedMessage,
            String confirmAlertButtonText);

    public abstract boolean isAlertPresent();

    public abstract void dismissAllAlertsOnStart();

    public abstract void dismissAlert();

}
