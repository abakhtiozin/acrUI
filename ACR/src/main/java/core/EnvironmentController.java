package main.java.core;

import db.DataBase;

import java.sql.SQLException;
import java.util.Properties;

import static main.java.utils.PropertiesHelper.getProjectProperties;
import static main.java.utils.PropertiesHelper.saveProjectProperties;

/**
 * Created by Andrii.Bakhtiozin on 15.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class EnvironmentController {
    private final String environment = (System.getProperty("environment") == null) ? "dev" : System.getProperty("environment");

    private void connectToDataBase() {
        try {
            Properties properties = getProjectProperties(environment + ".database.properties");
            DataBase.getInstance("jdbc:mysql://" + properties.getProperty("database.hostUrl") + "/",
                    properties.getProperty("database.dbName"),
                    properties.getProperty("database.backEndDbName"),
                    properties.getProperty("database.login"),
                    properties.getProperty("database.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setEnvironmentUrl() {
        Properties urlProperties = getProjectProperties("url.environment.properties");
        String environmentPropertiesName = "environment.properties";
        Properties environmentProperties = getProjectProperties(environmentPropertiesName);
        switch (environment) {
            case "dev":
                environmentProperties.setProperty("url", urlProperties.getProperty("dev.url"));
                saveProjectProperties(environmentProperties, environmentPropertiesName);
                break;
            case "prerelease":
                environmentProperties.setProperty("url", urlProperties.getProperty("prerelease.url"));
                saveProjectProperties(environmentProperties, environmentPropertiesName);
                break;
            default:
                environmentProperties.setProperty("url", urlProperties.getProperty("default.url"));
        }
    }

    public void setUp() {
        connectToDataBase();
        setEnvironmentUrl();
    }

}
