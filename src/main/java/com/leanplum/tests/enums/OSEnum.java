package com.leanplum.tests.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OSEnum {

    WINDOWS("Windows"), MAC("Mac");

    private String osName;

    OSEnum(String osName) {
        this.osName = osName;
    }

    public String getOsName() {
        return osName;
    }

    public static Optional<OSEnum> valueOfEnum(String name) {
        return Arrays.stream(values())
                .filter(optionEnum -> name.toLowerCase().contains(optionEnum.osName.toLowerCase())).findFirst();
    }
}
