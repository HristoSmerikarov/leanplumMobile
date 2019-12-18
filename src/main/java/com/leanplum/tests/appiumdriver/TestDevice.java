package com.leanplum.tests.appiumdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.leanplum.tests.enums.PlatformEnum;

public class TestDevice {

	private String id;
	private String name;
	private String platformVersion;
	private PlatformEnum platform;
	public static final String IOS_UDID_REGEX = "\\[(.*?)\\]";
	public static final String IOS_NAME_REGEX = "^(.*?)\\W";
	public static final String IOS_PLATFORM_VERSION_REGEX = "\\((.*?)\\)";

	public TestDevice(String id, String name, PlatformEnum platform, String platformVersion) {
		this.id = id;
		this.name = name;
		this.platformVersion = platformVersion;
		this.platform = platform;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PlatformEnum getPlatform() {
		return platform;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

}
