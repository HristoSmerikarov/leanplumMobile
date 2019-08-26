package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileBrowserPO extends BasePO {

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.android.chrome:id/action_bar_root")
    public MobileElement browser;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.android.chrome:id/url_bar")
    public MobileElement browserUrlBar;

    // com.android.chrome:id/url_bar

    AppiumDriver<MobileElement> driver;

    public MobileBrowserPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean isCorrectURLOpened(String url) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(browser));

        return url.contains(browserUrlBar.getAttribute("text"));
    }

}
