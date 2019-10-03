package com.leanplum.tests.pageobject;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.helpers.Utils.SwipeDirection;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AdHocPO extends BasePO {

    private static final String USER_ATTRIBUTE_FIELD_XPATH = "//XCUIElementTypeStaticText[@label='User attrib:']/following-sibling::*/XCUIElementTypeTextField";

    @AndroidFindBy(id = "com.leanplum.rondo:id/info")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='You can type in custom events, sessions and attributes to send from here.']")
    public MobileElement descriptionText;

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

    @iOSXCUITFindBy(xpath = USER_ATTRIBUTE_FIELD_XPATH + "[1]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrKey")
    public MobileElement attributeKeyField;

    @iOSXCUITFindBy(xpath = USER_ATTRIBUTE_FIELD_XPATH + "[2]")
    @AndroidFindBy(id = "com.leanplum.rondo:id/attrValue")
    public MobileElement attributeValueField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@label='Set User Attribute']")
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

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonUserId")
    public MobileElement setUserIdButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.leanplum.rondo:id/userIdKey")
    public MobileElement userIdField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Rondo Push Notification's\"]")
    public MobileElement iosPushNotifications;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"View\"]")
    public MobileElement viewIOSPushNotifications;

    private MobileDriver<MobileElement> driver;
    private Utils utils;

    public AdHocPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        this.utils = new Utils();
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

    public void setUserId(String userId) {
        sendAdHocProperties(setUserIdButton, ImmutableMap.of(userIdField, userId));
    }

    private void sendAdHocProperties(MobileElement button, Map<MobileElement, String> fieldValueMap) {
        try {
            utils.swipeToElement(this.driver, button, SwipeDirection.DOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fieldValueMap.entrySet().forEach(entry -> {
            MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(entry.getKey()));
            entry.getKey().clear();
            entry.getKey().sendKeys(entry.getValue());
        });
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(button)).click();
        try {
            utils.swipeToElement(this.driver, descriptionText, SwipeDirection.UP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //descriptionText.click();
    }

}
