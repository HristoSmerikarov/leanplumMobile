package com.leanplum.base;

import java.io.ByteArrayInputStream;
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

import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import com.leanplum.tests.appiumdriver.PropertiesUtils;
import com.leanplum.tests.appiumdriver.TestConfig;
import com.leanplum.tests.helpers.MobileDriverUtils;
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
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

@Listeners({ TestListener.class })
public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;
    private AppiumDriverLocalService service = null;
    public boolean hasFailedStep = false;
    private static final String TEST_CONFIG_FILE = "resources/test.properties";
    private static TestConfig testConfig;
    private SoftAssert softAssertion;
    private String testIdentifier;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void setupAppiumService() {
        testConfig = (TestConfig) PropertiesUtils.loadProperties(TEST_CONFIG_FILE, TestConfig.class);

        if (testConfig.getOS().toLowerCase().equals("android")) {
            service = AppiumServiceUtils.setupAppiumService();
            service.start();
        } else {
            service = AppiumServiceUtils.setupAppiumService();
            service.start();
        }
    }

    @BeforeClass(dependsOnMethods = "setupAppiumService")
    public void setupDriver() {
        DriverFactory df = new DriverFactory();
        this.driver = df.createDriver(
                DevicePropertiesUtils.getDeviceProperties(testConfig.getOS(), testConfig.getDeviceType()));
    }

    @BeforeMethod()
    public void setUpApp() {
        MobileDriver<MobileElement> driver = getDriver();
        driver.closeApp();
        MobileDriverUtils.waitInMs(500);
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
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
     * @param condition
     */
    public <T> void endStep(boolean condition) {
        endStep("Assertion", condition);
    }

    /**
     * End step with custom description and verifying a condition
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

    @AfterClass(alwaysRun = true)
    public void stopAppiumService() {
        if (testConfig.getOS().toLowerCase().equals("android")) {
            service.stop();
        } else {
            service.stop();
        }
    }
}