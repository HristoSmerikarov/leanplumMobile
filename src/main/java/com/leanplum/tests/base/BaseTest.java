package com.leanplum.tests.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;
    private AppiumDriverLocalService service = null;

    @BeforeTest
    public void setupAppiumService() {
        if (System.getProperty("os").toLowerCase().equals("android")) {
            service = AppiumServiceUtils.setupAppiumService();
            service.start();
        }
    }

    @BeforeTest(dependsOnMethods = "setupAppiumService")
    public void setupTest() {
        DriverFactory df = new DriverFactory();
        this.driver = df.createDriver(
                DevicePropertiesUtils.getDeviceProperties(System.getProperty("os"), System.getProperty("deviceType")));
    }

    public AppiumDriver<MobileElement> getAppiumDriver() {
        return this.driver;
    }

    @AfterTest
    public void stopAppiumService() {
        if (System.getProperty("os").toLowerCase().equals("android")) {
            service.stop();
        }
    }
}