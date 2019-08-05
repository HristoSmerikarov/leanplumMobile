package com.leanplum.tests.appiumdriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;

public class DesiredCapabilitiesUtils {

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    public DesiredCapabilitiesUtils(DriverConfig driverConfig, DeviceProperties deviceProperties) {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceProperties.getPlatformVersion());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceProperties.getDeviceName());

        capabilities.setCapability("appPackage", deviceProperties.getAppPackage());
        capabilities.setCapability("appActivity", deviceProperties.getAppActivity());

        capabilities.setCapability("noreset", deviceProperties.getNoReset());
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return this.capabilities;
    }
}