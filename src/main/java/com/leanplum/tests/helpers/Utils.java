package com.leanplum.tests.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.enums.OSEnum;

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

    public static String runCommandInTerminal(OSEnum os, String command) {
        String responce;
        switch (os) {
        case WINDOWS:
            Process p;
            try {
                p = Runtime.getRuntime().exec("cmd /c " + command);

                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        case MAC:
            // TODO
            break;
        }
        return "";
    }

    public static void swipeTopToBottom(MobileDriver<MobileElement> driver) {
        Dimension screenSize = driver.manage().window().getSize();
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
