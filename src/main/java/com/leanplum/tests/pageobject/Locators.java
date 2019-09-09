package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class Locators {

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "android:id/button1")
    public MobileElement button1;

    @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Adhoc\"]")
    public MobileElement adhoc;

    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonTrack")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Send Track Event\"]")
    public MobileElement buttonTrack;

    
    public Locators(WebDriver driver) {
        try {
            PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
