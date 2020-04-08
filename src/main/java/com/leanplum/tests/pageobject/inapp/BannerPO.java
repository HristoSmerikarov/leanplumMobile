package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NBannerPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNBannerPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class BannerPO extends BasePO {

    public BannerPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends BannerPO> BannerPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NBannerPO(driver);
        } else {
            return new RNBannerPO(driver);
        }
    }

    public abstract boolean verifyBannerLayout(String bannerTitle, String bannerMessage);

    public abstract void clickOnBanner();

    public abstract boolean isCloseButtonPresent();

    public abstract void closeBanner();
}
