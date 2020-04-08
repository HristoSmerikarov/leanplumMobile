package com.leanplum.tests.pageobject;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.nativesdk.NAppSetupPO;
import com.leanplum.tests.pageobject.reactnativesdk.RNAppSetupPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AppSetupPO extends BasePO {

    protected AppSetupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends AppSetupPO> AppSetupPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NAppSetupPO(driver);
        } else {
            return new RNAppSetupPO(driver);
        }
    }

    public abstract void selectApp(String appName);

    public abstract void selectEnvironment(String envName);

    public abstract void createApp(String appName, String appId, String prodKey, String devKey);

    public abstract String getUserId();

    public abstract String getDeviceId();

    public abstract void clickAppStatus();

    public abstract void waitForDeviceIdIUpdate(String expectedDeviceId);

}
