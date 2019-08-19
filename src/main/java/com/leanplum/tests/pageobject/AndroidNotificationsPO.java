package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AndroidNotificationsPO extends BasePO {

    public static final String PUSH_NOTIFICATION_BY_NAME_XPATH = "//*[@resource-id='android:id/title' and @text='%s']";

    public AndroidNotificationsPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

}
