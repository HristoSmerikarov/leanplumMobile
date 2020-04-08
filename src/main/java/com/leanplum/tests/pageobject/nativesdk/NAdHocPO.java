package com.leanplum.tests.pageobject.nativesdk;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.helpers.Utils.SwipeDirection;
import com.leanplum.tests.pageobject.AdHocPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class NAdHocPO extends AdHocPO {

    private static final String USER_ATTRIBUTE_FIELD_XPATH = "//XCUIElementTypeStaticText[@label='User attrib:']/following-sibling::*/XCUIElementTypeTextField";
    private static final String IOS_USER_ID_LABEL_XPATH = "//XCUIElementTypeStaticText[@label='Set UserId:']";
    private static final String IOS_ADVANCE_TO_STATE_LABEL_XPATH = "//XCUIElementTypeStaticText[@label=\"Advance to State:\"]";
    private static final String IOS_SELECT_ALL_XPATH = "//XCUIElementTypeMenuItem[@name='Select All']";

    @AndroidFindBy(id = "com.leanplum.rondo:id/info")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='You can type in custom events, sessions and attributes to send from here.']")
    private MobileElement descriptionText;

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

    public NAdHocPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public void sendTrackEvent(String eventName) {
        sendAdHocProperties(driver, trackEventButton, trackEventLabel, ImmutableMap.of(trackEventField, eventName));
    }

    @Override
    public void sendStateEvent(String stateName) {
        sendAdHocProperties(driver, stateNameButton, stateNameLabel, ImmutableMap.of(stateNameField, stateName));
    }

    @Override
    public void sendTrackEventWithParameter(String eventName, String paramKey, String paramValue) {
        sendAdHocProperties(driver, trackEventButton, trackEventLabel, ImmutableMap.of(trackEventField, eventName,
                trackEventParamKey, paramKey, trackEventParamValue, paramValue));
    }

    @Override
    public void sendUserAttribute(String userAttributeKey, String userAttributeValue) {
        sendAdHocProperties(driver, userAttributeButton, attributeLabel,
                ImmutableMap.of(attributeKeyField, userAttributeKey, attributeValueField, userAttributeValue));
    }

    @Override
    public void sendDeviceLocation(String latitude, String longitude) {
        Utils.swipeToElement(this.driver, deviceLocationButton, SwipeDirection.DOWN);
        sendAdHocProperties(driver, deviceLocationButton, locationLabel,
                ImmutableMap.of(latitudeField, latitude, longitudeField, longitude));
    }

    @Override
    public void setUserId(String userId) {
        Utils.swipeToElement(driver, userIdField, SwipeDirection.DOWN);
        sendAdHocProperties(driver, setUserIdButton, userIdLabel, ImmutableMap.of(userIdField, userId));
        Utils.swipeToElement(driver, descriptionText, SwipeDirection.UP);
    }

    // Workaround after iOS13
    protected void populateIOSFields(AppiumDriver<MobileElement> driver, MobileElement el, String value) {
        long startTime = System.currentTimeMillis();
        TouchAction action = new TouchAction(driver);
        action.tap(new TapOptions().withElement(new ElementOption().withElement(el)))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(50))).release().perform()
                .tap(new TapOptions().withElement(new ElementOption().withElement(el)))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(50))).release().perform();
        long stopTimePopulate = System.currentTimeMillis();
        long elapsedTimePopulate = stopTimePopulate - startTime;
        System.out.println("I spent " + elapsedTimePopulate + " seconds to click select all");

        if (MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_SELECT_ALL_XPATH)) {
            driver.findElement(By.xpath(IOS_SELECT_ALL_XPATH)).click();
        }

        el.sendKeys(value);
    }

    protected void populateAndoridFields(MobileElement el, String value) {
        el.clear();
        el.sendKeys(value);
    }
}
