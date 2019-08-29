package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leanplum.base.BaseTest;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AndroidPushNotification;
import com.leanplum.tests.pageobject.BasePO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.BannerPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.tests.pageobject.inapp.InterstitialPO;
import com.leanplum.tests.pageobject.inapp.RichInterstitialPO;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;
import com.leanplum.tests.pageobject.inapp.WebInterstitialPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class InAppTemplateTest extends BaseTest {

    private static final String START_EVENT = "templates";
    private static final String END_EVENT = "templatesEnd";

    @BeforeMethod
    public void setUpApp() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        driver.resetApp();
    }

//     @Test(description = "In-App Templates - Confirm, RichInterstitial, StarRating, CenterPopup")
//     public void confirmRichInterstitialStarRatingCenterPopupTemplates(Method method) {
//     ExtentTestManager.startTest(method.getName(),
//     "In-App Templates - Confirm, RichInterstitial, StarRating, CenterPopup");
//    
//     AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
//     AdHocPO adHocPO = sendMessage(driver);
//    
//     ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
//     verifyConfirmPopupLayout(driver, confirmInApp);
//    
//     clickAccept(confirmInApp);
//    
//     RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
//     verifyRichInterstitialLayout(driver, richInterstitial);
//    
//     richInterstitial.clickRightButton();
//     step("Click rate our app button");
//    
//    
//     //Star rating not visible in dom
//     StarRatingPO starRating = new StarRatingPO(driver);
//     step("Verify star rating popup layout",
//     starRating.verifyStarRating("Is our app useful?", 3, "Not at all", "Yes, I like it!"));
//    
//     starRating.submitRating(2);
//     step("Submit 2 star rating");
//    
//     CenterPopupPO centerPopup = new CenterPopupPO(driver);
//     step("Verify center popup layout",
//     centerPopup.verifyCenterPopup("Thanks for your feedback!", "Much appreciated!", "No problem.."));
//    
//     centerPopup.clickAcceptButton();
//     step("Accept center popup");
//    
//     adHocPO.sendTrackEvent(END_EVENT);
//     step("Send track evetn: " + END_EVENT);
//     }

    // @Test(description = "In-App Templates - Confirm, RichInterstitial, WebInterstitial")
    // public void confirmRichInterstitialWebInterstitialTemplates(Method method) {
    // ExtentTestManager.startTest(method.getName(), "In-App Templates - Confirm, RichInterstitial, WebInterstitial");
    //
    // AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
    // AdHocPO adHocPO = sendMessage(driver);
    //
    // ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
    // verifyConfirmPopupLayout(driver, confirmInApp);
    //
    // clickAccept(confirmInApp);
    //
    // RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
    // verifyRichInterstitialLayout(driver, richInterstitial);
    //
    // richInterstitial.clickLeftButton();
    // step("Click read release notes button");
    //
    // WebInterstitialPO webInterstitial = new WebInterstitialPO(driver);
    // step("Verify web interstitial popup layout", webInterstitial.verifyWebInterstitial());
    //
    // webInterstitial.closeWebInterstitial();
    // step("Close web interstitial");
    //
    // adHocPO.sendTrackEvent(END_EVENT);
    // step("Send track evetn: " + END_EVENT);
    // }
//
    @Test(description = "In-App Templates - Confirm, Interstitial, Alert, Banner")
    public void confirmInterstitialAlertBannerTemplates(Method method) {
        ExtentTestManager.startTest(method.getName(), "In-App Templates - Confirm, Interstitial, Alert, Banner");

        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        AdHocPO adHocPO = sendMessage(driver);

        ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
        verifyConfirmPopupLayout(driver, confirmInApp);

        clickCancel(confirmInApp);

        InterstitialPO interstitial = new InterstitialPO(driver);
        step("Verify interstitial layout",
                interstitial.verifyInterstitialLayout("Reminder", "We'll set you a banner as a reminder!", "Okay.."));

        interstitial.clickAcceptButton();
        step("Click accept button");

        AlertPO alert = new AlertPO(driver);
        step("Verify alert layout", alert.verifyAlertLayout("Banner will be shown..", ".. as a reminder!"));

        alert.click(alert.confirmAlertButton);
        step("Click okay");

        BannerPO banner = new BannerPO(driver);
        step("Verify banner popup layout",
                banner.verifyBannerLayout("New version of the app available!", "Down load when you can!"));

        banner.clickOnCloseBanner();
        step("Close banner");

        adHocPO.sendTrackEvent(END_EVENT);
        step("Send track evetn: " + END_EVENT);
    }

    private void verifyConfirmPopupLayout(AndroidDriver<MobileElement> driver, ConfirmInAppPO confirmInApp) {
        step("Verify confirm popup layout", confirmInApp.verifyConfirmInApp("New update!", "The new update is here!",
                "Download now!", "Maybe later.."));
    }

    private void clickAccept(ConfirmInAppPO confirmInApp) {
        confirmInApp.clickAcceptButton();
        step("Click download now");
    }

    private void clickCancel(ConfirmInAppPO confirmInApp) {
        confirmInApp.clickCancelButton();
        step("Click maybe later..");
    }

    private void verifyRichInterstitialLayout(AndroidDriver<MobileElement> driver,
            RichInterstitialPO richInterstitial) {
        step("Verify rich interstitial popup layout", richInterstitial.verifyRichInterstitial("Download now..",
                ".. from the app store", "Read realease notes", "Rate our app"));
    }

    private AdHocPO sendMessage(AndroidDriver<MobileElement> driver) {
        BasePO basePO = new BasePO(driver);
        basePO.click(basePO.confirmAlertButton);
        step("Confirm alert");

        AdHocPO adHocPO = new AdHocPO(driver);
        adHocPO.click(adHocPO.adhoc);
        step("Click on Ad-Hoc");

        adHocPO.sendTrackEvent(START_EVENT);
        step("Send track evetn: " + START_EVENT);

        return adHocPO;
    }
}
