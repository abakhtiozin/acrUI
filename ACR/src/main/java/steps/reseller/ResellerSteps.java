package main.java.steps.reseller;

import main.java.steps.Steps;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Andrii.Bakhtiozin on 17.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class ResellerSteps extends Steps implements AtAnyPageSteps {

    @Step
    public void shouldSeeSuccessAlertWithText(String text) {
        Assert.assertTrue(applicationStorage.getCurrentPage().isSuccessAlertDisplayedWithText(text), "Success alert with text '" + text + "' not found");
    }

    @Step
    public void shouldSeeErrorAlertWithText(String text) {
        Assert.assertTrue(applicationStorage.getCurrentPage().isErrorAlertDisplayedWithText(text), "Error alert with text '" + text + "' not found");
    }

    @Step
    public void shouldSeeErrorAlert() {
        Assert.assertTrue(applicationStorage.getCurrentPage().isErrorAlertDisplayed(), "Error alert not found");
    }

    @Step
    public void shouldNotSeeErrorAlert() {
        Assert.assertTrue(applicationStorage.getCurrentPage().getErrorAlertText().isEmpty(), "Error alert found '" + applicationStorage.getCurrentPage().getErrorAlertText() + "'");
    }

    @Step
    public void shouldSeeSuccessAlert() {
        Assert.assertTrue(applicationStorage.getCurrentPage().isSuccessAlertDisplayed(), "Success alert not found");
    }

    @Step
    public void shouldSeeInfoAlert(String text) {
        Assert.assertTrue(applicationStorage.getCurrentPage().isInfoAlertDisplayedWithText(text), "Info alert with text '" + text + "' not found");
    }

    @Step
    public void closeAlertBlock() {
        if (applicationStorage.getCurrentPage().isErrorAlertExist()) {
            applicationStorage.getCurrentPage().closeAlertBlock();
        }
    }
}
