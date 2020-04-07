package com.leanplum.tests.enums;

import java.util.Arrays;
import java.util.Optional;

public enum SdkEnum {

    REACT_NATIVE("ReactNative"), NATIVE("Native");
    
    private String sdk;
    
    SdkEnum(String sdk) {
        this.sdk = sdk;
    }
    
    public String getSdk(){
        return sdk;
    }
    
    public static Optional<SdkEnum> valueOfEnum(String sdk) {
        return Arrays.stream(values())
                .filter(optionEnum -> optionEnum.sdk.toLowerCase().equals(sdk.toLowerCase())).findFirst();
    }
}
