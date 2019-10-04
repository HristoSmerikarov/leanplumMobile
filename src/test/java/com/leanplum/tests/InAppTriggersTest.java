package com.leanplum.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.AdHocPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.tests.pageobject.inapp.BannerPO;
import com.leanplum.tests.pageobject.inapp.ConfirmInAppPO;
import com.leanplum.utils.extentreport.ExtentTestManager;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class InAppTriggersTest extends CommonTestSteps {

	private static final String TRIGGER_EVENT = "limit";
	private static final String TRIGGER_ATTRIBUTE = "triggerAttribute";
	private static final String ATTRIBUTE_VALUE = "attrValue";
	private static final String CONFIRM_ACCEPT = "Yes, it's here!";
	private static final int LIMIT_PER_SESSION = 2;

	// @Test(description = "Alert message triggered on start chained to new
	// Message")
	// public void alertTriggeredOnStartChainedToNewMessage(Method method) {
	// ExtentTestManager.startTest(method.getName(), "Alert message triggered on
	// start chained to new Message");
	//
	// TestStepHelper stepHelper = new TestStepHelper(this);
	// MobileDriver<MobileElement> driver = getDriver();
	//
	// // Verify on app start alert layout
	// AlertPO alert = new AlertPO(driver);
	// stepHelper.verifyCondition("Verify on app start alert layout",
	// alert.verifyAlertLayout("Triggered alert", "Triggered alert with chain", "Yes
	// I see it!"));
	//
	// // Confrim alert
	// stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert
	// button");
	//
	// // Verify dismissed alert layout
	// stepHelper.verifyCondition("Verify dismissed alert layout",
	// alert.verifyAlertLayout("Dismissed action", "Dismissed alert", "Okay.."));
	//
	// // Confrim alert
	// stepHelper.clickElement(alert, alert.confirmAlertButton, "Confirm alert
	// button");
	// }

//    @Test(description = "Banner message triggered on event with limit per session")
//    public void bannerTriggerEventLimitPerSession(Method method) {
//        ExtentTestManager.startTest(method.getName(), "Banner message triggered on event with limit per session");
//
//        TestStepHelper stepHelper = new TestStepHelper(this);
//        MobileDriver<MobileElement> driver = getDriver();
//
//        AlertPO alert = new AlertPO(driver);
//        stepHelper.acceptAllAlertsOnAppStart(alert);
//
//        // Track event
//        AdHocPO adHocPO = new AdHocPO(driver);
//        stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");
//
//        BannerPO banner = new BannerPO(driver);
//        for (int i = 0; i < LIMIT_PER_SESSION; i++) {
//            stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);
//
//            stepHelper.verifyCondition("Verify banner popup layout", banner.verifyBannerLayout("Center bottom banner",
//                    "This banner message is here to remind you something!"));
//
//            stepHelper.clickElement(banner, banner.banner, "banner");
//
//            MobileDriverUtils.waitInMs(100);
//        }
//
//        stepHelper.sendTrackEvent(adHocPO, TRIGGER_EVENT);
//
//        startStep("Verify banner is not shown");
//        endStep(!MobileDriverUtils.doesSelectorMatchAnyElements(driver, BannerPO.POPUP_CONTAINER_XPATH));
//    }

	@Test(description = "Confirm in-app on attribute change every two times")
	public void confirmWithTriggerEveryTwoTimes(Method method) {
		ExtentTestManager.startTest(method.getName(), "Confirm in-app on attribute change every two times");

		TestStepHelper stepHelper = new TestStepHelper(this);
		MobileDriver<MobileElement> driver = getDriver();

		AlertPO alert = new AlertPO(driver);
		stepHelper.acceptAllAlertsOnAppStart(alert);

		// Track event
		AdHocPO adHocPO = new AdHocPO(driver);
		stepHelper.clickElement(adHocPO, adHocPO.adhoc, "Ad-Hoc button");

		// First trigger
		stepHelper.sendUserAttribute(adHocPO, TRIGGER_ATTRIBUTE,
				ATTRIBUTE_VALUE + Utils.generateRandomNumberInRange(0, 100));

		stepHelper.verifyCondition("Verify confirm popup layout is not present",
				!MobileDriverUtils.doesSelectorMatchAnyElements(driver, ConfirmInAppPO.CONFIRM_IN_APP));

		// Second trigger
		stepHelper.sendUserAttribute(adHocPO, TRIGGER_ATTRIBUTE,
				ATTRIBUTE_VALUE + Utils.generateRandomNumberInRange(0, 100));

		// Verify Confirm popup
		ConfirmInAppPO confirmInApp = new ConfirmInAppPO(driver);
		stepHelper.verifyCondition("Verify confirm popup layout", confirmInApp.verifyConfirmInApp("ConfirmTitle",
				"This message appears when attribute changes every 2 times", CONFIRM_ACCEPT, "No!"));

		stepHelper.clickElement(confirmInApp, confirmInApp.confirmAcceptButton,
				"Confirm popup accept button - " + CONFIRM_ACCEPT);
	}
}
