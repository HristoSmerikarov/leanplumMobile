package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

public interface DeviceProperties {

    @BoundProperty("platformName")
    String getPlatformName();

    @BoundProperty("appPackage")
    String getAppPackage();

    @BoundProperty("appActivity")
    String getAppActivity();

    @BoundProperty("noreset")
    String getNoReset();

    @BoundProperty("app")
    String getApp();

    @BoundProperty("bundleId")
    String getBundleId();

    @BoundProperty("automationName")
    String getAutomationName();

    @BoundProperty("xcodeOrgId")
    String getXcodeOrgId();

    @BoundProperty("xcodeSigningId")
    String getXcodeSigningId();

    @BoundProperty("debugProxyPort")
    String getDebugProxyPort();

    @BoundProperty("useNewWDA")
    String getUseNewWda();

}