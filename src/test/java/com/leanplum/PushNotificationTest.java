package com.leanplum;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.leanplum.tests.base.BaseTest;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AndroidNotificationsPO;
import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class PushNotificationTest extends BaseTest {

    @Test
    public void verifyAndroidPusnNotificationInfo() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();

        BasePO basePO = new BasePO(driver);
        basePO.click(basePO.confirmAlert);

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);

        adHocPO.sendTrackEvent("deliveryNow");

        driver.openNotifications();
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(AndroidNotificationsPO.PUSH_NOTIFICATION_BY_NAME_XPATH, "Rondo"))));

        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        adHocPO.sendTrackEvent("endCampaign");
    }
}
