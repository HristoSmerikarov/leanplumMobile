package com.leanplum.tests;

import java.lang.reflect.Method;
import java.util.Random;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.leanplum.base.CommonTestSteps;
import com.leanplum.base.TestStepHelper;
import com.leanplum.tests.helpers.Utils;
import com.leanplum.tests.pageobject.VariablesPO;
import com.leanplum.tests.pageobject.inapp.AlertPO;
import com.leanplum.utils.listeners.TestListener;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

@Listeners({ TestListener.class })
public class VariablesTest extends CommonTestSteps {

//    /**
//     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186440">C186440</a>
//     * @see <a href="https://teamplumqa.testrail.com/index.php?/cases/view/186441">C186441</a>
//     */
//    @Parameters({"id"})
//    @Test(groups = { "android", "ios", "pushNotifications" }, description = "Verification of IOS variables")
//    public void variableSync(Method method, String id) {
//        try {
//            TestStepHelper stepHelper = new TestStepHelper(this);
//            MobileDriver<MobileElement> driver = getDriver();
//
//            String stringVar = "test string var with numbers and cyrilic " + Utils.generateRandomNumberInRange(0, 100)
//                    + " cyrilic words";
//            String numVar = Utils.generateRandomNumberInRange(0, 1000);
//            Random random = new Random();
//            String boolVar = String.valueOf(random.nextBoolean());
//            String fileNameVar = "";
//            boolean hasImage = true;
//
//            if (driver instanceof AndroidDriver) {
//                // TemporaryAPI.setAndroidVars(stringVar, numVar, boolVar, fileNameVar, String.valueOf(hasImage));
//            } else {
//                // TemporaryAPI.setIOSVars(stringVar, numVar, boolVar, fileNameVar, String.valueOf(hasImage));
//            }
//            AlertPO alertPO = new AlertPO(driver);
//            stepHelper.acceptAllAlertsOnAppStart(alertPO);
//
//            VariablesPO variablesPO = new VariablesPO(driver);
//            variablesPO.areValuesCorrect(stringVar, numVar, boolVar, fileNameVar, hasImage);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            endStep(e.toString(), false);
//        }
//        endTest();
//    }
}
