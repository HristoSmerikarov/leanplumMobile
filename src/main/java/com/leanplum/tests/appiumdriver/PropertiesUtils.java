package com.leanplum.tests.appiumdriver;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.pholser.util.properties.PropertyBinder;

public class PropertiesUtils {

    public static Properties loadProperties(String file) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(file);

            prop.load(in);

            in.close();

            return prop;
        } catch (Exception e) {
            fail("Could not load properties '" + file + "'");

            return null;
        }
    }

    /**
     * Generic Bound property loader
     * 
     * @param file - file to load
     * @param clazz - interface type expected for Bound property
     * @return
     */
    public static Object loadProperties(String file, Class<?> clazz) {
        Object data = null;
        assertNotNull("Expected property file to load", file);
        try (FileInputStream fileStream = new FileInputStream(file)) {
            assertNotNull("No Properties for " + file + " found");
            PropertyBinder<?> binder = PropertyBinder.forType(clazz);
            data = binder.bind(fileStream);
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
