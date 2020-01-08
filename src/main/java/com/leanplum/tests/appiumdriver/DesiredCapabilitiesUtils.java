package com.leanplum.tests.appiumdriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.testdevices.AndroidTestDevice;
import com.leanplum.tests.testdevices.IOSTestDevice;
import com.leanplum.tests.testdevices.TestDevice;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DesiredCapabilitiesUtils {

	private DesiredCapabilities capabilities = new DesiredCapabilities();

	public DesiredCapabilities getAndroidDesiredCapabilities(AndroidTestDevice testDevice, DeviceProperties deviceProperties) {
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testDevice.getPlatformVersion());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testDevice.getName());
		capabilities.setCapability(MobileCapabilityType.UDID, testDevice.getId());
//		File rondoAppFile = new File("./resources/RondoApp-debug.apk");
//		System.out.println(rondoAppFile.getAbsolutePath());
//        capabilities.setCapability(MobileCapabilityType.APP, rondoAppFile.getAbsolutePath());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, deviceProperties.getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, deviceProperties.getAppActivity());
		capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, AppiumServiceUtils.findFreePort());
		// capabilities.setCapability("bundleId", deviceProperties.getBundleId());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		return this.capabilities;
	}

	public DesiredCapabilities getIOSDesiredCapabilities(IOSTestDevice testDevice, DeviceProperties deviceProperties) {
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testDevice.getPlatformVersion());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testDevice.getName());
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, deviceProperties.getAutomationName());
		capabilities.setCapability(MobileCapabilityType.UDID, testDevice.getId());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
		capabilities.setCapability("sessionOverride", true);

		capabilities.setCapability("wdaLocalPort",testDevice.getWdaPort());
		capabilities.setCapability("webkitDebugProxyPort", deviceProperties.getDebugProxyPort());
		capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, deviceProperties.getUseNewWda());

		capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, deviceProperties.getBundleId());
		capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, deviceProperties.getXcodeOrgId());
		capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, deviceProperties.getXcodeSigningId());
		return this.capabilities;
	}

}