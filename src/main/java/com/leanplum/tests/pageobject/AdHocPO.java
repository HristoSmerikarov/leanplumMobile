package com.leanplum.tests.pageobject;

import java.util.Map;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.tests.pageobject.reactnativesdk.RNAdHocPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileElement;

public abstract class AdHocPO extends BasePO {

    protected AdHocPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends AdHocPO> AdHocPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NAdHocPO(driver);
        } else {
            return new RNAdHocPO(driver);
        }
    }

    public abstract void sendTrackEvent(String eventName);

    public abstract void sendStateEvent(String stateName);

    public abstract void sendTrackEventWithParameter(String eventName, String paramKey, String paramValue);

    public abstract void sendUserAttribute(String userAttributeKey, String userAttributeValue);

    public abstract void sendDeviceLocation(String latitude, String longitude);

    public abstract void setUserId(String userId);

    protected void sendAdHocProperties(AppiumDriver<MobileElement> driver, MobileElement button, MobileElement label,
            Map<MobileElement, String> fieldValueMap) {
        long startTime = System.currentTimeMillis();

        long stopTimeFirstTry = System.currentTimeMillis();
        long elapsedTimeFirstTry = stopTimeFirstTry - startTime;
        System.out.println("I spent " + elapsedTimeFirstTry + " seconds to swipe first try");

        fieldValueMap.forEach((key, value) -> {
            key.clear();
            key.sendKeys(value);
        });

        long stopTimePopulate = System.currentTimeMillis();
        long elapsedTimePopulate = stopTimePopulate - startTime;
        System.out.println("I spent " + elapsedTimePopulate + " seconds to populate ");

        label.click();

        if (((HasOnScreenKeyboard) driver).isKeyboardShown()) {
            driver.hideKeyboard();
        }

        button.click();
    }
}
