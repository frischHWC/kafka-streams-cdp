package com.cloudera.frisch.kafka_streams_tester.config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesLoader {

    private static final Logger logger = Logger.getLogger(PropertiesLoader.class);

    public static final Properties properties = loadProperties();

    private static Properties loadProperties() {
        // Load config file
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error("Property file not found !", e);
        }
        return properties;
    }

    public static String getProperty(String key) {
        String property = "null";
        try {
             property = properties.getProperty(key);
            if(property.substring(0,2).equalsIgnoreCase("${")) {
                property = PropertiesLoader.getProperty(property.substring(2,property.length()-1));
            }
        } catch (Exception e) {
            logger.warn("Could not get property : " + key + " due to following error: ", e);
        }
        return property;
    }
}
