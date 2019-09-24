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

    public static <T> T waitForExpectedCondition(MobileDriver<MobileElement> driver, int waitTimeout,
            ExpectedCondition<T> expectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, waitTimeout);
        turnOffImplicitWaits(driver);
        T result = null;
        result = wait.until(expectedCondition);
        turnOnImplicitWaits(driver);
        return result;
    }

    public static <T> T waitForExpectedCondition(MobileDriver<MobileElement> driver,
            ExpectedCondition<T> expectedCondition) {
        return waitForExpectedCondition(driver, WAIT_TAIMEOUT, expectedCondition);
    }

    public static void waitInMs(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
