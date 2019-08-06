package com.leanplum.tests.appiumdriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DesiredCapabilitiesUtils {

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    public DesiredCapabilitiesUtils(DeviceProperties deviceProperties) {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceProperties.getPlatformVersion());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceProperties.getDeviceName());
        
        capabilities.setCapability("app", deviceProperties.getApp());
      capabilities.setCapability("bundleId", deviceProperties.getBundleId());

//        capabilities.setCapability("appPackage", deviceProperties.getAppPackage());
//        capabilities.setCapability("appActivity", deviceProperties.getAppActivity());

        capabilities.setCapability("noreset", deviceProperties.getNoReset());
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return this.capabilities;
    }
}