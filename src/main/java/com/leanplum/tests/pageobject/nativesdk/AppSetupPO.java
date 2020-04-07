package com.leanplum.tests.pageobject.nativesdk;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AppSetupPO extends BasePO {

    private static final String ANDROID_ENTRY_LABEL_XPATH = "//*[@resource-id='com.leanplum.rondo:id/name' and @text=\"%s\"]";
    private static final String IOS_ENTRY_LABEL_XPATH = "//XCUIElementTypeStaticText[@name=\"%s\"]";

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
    // @iOSXCUITFindBy(xpath = ""]")
    private
    MobileElement permissionMessage;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow\"]")
    private MobileElement allowPermissionButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"iOS Push Permission\"]")
    private MobileElement iosPushPermissionButton;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_picker")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Picker\"]")
    private MobileElement appPicker;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/env_picker")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Env Picker\"]")
    private MobileElement envPicker;

    @AndroidFindBy(id = "com.leanplum.rondo:id/create")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"New App\"]")
    private MobileElement newApp;

    @AndroidFindBy(id = "com.leanplum.rondo:id/userId")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='User ID:']/following-sibling::*")
    public MobileElement userId;

    @AndroidFindBy(id = "com.leanplum.rondo:id/deviceId")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Device ID:']/following-sibling::*")
    public MobileElement deviceId;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_setup")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"App Setup\"]")
    public MobileElement appSetup;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/displayName")
    // @iOSXCUITFindBy(xpath = "")
    private
    MobileElement appNameField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/appId")
    // @iOSXCUITFindBy(xpath = "")
    private
    MobileElement appIdField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/prodKey")
    // @iOSXCUITFindBy(xpath = "")
    private
    MobileElement prodKeyField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/devKey")
    // @iOSXCUITFindBy(xpath = "")
    private
    MobileElement devKeyField;
    
    @AndroidFindBy(id = "com.leanplum.rondo:id/create")
    // @iOSXCUITFindBy(xpath = "")
    private
    MobileElement createKey;

    private AppiumDriver<MobileElement> driver;

    public AppSetupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    private void allowPermission() {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(permissionMessage));
        click(allowPermissionButton);
    }

    public void allowIosPushPermission() {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(iosPushPermissionButton));
        click(iosPushPermissionButton);

        allowPermission();
    }

    public void selectApp(String appName) {
        click(appPicker);
        
        pickEntryFromList(appName);
    }
    
    public void selectEnvironment(String envName) {
        click(envPicker);
        
        pickEntryFromList(envName);
    }

    public void createApp(String appName, String appId, String prodKey, String devKey) {
        click(appPicker);
        click(newApp);
        appNameField.sendKeys(appName);
        appIdField.sendKeys(appId);
        prodKeyField.sendKeys(prodKey);
        devKeyField.sendKeys(devKey);
        click(createKey);
        
        pickEntryFromList(appName);
    }
    
    /**
     * Can select App and Env
     * 
     * @param appName
     */
    private void pickEntryFromList(String appName) {
        String entryXpath;
        if (getDriver() instanceof AndroidDriver) {
            entryXpath = String.format(ANDROID_ENTRY_LABEL_XPATH, appName);
        } else {
            entryXpath = String.format(IOS_ENTRY_LABEL_XPATH, appName);
        }
        MobileDriverUtils
                .waitForExpectedCondition(driver, ExpectedConditions.visibilityOfElementLocated(By.xpath(entryXpath)))
                .click();
    }
}
