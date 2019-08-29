package com.leanplum.tests.pageobject.inapp;

import java.util.Map;

import com.leanplum.tests.pageobject.BasePO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class InAppPopupPO extends BasePO {

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
        return elementTextMap.entrySet().stream()
                .filter(map -> !map.getKey().getAttribute("class").equalsIgnoreCase(map.getValue())).findFirst()
                .isPresent();
    }
}
