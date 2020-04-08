package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NRichInterstitialPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNRichInterstitialPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class RichInterstitialPO extends BasePO {

    public RichInterstitialPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends RichInterstitialPO> RichInterstitialPO initialize(AppiumDriver<MobileElement> driver,
            SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NRichInterstitialPO(driver);
        } else {
            return new RNRichInterstitialPO(driver);
        }
    }

    public abstract boolean verifyRichInterstitial(String title, String message, String leftButtonText,
            String rightButtonText, boolean hasCloseButton);

    public abstract void clickLeftButton();

    public abstract void clickRightButton();
}
