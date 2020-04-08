package com.leanplum.tests.pageobject.reactnativesdk;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.AppSetupPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNAppSetupPO extends AppSetupPO {

    private static final String ANDROID_ENTRY_LABEL_XPATH = "//*[@resource-id='com.leanplum.rondo:id/name' and @text=\"%s\"]";
    private static final String IOS_ENTRY_LABEL_XPATH = "//XCUIElementTypeStaticText[@name=\"%s\"]";

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
    // @iOSXCUITFindBy(xpath = ""]")
    private MobileElement permissionMessage;

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
    private MobileElement appNameField;

    @AndroidFindBy(id = "com.leanplum.rondo:id/appId")
    // @iOSXCUITFindBy(xpath = "")
    private MobileElement appIdField;

    @AndroidFindBy(id = "com.leanplum.rondo:id/prodKey")
    // @iOSXCUITFindBy(xpath = "")
    private MobileElement prodKeyField;

    @AndroidFindBy(id = "com.leanplum.rondo:id/devKey")
    // @iOSXCUITFindBy(xpath = "")
    private MobileElement devKeyField;

    @AndroidFindBy(id = "com.leanplum.rondo:id/create")
    // @iOSXCUITFindBy(xpath = "")
    private MobileElement createKey;

    private AppiumDriver<MobileElement> driver;

    public RNAppSetupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public void selectApp(String appName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectEnvironment(String envName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createApp(String appName, String appId, String prodKey, String devKey) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getUserId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDeviceId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clickAppStatus() {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitForDeviceIdIUpdate(String expectedDeviceId) {
        // TODO Auto-generated method stub

    }
}
