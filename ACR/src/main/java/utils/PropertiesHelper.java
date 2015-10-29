package main.java.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by Andrii.Bakhtiozin on 22.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PropertiesHelper {
    public final static String PROJECT_PROPERTIES_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\properties\\";

    public static Properties getProjectProperties(String propertiesName) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(new File(PROJECT_PROPERTIES_PATH + propertiesName));
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveProjectProperties(Properties properties, String propertiesName) {
        try {
            OutputStream fileOutputStream = new FileOutputStream(PROJECT_PROPERTIES_PATH + propertiesName);
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
