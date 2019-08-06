package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

public interface AppiumServiceConfig {

    @BoundProperty("appium_service_ip")
    public String getAppiumServiceIp();
    
    @BoundProperty("appium_service_port")
    public String getAppiumServicePort();
}