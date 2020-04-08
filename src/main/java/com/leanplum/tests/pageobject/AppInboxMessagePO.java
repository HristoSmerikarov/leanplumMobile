package com.leanplum.tests.pageobject;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.nativesdk.NAppInboxMessagePO;
import com.leanplum.tests.pageobject.reactnativesdk.RNAppInboxMessagePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AppInboxMessagePO extends BasePO {

    protected AppInboxMessagePO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends AppInboxMessagePO> AppInboxMessagePO initialize(AppiumDriver<MobileElement> driver,
            SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NAppInboxMessagePO(driver);
        } else {
            return new RNAppInboxMessagePO(driver);
        }
    }

    public abstract void waitForInboxMessage();

    public abstract boolean isTitleCorrect(String title);

    public abstract boolean isSubTitleCorrect(String subtitle);

    public abstract boolean doesContainImage();

    public abstract void performReadAction();
}
