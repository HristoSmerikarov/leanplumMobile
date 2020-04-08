package com.leanplum.tests.pushnotification;

public interface PushNotification {

    boolean doesContainImage();

    void waitForPresence();

    boolean isAbsent();

    void view();

    void dismiss();
}
