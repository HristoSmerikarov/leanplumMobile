package com.leanplum.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
import com.leanplum.tests.testdevices.TestDevice;
import com.leanplum.utils.listeners.TestListener;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;

@Listeners({ TestListener.class })
public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;
    private AppiumDriverLocalService service = null;
    public boolean hasFailedStep = false;
    private static final String TEST_CONFIG_FILE = "resources/test.properties";
    List<TestDevice> connectedTestDevices;
    private static TestConfig testConfig;
    private SoftAssert softAssertion;
    private String testIdentifier;
    private int currentPort;
    private String reportFolder = "target/allure-results";
    private OSEnum os;
    private PlatformEnum platform;
    private String startTestTimestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ms")
            .format(new Timestamp(new Date().getTime()));
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void determineConnectedTestDevices() {
        this.os = determineOS();
        this.platform = determinePlatform();
        connectedTestDevices = determineTestDevice();
    }

    @BeforeClass(dependsOnMethods = "determineConnectedTestDevices")
    public void setupAppiumService() {
        testConfig = (TestConfig) PropertiesUtils.loadProperties(TEST_CONFIG_FILE, TestConfig.class);

        File jsonFile = new File("resources/" + platform.getPlatformName().toLowerCase() + "Node.json");

        // System.out.println(jsonFile.getAbsolutePath());
        // Utils.runCommandInTerminal(OSEnum.WINDOWS,
        // "appium -a 127.0.0.1 -p 4723 --nodeconfig " + jsonFile.getAbsolutePath());

        currentPort = findFreePort();

        for (int i = 0; i < connectedTestDevices.size(); i++) {
            System.out.println("CURRENT PORT "+currentPort);
            this.service = AppiumServiceUtils.setupAppiumService(platform, currentPort);
        }

        service.start();
    }

    @BeforeClass(dependsOnMethods = "setupAppiumService")
    public void setupDriver() {
        DriverFactory df = new DriverFactory();
        // TODO device name!!!!!
        connectedTestDevices.forEach(device -> {
            System.out.println("Platform is: " + device.getPlatform().getPlatformName().toLowerCase());
            System.err.println("Current port is: "+currentPort);
            this.driver = df.createDriver(device, DevicePropertiesUtils
                    .getDeviceProperties(device.getPlatform().getPlatformName().toLowerCase(), "phone"), currentPort);
        });
    }

    @BeforeMethod()
    public void setUpApp() {
        MobileDriver<MobileElement> driver = getDriver();
        driver.closeApp();
        MobileDriverUtils.waitInMs(500);
        if (driver instanceof AndroidDriver) {
            // ((AndroidDriver<MobileElement>) driver).pressKey(new
            // KeyEvent().withKey(AndroidKey.BACK));
        }
        MobileDriverUtils.waitInMs(500);
        driver.launchApp();
    }

    @BeforeMethod(dependsOnMethods = "setUpApp")
    public void startTest() {
        testIdentifier = UUID.randomUUID().toString();
        hasFailedStep = false;
        softAssertion = new SoftAssert();

    }

    public <T> void startStep(String stepDescription) {
        Allure.step(stepDescription);
    }

    /**
     * End step with status Passed
     */
    public <T> void endStep() {
        endStep("End step", Status.PASSED);
    }

    /**
     * End step verifying an assertion
     * 
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
    public <T> void endStep(String stepDescription, boolean condition) {
        softAssertion.assertTrue(condition);

        if (condition) {
            endStep(stepDescription, Status.PASSED);
        } else {
            endStep(stepDescription, Status.FAILED);
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
        Allure.addAttachment(stepDescription,
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        Allure.getLifecycle().stopStep(testIdentifier);
    }

    /**
     * Asserts all soft asserts made throughout the test
     */
    @Step
    public void endTest() {
        softAssertion.assertAll();
        getDriver().closeApp();
        testIdentifier = "";
        softAssertion = null;
    }

    public MobileDriver<MobileElement> getDriver() {
        return this.driver;
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    private boolean isPortFree(String port) {
        switch (os) {
        case WINDOWS:
            return Utils.runCommandInTerminal(os, String.format("netstat -ano | findStr %s", port)).isEmpty();
        case MAC:
            // TODO
            return false;
        }
        return true;
    }

    private int findFreePort() {
        String port = Utils.generateRandomNumberInRange(4700, 5000);
        while (!isPortFree(port)) {
            port = Utils.generateRandomNumberInRange(4700, 5000);
        }

        System.out.println("Free port: "+port);
        
        return Integer.valueOf(port);
    }

    private List<TestDevice> determineTestDevice() {
        List<TestDevice> testDevices = new ArrayList<>();
        testDevices.addAll(Utils.getConnectedAndroidDevice(os));
        testDevices.addAll(Utils.getConnectedIOSDevice(os));
        return testDevices;
    }

    private OSEnum determineOS() {
        return OSEnum.valueOfEnum(System.getProperty("os.name")).get();
    }

    private PlatformEnum determinePlatform() {
        if (this.os == OSEnum.WINDOWS) {
            return PlatformEnum.ANDROID_APP;
        } else {
            return PlatformEnum.IOS_APP;
        }
    }

    @AfterClass(alwaysRun = true)
    public void stopAppiumService() {
        service.stop();
    }

    @AfterClass(dependsOnMethods = "stopAppiumService", alwaysRun = true)
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