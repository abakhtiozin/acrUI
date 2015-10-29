package main.java.ui.pages;


import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;

import com.codeborne.selenide.SelenideElement;

import main.java.model.Country;


public class LocationPage extends AnyPage { 

	private SelenideElement getFieldLocation() {
		return $("#location_country");		
	}	
	private void submit() {
		SelenideElement btnSubmit = $(By.xpath("//input[@value='Save location']")); 
		btnSubmit.click();
	}
	
	public LocationPage() {
		super();
	}
		
	@Step
	public LoginPage setLocation(Country country) {
		try {
			getFieldLocation().selectOptionByValue(country.iso2Code());
		} catch (Exception e) { /* Used default selection */ }
		finally {
			submit();			
		}
			
		return page(LoginPage.class);			
	}
	
	@Override
	public boolean onThisPage() {
		return getFieldLocation().isDisplayed();
	}

}