package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePO {

    @AndroidFindBy(id = "android:id/alertTitle")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement alertTitle;
    
    @AndroidFindBy(id = "android:id/message")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement alertMessage;
    
    @AndroidFindBy(id = "android:id/button1")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement confirmAlert;
    
    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement permissionMessage;
    
    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement allowPermissionButton;

    private AppiumDriver<MobileElement> driver;

    public BasePO(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public AppiumDriver<MobileElement> getDriver() {
        return this.driver;
    }

    public void click(MobileElement element) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public boolean verifyText(MobileElement element, String expectedText) {
        return element.getText().equals(expectedText);
    }
    
    public void allowPermission() {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(permissionMessage));
        click(allowPermissionButton);
    }
}
