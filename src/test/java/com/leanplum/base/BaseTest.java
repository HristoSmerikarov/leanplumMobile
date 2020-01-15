package com.leanplum.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.allurefw.allure1.AllureUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.leanplum.tests.appiumdriver.AppiumServiceConfig;
import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import com.leanplum.tests.appiumdriver.PropertiesUtils;
import com.leanplum.tests.appiumdriver.TestConfig;
import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.helpers.MobileDriverUtils;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.testdevices.DeviceManager;
import com.leanplum.tests.testdevices.TestDevice;
import com.leanplum.utils.listeners.TestListener;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureConstants;
import io.qameta.allure.AllureId;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.testng.AllureTestNg;
import io.qameta.allure.util.ResultsUtils;

@Listeners({ TestListener.class })
public class BaseTest {

    protected ThreadLocal<AppiumDriver<MobileElement>> driver = new ThreadLocal<>();
    public boolean hasFailedStep = false;
    private static final String TEST_CONFIG_FILE = "resources/test.properties";
    private DeviceManager deviceManager;
    private AppiumServiceUtils appiumServiceUtils = new AppiumServiceUtils();
    // private List<TestDevice> connectedTestDevices = new ArrayList<>();
    protected Map<AppiumDriver<MobileElement>, TestDevice> driverToDeviceMap = new HashMap<>();
    // private Map<TestDevice, AppiumDriverLocalService> appiumServiceForDevice = new HashMap<>();
    private List<AppiumDriver<MobileElement>> appiumDrivers = new ArrayList<>();
    private static TestConfig testConfig;
    private String testIdentifier;
    private SoftAssert softAssert;
    private String reportFolder = "target/allure-results";
    private OSEnum os;
    private PlatformEnum platform;
    private static String startTestTimestamp;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    // private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeSuite
    public void init() {
        // Get OS and
        this.os = Utils.determineOS();

        // Get connected devices
        deviceManager = new DeviceManager();
        deviceManager.determineConnectedDevices();

        // Create and start Appium services for each test device with individual IP and port

        String initialIPAddress = "127.0.0.10";
        for (int i = 0; i < DeviceManager.connectedTestDevices.size(); i++) {
            appiumServiceUtils.setupAppiumService(platform, initialIPAddress, AppiumServiceUtils.findFreePort());

            System.out.println("APPIUM SERVICE URL: " + AppiumServiceUtils.appiumServices.get(i).getUrl());

            AppiumServiceUtils.appiumServices.get(i).start();
            // appiumServiceForDevice.put(DeviceManager.connectedTestDevices.get(i),
            // AppiumServiceUtils.appiumServices.get(i));
            // appiumServiceUsageMap.put(AppiumServiceUtils.appiumServices.get(i), false);
        }

        System.out.println("DEVICE LIST SIZE: " + DeviceManager.connectedTestDevices.size());
    }

    // public AppiumDriverLocalService findNextUnusedAppiumService() {
    // for (Entry<AppiumDriverLocalService, Boolean> appiumService : appiumServiceUsageMap.entrySet()) {
    // if (!appiumService.getValue()) {
    // System.out.println("FOUND ONE!!");
    // appiumServiceUsageMap.replace(appiumService.getKey(), true);
    // return appiumService.getKey();
    // }
    // }
    // return null;
    // }

    public AppiumDriver<MobileElement> initTest() {
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

        System.out.println("INITIALIZING FOR TEST DEVICE: " + currentTestDevice.getId());
        System.out.println("INITIALIZING FOR SERVICE: " + AppiumServiceUtils.appiumService.toString());

        AppiumDriver<MobileElement> driver = createDriver(currentTestDevice, AppiumServiceUtils.appiumService);

        driverToDeviceMap.put(driver, currentTestDevice);
        appiumDrivers.add(driver);

        this.driver.set(driver);

        driver.closeApp();
        MobileDriverUtils.waitInMs(500);
        if (driver instanceof AndroidDriver) {
            ((PressesKey) getCurrentDriver()).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }
        MobileDriverUtils.waitInMs(500);
        driver.launchApp();

        System.out.println("INITIALIZED FOR TEST DEVICE: " + currentTestDevice.getId());

        return driver;
    }

    public AppiumDriver<MobileElement> getCurrentDriver() {
        return driver.get();
    }

    // public AppiumDriver<?> initTest(AppiumDriverLocalService appiumService) {
    //
    // System.out.println("APPIUM SERVICE: "+appiumService);
    // System.out.println("MAP: " + appiumServiceForDevice);
    //
    // TestDevice testDevice = appiumServiceForDevice.get(appiumService);
    //
    // System.out.println("INITIALIZING FOR TEST DEVICE: " + testDevice.getId());
    //
    // AppiumDriver<?> driver = createDriver(testDevice, appiumService);
    //
    // appiumDrivers.add(driver);
    //
    // driver.closeApp();
    // MobileDriverUtils.waitInMs(500);
    // if (driver instanceof AndroidDriver) {
    // ((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    // }
    // MobileDriverUtils.waitInMs(500);
    // driver.launchApp();
    //
    // System.out.println("INITIALIZED FOR TEST DEVICE: " + testDevice.getId());
    //
    // System.out.println("APPIUM DRIVERS: " + appiumDrivers);
    //
    // return driver;
    // }

