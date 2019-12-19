package com.leanplum.tests.testdevices;

import com.leanplum.tests.enums.PlatformEnum;

public class IOSTestDevice extends TestDevice{

    private String wdaPort;
    public static final String IOS_UDID_REGEX = "\\[(.*?)\\]";
    public static final String IOS_NAME_REGEX = "^(.*?)\\W";
    public static final String IOS_PLATFORM_VERSION_REGEX = "\\((.*?)\\)";

    public IOSTestDevice(String id, String name, String platformVersion, String wdaPort) {
        super(id, name, platformVersion, PlatformEnum.IOS_APP);
        this.wdaPort = wdaPort;
    }
   
    public String getWdaPort() {
        return wdaPort;
    }
}
