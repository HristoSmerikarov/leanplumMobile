package com.leanplum.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import com.leanplum.tests.appiumdriver.GridManager;
import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.enums.SdkEnum;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.testdevices.DeviceManager;
import com.leanplum.tests.testdevices.TestDevice;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

@Listeners({ TestListener.class })
public class BaseTest {

    private ThreadLocal<AppiumDriver<MobileElement>> driver = new ThreadLocal<>();
    public boolean hasFailedStep = false;
    private AppiumServiceUtils appiumServiceUtils = new AppiumServiceUtils();
    private Map<AppiumDriver<MobileElement>, TestDevice> driverToDeviceMap = new HashMap<>();
    private List<AppiumDriver<MobileElement>> appiumDrivers = new ArrayList<>();
    private SoftAssert softAssert;
    private boolean useGrid = Boolean.valueOf(System.getProperty("useGrid"));
    protected SdkEnum sdk = SdkEnum.valueOfEnum(System.getProperty("sdk")).get();
    private String serviceIpAddress;
    private OSEnum os;
    private PlatformEnum platform;
    private static String startTestTimestamp;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void init() throws Exception {
        // Get OS and
        this.os = Utils.determineOS();

        // Get connected devices
        DeviceManager deviceManager = new DeviceManager();
        deviceManager.determineConnectedDevices();

        // Create and start Appium services for each test device with individual IP and port
        serviceIpAddress = "127.0.0.1";
        if (!useGrid) {
            for (int i = 0; i < DeviceManager.connectedTestDevices.size(); i++) {
                appiumServiceUtils.setupAppiumService(platform, serviceIpAddress, AppiumServiceUtils.findFreePort());

                System.out.println("APPIUM SERVICE URL: " + AppiumServiceUtils.appiumServices.get(i).getUrl());

                AppiumServiceUtils.appiumServices.get(i).start();
            }
        } else {
            serviceIpAddress = "http://" + serviceIpAddress + ":%s/wd/hub";
            for (int i = 0; i < DeviceManager.connectedTestDevices.size(); i++) {
                GridManager.addUrlForGrid(new URL(String.format(serviceIpAddress, AppiumServiceUtils.findFreePort())));
                System.out.println("GRID URL ADDED: " + GridManager.getGridURL(i));
            }
        }

        System.out.println("DEVICE LIST SIZE: " + DeviceManager.connectedTestDevices.size());
    }

    @SuppressWarnings("rawtypes")
    public AppiumDriver<MobileElement> initTest() throws MalformedURLException {
        String currentThread = Thread.currentThread().getName();
        System.out.println("CURRENT THREAD: " + currentThread);

        int threadIndex;
        if (currentThread.equals("main")) {
            threadIndex = 0;
        } else {
            threadIndex = Integer.parseInt(currentThread.substring(13, 14)) - 1;
        }

        System.out.println("CONNECTED DEVICES: " + DeviceManager.connectedTestDevices.size());

        System.out.println("DEVICE INDEX: " + threadIndex);
        TestDevice currentTestDevice = DeviceManager.connectedTestDevices.get(threadIndex);

        AppiumDriver<MobileElement> driver;
        if (!useGrid) {
            System.out.println("INITIALIZING FOR TEST DEVICE: " + currentTestDevice.getId());
            System.out.println("INITIALIZING FOR SERVICE: " + AppiumServiceUtils.appiumService.toString());
            driver = createDriver(currentTestDevice, AppiumServiceUtils.appiumService.getUrl());
        } else {
            System.out.println("INITIALIZING FOR TEST DEVICE GRID: " + currentTestDevice.getId());
            GridManager.getGridURLs().forEach(url -> {
                System.out.println("PORT: " + url.getPort());
            });
            driver = createDriver(currentTestDevice, GridManager.getGridURL(threadIndex));
        }

        driverToDeviceMap.put(driver, currentTestDevice);
        appiumDrivers.add(driver);

        this.driver.set(driver);

        if (driver instanceof AndroidDriver) {
            // ((AndroidDriver) driver).isDeviceLocked();

            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.POWER));

            // ((AndroidDriver) driver).lockDevice();
            MobileDriverUtils.waitInMs(1000);

            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.POWER));
            // ((AndroidDriver) driver).unlockDevice();
        }

        driver.closeApp();
        MobileDriverUtils.waitInMs(1000);
        driver.launchApp();

        System.out.println("INITIALIZED FOR TEST DEVICE: " + currentTestDevice.getId());

        return driver;
    }

    private AppiumDriver<MobileElement> createDriver(TestDevice device, URL appiumServiceIp) {
        DriverFactory df = new DriverFactory();
        AppiumDriver<MobileElement> driver = df.initializeDriver(device, DevicePropertiesUtils
                .getDeviceProperties(device.getPlatform().getPlatformName().toLowerCase(), "phone"), appiumServiceIp);
        System.out.println("INIT DRIVER: " + driver.toString());
        return driver;
    }

    public SdkEnum getSdk() {
        return this.sdk;
    }

    @BeforeSuite()
    public void createFolder() {
        startTestTimestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms").format(new Timestamp(new Date().getTime()));
        System.out.println("StartTestTimestamp: " + startTestTimestamp);
    }

    public void startTest() {
        startTest(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms").format(new Timestamp(new Date().getTime())),
                driverToDeviceMap.get(getDriver()).getName());
        softAssert = new SoftAssert();
    }

    @Step("Started on: {currentTime} Device/Browser : {deviceName}")
    private void startTest(String currentTime, String deviceName) {
    }

    public <T> void startStep(String stepDescription) {
        Allure.step(stepDescription);
    }

    /**
     * End step with status Passed
     */
    public <T> void endStep() {
        endStep("End step", true);
    }

    public <T> void endStep(boolean condition) {
        endStep("End step assertion", condition);
    }

    /**
     * End step verifying an assertion
     * @param condition
     */
    public <T> void endStep(String description, boolean condition) {
        softAssert.assertTrue(condition);

        screenshot("Screenshot");

        if (condition) {
            Allure.step(description, Status.PASSED);
        } else {
            Allure.step(description, Status.FAILED);
        }
    }

    /**
     * End step with custom description and verifying a condition
     * 
     * @param stepDescription
     */
    @Step
    public <T> void screenshot(String stepDescription) {
        Allure.addAttachment(stepDescription, new ByteArrayInputStream(getDriver().getScreenshotAs(OutputType.BYTES)));
    }

    /**
     * Asserts all soft asserts made throughout the test
     */
    @Step
    public void endTest() {
        softAssert.assertAll();
    }

    public AppiumDriver<MobileElement> getDriver() {
        return this.driver.get();
    }

    @AfterMethod
    public void quitDriver() {
        driver.get().quit();
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumService() {
        AppiumServiceUtils.appiumServices.forEach(service -> service.stop());

        AppiumServiceUtils.appiumServices.forEach(service -> {
            service.stop();
        });

        AppiumServiceUtils.killNodeServer(os);
    }

    @AfterSuite(alwaysRun = true)
    public void allureServe() {
        String reportFolder = "target/allure-results";
        File sourceFile = new File(reportFolder);
        File destFile = new File(reportFolder + "_" + startTestTimestamp);

        if (sourceFile.renameTo(destFile)) {
            logger.info("File renamed successfully to " + destFile.getAbsolutePath());
            System.out.println("File renamed successfully to " + destFile.getAbsolutePath());
            System.out.println("For local report use command: allure serve allure-results_" + startTestTimestamp);
        } else {
            logger.info("Failed to rename file");
        }
    }
}