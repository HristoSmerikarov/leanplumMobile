package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AppSetupPO extends BasePO {

    private static final String ANDROID_APP_LABEL_XPATH = "//*[@resource-id='com.leanplum.rondo:id/name' and @text=\"%s\"]";
    private static final String IOS_APP_LABEL_XPATH = "";

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement permissionMessage;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow\"]")
    public MobileElement allowPermissionButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"iOS Push Permission\"]")
    public MobileElement iosPushPermissionButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_picker")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Picker\"]")
    public MobileElement appPicker;

    @AndroidFindBy(id = "com.leanplum.rondo:id/create")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"New App\"]")
    public MobileElement newApp;

    @AndroidFindBy(id = "com.leanplum.rondo:id/userId")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='User ID:']/following-sibling::*")
    public MobileElement userId;

    @AndroidFindBy(id = "com.leanplum.rondo:id/deviceId")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Device ID:']/following-sibling::*")
    public MobileElement deviceId;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_setup")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement appSetup;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/displayName")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement appNameField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/appId")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement appIdField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/prodKey")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement prodKeyField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/devKey")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement devKeyField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/create")
    // @iOSXCUITFindBy(xpath = "")
    public MobileElement createKey;

    private AppiumDriver<MobileElement> driver;

    public AppSetupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void allowPermission() {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(permissionMessage));
        click(allowPermissionButton);
    }

    public void allowIosPushPermission() {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(iosPushPermissionButton));
        click(iosPushPermissionButton);

        allowPermission();
    }

    public void pickApp(String appName) {
        click(appPicker);
        
        pickAppFromList(appName);
    }

    public void createApp(String appName, String appId, String prodKey, String devKey) {
        click(appPicker);
        click(newApp);
        appNameField.sendKeys(appName);
        appIdField.sendKeys(appId);
        prodKeyField.sendKeys(prodKey);
        devKeyField.sendKeys(devKey);
        click(createKey);
        
        pickAppFromList(appName);
    }
    
    private void pickAppFromList(String appName) {
        String appXpath = "";
        if (getDriver() instanceof AndroidDriver) {
            appXpath = String.format(ANDROID_APP_LABEL_XPATH, appName);
        } else {
            appXpath = String.format(IOS_APP_LABEL_XPATH, appName);
        }
        MobileDriverUtils
                .waitForExpectedCondition(driver, ExpectedConditions.visibilityOfElementLocated(By.xpath(appXpath)))
                .click();
    }
}
