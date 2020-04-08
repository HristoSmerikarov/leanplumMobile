package com.leanplum.tests;

import org.testng.annotations.Listeners;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.utils.listeners.TestListener;

@Listeners({ TestListener.class })
public class CreateAppTest extends CommonTestSteps {

    // @Test(description = "Create app")
    // public void createApp(Method method) {
    // ExtentTestManager.startTest(method.getName(), "Push Notification's open action is New Action");
    //
    // TestStepHelper stepHelper = new TestStepHelper(this);
    // MobileDriver<MobileElement> driver = getDriver();
    //
    // // Track event
    // AlertPO alertPO = new AlertPO(driver);
    // stepHelper.acceptAllAlertsOnAppStart(alertPO);
    //
    // AppSetupPO appSetupPO = new AppSetupPO(driver);
    // appSetupPO.createApp("Arash's Test App", "app_NkAspaCI7FVQ036YAtjVT2hL0xjTcO3gBOibGpFpbMw",
    // "prod_hSnSV38jxsfiSPCzCCc3vFg8c5WMHtgf4OeFgLGjIrQ", "dev_V1vGrptcSGi047LrynSnqxXHFvgKENBPDLf4IrtEIoss");
    //
    // driver.closeApp();
    // }
    //
    // // Temporary
    // private void createApp(MobileDriver<MobileElement> driver) {
    // AppSetupPO appSetupPO = new AppSetupPO(driver);
    // // MobileDriverUtils
    // // .waitForExpectedCondition(driver, ExpectedConditions
    // // .visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Always
    // // Allow\"]")))
    // // .click();
    //
    // appSetupPO.appPicker.click();
    //
    // MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(appSetupPO.newApp)).click();
    //
    // driver.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys("testRondo");
    // driver.findElement(By.xpath("//XCUIElementTypeTextField[2]"))
    // .sendKeys("app_ve9UCNlqI8dy6Omzfu1rEh6hkWonNHVZJIWtLLt6aLs");
    // driver.findElement(By.xpath("//XCUIElementTypeTextField[3]"))
    // .sendKeys("prod_D5ECYBLrRrrOYaFZvAFFHTg1JyZ2Llixe5s077Lw3rM");
    // driver.findElement(By.xpath("//XCUIElementTypeTextField[4]"))
    // .sendKeys("dev_cKF5HMpLGqhbovlEGMKjgTuf8AHfr2Jar6rrnNhtzQ0");
    //
    // driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Create\"]"));
    //
    // MobileDriverUtils
    // .waitForExpectedCondition(driver, ExpectedConditions
    // .visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name=\"testRondo\"]")))
    // .click();
    //
    // driver.resetApp();
    // }

}
