package com.leanplum.tests.helpers;

import java.util.*;

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
}
