package main.java.core;

/**
 * Created by Andrii.Bakhtiozin on 11.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class ACR {
    private static ACR instance;
    private String siteURL;

    private ACR() {
    }

    public static ACR getInstance() {
        if (instance == null) {
            instance = new ACR();
        }
        return instance;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }
}
