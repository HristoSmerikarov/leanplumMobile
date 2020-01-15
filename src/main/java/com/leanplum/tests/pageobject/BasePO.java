package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePO {

    private AppiumDriver<MobileElement> driver;

    public BasePO(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public AppiumDriver<MobileElement> getDriver() {
        return this.driver;
    }

    public void click(MobileElement element) {
        long startTime = System.currentTimeMillis();
        element.click();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("I spent " + elapsedTime + " seconds to click");
    }

    public boolean verifyText(MobileElement element, String expectedText) {
        return element.getText().equals(expectedText);
    }

    public String getTextFromElement(MobileElement el) {
        if (driver instanceof AndroidDriver) {
            return el.getAttribute("text");
        } else {
            String value = el.getAttribute("value");
            return value != null ? value : el.getAttribute("label");
        }
    }
}