    public AppiumDriver<MobileElement> createDriver(TestDevice device, AppiumDriverLocalService appiumService) {
        DriverFactory df = new DriverFactory();
        AppiumDriver<MobileElement> driver = df.createDriver(device, DevicePropertiesUtils.getDeviceProperties(
                device.getPlatform().getPlatformName().toLowerCase(), "phone"), appiumService.getUrl());
        System.out.println("INIT DRIVER: " + driver.toString());
        return driver;
    }

    // @BeforeClass(dependsOnMethods = "determineConnectedTestDevices")
    // public void setupAppiumService() {
    // testConfig = (TestConfig) PropertiesUtils.loadProperties(TEST_CONFIG_FILE, TestConfig.class);
    // File jsonFile = new File("resources/" + platform.getPlatformName().toLowerCase() + "Node.json");
    //
    // // System.out.println(jsonFile.getAbsolutePath());
    // // Utils.runCommandInTerminal(OSEnum.WINDOWS,
    // // "appium -a 127.0.0.1 -p 4723 --nodeconfig " + jsonFile.getAbsolutePath());
    //
    // currentPort = findFreePort();
    //
    // for (int i = 0; i < connectedTestDevices.size(); i++) {
    // System.out.println("CURRENT PORT " + currentPort);
    // this.service = AppiumServiceUtils.setupAppiumService(platform, "", currentPort);
    // }
    //
    // service.start();
    // }
    //
    // @BeforeClass(dependsOnMethods = "init")
    // public void setupDriver() {
    // DriverFactory df = new DriverFactory();
    // // TODO device name!!!!!
    // connectedTestDevices.forEach(device -> {
    // System.out.println("Platform is: " + device.getPlatform().getPlatformName().toLowerCase());
    // System.err.println("Current port is: " + currentPort);
    // this.driver = df.createDriver(device, DevicePropertiesUtils
    // .getDeviceProperties(device.getPlatform().getPlatformName().toLowerCase(), "phone"), currentPort);
    // });
    // }

    @BeforeSuite()
    public void createFolder() {
        startTestTimestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms").format(new Timestamp(new Date().getTime()));
        System.out.println("StartTestTimestamp: " + startTestTimestamp);
    }

    // @Override
    // public String getTestName() {
    // return testName.get();
    // }
    //
    // @BeforeMethod(alwaysRun = true)
    // public void setTestName(Method method) {
    // String testCaseName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms").format(new Timestamp(new Date().getTime()));
    // System.out.println(method.getName() + testCaseName);
    // testName.set(method.getName() + "_" + testCaseName);
    // }

    public void startTest() {
        startTest(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms").format(new Timestamp(new Date().getTime())),
                driverToDeviceMap.get(getCurrentDriver()).getName());
        softAssert = new SoftAssert();
    }

    @Step("Started on: {currentTime} Device/Browser : {deviceName}")
    public void startTest(String currentTime, String deviceName) {
        testIdentifier = UUID.randomUUID().toString();
        // hasFailedStep = false;
        // softAssertion = new SoftAssert();
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

    /**
     * End step verifying an assertion
     * @param condition
     */
    public <T> void endStep(boolean condition) {
        endStep("Assertion", condition);
    }

    /**
     * End step with custom description and verifying a condition
     * 
     * @param stepDescription
     * @param condition
     */
    @Step
    public <T> void endStep(String stepDescription, boolean condition) {
        Allure.addAttachment(stepDescription,
                new ByteArrayInputStream(getCurrentDriver().getScreenshotAs(OutputType.BYTES)));

        softAssert.assertTrue(condition);

        System.out.println("condition: " + condition);

        if (condition) {
            Allure.step(stepDescription, Status.PASSED);
        } else {
            Allure.step(stepDescription, Status.FAILED);
        }
    }

    /**
     * End step with custom description and status
     * 
     * @param stepDescription
     * @param status
     */
    protected <T> void endStep(String stepDescription, Status status) {
        Allure.getLifecycle().startStep(testIdentifier, new StepResult().setStatus(status));
        Allure.getLifecycle().stopStep(testIdentifier);
    }

    /**
     * Asserts all soft asserts made throughout the test
     */
    @Step
    public void endTest() {
        softAssert.assertAll();
        testIdentifier = "";
    }

    public MobileDriver<?> getDriver() {
        return this.driver.get();
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    public OSEnum getOs() {
        return os;
    }


    @AfterSuite(alwaysRun = true)
    public void stopAppiumService() {
        // getCurrentDriver().closeApp();
        AppiumServiceUtils.appiumServices.forEach(service -> {
            service.stop();
        });
    }

    @AfterSuite(alwaysRun = true)
    public void allureServe() {
        File sourceFile = new File(reportFolder);
        File destFile = new File(reportFolder + "_" + startTestTimestamp);

        if (sourceFile.renameTo(destFile)) {
            logger.info("File renamed successfully to " + destFile.getAbsolutePath());
            System.out.println("For local report use command: allure serve allure-results_" + startTestTimestamp);
        } else {
            logger.info("Failed to rename file");
        }
    }
}