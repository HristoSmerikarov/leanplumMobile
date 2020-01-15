package com.leanplum.tests.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PlatformEnum {
	ANDROID_APP("Android"), IOS_APP("iOS");

	String platformName;

	PlatformEnum(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public static Optional<PlatformEnum> valueOfEnum(String name) {
		return Arrays.stream(values())
				.filter(optionEnum -> optionEnum.platformName.toLowerCase().equals(name.toLowerCase())).findFirst();
	}

}
