package com.leanplum.tests.pageobject.inapp;

import java.util.Map;
import java.util.Map.Entry;

import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class InAppPopupPO extends BasePO {

    public static final String POPUP_CONTAINER_XPATH = "//*[contains(@resource-id,'com.leanplum.rondo:id/container_view']";
    AppiumDriver<MobileElement> driver;

    public InAppPopupPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Confirms that all texts displayed in app are correct
     * @param elementTextMap
     * @return 
     */
    protected boolean verifyInAppPopup(Map<MobileElement, String> elementTextMap) {
        for (Entry<MobileElement, String> entry : elementTextMap.entrySet()) {
            String actual = entry.getKey().getAttribute("text");
            String expected = entry.getValue();
            if (!actual.equalsIgnoreCase(expected)) {
                return false;
            }
        }
        return true;
    }
}
