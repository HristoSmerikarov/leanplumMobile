package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.pageobject.nativesdk.NAdHocPO;
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
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

@Listeners({ TestListener.class })
public class InAppTemplateTest extends CommonTestSteps {

    private static final String START_EVENT = "templates";
    private static final String PARAM_KEY = "update";
    private static final String PARAM_VALUE = "true";
    private static final String END_EVENT = "templatesEnd";
    private static final String VERIFY_CONFIRM_POPUP_LAYOUT = "Verify confirm popup layout";
    private static final String NEW_UPDATE = "New update!";
    private static final String THE_NEW_UPDATE_IS_HERE = "The new update is here!";
    private static final String CONFIRM_ACCEPT = "Download now!";
    private static final String CONFIRM_CANCEL = "Maybe later..";
    private static final String VERIFY_RICH_INTERSTITIAL = "Verify rich interstitial popup layout";
    private static final String RICH_INTERSTITIAL_TITLE = "Свали сега..";
    private static final String RICH_INTERSTITIAL_MESSAGE = ".. from the app store :)";
    private static final String RICH_INTERSTITIAL_LEFT_BUTTON = "Read release notes";
    private static final String RICH_INTERSTITIAL_RIGHT_BUTTON = "Rate our app";
    private static final String VERIFY_CENTER_POPUP_LAYOUT = "Verify center popup layout";
    private static final String CENTER_POPUP_TITLE = "Downloaded!";
    private static final String CENTER_POPUP_MESSAGE = "Please leave us your feedback!";
    private static final String CENTER_POPUP_BUTTON = "No problem..";

