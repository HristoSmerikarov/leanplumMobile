package com.leanplum.tests.appiumdriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.helpers.Utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServiceUtils {

    public static List<AppiumDriverLocalService> appiumServices = new ArrayList<>();
    public static AppiumDriverLocalService appiumService;
    
    public AppiumDriverLocalService setupAppiumService(PlatformEnum platform, String ipAddress, int port) {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(ipAddress);
        builder.usingPort(port);
       // builder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"));

        appiumService = AppiumDriverLocalService.buildService(builder);
        appiumServices.add(appiumService);

        return appiumService;
    }

    public static int findFreePortBetween(int from, int to) {
        String port = Utils.generateRandomNumberInRange(from, to);
        while (!isPortFree(port)) {
            port = Utils.generateRandomNumberInRange(from, to);
        }

        System.out.println("Free port: " + port);

        return Integer.valueOf(port);
    }

    public static int findFreePort() {
        return findFreePortBetween(4700, 5000);
    }
    
    public static void killNodeServer(OSEnum os) {
        if(os==OSEnum.WINDOWS) {
            Utils.runCommandInTerminal(os, "taskkill /f /im node.exe"); 
        }else {
            Utils.runCommandInTerminal(os, "killall node"); 
        }
    }

    private static boolean isPortFree(String port) {
        OSEnum os = Utils.determineOS();
        switch (os) {
        case WINDOWS:
            return Utils.runCommandInTerminal(os, String.format("netstat -ano | findStr %s", port)).isEmpty();
        case MAC:
            return Utils.runCommandInTerminal(os, String.format("lsof -nP -i4TCP:%s | grep LISTEN", port)).isEmpty();
        }
        return true;
    }
}
