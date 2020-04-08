package com.leanplum.tests.campaigncomposer;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.AppSetupPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.CenterPopupPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.tests.pageobject.inapp.InterstitialPO;
import com.leanplum.tests.pageobject.inapp.StarRatingPO;
import com.leanplum.tests.pageobject.inapp.WebInterstitialPO;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

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

            AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(driver, appSetupPO, userId);

            AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
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

            AlertPO alert = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alert);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendUserAttribute(adHocPO, "testAttribute", "attrb" + Utils.generateRandomNumberInRange(0, 100));
            MobileDriverUtils.waitInMs(2000);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = ConfirmInAppPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify Confirm message template",
                    confirmInApp.verifyConfirmInApp(CONFIRM_TITLE, CONFIRM_MESSAGE, CONFIRM_ACCEPT, CONFIRM_CANCEL));

            // Click Accept button
            startStep("Click accept on confirm message");
            confirmInApp.acceptConfirmMessage();
            endStep();

            InterstitialPO interstitial = InterstitialPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify interstitial layout", interstitial.verifyInterstitialLayout(
                    "Остави ни обратна връзка!", "Interstitial message goes here.", "Okay 👍"));

            // Click accept button
            startStep("Click accept on interstitial message");
            interstitial.clickAcceptButton();
            endStep();

            // Star rating not visible in dom
            StarRatingPO starRating = StarRatingPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify star rating popup layout",
                    starRating.verifyStarRating("Do you our product?", 7, "No, I hate it!", "Yes, I like it!"));

            // Leave rating and submit
            startStep("Submit 6 stars");
            starRating.submitRating(6);
            endStep();

            // Verify center popup layout
            CenterPopupPO centerPopup = CenterPopupPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify center popup layout",
                    centerPopup.verifyCenterPopup("Many thanks!", "💙💙", "Благодарим много!"));

            // Click accept
            startStep("Click accept on center popup message");
            centerPopup.clickAcceptButton();
            endStep();

            // Verify web interstitial layout
            WebInterstitialPO webInterstitial = WebInterstitialPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify web interstitial popup layout", webInterstitial.verifyWebInterstitial());

            // Close webInterstitial
            startStep("Close web interstitial message");
            webInterstitial.closeWebInterstitial();
            endStep();

            // Send conversion event
            MobileDriverUtils.waitInMs(2000);
            stepHelper.sendTrackEvent(AdHocPO.initialize(driver, sdk), CONVERSION_EVENT);
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

            AppSetupPO appSetupPO = AppSetupPO.initialize(driver, sdk);
            String userId = "rondoTestUser" + Utils.generateRandomNumberInRange(0, 10);
            setUserId(driver, appSetupPO, userId);

            AdHocPO adHocPO = AdHocPO.initialize(driver, sdk);
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

            AlertPO alert = AlertPO.initialize(driver, sdk);
            stepHelper.dismissAllAlertsOnAppStart(alert);

            stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

            stepHelper.sendUserAttribute(adHocPO, "testAttribute", "attrb" + Utils.generateRandomNumberInRange(0, 100));
            MobileDriverUtils.waitInMs(2000);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = ConfirmInAppPO.initialize(driver, sdk);
            stepHelper.verifyCondition("Verify Confirm message template",
                    confirmInApp.verifyConfirmInApp(CONFIRM_TITLE, CONFIRM_MESSAGE, CONFIRM_ACCEPT, CONFIRM_CANCEL));

            // Click Accept button
            startStep("Click cancel on confirm message");
            confirmInApp.cancelConfirmMessage();
            endStep();

            // Verify alert layout
            stepHelper.verifyCondition("Verify alert layout", alert.verifyAlertLayout("Bye bye", "🖖", "Thanks!"));

            // Confrim alert
            stepHelper.dismissAlert(alert);

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
