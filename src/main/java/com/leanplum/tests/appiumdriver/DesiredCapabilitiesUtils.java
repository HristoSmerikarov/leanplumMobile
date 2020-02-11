package com.leanplum.tests.appiumdriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.leanplum.tests.testdevices.AndroidTestDevice;
import com.leanplum.tests.testdevices.IOSTestDevice;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

class DesiredCapabilitiesUtils {

	private DesiredCapabilities capabilities = new DesiredCapabilities();

	public DesiredCapabilities getAndroidDesiredCapabilities(AndroidTestDevice testDevice, DeviceProperties deviceProperties) {
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testDevice.getPlatformVersion());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testDevice.getName());
		capabilities.setCapability(MobileCapabilityType.UDID, testDevice.getId());
//		File rondoAppFile = new File("./resources/RondoApp-debug.apk");
//		System.out.println(rondoAppFile.getAbsolutePath());
//        capabilities.setCapability(MobileCapabilityType.APP, rondoAppFile.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
		capabilities.setCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT, "300000");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, deviceProperties.getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, deviceProperties.getAppActivity());
		capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, AppiumServiceUtils.findFreePort());
		// capabilities.setCapability("bundleId", deviceProperties.getBundleId());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, "true");
		return this.capabilities;
	}

	public DesiredCapabilities getIOSDesiredCapabilities(IOSTestDevice testDevice, DeviceProperties deviceProperties) {
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceProperties.getPlatformName());
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testDevice.getPlatformVersion());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testDevice.getName());
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, deviceProperties.getAutomationName());
		capabilities.setCapability(MobileCapabilityType.UDID, testDevice.getId());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceProperties.getNoReset());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
		capabilities.setCapability("sessionOverride", true);

		capabilities.setCapability("wdaLocalPort",AppiumServiceUtils.findFreePort());
		capabilities.setCapability("webkitDebugProxyPort", deviceProperties.getDebugProxyPort());
		capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, deviceProperties.getUseNewWda());

		capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, deviceProperties.getBundleId());
		capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, deviceProperties.getXcodeOrgId());
		capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, deviceProperties.getXcodeSigningId());
		return this.capabilities;
	}

}