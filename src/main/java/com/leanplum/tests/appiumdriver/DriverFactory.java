package com.leanplum.tests.appiumdriver;

import static org.testng.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.testdevices.AndroidTestDevice;
import com.leanplum.tests.testdevices.IOSTestDevice;
import com.leanplum.tests.testdevices.TestDevice;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverFactory {

    private static String DRIVER_CONFIG_FILE = "resources/driver.properties";

    public AppiumDriver<MobileElement> createDriver(TestDevice testDevice, DeviceProperties deviceProperties,
            int port) {
        DriverConfig driverConfig = (DriverConfig) PropertiesUtils.loadProperties(DRIVER_CONFIG_FILE,
                DriverConfig.class);

        System.out.println("ID: " + testDevice.getId());
        System.out.println("Name: " + testDevice.getName());
        System.out.println("PORT: " + port);
        System.out.println("IP: " + driverConfig.getAppiumServerUrl().replace("null", String.valueOf(port)));

        boolean useSeleniumGrid = driverConfig.isSeleniumGrid();

        String url;

        if (useSeleniumGrid) {
            url = driverConfig.getGridHubUrl();
        } else {
            url = driverConfig.getAppiumServerUrl().replace("null", String.valueOf(port));
        }

        System.out.println("APPIUM URL: " + url);
        return initializeDriver(url, testDevice, deviceProperties);
    }

    private AppiumDriver<MobileElement> initializeDriver(String url, TestDevice testDevice,
            DeviceProperties deviceProperties) {
        DesiredCapabilitiesUtils capabilitiesUtils = new DesiredCapabilitiesUtils();
        switch (testDevice.getPlatform()) {
        case ANDROID_APP:
            try {
                return new AndroidDriver<MobileElement>(new URL(url), capabilitiesUtils
                        .getAndroidDesiredCapabilities((AndroidTestDevice) testDevice, deviceProperties));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        case IOS_APP:
            try {
                return new IOSDriver<>(new URL(url),
                        capabilitiesUtils.getIOSDesiredCapabilities((IOSTestDevice) testDevice, deviceProperties));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        default:
            return new AppiumDriver<MobileElement>(new DesiredCapabilities());
        }
    }
}
