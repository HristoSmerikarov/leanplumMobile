package com.leanplum.tests.campaigncomposer;

import java.lang.reflect.Method;
import java.util.Set;

import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.api.TemporaryAPI;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.tests.pageobject.nativesdk.AppInboxMessagePO;
import com.leanplum.tests.pageobject.nativesdk.AppSetupPO;
import com.leanplum.tests.pageobject.nativesdkinapp.AlertPO;
import com.leanplum.tests.pageobject.nativesdkinapp.BannerPO;
import com.leanplum.tests.pageobject.nativesdkinapp.CenterPopupPO;
import com.leanplum.tests.pageobject.nativesdkinapp.ConfirmInAppPO;
import com.leanplum.tests.pageobject.nativesdkinapp.InterstitialPO;
import com.leanplum.tests.pageobject.nativesdkinapp.RichInterstitialPO;
import com.leanplum.tests.pageobject.nativesdkinapp.StarRatingPO;
import com.leanplum.tests.pageobject.nativesdkinapp.WebInterstitialPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.response.Response;

@Listeners({ TestListener.class })
public class InAppTemplatesCampaignComposer extends CommonTestSteps {

    private static final String IN_APP_ENTER = "inAppTemplateTrigger";
    private static final String CONVERSION_EVENT = "inAppTemplates";
    private static final String TEMPLATE_VERIFICATION = "templateVerification";
    private static final String CONFIRM_TITLE = "Confirm message title";
    private static final String CONFIRM_MESSAGE = "Желаете ли да продължите?";
    private static final String CONFIRM_ACCEPT = "👍👍";
    private static final String CONFIRM_CANCEL = "😢😢";

