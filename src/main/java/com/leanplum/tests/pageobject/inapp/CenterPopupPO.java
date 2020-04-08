package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NCenterPopupPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNCenterPopupPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class CenterPopupPO extends BasePO {

    public CenterPopupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends CenterPopupPO> CenterPopupPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NCenterPopupPO(driver);
        } else {
            return new RNCenterPopupPO(driver);
        }
    }

    public abstract boolean verifyCenterPopup(String title, String message, String buttonText);

    public abstract void clickAcceptButton();
}
