package com.leanplum.tests.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TemporaryAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryAPI.class);
    private static ApiKeysEnum apiKeys =  ApiKeysEnum.valueOfEnum(System.getProperty("application")).get();
    private static String testAppKey = apiKeys.getTestAppKey();
    private static String apiVersion = "1.0.6";
    private static String testProdKey = apiKeys.getTestProdKey();
    private static String exportKey = apiKeys.getExportKey();
    private static String path = "api?action=%s&appId=%s&apiVersion=%s&clientKey=%s";
    private static String trackAction = "track";
    private static String start = "start";
    private static String exportUserAction = "exportUser";
    private static String getNewsfeedMessages = "getNewsfeedMessages";
    private static String deleteNewsfeedMessage = "deleteNewsfeedMessage";
    private static String pauseSession = "pauseSession";
    private static String deleteUser = "deleteUser";
    private static String sendMessage = "sendMessage";
    private static String userIdParameter = "&userId=%s";
    private static String eventParameter = "&event=%s";
    private static String deviceIdParameter = "&deviceId=%s";
    private static String newsfeedIdParameter = "&newsfeedMessageId=%s";

    static {
        RestAssured.baseURI = apiKeys.getUrl();
        System.out.println("URI: " + RestAssured.baseURI);
        // RestAssured.basePath = String.format(trackPath, testAppKey, apiVersion, testProdKey);
        // System.out.println("PATH: " + RestAssured.basePath);
    }

    public static Response track(String userId, String trackEvent) {
        String formattedEndpoint = String.format(path, trackAction, testAppKey, apiVersion, testProdKey)
                + String.format(userIdParameter, userId) + String.format(eventParameter, trackEvent);
        return post(formattedEndpoint);
    }

    public static Response getNewsfeedMessages(String deviceId) {
        String formattedEndpoint = String.format(path, getNewsfeedMessages, testAppKey, apiVersion, testProdKey)
                + String.format(deviceIdParameter, deviceId);
        return get(formattedEndpoint);
    }

    public static Response deleteNewsfeedMessage(String deviceId, String userId, String newsfeedId) {
        String formattedEndpoint = String.format(path, deleteNewsfeedMessage, testAppKey, apiVersion, testProdKey)
                + String.format(deviceIdParameter, deviceId) + String.format(userIdParameter, userId)
                + String.format(newsfeedIdParameter, newsfeedId);
        return post(formattedEndpoint);
    }

    public static Response pauseSession(String userId) {
        String formattedEndpoint = String.format(path, pauseSession, testAppKey, apiVersion, testProdKey)
                + String.format(userIdParameter, userId);
        return post(formattedEndpoint);
    }

    public static Response deleteUser(String userId) {
        String formattedEndpoint = String.format(path, deleteUser, testAppKey, apiVersion, testProdKey)
                + String.format(userIdParameter, userId);
        return post(formattedEndpoint);
    }

    public static Response sendMessage(String userId, String deviceId) {
        String formattedEndpoint = String.format(path, sendMessage, testAppKey, apiVersion, testProdKey)
                + String.format(userIdParameter, userId) + String.format(deviceIdParameter, deviceId);
        return post(formattedEndpoint);
    }
    
    public static Response getUser(String userId) {
        String formattedEndpoint = String.format(path, exportUserAction, testAppKey, apiVersion, exportKey)
                + String.format(userIdParameter, userId);
        return get(formattedEndpoint);
    }
    
    public static Response start(String userId) {
        String formattedEndpoint = String.format(path, start, testAppKey, apiVersion, exportKey)
                + String.format(userIdParameter, userId);
        return post(formattedEndpoint);
    }
    
    private static Response post(String endpoint) {
        System.out.println("POST TO ENDPOINT: " + endpoint);
        Response response = RestAssured.given().contentType(ContentType.JSON).log().all().post(endpoint);
        System.out.println("Post Response:" + response.getBody().asString());
        LOGGER.info("Post Response:" + response.getBody().asString());
        
        return response;
    }
    
    private static Response get(String endpoint) {
        System.out.println("GET FROM ENDPOINT: " + endpoint);
        Response response = RestAssured.given().contentType(ContentType.JSON).log().all().get(endpoint);
        System.out.println("Get Response:" + response.getBody().asString());
        LOGGER.info("Get Response:" + response.getBody().asString());
        
        return response;
    }

}
