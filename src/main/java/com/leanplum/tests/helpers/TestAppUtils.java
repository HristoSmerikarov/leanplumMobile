package com.leanplum.tests.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leanplum.tests.pageobject.nativesdk.NAppSetupPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestAppUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestAppUtils.class);

    public void selectAppAndEnv(AppiumDriver<MobileElement> driver) {

        String appName = System.getProperty("application");
        String envName = System.getProperty("environment");

        if (!appName.equals("") && !envName.equals("")) {
            NAppSetupPO appSetup = new NAppSetupPO(driver);

            selectApp(appSetup, appName);
            selectEnvironment(appSetup, envName);

            logger.info("App " + appName + " and environment" + envName + " selected");
        }
    }

    private void selectApp(NAppSetupPO appSetup, String appName) {
        appSetup.selectApp(appName);
    }

    private void selectEnvironment(NAppSetupPO appSetup, String envName) {
        appSetup.selectEnvironment(envName);
    }
}
