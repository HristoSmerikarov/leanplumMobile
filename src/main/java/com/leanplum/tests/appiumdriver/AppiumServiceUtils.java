package com.leanplum.tests.appiumdriver;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServiceUtils {

    private static String DRIVER_CONFIG_FILE = "resources/appium_service.properties";

    AppiumDriverLocalService service = null;

    public static AppiumDriverLocalService setupAppiumService() {
        AppiumServiceConfig appiumServiceConfig = (AppiumServiceConfig) PropertiesUtils
                .loadProperties(DRIVER_CONFIG_FILE, AppiumServiceConfig.class);

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(appiumServiceConfig.getAppiumServiceIp());
        builder.usingPort(Integer.valueOf(appiumServiceConfig.getAppiumServicePort()));
        return AppiumDriverLocalService.buildService(builder);
        // your test scripts logic...
        // service.stop();
    }
}
