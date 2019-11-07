package com.leanplum.tests.appiumdriver;

import static org.testng.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverFactory {

	private static String DRIVER_CONFIG_FILE = "resources/driver.properties";
	private static final String TEST_CONFIG_FILE = "resources/test.properties";
	private AppiumDriver<MobileElement> driver;

	public AppiumDriver<MobileElement> createDriver(DeviceProperties deviceProperties) {
		DriverConfig driverConfig = (DriverConfig) PropertiesUtils.loadProperties(DRIVER_CONFIG_FILE,
				DriverConfig.class);
		TestConfig testConfig = (TestConfig) PropertiesUtils.loadProperties(TEST_CONFIG_FILE, TestConfig.class);

		PlatformEnum platform = PlatformEnum.valueOfEnum(testConfig.getOS()).get();
		boolean useSeleniumGrid = driverConfig.isSeleniumGrid();

		String url;

		if (useSeleniumGrid) {
			url = driverConfig.getGridHubUrl();
		} else {
			url = driverConfig.getAppiumServerUrl();
		}

		return initializeDriver(platform, url, deviceProperties);
	}

	private AppiumDriver<MobileElement> initializeDriver(PlatformEnum platform, String url,
			DeviceProperties deviceProperties) {
		DesiredCapabilitiesUtils capabilitiesUtils = new DesiredCapabilitiesUtils();
		switch (platform) {
		case ANDROID_APP:
			try {
				driver = new AndroidDriver<MobileElement>(new URL(url),
						capabilitiesUtils.getAndroidDesiredCapabilities(deviceProperties));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			return driver;
		case IOS_APP:
			try {
				driver = new IOSDriver<>(new URL(url), capabilitiesUtils.getIOSDesiredCapabilities(deviceProperties));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			return driver;
		default:
			return new AppiumDriver<MobileElement>(new DesiredCapabilities());
		}
	}
}
