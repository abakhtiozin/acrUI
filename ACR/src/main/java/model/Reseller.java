package main.java.model;

import db.SQL;

/**
 * Created by Andrii.Bakhtiozin on 24.12.2014.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Reseller {

    private String resellerCode;
    private String userName;
    private String password;
    private String lang;

    private Balance balance;
    private Currency currency;
    private boolean isPartner;

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

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return Currency.valueOf(SQL.getResellerCurrency(this.resellerCode));
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

    @Override
    public String toString() {
        return resellerCode + ":" + userName;
    }
}
