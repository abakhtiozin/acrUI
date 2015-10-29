package main.java.ui.webElements;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import main.java.model.restriction.Limit;
import main.java.ui.pages.*;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;



public class MainMenu implements Limit {
	
	private static SelenideElement divMenu;
	
	public MainMenu() {
		if ( $("#menu").waitWhile(Condition.hidden, ELM_VISIBILITY_MAX_WAIT_TIME).isDisplayed() ) {
			divMenu = $("#menu").waitWhile(Condition.hidden, ELM_VISIBILITY_MAX_WAIT_TIME);
		}
		else {
			throw new IllegalStateException("Could not initialize the MainMenu object: there is no correspondig div on the Page " + title() /* + "(url: " + url() + ")"*/);
		}		
	}
	
	private SelenideElement getElement(String link) {
		return divMenu.$(By.xpath("//a[contains(@href,'" + link.trim().toLowerCase() + "/index')]"));
	}
	private void toPage(String link) {
		getElement(link).click();
	}
	
	@Step	
	public HomePage toHomePage() {
		toPage("Home");
		return page(HomePage.class);
	}
	@Step
	public SearchPage toSearchPage() {
		toPage("Search");		
		return page(SearchPage.class);
	}
	@Step
	public ReservationsPage toReservationsPage() {
		toPage("Reservations");
		return page(ReservationsPage.class);
	}
	@Step
	public FinInfoPage toFinInfoPage() {
		toPage("Financial");
		return page(FinInfoPage.class);
	}
	
}
