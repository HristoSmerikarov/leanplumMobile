package com.leanplum.tests.appiumdriver;

import java.net.URL;

import org.openqa.selenium.Capabilities;

import io.appium.java_client.ios.IOSDriver;

@SuppressWarnings("rawtypes")
public class LPIosDriver extends IOSDriver{

	public LPIosDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
	}
}
