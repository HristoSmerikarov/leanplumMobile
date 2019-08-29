package com.leanplum.tests.pageobject;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class InAppMessagesPO extends BasePO {

    @AndroidFindBy(xpath = "//*[text()='alert']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement alert;

    @AndroidFindBy(xpath = "//*[text()='centerPopup']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement centerPopup;

    @AndroidFindBy(xpath = "//*[text()='confirm']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement confirm;

    @AndroidFindBy(xpath = "//*[text()='interstitial']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement interstitial;

    @AndroidFindBy(xpath = "//*[text()='richInterstitial']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement richInterstitial;

    @AndroidFindBy(xpath = "//*[text()='webInterstitial']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement webInterstitial;

    @AndroidFindBy(xpath = "//*[text()='banner']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement banner;
    
    @AndroidFindBy(xpath = "//*[text()='Red Popup Title']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement centerPopupTitle;
    
    @AndroidFindBy(xpath = "//*[text()='This should be blue']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement centerPopupMessage;
    
    @AndroidFindBy(xpath = "//*[text()='Green OK']")
    // @iOSXCUITFindBy(xpath = ""]")
    public MobileElement centerPopupConfirmButton;

    public InAppMessagesPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }
    
    /**
     * Confirms alert message and closes alert
     * @return
     */
    public boolean isAlertMessageCorrect() {
        if(!alertTitle.isDisplayed()) {
            return false;
        }
       
        if(!alertMessage.isDisplayed()) {
            return false;
        }
        
        click(confirmAlertButton);
        return true;
    }
    
    public boolean isCenterPopupCorrect() {
        if(!centerPopupTitle.isDisplayed()) {
            return false;
        }
        
        if(!centerPopupMessage.isDisplayed()) {
            return false;
        }
        
        if(!centerPopupConfirmButton.isDisplayed()) {
            return false;
        }
        
        click(centerPopupConfirmButton);
        return true;
    }

}
