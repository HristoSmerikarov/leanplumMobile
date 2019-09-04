package com.leanplum.base;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.leanplum.tests.appiumdriver.AppiumServiceUtils;
import com.leanplum.tests.appiumdriver.DevicePropertiesUtils;
import com.leanplum.tests.appiumdriver.DriverFactory;
import com.leanplum.utils.extentreport.ExtentTestManager;
import com.leanplum.utils.listeners.AnnotationTransformer;
import com.leanplum.utils.listeners.TestListener;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import ru.yandex.qatools.allure.annotations.Step;

@Listeners({ TestListener.class, AnnotationTransformer.class })
public class BaseTest {

    private AppiumDriver<MobileElement> driver = null;
    private AppiumDriverLocalService service = null;
    public boolean hasFailedStep = false;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUpApp() {
        AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) getAppiumDriver();
        driver.closeApp();
        driver.launchApp();
    }
    
    @BeforeClass
    public void setupAppiumService() {
        if (System.getProperty("os").toLowerCase().equals("android")) {
            service = AppiumServiceUtils.setupAppiumService();
            service.start();
        }
    }

    @BeforeClass(dependsOnMethods = "setupAppiumService")
    public void setupTest() {
        DriverFactory df = new DriverFactory();
        this.driver = df.createDriver(
                DevicePropertiesUtils.getDeviceProperties(System.getProperty("os"), System.getProperty("deviceType")));
    }

    @Step()
    public <T> void step(String stepDescription) {
        ExtentTestManager.getTest().log(LogStatus.PASS, stepDescription, takeScreenshot());
    }
    
    @Step()
    public <T> void startStep(String stepDescription) {
        log(LogStatus.INFO, stepDescription);
    }
    
    @Step()
    public <T> void endStep() {
        log(LogStatus.INFO, takeScreenshot());
    }
    
    @Step()
    public <T> void endStep(boolean condition) {
        if (condition) {
            log(LogStatus.PASS, takeScreenshot());
        } else {
            log(LogStatus.FAIL, takeScreenshot());
            
            hasFailedStep = true;
        }
    }

    @Step()
    public void step(String stepDescription, boolean condition) {
        if (condition) {
            ExtentTestManager.getTest().log(LogStatus.PASS, stepDescription, takeScreenshot());
        } else {
            ExtentTestManager.getTest().log(LogStatus.FAIL, stepDescription, takeScreenshot());
            hasFailedStep = true;
        }
        
        
    }

    public AppiumDriver<MobileElement> getAppiumDriver() {
        return this.driver;
    }

//    @AfterTest
//    public boolean hasFailedSteps() {
//        return hasFailedStep;
//    }

    @AfterClass
    public void stopAppiumService() {
        service.stop();
    }

    protected String takeScreenshot() {
        return ExtentTestManager.getTest().addBase64ScreenShot(
                "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64));
    }
    
    /**
     * 
     * @param logStatus - @LogStatus
     * @param stepInfo - screenshot string or step description
     */
    private void log(LogStatus logStatus, String stepInfo) {
        ExtentTestManager.getTest().log(logStatus, stepInfo);
    }
}