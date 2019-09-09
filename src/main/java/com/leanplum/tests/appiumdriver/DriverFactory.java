package com.leanplum.tests.appiumdriver;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class DriverFactory {

    private static String DRIVER_CONFIG_FILE = "resources/driver.properties";
    private static final String TEST_CONFIG_FILE = "resources/test.properties";

    public AppiumDriver<MobileElement> createDriver(DeviceProperties deviceProperties) {

        DriverConfig driverConfig = (DriverConfig) PropertiesUtils.loadProperties(DRIVER_CONFIG_FILE,
                DriverConfig.class);
        TestConfig testConfig = (TestConfig) PropertiesUtils.loadProperties(TEST_CONFIG_FILE,
                TestConfig.class);
        
        PlatformEnum platform = PlatformEnum.valueOfEnum(testConfig.getOS()).get();
        boolean useSeleniumGrid = driverConfig.isSeleniumGrid();

        String url;

        if (useSeleniumGrid) {
            url = driverConfig.getGridHubUrl();
        } else {
            url = driverConfig.getAppiumServerUrl();
        }
        
        return platform.initializeDriver(url, platform.loadDesiredCapabilities(deviceProperties));
    }
}
