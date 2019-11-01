package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

public interface DeviceProperties {

    @BoundProperty("platformName")
    public String getPlatformName();

    @BoundProperty("platformVersion")
    public String getPlatformVersion();

    @BoundProperty("deviceName")
    public String getDeviceName();

    @BoundProperty("appPackage")
    public String getAppPackage();
    
    @BoundProperty("appActivity")
    public String getAppActivity();
    
    @BoundProperty("noreset")
    public String getNoReset();
    
    @BoundProperty("app")
    public String getApp();
    
    @BoundProperty("bundleId")
    public String getBundleId();
    
    @BoundProperty("automationName")
    public String getAutomationName();
    
    @BoundProperty("udid")
    public String getUdid();
    
    @BoundProperty("xcodeOrgId")
	public String getXcodeOrgId();
    
    @BoundProperty("xcodeSigningId")
	public String getXcodeSigningId();

    @BoundProperty("wdaLocalPort")
	public String getWdaLocalPort();
    
    @BoundProperty("debugProxyPort")
	public String getDebugProxyPort();

    @BoundProperty("useNewWDA")
	public String getUseNewWda();
    
}