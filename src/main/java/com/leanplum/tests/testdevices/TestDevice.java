package com.leanplum.tests.testdevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.leanplum.tests.enums.PlatformEnum;

public class TestDevice {

    private String id;
    private String name;
    private String platformVersion;
    private PlatformEnum platform;
    private String appiumServiceIp;

    public TestDevice(String id, String name, String platformVersion, PlatformEnum platform) {
        this.id = id;
        this.name = name;
        this.platformVersion = platformVersion;
        this.platform = platform;
        this.appiumServiceIp = appiumServiceIp;
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
