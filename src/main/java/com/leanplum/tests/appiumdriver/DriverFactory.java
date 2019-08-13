package com.leanplum.tests.appiumdriver;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class DriverFactory {

    private static String DRIVER_CONFIG_FILE = "resources/driver.properties";

    public AppiumDriver<MobileElement> createDriver(DeviceProperties deviceProperties) {

        DriverConfig driverConfig = (DriverConfig) PropertiesUtils.loadProperties(DRIVER_CONFIG_FILE,
                DriverConfig.class);
        PlatformEnum platform = PlatformEnum.valueOfEnum(driverConfig.getTargetPlatform()).get();
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
