package com.leanplum.base;

import java.io.IOException;
import java.util.function.Function;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.model.Media;
import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import com.leanplum.utils.extentreport.ExtentManager;
import com.leanplum.utils.extentreport.ExtentTestManager;
import com.leanplum.utils.listeners.AnnotationTransformer;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import ru.yandex.qatools.allure.annotations.Step;

@Listeners({ TestListener.class, AnnotationTransformer.class })
public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;
    private AppiumDriverLocalService service = null;
    private ExtentTest test;
    private boolean hasFailedSteps = true;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setupAppiumService() {
        if (System.getProperty("os").toLowerCase().equals("android")) {
            service = AppiumServiceUtils.setupAppiumService();
            service.start();
        }
    }

    @BeforeMethod(dependsOnMethods = "setupAppiumService")
    public void setupTest() {
        DriverFactory df = new DriverFactory();
        this.driver = df.createDriver(
                DevicePropertiesUtils.getDeviceProperties(System.getProperty("os"), System.getProperty("deviceType")));
    }

    @Step()
    public <T> void step(ExtentTest test, String stepDescription) {
        test.pass(stepDescription, getMedia());
    }

    @Step()
    public void step(ExtentTest test, String stepDescription, boolean condition) {
        if (condition) {
            test.pass(stepDescription, getMedia());
        } else {
            test.fail(stepDescription, getMedia());
            hasFailedSteps = true;
        }
    }

    public AppiumDriver<MobileElement> getAppiumDriver() {
        return this.driver;
    }

    @AfterMethod()
    public void hasFailedSteps() {
        Assert.assertFalse(hasFailedSteps);
    }

    @AfterTest(alwaysRun = true)
    public void stopAppiumService() {
        if (driver != null) {
            driver.quit();
        }

        if (System.getProperty("os").toLowerCase().equals("android")) {
            service.stop();
        }
    }

    private MediaEntityModelProvider getMedia() {
        MediaEntityModelProvider m = new MediaEntityModelProvider(new Media());
        try {
            m = MediaEntityBuilder
                    .createScreenCaptureFromBase64String(
                            "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}