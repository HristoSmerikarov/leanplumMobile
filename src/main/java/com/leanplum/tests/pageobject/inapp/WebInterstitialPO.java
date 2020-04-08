package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NWebInterstitialPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNWebInterstitialPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class WebInterstitialPO extends BasePO {

    public WebInterstitialPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends WebInterstitialPO> WebInterstitialPO initialize(AppiumDriver<MobileElement> driver,
            SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NWebInterstitialPO(driver);
        } else {
            return new RNWebInterstitialPO(driver);
        }
    }

    public abstract boolean verifyWebInterstitial();

    public abstract void closeWebInterstitial();
}
