package com.leanplum.tests.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TemporaryAPI {

    public static final Logger LOGGER = LoggerFactory.getLogger(TemporaryAPI.class);
    private static String testAppKey = "app_ve9UCNlqI8dy6Omzfu1rEh6hkWonNHVZJIWtLLt6aLs";
    private static String apiVersion = "1.0.6";
    private static String testProdKey = "prod_D5ECYBLrRrrOYaFZvAFFHTg1JyZ2Llixe5s077Lw3rM";
    private static String trackPath = "api?action=track&appId=%s&apiVersion=%s&clientKey=%s";
    private static String parameters = "&userId=%s&event=%s";

    static {
        RestAssured.baseURI = "https://www.leanplum.com";
        System.out.println("URI: " + RestAssured.baseURI);
        // RestAssured.basePath = String.format(trackPath, testAppKey, apiVersion, testProdKey);
        // System.out.println("PATH: " + RestAssured.basePath);
    }

    public static Response post(String userId, String trackEvent) {
        String formattedEndpoint = String.format(trackPath, testAppKey, apiVersion, testProdKey)
                + String.format(parameters, userId, trackEvent);
        System.out.println("FORMATTED: " + formattedEndpoint);

        Response response = RestAssured.given().contentType(ContentType.JSON).log().all().post(formattedEndpoint);
        LOGGER.info("Post Response:" + response.getBody().asString());
        return response;
    }

}
