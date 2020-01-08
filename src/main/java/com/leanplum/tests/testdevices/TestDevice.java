package com.leanplum.tests.testdevices;

import com.leanplum.tests.enums.PlatformEnum;

public class TestDevice {

    private String id;
    private String name;
    private String platformVersion;
    private PlatformEnum platform;

    public TestDevice(String id, String name, String platformVersion, PlatformEnum platform) {
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

    public String getPlatformVersion() {
        return platformVersion;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }
}
