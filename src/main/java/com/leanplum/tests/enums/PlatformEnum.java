package com.leanplum.tests.enums;

import static org.testng.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public enum PlatformEnum {
    ANDROID_APP("Android") {
        @Override
        public AppiumDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps) {
            AppiumDriver<MobileElement> driver = null;
            try {
                driver = new AndroidDriver<>(new URL(url), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
            return driver;
        }
    },
    IOS_APP("iOS") {
        @Override
        public AppiumDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps) {
            AppiumDriver<MobileElement> driver = null;
            try {
                driver = new IOSDriver<>(new URL(url), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
            return driver;
        }
    };

    String platformName;

    PlatformEnum(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public abstract AppiumDriver<MobileElement> initializeDriver(String url, DesiredCapabilities caps);

    public static Optional<PlatformEnum> valueOfEnum(String name) {
        return Arrays.stream(values()).filter(optionEnum -> optionEnum.platformName.toLowerCase().equals(name.toLowerCase()))
                .findFirst();
    }

}
