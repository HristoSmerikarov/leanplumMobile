package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NConfirmInAppPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNConfirmInAppPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class ConfirmInAppPO extends BasePO {

    public ConfirmInAppPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends ConfirmInAppPO> ConfirmInAppPO initialize(AppiumDriver<MobileElement> driver,
            SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NConfirmInAppPO(driver);
        } else {
            return new RNConfirmInAppPO(driver);
        }
    }

    public abstract boolean verifyConfirmInApp(String title, String message, String acceptButtonText,
            String cancelButtonText);

    public abstract boolean verifyConfirmInAppIsAbsent();

    public abstract void acceptConfirmMessage();

    public abstract void cancelConfirmMessage();
}
