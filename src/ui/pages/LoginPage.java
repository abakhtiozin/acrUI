package ui.pages;

import com.codeborne.selenide.SelenideElement;
import model.Reseller;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by AA on 07.01.2015.
 */
public class LoginPage {

    private SelenideElement resellerCode = $("#resellerCode");
    private SelenideElement userName = $("#username");
    private SelenideElement password = $("#password");
    private SelenideElement submitButton = $("button");

    public JourneySearchPage validLogin(Reseller reseller) {
        resellerCode.setValue(reseller.getResellerCode());
        userName.setValue(reseller.getUserName());
        password.setValue(reseller.getPassword());
        submitButton.click();
        $(".center.loader").should(disappear); // Waits until element disappears
        return page(JourneySearchPage.class);
    }
}
