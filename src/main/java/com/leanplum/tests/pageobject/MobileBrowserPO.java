package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileBrowserPO extends BasePO {

    private static final String BROWSER_PROGRESS_BAR_XPATH = "//*[@class='android.widget.ProgressBar']";
    
    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.android.chrome:id/action_bar_root")
    public MobileElement browser;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(id = "com.android.chrome:id/url_bar")
    public MobileElement browserUrlBar;

    // com.android.chrome:id/url_bar

    MobileDriver<MobileElement> driver;

    public MobileBrowserPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean isCorrectURLOpened(String url) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(browser));
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.invisibilityOfElementLocated(By.xpath(BROWSER_PROGRESS_BAR_XPATH)));
        return url.contains(browserUrlBar.getAttribute("text"));
    }

}
