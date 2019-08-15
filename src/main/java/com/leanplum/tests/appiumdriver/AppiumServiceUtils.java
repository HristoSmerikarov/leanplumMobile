package com.leanplum.tests.appiumdriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

import com.leanplum.tests.enums.PlatformEnum;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServiceUtils {

	private static String DRIVER_CONFIG_FILE = "resources/appium_service.properties";

	AppiumDriverLocalService service = null;

	// AppiumDriverLocalService
	public static AppiumDriverLocalService setupAppiumService() {
		AppiumServiceConfig appiumServiceConfig = (AppiumServiceConfig) PropertiesUtils
				.loadProperties(DRIVER_CONFIG_FILE, AppiumServiceConfig.class);

		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort()
				.withIPAddress("127.0.0.1").usingPort(4723)
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				.usingDriverExecutable(new File("/usr/local/bin/node")).withLogFile(new File("AppiumLogFile.txt")));

//		try {
//			ProcessBuilder procBuilder = new ProcessBuilder();
//			procBuilder.command("sh", "-c", "./appium --address 127.0.0.1 --port 4723");
//			procBuilder.directory(new File("/usr/local/bin"));
//			Process proc = procBuilder.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		AppiumServiceBuilder builder = new AppiumServiceBuilder();
//		builder.usingDriverExecutable(new File("/usr/local/bin/node"))
//				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
//				.withIPAddress(appiumServiceConfig.getAppiumServiceIp())
//				.withLogFile(new File("/usr/local/lib/node_modules/appium/node_modules/appium-support/lib/logger.js"))
//		// builder.withArgument(GeneralServerFlag.SHELL,
//		// "/Users/hristogergov/Downloads/Rondo-iOS.ipa");
//		.usingPort(Integer.valueOf(appiumServiceConfig.getAppiumServicePort())).build();
//		return AppiumDriverLocalService.buildService(builder);
//		// your test scripts logic...
//		// service.stop();
	}
}
