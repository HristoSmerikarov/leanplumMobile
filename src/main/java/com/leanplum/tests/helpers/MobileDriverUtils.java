package com.leanplum.tests.helpers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class MobileDriverUtils {

    private static final int WAIT_TAIMEOUT = 60;

    public static boolean doesSelectorMatchAnyElements(WebDriver driver, String xpathSelector) {
        turnOffImplicitWaits(driver);
        boolean matchAnyElements = !driver.findElements(By.xpath(xpathSelector)).isEmpty();
        turnOnImplicitWaits(driver);
        return matchAnyElements;
    }

    public static <T> T waitForExpectedCondition(MobileDriver<MobileElement> driver,
            ExpectedCondition<T> expectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TAIMEOUT);
        T result;
        result = wait.until(expectedCondition);
        return result;
    }

    /**
     * Turn off Implicit Waits
     */
    private static void turnOffImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    /**
     * Turn on Implicit Waits
     */
    private static void turnOnImplicitWaits(WebDriver driver) {
        turnOnImplicitWaits(60, driver);
    }

    /**
     * Turn on Implicit Waits
     */
    private static void turnOnImplicitWaits(int secondsToWait, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(secondsToWait, TimeUnit.SECONDS);
    }
}
