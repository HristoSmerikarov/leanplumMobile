package com.leanplum.tests.pageobject.nativesdk;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

class SdkQaPO extends BasePO {

    @AndroidFindBy(id = "com.leanplum.rondo:id/sdq_qa")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement sdkQa;

    @AndroidFindBy(id = "com.leanplum.rondo:id/triggers")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement triggersButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/messages")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement messagesButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/push")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement pushButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/triggerEvent")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement triggerEventButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/triggerState")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement triggerStateButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/userAttributeChange")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement userAttributeChangeButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/sessionLimit")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement sessionLimitButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/lifetimeLimit")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement lifetimeLimitButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/chainInApp")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement changeToInAppButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/diffPrioritySameTime")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement diffPrioritySameTimeButton;

    public SdkQaPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }
}
