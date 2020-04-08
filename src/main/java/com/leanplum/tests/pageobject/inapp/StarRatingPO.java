package com.leanplum.tests.pageobject.inapp;

import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.nativesdkinapp.NStarRatingPO;
import com.leanplum.tests.pageobject.reactnativeinapp.RNStarRatingPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class StarRatingPO extends BasePO {

    public StarRatingPO(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public static <T extends StarRatingPO> StarRatingPO initialize(AppiumDriver<MobileElement> driver, SdkEnum sdk) {
        if (sdk.equals(SdkEnum.NATIVE)) {
            return new NStarRatingPO(driver);
        } else {
            return new RNStarRatingPO(driver);
        }
    }

    public abstract boolean verifyStarRating(String surveyQuestion, int numberOfRatingStars, String lowRatingText,
            String highRatingText);

    /**
     * @param rating - from 1 to maximum setup in the message(5 as default)
     */
    public abstract void submitRating(int rating);
}
