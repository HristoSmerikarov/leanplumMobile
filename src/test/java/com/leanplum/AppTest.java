package com.leanplum;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leanplum.tests.base.BaseTest;
import com.leanplum.tests.pageobject.Locators;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AppTest extends BaseTest {

    @Test
    public void test() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        Locators loc = new Locators(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(loc.button1));
        loc.button1.click();

        wait.until(ExpectedConditions.visibilityOf(loc.adhoc));
        loc.adhoc.click();

        wait.until(ExpectedConditions.visibilityOf(loc.buttonTrack));
        String buttonText = loc.buttonTrack.getAttribute("name");

        Assert.assertTrue(buttonText.equals("SEND TRACK EVENT"));

        driver.closeApp();

        driver.quit();
    }
}
