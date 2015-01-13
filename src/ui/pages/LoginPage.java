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

    private SelenideElement getResellerCodeField(){
        return $("#resellerCode");
    }
    private SelenideElement getUserNameField() {
        return $("#username");
    }
    private SelenideElement getPasswordField() {
        return $("#password");
    }
    private void submit(){
        $("button").click();
    }

    public JourneySearchPage validLogin(Reseller reseller) {
        getResellerCodeField().setValue(reseller.getResellerCode());
        getUserNameField().setValue(reseller.getUserName());
        getPasswordField().setValue(reseller.getPassword());
        submit();
//        $(".center.loader").should(disappear); // Waits until element disappears
        return page(JourneySearchPage.class);
    }


}
