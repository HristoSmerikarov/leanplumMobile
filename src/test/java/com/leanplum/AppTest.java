package com.leanplum;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.leanplum.base.BaseTest;
import com.leanplum.tests.pageobject.Locators;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppTest extends BaseTest {

    @Test
    public void test() {
        AppiumDriver<MobileElement> driver = (AppiumDriver<MobileElement>) getAppiumDriver();
        Locators loc = new Locators(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        
        if(System.getProperty("os").equals("android")) {
        	wait.until(ExpectedConditions.visibilityOf(loc.button1));
            loc.button1.click();
        }

        wait.until(ExpectedConditions.visibilityOf(loc.adhoc));
        loc.adhoc.click();

        wait.until(ExpectedConditions.visibilityOf(loc.buttonTrack));
        String buttonText = loc.buttonTrack.getAttribute("name");

        String sendTrackEvent = "SEND TRACK EVENT";
        Assert.assertTrue(buttonText.toLowerCase().equals(sendTrackEvent.toLowerCase()));

        driver.closeApp();

        driver.quit();
    }
}
