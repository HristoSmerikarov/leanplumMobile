package com.leanplum.tests.pushnotification;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class IOSPushNotification implements PushNotification{

    private static final String PUSH_NOTIFICATION_MESSAGE_XPATH = "//XCUIElementTypeStaticText[contains(@name,\"%s\")]";
    private static final String PUSH_NOTIFICATION_BY_MESSAGE_XPATH = PUSH_NOTIFICATION_MESSAGE_XPATH
            + "/ancestor::XCUIElementTypeAlert";
    private static final String PUSH_NOTIFICATION_VIEW_BUTTON_XPATH = PUSH_NOTIFICATION_BY_MESSAGE_XPATH
            + "//XCUIElementTypeButton[@name=\"View\"]";
    private static final String PUSH_NOTIFICATION_CANCEL_BUTTON_XPATH = PUSH_NOTIFICATION_BY_MESSAGE_XPATH
            + "//XCUIElementTypeButton[@name=\"Cancel\"]";
//    private static final String PICTURE_IN_PUSH_NOTIFICATION_XPATH = PUSH_NOTIFICATION_BY_MESSAGE_XPATH
//            + "//*[@resource-id='android:id/big_picture']";
    
    private MobileDriver<MobileElement> driver;
    private String message;

    /**
     * Push notification identified by message since several push notifications from same app can be displayed
     * @param driver
     * @param message
     */
    public IOSPushNotification(MobileDriver<MobileElement> driver, String message) {
        this.driver = driver;
        this.message = message;
    }
    
    @Override
    public boolean doesContainImage() {
//        String pictureInPushNotificationFormattedXpath = String.format(PICTURE_IN_PUSH_NOTIFICATION_XPATH, message);
//        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, pictureInPushNotificationFormattedXpath);
    return false;
    }

    @Override
    public void waitForPresence() {
        String formattedNotificationXpath = String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message);

        MobileDriverUtils.waitForExpectedCondition(driver, 120,
                ExpectedConditions.visibilityOfElementLocated(By.xpath(formattedNotificationXpath)));
    }

    @Override
    public boolean isAbsent() {
        try {
            MobileDriverUtils.waitForExpectedCondition(driver, 10, ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void view() {
        MobileDriverUtils
        .waitForExpectedCondition(driver, ExpectedConditions
                .visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_VIEW_BUTTON_XPATH, message))))
        .click();
    }

    @Override
    public void dismiss() {
    	 MobileDriverUtils
         .waitForExpectedCondition(driver, ExpectedConditions
                 .visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_CANCEL_BUTTON_XPATH, message))))
         .click();
    }
}
