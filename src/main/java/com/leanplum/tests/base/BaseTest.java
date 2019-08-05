package com.leanplum.tests.base;

import org.testng.annotations.BeforeTest;

import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;

    @BeforeTest
    public void setupTest() {
        driver = DriverFactory.createDriver(DevicePropertiesUtils.getDeviceProperties("android", "emulator"));
    }

    
    public AppiumDriver<MobileElement> getAppiumDriver() {
        return this.driver;
    }

}