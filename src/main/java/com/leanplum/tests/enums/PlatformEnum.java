package com.leanplum.tests.enums;

import static org.testng.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.appiumdriver.DeviceProperties;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public enum PlatformEnum {
	ANDROID_APP("Android") {
		@Override
		public AppiumDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps) {
			AndroidDriver<MobileElement> driver = null;
			try {
				driver = new AndroidDriver<MobileElement>(new URL(url), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			return driver;
		}

		@Override
		public DesiredCapabilities loadDesiredCapabilities(DeviceProperties deviceProperties) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceProperties.getPlatformVersion());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceProperties.getDeviceName());
			File rondoAppFile = new File("./resources/RondoApp-debug.apk");
			System.out.println(rondoAppFile.getAbsolutePath());
	        capabilities.setCapability(MobileCapabilityType.APP, rondoAppFile.getAbsolutePath());
//			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, deviceProperties.getAppPackage());
//			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, deviceProperties.getAppActivity());
			//capabilities.setCapability("bundleId", deviceProperties.getBundleId());
			capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
			capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
			return capabilities;
		}
	},
	IOS_APP("iOS") {
		@Override
		public IOSDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps) {
			IOSDriver<MobileElement> driver = null;
			try {
				driver = new IOSDriver<>(new URL(url), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			return driver;
		}
		
		@Override
		public DesiredCapabilities loadDesiredCapabilities(DeviceProperties deviceProperties) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceProperties.getPlatformVersion());
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceProperties.getDeviceName());
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, deviceProperties.getAutomationName());
			capabilities.setCapability(MobileCapabilityType.UDID, deviceProperties.getUdid());
			capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
			
			capabilities.setCapability("wdaLocalPort", deviceProperties.getWdaLocalPort());
            capabilities.setCapability("webkitDebugProxyPort", deviceProperties.getDebugProxyPort());
			capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, deviceProperties.getUseNewWda());
            
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, deviceProperties.getBundleId());
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, deviceProperties.getXcodeOrgId());
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, deviceProperties.getXcodeSigningId());
			
			
			return capabilities;
		}
		
		
	};

	String platformName;

	PlatformEnum(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public abstract AppiumDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps);

	public abstract DesiredCapabilities loadDesiredCapabilities(DeviceProperties deviceProperties);

	public static Optional<PlatformEnum> valueOfEnum(String name) {
		return Arrays.stream(values())
				.filter(optionEnum -> optionEnum.platformName.toLowerCase().equals(name.toLowerCase())).findFirst();
	}

}
