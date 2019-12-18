package com.leanplum.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leanplum.tests.appiumdriver.TestDevice;
import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.helpers.Utils;

public class RunTests {

	public static void main(String[] args) throws IOException {

		// List<String> responseLines = Utils.runCommandInTerminal(OSEnum.MAC, "adb
		// devices");

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "adb devices");
		List<String> responseLines = new ArrayList<>();
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				responseLines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> androidDeviceIds = new ArrayList<String>();
		responseLines.forEach(line -> {
			System.out.println(line);
			if (!line.equals("List of devices attached") && !line.isEmpty()) {
				androidDeviceIds.add(Utils.findPropertyMatch(line, "^(.*?)\\W"));
			}
		});

		List<TestDevice> androidTestDevices = new ArrayList<>();
		androidDeviceIds.forEach(id -> {
			List<String> deviceModel = Utils.runCommandInTerminal(OSEnum.MAC,
					"adb -s " + id + " shell getprop ro.product.model");
			List<String> androidVersion = Utils.runCommandInTerminal(OSEnum.MAC,
					"adb -s " + id + " shell getprop ro.build.version.release");

			System.out.println("ID: " + id);
			System.out.println("Model: " + deviceModel);
			System.out.println("Version: " + androidVersion);

			androidTestDevices
					.add(new TestDevice(id, deviceModel.get(0), PlatformEnum.ANDROID_APP, androidVersion.get(0)));
		});
	}

}
