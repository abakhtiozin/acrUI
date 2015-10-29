package main.java.ui.pages;

import main.java.core.ACR;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Andrii.Bakhtiozin on 20.10.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface Logout {
    default LoginPage logout() {
        return open(ACR.getInstance().getSiteURL() + "/ru/logout", LoginPage.class);
    }
}
