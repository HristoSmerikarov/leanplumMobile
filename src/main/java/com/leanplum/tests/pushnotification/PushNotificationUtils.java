package com.leanplum.tests.pushnotification;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class PushNotificationUtils {

	private MobileDriver<MobileElement> driver;
	
	public PushNotificationUtils(MobileDriver<MobileElement> mobileDriver) {
		this.driver = mobileDriver;
	}
	
	public void openNotifications() {
		if(driver instanceof AndroidDriver) {
			((AndroidDriver<MobileElement>) driver).openNotifications();
		}else {
			if (!((IOSDriver<MobileElement>) driver).isDeviceLocked()) {
				((IOSDriver<MobileElement>) driver).lockDevice();
			}
			//TODO - waits for few seconds for the push - better solution is needed
			MobileDriverUtils.waitInMs(10000);
			if (((IOSDriver<MobileElement>) driver).isDeviceLocked()) {
				((IOSDriver<MobileElement>) driver).unlockDevice();
			}
			Utils.swipeTopToBottom(driver);
		}
	}
	
}
