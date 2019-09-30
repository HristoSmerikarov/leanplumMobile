package com.leanplum.tests.pageobject;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AdHocPO extends BasePO {

	private static final String USER_ATTRIBUTE_FIELD_XPATH = "//XCUIElementTypeStaticText[@label='User attrib:']/following-sibling::*/XCUIElementTypeTextField";

	// @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
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

	public void setUserId(String userId) {
		sendAdHocProperties(setUserIdButton, ImmutableMap.of(userIdField, userId));
	}

	private void sendAdHocProperties(MobileElement button, Map<MobileElement, String> fieldValueMap) {
		try {
			scrollToElement(button);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldValueMap.entrySet().forEach(entry -> {
			MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(entry.getKey()));
			entry.getKey().clear();
			entry.getKey().sendKeys(entry.getValue());
		});

		descriptionText.click();
		MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(button)).click();;
	}

	public boolean scrollToElement(MobileElement element) throws Exception {
		boolean isVisible = false;
		while (!isVisible) {
			try {
				MobileDriverUtils.waitForExpectedCondition(driver, 1, ExpectedConditions.visibilityOf(element));
				isVisible = true;
			} catch (Exception e) {
				swipeVertical(0.8, 0.1, 0.5, 1000);
			}
		}

		return isVisible;
	}

	public void swipeVertical(double startPercentage, double finalPercentage, double anchorPercentage, int duration)
			throws Exception {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * finalPercentage);
		getTouchAction().press(PointOption.point(anchor, startPoint))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
				.moveTo(PointOption.point(anchor, endPoint)).release().perform();
	}

	public TouchAction getTouchAction() {
		return new TouchAction(driver);
	}

}
