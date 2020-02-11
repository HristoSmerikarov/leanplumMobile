package com.leanplum.tests.appiumdriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.Assert;

import com.pholser.util.properties.PropertyBinder;

class PropertiesUtils {

    public static Properties loadProperties(String file) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            in.close();
            return prop;
        } catch (Exception e) {
            Assert.isTrue(false, "Could not load properties '" + file + "'");
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

        Assert.notNull("Expected property file to load", file);
        try (FileInputStream fileStream = new FileInputStream(file)) {
            Assert.notNull("No Properties for " + file + " found", file);
            PropertyBinder<?> binder = PropertyBinder.forType(clazz);
            data = binder.bind(fileStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
