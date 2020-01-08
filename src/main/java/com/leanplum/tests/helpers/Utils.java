package com.leanplum.tests.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.enums.PlatformEnum;
import com.leanplum.tests.testdevices.AndroidTestDevice;
import com.leanplum.tests.testdevices.IOSTestDevice;
import com.leanplum.tests.testdevices.TestDevice;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Utils {

    /**
     * Generate random integer number with given range
     *
     * @param min lower range inclusive
     * @param max upper range inclusive
     * @return generated random number
     */
    public static String generateRandomNumberInRange(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return Integer.toString(randomNumber);
    }

    public static List<String> runCommandInTerminal(OSEnum os, String command) {
        List<String> response = new ArrayList<>();
        switch (os) {
        case WINDOWS:
            Process p1;
            try {
                p1 = Runtime.getRuntime().exec("cmd /c " + command);
                p1.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        case MAC:
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        return response;
    }

    public static List<TestDevice> getConnectedAndroidDevice(OSEnum os) {
        List<String> responseLines = runCommandInTerminal(os, "adb devices");
        List<String> androidDeviceIds = new ArrayList<String>();
        responseLines.forEach(line -> {
            if (!line.contains("List of devices attached") && !line.isEmpty()) {
                androidDeviceIds.add(findPropertyMatch(line, "^(.*?)\\W"));
            }
        });
        
        List<TestDevice> androidTestDevices = new ArrayList<>();
        for (String id : androidDeviceIds) {
            List<String> deviceModel = runCommandInTerminal(os, "adb -s " + id + " shell getprop ro.product.model");
            List<String> androidVersion = runCommandInTerminal(os,
                    "adb -s " + id + " shell getprop ro.build.version.release");
            androidTestDevices.add(new AndroidTestDevice(id, deviceModel.get(0), androidVersion.get(0)));
        }
        return androidTestDevices;
    }
    
    public static OSEnum determineOS() {
        return OSEnum.valueOfEnum(System.getProperty("os.name")).get();

    public static String findPropertyMatch(String line, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        String property = "";
        if (m.find())
            property = m.group(1);

        return property;
    }

    public static void swipeTopToBottom(MobileDriver<MobileElement> driver) {
    	Options opt = driver.manage();
    	Window wind = opt.window();
    	Dimension screenSize = wind.getSize();
    	
        //Dimension screenSize = driver.manage().window().getSize();
        int yMargin = 5;
        int xMid = screenSize.width / 2;
        PointOption top = PointOption.point(xMid, yMargin);
        PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

        new TouchAction(driver).press(top).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1500))).moveTo(bottom)
                .perform();
    }

    public static void swipeBottomToTop(MobileDriver<MobileElement> driver) {
        Dimension screenSize = driver.manage().window().getSize();
        int yMargin = 15;
        int xMid = screenSize.width / 2;
        PointOption top = PointOption.point(xMid, yMargin);
        PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

        System.out.println("BOTTOM Y: " + (screenSize.height - yMargin));
        System.out.println("HEIGHT:" + screenSize.height);
        // System.out.println("TOP: "+bottom.toString());

        new TouchAction(driver).press(bottom).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1500))).moveTo(top)
                .perform();
    }

    public static boolean swipeToElement(MobileDriver<MobileElement> driver, MobileElement element,
            SwipeDirection direction) {
        boolean isVisible = false;
        int tries = 0;
        while (!isVisible) {
            try {
                MobileDriverUtils.waitForExpectedCondition(driver, 1, ExpectedConditions.visibilityOf(element));
                isVisible = true;
            } catch (Exception e) {
                switch (direction) {
                case UP:
                    swipeUp(driver);
                    break;
                case DOWN:
                    swipeDownToElement(driver);
                    break;
                }
                tries++;
                if (tries == 2) {
                    break;
                }
            }
        }

        return isVisible;
    }

    private static void swipeDownToElement(MobileDriver<MobileElement> driver) {
        swipeVertical(driver, 0.7, 0.1, 0.5, 1000);
    }

    private static void swipeUp(MobileDriver<MobileElement> driver) {
        swipeVertical(driver, 0.2, 0.7, 0.5, 1000);
    }

    private static void swipeVertical(MobileDriver<MobileElement> driver, double startPercentage,
            double finalPercentage, double anchorPercentage, int duration) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * finalPercentage);
        new TouchAction(driver).press(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
                .moveTo(PointOption.point(anchor, endPoint)).release().perform();
    }

    public enum SwipeDirection {
        UP, DOWN;
    }
}
