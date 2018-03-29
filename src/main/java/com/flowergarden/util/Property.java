package com.flowergarden.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Andrew Bandura
 */
public class Property {

    public Properties getProperties() {

        String propFileName = "config.properties";
        Properties prop = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
