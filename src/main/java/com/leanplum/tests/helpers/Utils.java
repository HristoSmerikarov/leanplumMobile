package com.leanplum.tests.helpers;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
    
    

    public void swipeDownToElement(MobileDriver<MobileElement> driver) {
        swipeVertical(driver, 0.7, 0.1, 0.5, 1000);
    }

    public void swipeUpToElement(MobileDriver<MobileElement> driver) {
        swipeVertical(driver, 0.2, 0.7, 0.5, 1000);
    }

    public boolean swipeToElement(MobileDriver<MobileElement> driver, MobileElement element, SwipeDirection direction) {
        boolean isVisible = false;
        int tries = 0;
        while (!isVisible) {
            try {
                MobileDriverUtils.waitForExpectedCondition(driver, 1, ExpectedConditions.visibilityOf(element));
                isVisible = true;
            } catch (Exception e) {
                switch (direction) {
                case UP:
                    swipeUpToElement(driver);
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

    private void swipeVertical(MobileDriver<MobileElement> driver, double startPercentage, double finalPercentage,
            double anchorPercentage, int duration) {
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
