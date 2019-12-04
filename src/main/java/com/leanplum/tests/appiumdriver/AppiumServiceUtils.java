package com.leanplum.tests.appiumdriver;

import java.io.File;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServiceUtils {

    private static String DRIVER_CONFIG_FILE = "resources/appium_service.properties";

    AppiumDriverLocalService service = null;

    public static AppiumDriverLocalService setupAppiumService(PlatformEnum platform) {
        AppiumServiceConfig appiumServiceConfig = (AppiumServiceConfig) PropertiesUtils
                .loadProperties(DRIVER_CONFIG_FILE, AppiumServiceConfig.class);

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(appiumServiceConfig.getAppiumServiceIp());
        builder.usingPort(Integer.valueOf(appiumServiceConfig.getAppiumServicePort()));
        File jsonFile = new File("resources/" + platform.getPlatformName().toLowerCase() + "Node.json");

        System.out.println(jsonFile.getAbsolutePath());
        builder.withArgument(GeneralServerFlag.CONFIGURATION_FILE, jsonFile.getAbsolutePath());
        return AppiumDriverLocalService.buildService(builder);
    }
}
