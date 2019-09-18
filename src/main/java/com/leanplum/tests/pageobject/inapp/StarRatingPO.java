package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class StarRatingPO extends InAppPopupPO {

    private static final String ANDROID_RATING_STAR_XPATH = "//*[contains(@resource-id,'rating') and not(contains(@resource-id,'text'))]";
    private static final String ANDROID_RATING_STAR_BY_NUMBER_XPATH = "//*[contains(@resource-id,'rating-%s')]";
    private static final String ANDROID_STAR_RATING_POPUP_XPATH = ANDROID_RATING_STAR_XPATH
            + "//ancestor::*[@resource-id='view']";
    private static final String ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH = "//*[@resource-id='submit-button']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_POPUP_XPATH)
    public MobileElement starRatingPopupXpath;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_POPUP_XPATH + "//*[@resource-id='survey-question']")
    public MobileElement starRatingSurveyQuestion;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='low-rating-text']")
    public MobileElement lowRatingTextElement;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[@resource-id='high-rating-text']")
    public MobileElement highRatingTextElement;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH)
    public MobileElement ratingSubmitButton;

    MobileDriver<MobileElement> driver;

    public StarRatingPO(MobileDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean verifyStarRating(String surveyQuestion, int numberOfRatingStars, String lowRatingText,
            String highRatingText) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(starRatingPopupXpath));
        return doesRatingPopupContainStars(numberOfRatingStars)
                && verifyInAppPopup(ImmutableMap.of(starRatingSurveyQuestion, surveyQuestion, lowRatingTextElement,
                        lowRatingText, highRatingTextElement, highRatingText))
                && MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_STAR_RATING_SUBMIT_BUTTON_XPATH);
    }

    /**
     * @param rating - from 1 to maximum setup in the message(5 as default)
     */
    public void submitRating(int rating) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(starRatingPopupXpath));

        // Click on rating star
        String ratingStarXpath = String.format(ANDROID_RATING_STAR_BY_NUMBER_XPATH, String.valueOf(rating));
        driver.findElementByXPath(ratingStarXpath).click();

        // Submit
        submitRating();
    }

    public void submitRating() {
        ratingSubmitButton.click();
    }

    private boolean doesRatingPopupContainStars(int numberOfRatingStars) {
        return driver.findElementsByXPath(ANDROID_RATING_STAR_XPATH).size() == numberOfRatingStars;
    }
}
