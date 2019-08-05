package com.leanplum.tests.appiumdriver;

public class DevicePropertiesUtils {

    private static String DEVICE_CONFIG_FILE = "resources/%s_%s.properties";

    public static DeviceProperties getDeviceProperties(String platform, String deviceType) {
        return (DeviceProperties) PropertiesUtils
                .loadProperties(String.format(DEVICE_CONFIG_FILE, platform, deviceType), DeviceProperties.class);
    }
}
