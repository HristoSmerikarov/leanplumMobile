package com.leanplum.tests.appiumdriver;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.testdevices.AndroidTestDevice;
import com.leanplum.tests.testdevices.IOSTestDevice;
import com.leanplum.tests.testdevices.TestDevice;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class DriverFactory {

    private String NODE_CONFIG_FILE_ANDROID_WINDOWS = "resources\\androidNode.json";
    private String NODE_CONFIG_FILE_ANDROID_MAC = "resources/androidNode.json";
    private String NODE_CONFIG_FILE_IOS_MAC = "resources/iosNode.json";

    public AppiumDriver<MobileElement> initializeDriver(TestDevice testDevice, DeviceProperties deviceProperties,
            URL appiumServiceUrl) {
        System.out.println("ID: " + testDevice.getId());
        System.out.println("Name: " + testDevice.getName());
        System.out.println("PORT: " + appiumServiceUrl.getPort());
        System.out.println("IP: " + appiumServiceUrl.getHost());

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(appiumServiceUrl.getHost()).usingPort(appiumServiceUrl.getPort());

        if (System.getProperty("useGrid").equals("true")) {
            builder.withArgument(GeneralServerFlag.CONFIGURATION_FILE,
                    getNodeConfigFilePath(Utils.determineOS(), testDevice.getPlatform()));
        }

        DesiredCapabilitiesUtils capabilitiesUtils = new DesiredCapabilitiesUtils();
        switch (testDevice.getPlatform()) {
        case ANDROID_APP:
            return new AndroidDriver<>(builder.build(),
                    capabilitiesUtils.getAndroidDesiredCapabilities((AndroidTestDevice) testDevice, deviceProperties));
        case IOS_APP:
            return new IOSDriver<>(builder.build(),
                    capabilitiesUtils.getIOSDesiredCapabilities((IOSTestDevice) testDevice, deviceProperties));
        default:
            return new AppiumDriver<MobileElement>(new DesiredCapabilities());
        }
    }

    private String getNodeConfigFilePath(OSEnum os, PlatformEnum platform) {
        switch (os) {
        case WINDOWS:
            return NODE_CONFIG_FILE_ANDROID_WINDOWS;
        case MAC:
            switch (platform) {
            case ANDROID_APP:
                return NODE_CONFIG_FILE_ANDROID_MAC;
            case IOS_APP:
                return NODE_CONFIG_FILE_IOS_MAC;
            }
        default:
            return "";
        }
    }
}
