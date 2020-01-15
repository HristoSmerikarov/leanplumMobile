package com.leanplum.tests.pageobject.inapp;

import java.util.Map;
import java.util.Map.Entry;

import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class InAppPopupPO extends BasePO {

	public static final String POPUP_CONTAINER_XPATH = "//*[@resource-id='com.leanplum.rondo:id/container_view']";
	private AppiumDriver<MobileElement> driver;

	public InAppPopupPO(AppiumDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
	}

	/**
	 * Confirms that all texts displayed in app are correct
	 * 
	 * @param elementTextMap
	 * @return
	 */
	protected boolean verifyInAppPopup(Map<MobileElement, String> elementTextMap) {
		for (Entry<MobileElement, String> entry : elementTextMap.entrySet()) {
			String actual;
			if (driver instanceof AndroidDriver) {
				actual = entry.getKey().getAttribute("text");
			} else {
				actual = entry.getKey().getAttribute("label");
			}
			String expected = entry.getValue();
			System.out.println("Actual: " + actual);
			System.out.println("Expected: " + expected);

			if (!actual.equalsIgnoreCase(expected)) {
				return false;
			}
		}
		return true;
	}
}
