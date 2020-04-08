package com.leanplum.tests.pageobject.reactnativesdk;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import com.leanplum.tests.pageobject.AdHocPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RNAdHocPO extends AdHocPO {

    private static final String USER_ATTRIBUTE_FIELD_XPATH = "//XCUIElementTypeStaticText[@label='User attrib:']/following-sibling::*/XCUIElementTypeTextField";
    private static final String IOS_USER_ID_LABEL_XPATH = "//XCUIElementTypeStaticText[@label='Set UserId:']";
    private static final String IOS_ADVANCE_TO_STATE_LABEL_XPATH = "//XCUIElementTypeStaticText[@label=\"Advance to State:\"]";
    private static final String IOS_SELECT_ALL_XPATH = "//XCUIElementTypeMenuItem[@name='Select All']";

    @AndroidFindBy(id = "com.leanplum.rondo:id/info")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='You can type in custom events, sessions and attributes to send from here.']")
    private MobileElement descriptionText;

    @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Adhoc\"]")
    public MobileElement adhoc;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Send track Event:\"]//following-sibling::XCUIElementTypeTextField")
    @AndroidFindBy(id = "com.leanplum.rondo:id/trackName")
    private MobileElement trackEventField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Params']/following-sibling::XCUIElementTypeTextField[1]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/paramKey")
    private MobileElement trackEventParamKey;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Params']/following-sibling::XCUIElementTypeTextField[2]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/paramValue")
    private MobileElement trackEventParamValue;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label=\"Send track Event:\"]")
    @AndroidFindBy(xpath = "//*[@text='Send Track Event']")
    private MobileElement trackEventLabel;

    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonTrack")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Send Track Event\"]")
    private MobileElement trackEventButton;

    @iOSXCUITFindBy(xpath = IOS_ADVANCE_TO_STATE_LABEL_XPATH + "/following-sibling::XCUIElementTypeTextField")
    @AndroidFindBy(id = "com.leanplum.rondo:id/stateName")
    private MobileElement stateNameField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Advance to State:']")
    @AndroidFindBy(xpath = "//*[@text='Set Session State']")
    private MobileElement stateNameLabel;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label=\"Send State Event\"]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonState")
    private MobileElement stateNameButton;

    @iOSXCUITFindBy(xpath = USER_ATTRIBUTE_FIELD_XPATH + "[1]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrKey")
    private MobileElement attributeKeyField;

    @iOSXCUITFindBy(xpath = USER_ATTRIBUTE_FIELD_XPATH + "[2]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrValue")
    private MobileElement attributeValueField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label=\"User attrib:\"]")
    @AndroidFindBy(xpath = "//*[@text='Set User Attribute']")
    private MobileElement attributeLabel;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label='Set User Attribute']")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonUserAttr")
    private MobileElement userAttributeButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonDeviceLocation")
    private MobileElement locationLabel;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/locLatitude")
    private MobileElement latitudeField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/locLongitude")
    private MobileElement longitudeField;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonDeviceLocation")
    private MobileElement deviceLocationButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label='Set UserId']")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonUserId")
    private MobileElement setUserIdButton;

    @iOSXCUITFindBy(xpath = IOS_USER_ID_LABEL_XPATH)
    @AndroidFindBy(id = "com.leanplum.rondo:id/userIdKey")
    private MobileElement userIdLabel;

    @iOSXCUITFindBy(xpath = IOS_USER_ID_LABEL_XPATH + "/following-sibling::XCUIElementTypeTextField")
    @AndroidFindBy(id = "com.leanplum.rondo:id/userIdKey")
    private MobileElement userIdField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Rondo Push Notification's\"]")
    public MobileElement iosPushNotifications;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"View\"]")
    public MobileElement viewIOSPushNotifications;

    private AppiumDriver<MobileElement> driver;

    public RNAdHocPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public void sendTrackEvent(String eventName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendStateEvent(String stateName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendTrackEventWithParameter(String eventName, String paramKey, String paramValue) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendUserAttribute(String userAttributeKey, String userAttributeValue) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendDeviceLocation(String latitude, String longitude) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUserId(String userId) {
        // TODO Auto-generated method stub

    }

}
