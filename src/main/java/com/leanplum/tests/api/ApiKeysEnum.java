package com.leanplum.tests.api;

import java.util.Arrays;
import java.util.Optional;

public enum ApiKeysEnum {

    RONDO_QA_PRODUCTION(
            "Rondo QA Production",
            "https://www.leanplum.com",
            "app_ve9UCNlqI8dy6Omzfu1rEh6hkWonNHVZJIWtLLt6aLs",
            "prod_D5ECYBLrRrrOYaFZvAFFHTg1JyZ2Llixe5s077Lw3rM",
            "exp_1HmWvROd2413fJ791WHtSLKkhAleXvExbphku8M9zpI"),
    RONDO_QA_AUTOMATION(
            "Rondo QA Automation",
            "https://www.leanplum.com",
            "app_UQcFGVeXzOCVsovrlUebad9R67hFJqzDegfQPZRnVZM",
            "prod_lL8RSFzmHy0iVYXQpzjUVEHDlaUz5idT0H7BVs6Bn1Q",
            "exp_1ZPbuh5w3jialtNpuuqd42jEuLcnw5CTVDCP2pIqX8E");

    private String appName;
    private String url;
    private String testAppKey;
    private String testProdKey;
    private String exportKey;

    ApiKeysEnum(String appName, String url, String testAppKey, String testProdKey, String exportKey) {
        this.appName = appName;
        this.url = url;
        this.testAppKey = testAppKey;
        this.testProdKey = testProdKey;
        this.exportKey = exportKey;
    }
    
    public String getAppName() {
        return appName;
    }

    public String getUrl() {
        return url;
    }

    public String getTestAppKey() {
        return testAppKey;
    }

    public String getTestProdKey() {
        return testProdKey;
    }

    public String getExportKey() {
        return exportKey;
    }

    public static Optional<ApiKeysEnum> valueOfEnum(String appName) {
        return Arrays.stream(values())
                .filter(button -> button.appName.toLowerCase().trim().equals(appName.toLowerCase().trim())).findFirst();
    }
}