    /**
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186450">C186450</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186453">C186453</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186454">C186454</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186449">C186449</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/190758">C190758</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186466">C186466</a>
     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186465">C186465</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "inAppTemplates" }, description = "In-App Templates - Confirm, RichInterstitial, StarRating, CenterPopup")
    public void confirmRichInterstitialStarRatingCenterPopupTemplates(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            NAdHocPO adHocPO = sendEventWithParameters(driver, stepHelper, START_EVENT, PARAM_KEY, PARAM_VALUE);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            verifyConfirmPopupLayout(driver, stepHelper, confirmInApp);

            // Click Accept button
            clickAccept(stepHelper, confirmInApp);

            // Verify rich interstitial
            RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
            verifyRichInterstitialLayout(driver, stepHelper, richInterstitial);

            // Click right button
            stepHelper.clickElement(richInterstitial, richInterstitial.richInterstitialRightButton,
                    "right button - " + RICH_INTERSTITIAL_RIGHT_BUTTON);

            // Verify center popup layout
            CenterPopupPO centerPopup = new CenterPopupPO(driver);
            stepHelper.verifyCondition(VERIFY_CENTER_POPUP_LAYOUT,
                    centerPopup.verifyCenterPopup(CENTER_POPUP_TITLE, CENTER_POPUP_MESSAGE, CENTER_POPUP_BUTTON));

            // Click accept
            stepHelper.clickElement(centerPopup, centerPopup.centerPopupAcceptButton, "Accept center popup");

            // Star rating not visible in dom
            StarRatingPO starRating = new StarRatingPO(driver);
            stepHelper.verifyCondition("Verify star rating popup layout",
                    starRating.verifyStarRating("Do you like the new update?", 3, "I hate it!", "I love it!"));

            // Leave rating and submit
            startStep("Submit 2 stars");
            starRating.submitRating(2);
            endStep();

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_EVENT);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    /**
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186450">C186450</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186453">C186453</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186453">C186453</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186452">C186452</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/190758">C190758</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186465">C186465</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "inAppTemplates" }, description = "In-App Templates - Confirm, RichInterstitial, WebInterstitial")
    public void confirmRichInterstitialWebInterstitialTemplates(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            NAdHocPO adHocPO = sendEventWithParameters(driver, stepHelper, START_EVENT, PARAM_KEY, PARAM_VALUE);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            verifyConfirmPopupLayout(driver, stepHelper, confirmInApp);

            // Click Accept button
            clickAccept(stepHelper, confirmInApp);

            // Verify rich interstitial
            RichInterstitialPO richInterstitial = new RichInterstitialPO(driver);
            verifyRichInterstitialLayout(driver, stepHelper, richInterstitial);

            // Click left button
            stepHelper.clickElement(richInterstitial, richInterstitial.richInterstitialLeftButton,
                    "left button - " + RICH_INTERSTITIAL_LEFT_BUTTON);

            // Verify web interstitial layout
            WebInterstitialPO webInterstitial = new WebInterstitialPO(driver);
            stepHelper.verifyCondition("Verify web interstitial popup layout", webInterstitial.verifyWebInterstitial());

            // Close webInterstitial
            stepHelper.clickElement(webInterstitial, webInterstitial.webInterstitialCloseButton,
                    "Web Interstitial's close icon");

            // Send end event
            stepHelper.sendTrackEvent(adHocPO, END_EVENT);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    /**
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186450">C186450</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186451">C186451</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186448">C186448</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186455">C186455</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/190758">C190758</a>
     * @see <a href=
     *      "https://teamplumqa.testrail.com/index.php?/cases/view/186465">C186465</a>
     */
    @Parameters({ "id" })
    @Test(groups = { "android", "ios",
            "inAppTemplates" }, description = "In-App Templates - Confirm, Interstitial, Alert, Banner")
    public void confirmInterstitialAlertBannerTemplates(Method method, String id) {
        try {
            AppiumDriver<MobileElement> driver = initiateTest();

            TestStepHelper stepHelper = new TestStepHelper(this);

            // Track event
            sendEventWithParameters(driver, stepHelper, START_EVENT, PARAM_KEY, PARAM_VALUE);

            // Verify Confirm popup
            ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
            verifyConfirmPopupLayout(driver, stepHelper, confirmInApp);

            // Click cancel
            clickCancel(stepHelper, confirmInApp);

            // Verify interstitial layout
            InterstitialPO interstitial = new InterstitialPO(driver);
            stepHelper.verifyCondition("Verify interstitial layout",
                    interstitial.verifyInterstitialLayout("Reminder", "Ще ти сетнем banner за напомняне!", "Okay.."));

            // Click accept button
            stepHelper.clickElement(interstitial, interstitial.interstitialAcceptButton,
                    "Interstitial's Accept button");

            // Verify alert layout
            AlertPO alert = new AlertPO(driver);
            stepHelper.verifyCondition("Verify alert layout",
                    alert.verifyAlertLayout("Banner will be shown..", ".. as a reminder!", "Okay"));

            // Confrim alert
            stepHelper.clickElement(alert, alert.confirmAlertButton, "Okay");

            // TODO Cannot be located on Android
             BannerPO banner = new BannerPO(driver);
             stepHelper.verifyCondition("Verify banner popup layout",
             banner.verifyBannerLayout("New version of the app available!", "Download when you can!"));
            
             banner.clickOnBanner();

             stepHelper.clickElement(banner, banner.bannerCloseButton, "banner close icon");

             //Send end event
             stepHelper.sendTrackEvent(new NAdHocPO(driver), END_EVENT);

        } catch (Exception e) {
            e.printStackTrace();
            endStep(e.toString(), false);
        }
        endTest();
    }

    private void verifyConfirmPopupLayout(MobileDriver<MobileElement> driver, TestStepHelper stepHelper,
            ConfirmInAppPO confirmInApp) {
        stepHelper.verifyCondition(VERIFY_CONFIRM_POPUP_LAYOUT,
                confirmInApp.verifyConfirmInApp(NEW_UPDATE, THE_NEW_UPDATE_IS_HERE, CONFIRM_ACCEPT, CONFIRM_CANCEL));
    }

    private void clickAccept(TestStepHelper stepHelper, ConfirmInAppPO confirmInApp) {
        stepHelper.clickElement(confirmInApp, confirmInApp.confirmAcceptButton,
                "Confirm popup accept button - " + CONFIRM_ACCEPT);
    }

    private void clickCancel(TestStepHelper stepHelper, ConfirmInAppPO confirmInApp) {
        stepHelper.clickElement(confirmInApp, confirmInApp.confirmCancelButton,
                "Confirm popup cancel button - " + CONFIRM_CANCEL);
    }

    private void verifyRichInterstitialLayout(MobileDriver<MobileElement> driver, TestStepHelper stepHelper,
            RichInterstitialPO richInterstitial) {
        stepHelper.verifyCondition(VERIFY_RICH_INTERSTITIAL,
                richInterstitial.verifyRichInterstitial(RICH_INTERSTITIAL_TITLE, RICH_INTERSTITIAL_MESSAGE,
                        RICH_INTERSTITIAL_LEFT_BUTTON, RICH_INTERSTITIAL_RIGHT_BUTTON, true));
    }
}
