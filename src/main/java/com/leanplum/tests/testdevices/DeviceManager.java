package com.leanplum.tests.testdevices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.helpers.Utils;

public class DeviceManager {

    public static List<TestDevice> connectedTestDevices = new ArrayList<>();

    public void determineConnectedDevices() {
        OSEnum os = Utils.determineOS();
        System.out.println("OS: "+os.getOsName());
        connectedTestDevices.addAll(getConnectedAndroidDevice(os));
        connectedTestDevices.addAll(getConnectedIOSDevice(os));

        connectedTestDevices.forEach(device -> {
            System.out.println("DEVICE LIST IDS: " + device.getId());
        });
    }

    public static List<TestDevice> getConnectedAndroidDevice(OSEnum os) {
        List<String> responseLines = Utils.runCommandInTerminal(os, "adb devices");
        List<String> androidDeviceIds = new ArrayList<String>();
        System.out.println("ANDROID DEVICES: "+androidDeviceIds);
        responseLines.forEach(line -> {
            if (!line.equals("List of devices attached") && !line.isEmpty()) {
                androidDeviceIds.add(Utils.findPropertyMatch(line, "^(.*?)\\s"));
            }
        });

        List<TestDevice> androidTestDevices = new ArrayList<>();
        for (String id : androidDeviceIds) {
            List<String> deviceModel = Utils.runCommandInTerminal(os,
                    "adb -s " + id + " shell getprop ro.product.model");
            List<String> androidVersion = Utils.runCommandInTerminal(os,
                    "adb -s " + id + " shell getprop ro.build.version.release");
            androidTestDevices.add(new AndroidTestDevice(id, deviceModel.get(0), androidVersion.get(0)));
        }
        return androidTestDevices;
    }

    public static List<TestDevice> getConnectedIOSDevice(OSEnum os) {
        List<TestDevice> devices = new ArrayList<>();
        if (os == OSEnum.MAC) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "xcrun instruments -s devices");
            List<String> responseLines = new ArrayList<>();
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains(":") && !line.contains("-")) {
                        responseLines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            int wdaPort = 8100;
            for (String line : responseLines) {
                wdaPort = +wdaPort;
                if (!line.contains("Simulator") && !line.contains("MacBook")) {
                    String udid = Utils.findPropertyMatch(line, IOSTestDevice.IOS_UDID_REGEX);
                    String platformVersion = Utils.findPropertyMatch(line, IOSTestDevice.IOS_PLATFORM_VERSION_REGEX);
                    String name = Utils.findPropertyMatch(line, IOSTestDevice.IOS_NAME_REGEX);
                    devices.add(new IOSTestDevice(udid, name, platformVersion, String.valueOf(wdaPort)));
                }
            }
        }
        return devices;
    }

}
