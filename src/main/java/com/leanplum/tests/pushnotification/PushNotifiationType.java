package com.leanplum.tests.pushnotification;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public enum PushNotifiationType {

    ANDROID("android") {
        @Override
        public PushNotification initialize(MobileDriver<MobileElement> driver, String message) {
            return new AndroidPushNotification(driver, message);
        }
    },
    IOS("ios") {
        @Override
        public PushNotification initialize(MobileDriver<MobileElement> driver, String message) {
            return new IOSPushNotification(driver, message);
        }
    };

    private String os;

    PushNotifiationType(String os) {
        this.os = os;
    }

    public String getOs() {
        return this.os;
    }

    public abstract PushNotification initialize(MobileDriver<MobileElement> driver, String message);
}
