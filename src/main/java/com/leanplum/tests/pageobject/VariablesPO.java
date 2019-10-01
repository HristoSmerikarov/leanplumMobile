package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class VariablesPO extends BasePO {

    private static final String ANDROID_IMAGE_XPATH = "";
    private static final String IOS_IMAGE_XPATH = "";

    @AndroidFindBy(id = "com.leanplum.rondo:id/varStringLabel")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement stringLabel;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varString")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement stringValue;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varNumberLabel")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement numberLabel;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varNumber")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement numberValue;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varBoolLabel")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement boolLabel;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varBool")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement boolValue;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varFileLabel")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement fileLabel;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varFile")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement fileValue;

    @AndroidFindBy(id = "com.leanplum.rondo:id/varFileImageLabel")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement fileImageLabel;

    @AndroidFindBy(xpath = ANDROID_IMAGE_XPATH)
    @iOSXCUITFindBy(xpath = IOS_IMAGE_XPATH)
    public MobileElement fileImage;

    private MobileDriver<MobileElement> driver;

    public VariablesPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean areValuesCorrect(String expectedString, String expectedNumber, String expectedBoolean,
            String expectedFile, boolean hasImage) {

        return getTextFromElement(stringValue).equals(expectedString)
                && getTextFromElement(numberValue).equals(expectedNumber)
                && getTextFromElement(boolValue).equals(expectedBoolean)
                && getTextFromElement(fileValue).equals(expectedFile) && hasImage == hasImage();
    }

    private boolean hasImage() {
        if (driver instanceof AndroidDriver) {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_IMAGE_XPATH);
        } else {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_IMAGE_XPATH);
        }
    }
}
