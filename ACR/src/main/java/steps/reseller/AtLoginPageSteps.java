package main.java.steps.reseller;

import main.java.model.Reseller;
import main.java.ui.pages.LoginPage;
import ru.yandex.qatools.allure.annotations.Step;

import static org.testng.Assert.assertTrue;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtLoginPageSteps extends ResellerSteps {

    private LoginPage loginPage;

    // --------------------------- CONSTRUCTORS ---------------------------


    @Override
    public void setUpResellerAtPage() {
        this.loginPage = (LoginPage) applicationStorage.getCurrentPage();
    }

    @Step
    public void loginAs(Reseller reseller) {
        if (applicationStorage.getCurrentPage() instanceof LoginPage) {
            setUpResellerAtPage();
        } else {
            applicationStorage.getCurrentPage().logout();
            setUpResellerAtPage();
        }
        shouldBeOnThisPage();
        loginPage.validLogin(reseller);
        stepManager.setUpResellerAtJourneySearchPage();
        applicationStorage.setReseller(reseller);
    }

    @Step
    public void invalidLoginAs(Reseller reseller) {
        setUpResellerAtPage();
        shouldBeOnThisPage();
        loginPage.invalidLogin(reseller);
        shouldBeOnThisPage();
    }

    @Step
    @Override
    public void shouldBeOnThisPage() {
        assertTrue(loginPage.onThisPage(), "Not on login page");
    }

}
