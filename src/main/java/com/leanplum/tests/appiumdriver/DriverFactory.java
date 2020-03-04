package com.leanplum.tests.appiumdriver;

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
            URL appiumServiceURL) {
        DriverConfig driverConfig = (DriverConfig) PropertiesUtils.loadProperties(DRIVER_CONFIG_FILE,
                DriverConfig.class);

        System.out.println("ID: " + testDevice.getId());
        System.out.println("Name: " + testDevice.getName());
        System.out.println("PORT: " + appiumServiceURL.getPort());
        System.out.println("IP: " + appiumServiceURL.getHost());

        boolean useSeleniumGrid = driverConfig.isSeleniumGrid();

        String url;

        if (useSeleniumGrid) {
            url = appiumServiceURL.getHost();
        } else {
            url = appiumServiceURL.getHost();
        }

        System.out.println("APPIUM URL: " + url);
        return initializeDriver(testDevice, deviceProperties, appiumServiceURL);
    }

    private AppiumDriver<MobileElement> initializeDriver(TestDevice testDevice, DeviceProperties deviceProperties,
            URL appiumServiceUrl) {
        DesiredCapabilitiesUtils capabilitiesUtils = new DesiredCapabilitiesUtils();
        switch (testDevice.getPlatform()) {
        case ANDROID_APP:

            try {
                return new AndroidDriver(new URL("http://jenkins-staging.leanplum.com:4444/wd/hub"),
                        capabilitiesUtils.getAndroidDesiredCapabilities((AndroidTestDevice) testDevice, deviceProperties));
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        // return new AndroidDriver<MobileElement>(appiumServiceUrl,
        // capabilitiesUtils.getAndroidDesiredCapabilities((AndroidTestDevice) testDevice, deviceProperties));
        case IOS_APP:
            return new IOSDriver<>(appiumServiceUrl,
                    capabilitiesUtils.getIOSDesiredCapabilities((IOSTestDevice) testDevice, deviceProperties));
        default:
            return new AppiumDriver<MobileElement>(new DesiredCapabilities());
        }
    }
}
