package com.leanplum.tests.appiumdriver;

import com.pholser.util.properties.BoundProperty;

interface DriverConfig {

    @BoundProperty("hub_url")
    String getGridHubUrl();

    @BoundProperty("useSeleniumGrid")
    boolean isSeleniumGrid();
}