package com.leanplum.tests.testdevices;

import com.leanplum.tests.enums.PlatformEnum;

public class AndroidTestDevice extends TestDevice {

    public AndroidTestDevice(String id, String name, String platformVersion) {
        super(id, name, platformVersion, PlatformEnum.ANDROID_APP);
    }
}
