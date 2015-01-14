package model;

/**
 * Created by AA on 24.12.2014.
 */
public class Reseller {

    private String resellerCode;
    private String userName;
    private String password;
    private String lang;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public String getResellerCode() {
        return resellerCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLang() {
        return lang;
    }

    public Reseller(String resellerCode, String userName, String password, String lang) {
        this.resellerCode = resellerCode;
        this.userName = userName;
        this.password = password;
        this.lang = lang;
    }

    public Reseller(String resellerCode, String userName, String password) {
        this.resellerCode = resellerCode;
        this.userName = userName;
        this.password = password;
    }
}
