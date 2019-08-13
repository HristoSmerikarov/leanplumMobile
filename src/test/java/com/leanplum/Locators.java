package com.leanplum;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class Locators{
	
	//@iOSFindBy(xpath = "")
	@AndroidFindBy(id = "android:id/button1")
	public MobileElement button1;

	@AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Adhoc\"]")
	public MobileElement adhoc;

	@AndroidFindBy(id = "com.leanplum.rondo:id/buttonTrack")
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Send Track Event\"]")
	public MobileElement buttonTrack;
	
	Locators(AppiumDriver<MobileElement> driver){
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}
}
