package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;
import main.java.model.Reseller;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 *  Created by Andrii.Bakhtiozin on 07.01.2015.
 */
public class LoginPage extends AnyPage {

    public LoginPage() {
        super();
    }

    private SelenideElement getResellerCodeField() {
        return $("#resellerCode");
    }

    private SelenideElement getUserNameField() {
        return $("#username");
    }

    private SelenideElement getPasswordField() {
        return $("#password");
    }

    private void submit() {
        $("button").click();
    }

    public JourneySearchPage validLogin(Reseller reseller) {
        getResellerCodeField().setValue(reseller.getResellerCode());
        getUserNameField().setValue(reseller.getUserName());
        getPasswordField().setValue(reseller.getPassword());
        submit();
        return page(JourneySearchPage.class);
    }

    public LoginPage invalidLogin(Reseller reseller) {
        getResellerCodeField().setValue(reseller.getResellerCode());
        getUserNameField().setValue(reseller.getUserName());
        getPasswordField().setValue(reseller.getPassword());
        submit();
        return page(LoginPage.class);
    }

    public boolean onThisPage() {
        return $("#signInForm").waitUntil(appear, MIDDLE_WAIT_TIME).isDisplayed();
    }


}
