package com.leanplum.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leanplum.base.BaseTest;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AlertPO;
import com.leanplum.tests.pageobject.AndroidPushNotification;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.CenterPopupPO;
import com.leanplum.tests.pageobject.MobileBrowserPO;
import com.leanplum.utils.extentreport.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.restassured.response.Response;

public class PushNotificationTest extends BaseTest {

    private static final String LOCAL_TRIGGER = "localTrigger";
    private static final String ATTRIBUTE_NAME = "testAttribute";
    private static final String OPEN_LEANPLUM_URL = "openLeanURL";
    private static final String OUTSIDE_APP_TRIGGER = "outsideAppTrigger";
    private static final String CHANNEL_DISABLED = "channelDisabled";
    private static final String ATTRIBUTE_VALUE = "testAttr" + Utils.generateRandomNumberInRange(0, 100);
    private static final String RONDO_PUSH_NOTIFICATION = "Rondo Push Notification";
    private static final String END_TRIGGER = "endTrigger";

    @BeforeMethod
    public void setUpApp() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        driver.resetApp();
    }

    @Test(description="Push Notification's open action is New Action")
    public void pushNotOpenActionWNewAction(Method method) {
        ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");
        
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        BasePO basePO = new BasePO(driver);
        basePO.click(basePO.confirmAlertButton);
        step("Confirm alert");
        
        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(LOCAL_TRIGGER);
        step("Send track evetn: "+LOCAL_TRIGGER);
        
        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
        openNotificationsAndOpenByMessage(driver, pushNotification);
        
        AlertPO alertPO = new AlertPO(driver);
        step("Verification of alert", alertPO.isAlertCorrect("Rondo Alert123", "Warning this is a Rondo Alert!!"));

        alertPO.click(alertPO.confirmAlertButton);
        step("Confirm alert");
        
        adHocPO.sendTrackEvent(END_TRIGGER);
        step("Send track evetn: "+END_TRIGGER);
        
        ExtentTestManager.endTest();
    }
    
//    @Test
//    public void pushNotOpenActionWExistingAction() {
//        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
//
//        BasePO basePO = new BasePO(driver);
//        basePO.click(basePO.confirmAlertButton);
//
//        AdHocPO adHocPO = new AdHocPO(driver);
//        adHocPO.click(adHocPO.adhoc);
//
//        adHocPO.sendUserAttribute(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
//
//        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
//        openAndroidNotifications(driver);
//        pushNotification.waitForPresense();
//        Assert.assertTrue(pushNotification.doesContainImage());
//        pushNotification.open();
//
//        CenterPopupPO centerPopupPO = new CenterPopupPO(driver);
//        centerPopupPO.isCenterPopup("Rondo Center Popup", "Rondo is ready!", "Start now!");
//
//        centerPopupPO.clickAcceptButton();
//        adHocPO.sendTrackEvent(END_TRIGGER);
//    }
//
//    @Test
//    public void pushNotOpenURL() {
//        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
//
//        BasePO basePO = new BasePO(driver);
//        basePO.click(basePO.confirmAlertButton);
//
//        AdHocPO adHocPO = new AdHocPO(driver);
//        adHocPO.click(adHocPO.adhoc);
//
//        adHocPO.sendTrackEvent(OPEN_LEANPLUM_URL);
//
//        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
//        openNotificationsAndOpenByMessage(driver, pushNotification);
//
//        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
//        Assert.assertTrue(mobileBrowserPO.isCorrectURLOpened("http://www.leanplum.com/"));
//
//        // Confirm on resume app
//        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
//        basePO.click(basePO.confirmAlertButton);
//
//        adHocPO.sendTrackEvent(END_TRIGGER);
//    }
//
//    @Test
//    public void pushNotMuteInsideApp() {
//        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
//        BasePO basePO = new BasePO(driver);
//        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
//                .click();
//
//        AdHocPO adHocPO = new AdHocPO(driver);
//        adHocPO.click(adHocPO.adhoc);
//
//        adHocPO.sendTrackEvent(OUTSIDE_APP_TRIGGER);
//
//        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
//
//        openAndroidNotifications(driver);
//
//        Assert.assertTrue(pushNotification.confirmAnbsence());
//
//        driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
//
//        Response response = TemporaryAPI.post("9f405fddfcf1352a", "outsideAppTrigger");
//        System.out.println("Response: " + response.body().asString());
//
//        openNotificationsAndOpenByMessage(driver, pushNotification);
//
//        Assert.assertTrue(verifyCorrectURLIsOpened(driver));
//
//        // Confirm on resume app
//        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
//        // Start app alert
//        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
//                .click();
//        // Resume app alert
//        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
//                .click();
//
//        adHocPO.click(adHocPO.adhoc);
//        adHocPO.sendTrackEvent(END_TRIGGER);
//    }
//
//    @Test
//    public void pushNotWithDisabledChannel() {
//        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
//        BasePO basePO = new BasePO(driver);
//        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(basePO.confirmAlertButton))
//                .click();
//
//        AdHocPO adHocPO = new AdHocPO(driver);
//        adHocPO.click(adHocPO.adhoc);
//
//        adHocPO.sendTrackEvent(CHANNEL_DISABLED);
//
//        AndroidPushNotification pushNotification = new AndroidPushNotification(driver, RONDO_PUSH_NOTIFICATION);
//        openAndroidNotifications(driver);
//
//        Assert.assertTrue(pushNotification.confirmAnbsence());
//
//        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
//
//        adHocPO.click(adHocPO.adhoc);
//        adHocPO.sendTrackEvent(END_TRIGGER);
//    }

    private void openAndroidNotifications(AndroidDriver<MobileElement> driver) {
        driver.openNotifications();
    }

    private void openNotificationsAndOpenByMessage(AndroidDriver<MobileElement> driver,
            AndroidPushNotification pushNotification) {
        openAndroidNotifications(driver);
        step("Open notifications");
        
        pushNotification.waitForPresence();
        step("Wait for presence of notification");
        
        pushNotification.open();
        step("Open notification");
    }

    private boolean verifyCorrectURLIsOpened(AndroidDriver<MobileElement> driver) {
        MobileBrowserPO mobileBrowserPO = new MobileBrowserPO(driver);
        return mobileBrowserPO.isCorrectURLOpened("http://www.leanplum.com/");
    }

}
