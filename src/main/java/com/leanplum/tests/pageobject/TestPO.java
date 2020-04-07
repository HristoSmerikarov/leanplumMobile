package com.leanplum.tests.pageobject;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.nativesdk.NTestPO;
import com.leanplum.tests.pageobject.nativesdkinapp.RNTestPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class TestPO extends BasePO{

    protected TestPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }
    
    public static <T extends TestPO>TestPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk){
        if(sdk.equals(SdkEnum.NATIVE)) {
            return new NTestPO(driver);
        }else {
            return new RNTestPO(driver);
        }
    }

    public abstract void click();
    
    public abstract void verifyTemplate();
    
}
