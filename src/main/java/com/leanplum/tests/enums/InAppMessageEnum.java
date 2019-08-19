package com.leanplum.tests.enums;

public enum InAppMessageEnum {

    ALERT("alert"),
    BANNER("banner"),
    CENTER_POPUP("centerPopup"),
    CONFIRM("confirm"),
    INTERSTITIAL("interstitial"),
    RICH_INTERSTITIAL("richInterstitial"),
    WEB_INTERSTITIAL("webInterstitial");

    private String inAppMessage;
    
    InAppMessageEnum(String inAppMessage) {
        this.inAppMessage = inAppMessage;
    }
    
    public String getInAppMessage() {
        return this.inAppMessage;
    }
}
