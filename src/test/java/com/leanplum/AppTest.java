package com.leanplum;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.leanplum.tests.base.BaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AppTest extends BaseTest {


	@Test
	public void test() {
		AppiumDriver<MobileElement> driver = (AppiumDriver<MobileElement>) getAppiumDriver();

		Locators loc = new Locators(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.visibilityOf(button1));
//
//        button1.click();

		wait.until(ExpectedConditions.visibilityOf(loc.adhoc));
		loc.adhoc.click();

		wait.until(ExpectedConditions.visibilityOf(loc.buttonTrack));
		String buttonText = loc.buttonTrack.getAttribute("name");

		Assert.assertTrue(buttonText.equals("SEND TRACK EVENT"));

		driver.closeApp();

		driver.quit();
	}

//	@Test
//	public void ios() {
//		IOSDriver<MobileElement> driver = (IOSDriver<MobileElement>) getAppiumDriver();
//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
//
//        driver.findElementById("android:id/button1").click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.leanplum.rondo:id/adhoc")));
//        driver.findElementById("com.leanplum.rondo:id/adhoc").click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.leanplum.rondo:id/buttonTrack")));
//        String buttonText = getAppiumDriver().findElementById("com.leanplum.rondo:id/buttonTrack").getText();
//
//        Assert.assertTrue(buttonText.equals("SEND TRACK EVENT"));
//        
//        driver.closeApp();
//        
//        driver.quit();
//	}
}
