package com.leanplum.tests.pageobject;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AdHocPO extends BasePO {

    @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Adhoc\"]")
    public MobileElement adhoc;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Send track Event:\"]//following-sibling::XCUIElementTypeTextField")
    @AndroidFindBy(id = "com.leanplum.rondo:id/trackName")
    public MobileElement trackEventField;

    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonTrack")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Send Track Event\"]")
    public MobileElement trackEventButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/stateName")
    public MobileElement stateNameField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonState")
    public MobileElement stateNameButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrKey")
    public MobileElement attributeKeyField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrValue")
    public MobileElement attributeValueField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonUserAttr")
    public MobileElement userAttributeButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/locLatitude")
    public MobileElement latitudeField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/locLongitude")
    public MobileElement longitudeField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonDeviceLocation")
    public MobileElement deviceLocationButton;
    
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Rondo Push Notification's\"]")
    public MobileElement iosPushNotifications;
    
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"View\"]")
    public MobileElement viewIOSPushNotifications;


    MobileDriver<MobileElement> driver;
    
    public AdHocPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void sendTrackEvent(String eventName) {
        sendAdHocProperties(trackEventButton, ImmutableMap.of(trackEventField, eventName));
    }

    public void sendSessionState(String stateName) {
        sendAdHocProperties(stateNameButton, ImmutableMap.of(stateNameField, stateName));
    }

    public void sendUserAttribute(String userAttributeKey, String userAttributeValue) {
        sendAdHocProperties(userAttributeButton,
                ImmutableMap.of(attributeKeyField, userAttributeKey, attributeValueField, userAttributeValue));
    }

    public void sendDeviceLocation(String latitude, String longitude) {
        sendAdHocProperties(deviceLocationButton, ImmutableMap.of(latitudeField, latitude, longitudeField, longitude));
    }

    private void sendAdHocProperties(MobileElement button, Map<MobileElement, String> fieldValueMap) {
        fieldValueMap.entrySet().forEach(entry -> {
            MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(entry.getKey()));
            entry.getKey().clear();
            entry.getKey().sendKeys(entry.getValue());
        });
        button.click();
    }
}
