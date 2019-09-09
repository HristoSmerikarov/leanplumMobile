package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

public interface TestConfig {

    @BoundProperty("os")
    public String getOS();

    @BoundProperty("deviceType")
    public String getDeviceType();
}
