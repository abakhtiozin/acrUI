package main.java.ui.pages;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;
import test.java.base.TestHelper;
import main.java.model.Reseller;



public class LoginPage extends AnyPage{

	private SelenideElement fldResellerCode() {
        return $(By.id("reseller_code"));
    }
    private SelenideElement fldUserName() {
        return $(By.id("username"));
    }
    private SelenideElement fldPassword() {
        return $(By.id("password"));
    }
    private SelenideElement fldLanguage() { 
    	return $(By.xpath("//select[@class='text']")); 
    }
    private void submit() {
        $(By.id("idLoginSubmit")).click();
    }

    public LoginPage() {
		super();
	}
    
    private void login(Reseller reseller) {
    	if (reseller.language() != null && reseller.language().locale().name() != fldLanguage().getSelectedValue()) {
    		fldLanguage().selectOptionByValue(reseller.language().locale().name());
    	}
    	fldResellerCode().clear();
		fldResellerCode().sendKeys(reseller.code());
		fldUserName().clear();
		fldUserName().sendKeys(reseller.userName());
		fldPassword().clear();
		fldPassword().sendKeys(reseller.password());
		submit();    	
    }
    
    @Step
	public HomePage validLogin(Reseller reseller) {
		login(reseller);
		$("#sign-in-box").waitWhile(Condition.visible, 10000);
		return page(HomePage.class);
	}
    
    @Step
	public LoginPage invalidLogin(Reseller reseller) {
		login(reseller);
		$(".form-errors").waitUntil(Condition.visible, 10000);
		
		return page(LoginPage.class);
	}
	
    @Step
	public HomePage forceLogin(Reseller reseller, boolean cancelOtherAuth) {
		login(reseller);
		try { 
			if ($("#reseller_user_auth_session_clean").waitUntil(Condition.visible, 10000).exists()) {
				if (cancelOtherAuth) {
					if (!$("#reseller_user_auth_session_clean").isSelected()) $("#reseller_user_auth_session_clean").click();
				}
				else {
					if ($("#reseller_user_auth_session_clean").isSelected()) $("#reseller_user_auth_session_clean").click();
				}	
				submit();
			}
			$("#sign-in-box").waitWhile(Condition.visible, 10000);
		}
		catch (com.codeborne.selenide.ex.ElementNotFound e) {
			TestHelper.Log.warning("An element \"input#reseller_user_auth_session_clean\" was expected but there is no such element on the form");
		}
		return page(HomePage.class);		
	}
	
	@Override
	public boolean onThisPage() {
		return $("#sign-in-box").waitUntil(Condition.exist, 10000).isDisplayed();
	}
	
    
}
