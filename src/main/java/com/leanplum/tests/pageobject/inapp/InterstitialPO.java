package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NInterstitialPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNInterstitialPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class InterstitialPO extends BasePO {

    public InterstitialPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends InterstitialPO> InterstitialPO initialize(AppiumDriver<MobileElement> driver,
            SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NInterstitialPO(driver);
        } else {
            return new RNInterstitialPO(driver);
        }
    }

    public abstract boolean verifyInterstitialLayout(String interstitialTitle, String interstitialMessage,
            String acceptButton);

    public abstract void clickAcceptButton();
}