    @Parameters({ "id" })
    @Test(groups = {
            "release" }, description = "Verification of automatically created campaign with Confirm, Interstitial, Rating, Center Popup and Web Interstitial In-App messages")
    public void InterstitialAndAlert(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(appSetupPO, userId);

            NAdHocPO adHocPO = new NAdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // Send conversion event
            stepHelper.sendTrackEvent(new NAdHocPO(driver), CONVERSION_EVENT);
            MobileDriverUtils.waitInMs(2000);

            stepHelper.sendTrackEvent(adHocPO, IN_APP_ENTER);
            MobileDriverUtils.waitInMs(2000);

            startStep("Restart app");
            driver.closeApp();
            MobileDriverUtils.waitInMs(15000);
            driver.launchApp();
            endStep();

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendUserAttribute(adHocPO, "testAttribute",
                    "attrb" + Utils.generateRandomNumberInRange(0, 100));
            MobileDriverUtils.waitInMs(2000);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            stepHelper.verifyCondition("Verify Confirm message template",
                    confirmInApp.verifyConfirmInApp(CONFIRM_TITLE, CONFIRM_MESSAGE, CONFIRM_ACCEPT, CONFIRM_CANCEL));

            // Click Accept button
            stepHelper.clickElement(confirmInApp, confirmInApp.confirmAcceptButton,
                    "Confirm popup cancel button - " + CONFIRM_ACCEPT);

            InterstitialPO interstitial = new InterstitialPO(driver);
            stepHelper.verifyCondition("Verify interstitial layout", interstitial.verifyInterstitialLayout(
                    "Остави ни обратна връзка!", "Interstitial message goes here.", "Okay 👍"));

            // Click accept button
            stepHelper.clickElement(interstitial, interstitial.interstitialAcceptButton,
                    "Interstitial's Accept button");

            // Star rating not visible in dom
            StarRatingPO starRating = new StarRatingPO(driver);
            stepHelper.verifyCondition("Verify star rating popup layout",
                    starRating.verifyStarRating("Do you our product?", 7, "No, I hate it!", "Yes, I like it!"));

            // Leave rating and submit
            startStep("Submit 6 stars");
            starRating.submitRating(6);
            endStep();

            // Verify center popup layout
            CenterPopupPO centerPopup = new CenterPopupPO(driver);
            stepHelper.verifyCondition("Verify center popup layout",
                    centerPopup.verifyCenterPopup("Many thanks!", "💙💙", "Благодарим много!"));

            // Click accept
            stepHelper.clickElement(centerPopup, centerPopup.centerPopupAcceptButton, "Accept center popup");

            // Verify web interstitial layout
            WebInterstitialPO webInterstitial = new WebInterstitialPO(driver);
            stepHelper.verifyCondition("Verify web interstitial popup layout", webInterstitial.verifyWebInterstitial());

            // Close webInterstitial
            stepHelper.clickElement(webInterstitial, webInterstitial.webInterstitialCloseButton,
                    "Web Interstitial's close icon");

            // Send conversion event
            MobileDriverUtils.waitInMs(2000);
            stepHelper.sendTrackEvent(new NAdHocPO(driver), CONVERSION_EVENT);
            MobileDriverUtils.waitInMs(2000);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    @Parameters({ "id" })
    @Test(groups = {
            "release" }, description = "Verification of automatically created campaign with Confirm and Alert In-App messages")
    public void confirmAndAlert(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            AppSetupPO appSetupPO = new AppSetupPO(driver);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(appSetupPO, userId);

            NAdHocPO adHocPO = new NAdHocPO(driver);
            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            // Send conversion event
            stepHelper.sendTrackEvent(new NAdHocPO(driver), CONVERSION_EVENT);
            MobileDriverUtils.waitInMs(2000);

            stepHelper.sendTrackEvent(adHocPO, IN_APP_ENTER);
            MobileDriverUtils.waitInMs(2000);

            startStep("Restart app");
            driver.closeApp();
            MobileDriverUtils.waitInMs(15000);
            driver.launchApp();
            endStep();

            AlertPO alert = new AlertPO(driver);
            stepHelper.acceptAllAlertsOnAppStart(alert);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendUserAttribute(adHocPO, "testAttribute",
                    "attrb" + Utils.generateRandomNumberInRange(0, 100));
            MobileDriverUtils.waitInMs(2000);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            stepHelper.verifyCondition("Verify Confirm message template",
                    confirmInApp.verifyConfirmInApp(CONFIRM_TITLE, CONFIRM_MESSAGE, CONFIRM_ACCEPT, CONFIRM_CANCEL));

            // Click Accept button
            stepHelper.clickElement(confirmInApp, confirmInApp.confirmCancelButton,
                    "Confirm popup cancel button - " + CONFIRM_CANCEL);

            // Verify alert layout
            stepHelper.verifyCondition("Verify alert layout", alert.verifyAlertLayout("Bye bye", "🖖", "Thanks!"));

            // Confrim alert
            stepHelper.clickElement(alert, alert.confirmAlertButton, "Thanks!");

            // Send conversion event
            stepHelper.sendTrackEvent(new NAdHocPO(driver), CONVERSION_EVENT);
            MobileDriverUtils.waitInMs(2000);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    // @Parameters({ "id" })
    // @Test(groups = {
    // "release" }, description = "Verification of automatically created campaign with Rich Interstitial and Banner
    // In-App messages")
    // public void richInAppAndBanner(Method method, String id) {
    // try {
    // AppiumDriver<MobileElement> driver = initiateTest();
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    //
    // AppSetupPO appSetupPO = new AppSetupPO(driver);
    // String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 100);
    // setUserId(appSetupPO, userId);
    //
    // AdHocPO adHocPO = new AdHocPO(driver);
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    //
    // // Send conversion event
    // stepHelper.sendTrackEvent(new AdHocPO(driver), CONVERSION_EVENT);
    // MobileDriverUtils.waitInMs(2000);
    //
    // stepHelper.sendTrackEvent(adHocPO, IN_APP_ENTER);
    // MobileDriverUtils.waitInMs(2000);
    //
    // startStep("Restart app");
    // driver.closeApp();
    // MobileDriverUtils.waitInMs(15000);
    // driver.launchApp();
    // endStep();
    //
    // AlertPO alert = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alert);
    //
    // stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
    //
    // stepHelper.sendTrackEvent(adHocPO, TEMPLATE_VERIFICATION);
    // MobileDriverUtils.waitInMs(2000);
    //
    // // Verify rich interstitial
    // RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
    // stepHelper.verifyCondition("Verify Rich-Interstitial template", richInterstitial.verifyRichInterstitial(
    // "Floating interstitial title", "Текст на съобщението 👌 😎", "Accept", "Decline", true));
    //
    // // Click left button
    // stepHelper.clickElement(richInterstitial, richInterstitial.richInterstitialLeftButton,
    // "left button - Accept");
    //
    // // TODO Cannot be located on Android
    // BannerPO banner = new BannerPO(driver);
    // stepHelper.verifyCondition("Verify banner popup layout",
    // banner.verifyBannerLayout("Заглавие на банера", "Banner message"));
    //
    // stepHelper.clickElement(banner, banner.bannerCloseButton, "banner close icon");
    //
    // // Send conversion event
    // stepHelper.sendTrackEvent(new AdHocPO(driver), CONVERSION_EVENT);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // endStep(e.toString(), false);
    // }
    // endTest();
    // }
    //

}
