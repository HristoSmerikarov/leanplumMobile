package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

public interface DriverConfig {

    @BoundProperty("hub_url")
    public String getGridHubUrl();

    @BoundProperty("appium_server_url")
    public String getAppiumServerUrl();

    @BoundProperty("useSeleniumGrid")
    public boolean isSeleniumGrid();

    @BoundProperty("platform")
    public String getTargetPlatform();

    @BoundProperty("appium-remote-directory")
    public String getAppiumRemoteDirectory();

    @BoundProperty("chromedriverExecutableDirLocal")
    public String getChromeDriverExecutablePathLocal();

    @BoundProperty("chromedriverExecutableDirRemote")
    public String getChromeDriverExecutablePathRemote();
}