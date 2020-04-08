package com.leanplum.tests.pageobject;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.nativesdk.NVariablesPO;
import com.leanplum.tests.pageobject.reactnativesdk.RNVariablesPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class VariablesPO extends BasePO {

    protected VariablesPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends VariablesPO> VariablesPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NVariablesPO(driver);
        } else {
            return new RNVariablesPO(driver);
        }
    }

    public abstract boolean areValuesCorrect(String expectedString, String expectedNumber, String expectedBoolean,
            String expectedFile, boolean hasImage);
}
