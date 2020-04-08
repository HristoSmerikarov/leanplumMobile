package com.leanplum.tests.pageobject.nativesdkinapp;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class NStarRatingPO extends StarRatingPO {

    private static final String ANDROID_RATING_STAR_XPATH = "//*[contains(@resource-id,'rating') and not(contains(@resource-id,'text'))]";
    private static final String ANDROID_RATING_STAR_BY_NUMBER_XPATH = "//*[contains(@resource-id,'rating-%s')]";
    private static final String ANDROID_STAR_RATING_POPUP_XPATH = ANDROID_RATING_STAR_XPATH
            + "//ancestor::*[@resource-id='view']";
    private static final String ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH = "//*[@resource-id='submit-button']";
    private static final String IOS_STAR_RATING_XPATH = "//XCUIElementTypeWebView[@visible='true']";
    private static final String IOS_STAR_RATING_TEXT_XPATH = "(" + IOS_STAR_RATING_XPATH
            + "//XCUIElementTypeStaticText)";
    private static final String IOS_RATING_STAR_BY_NUMBER_XPATH = "(" + IOS_STAR_RATING_XPATH
            + "//XCUIElementTypeOther[not(*)])[%s]";
    private static final String IOS_STAR_RATING_SUBMIT_BUTTON_XPATH = IOS_STAR_RATING_TEXT_XPATH + "[4]";

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_XPATH)
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_POPUP_XPATH)
    private MobileElement starRatingPopupXpath;

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_TEXT_XPATH + "[1]")
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_POPUP_XPATH + "//*[@resource-id='survey-question']")
    private MobileElement starRatingSurveyQuestion;

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_TEXT_XPATH + "[2]")
    @AndroidFindBy(xpath = "//*[@resource-id='low-rating-text']")
    private MobileElement lowRatingTextElement;

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_TEXT_XPATH + "[3]")
    @AndroidFindBy(xpath = "//*[@resource-id='high-rating-text']")
    private MobileElement highRatingTextElement;

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_SUBMIT_BUTTON_XPATH)
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH)
    private MobileElement ratingSubmitButton;

    @iOSXCUITFindBy(xpath = IOS_STAR_RATING_XPATH + "//XCUIElementTypeOther[not(*)]")
    @AndroidFindBy(xpath = ANDROID_RATING_STAR_XPATH)
    private List<MobileElement> ratingStars;

    private AppiumDriver<MobileElement> driver;

    public NStarRatingPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Override
    public boolean verifyStarRating(String surveyQuestion, int numberOfRatingStars, String lowRatingText,
            String highRatingText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(starRatingPopupXpath));

        return doesRatingPopupContainStars(numberOfRatingStars)
                && verifyInAppPopup(driver, ImmutableMap.of(starRatingSurveyQuestion, surveyQuestion,
                        lowRatingTextElement, lowRatingText, highRatingTextElement, highRatingText))
                && isSubmitButtonPresent();
    }

    /**
     * @param rating - from 1 to maximum setup in the message(5 as default)
     */
    @Override
    public void submitRating(int rating) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(starRatingPopupXpath));

        // Click on rating star
        String ratingStarXpath = constructRatingStarXpath(rating);
        driver.findElementByXPath(ratingStarXpath).click();

        // Submit
        submitRating();
    }

    private void submitRating() {
        ratingSubmitButton.click();
    }

    private boolean isSubmitButtonPresent() {
        if (driver instanceof AndroidDriver) {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH);
        } else {
            return MobileDriverUtils.doesSelectorMatchAnyElements(driver, IOS_STAR_RATING_SUBMIT_BUTTON_XPATH);
        }
    }

    /**
     * String.format cannot be used with FindBy annotation
     * 
     * @param rating
     * @return
     */
    private String constructRatingStarXpath(int rating) {
        if (this.driver instanceof AndroidDriver) {
            return String.format(ANDROID_RATING_STAR_BY_NUMBER_XPATH, String.valueOf(rating));
        } else {
            return String.format(IOS_RATING_STAR_BY_NUMBER_XPATH, String.valueOf(rating + 1));
        }
    }

    /**
     * For locators simplification a driver type check is introduced
     * 
     * @param numberOfRatingStars
     * @return
     */
    private boolean doesRatingPopupContainStars(int numberOfRatingStars) {
        if (this.driver instanceof AndroidDriver) {
            return ratingStars.size() == numberOfRatingStars;
        } else {
            return ratingStars.size() - 1 == numberOfRatingStars;
        }
    }
}
