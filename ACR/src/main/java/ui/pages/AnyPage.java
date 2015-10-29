package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;
import main.java.ApplicationStorage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 03.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class AnyPage implements Page, SelenideWait, Logout {

    public AnyPage() {
        ApplicationStorage.getInstance().setCurrentPage(this);
    }


    private SelenideElement alertSuccessElement() {
        return $(".alert.alert-success");
    }

    private SelenideElement alertErrorElement() {
        return $(".alert.alert-error");
    }

    private SelenideElement alertInfoElement() {
        return $(".alert.alert-info");
    }

    public boolean isSuccessAlertDisplayed() {
        return alertSuccessElement().waitUntil(appear, WAIT_TIME).isDisplayed();
    }

    public boolean isSuccessAlertDisplayedWithText(String text) {
        return alertSuccessElement().waitUntil(appear, WAIT_TIME).getText().equals(text);
    }
    public boolean isErrorAlertDisplayed() {
        return alertErrorElement().waitUntil(appear, WAIT_TIME).isDisplayed();
    }

    /*костыль, подумать на ожиданиями по всему приложению*/
    public boolean isErrorAlertExist() {
        return alertErrorElement().isDisplayed();
    }

    public String getErrorAlertText() {
        return alertErrorElement().getText();
    }

    public boolean isErrorAlertDisplayedWithText(String text) {
        return alertErrorElement().getText().contains(text);
    }

    public boolean isInfoAlertDisplayedWithText(String text) {
        return alertInfoElement().waitUntil(appear, WAIT_TIME).getText().contains(text);
    }

    public void closeAlertBlock() {
        $(".close").click();
    }
}
