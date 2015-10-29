package main.java.ui.pages;

import main.java.Manager;
import main.java.model.Locale;
import main.java.model.restriction.Limit;
import main.java.ui.webElements.MainMenu;
import main.java.ui.webElements.SearchForm;
import test.java.base.TestHelper.Log;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;




public abstract class CommonPage extends AnyPage implements PageHeader, Limit {
	
	protected long VISIBILITY_WAIT_TIME = 60000;	// Defines max wait time for element on page
	
	
	private SelenideElement getBtnLogout() {
		By locBtnLogout = By.xpath("//div[@class='logout-control']/a");
		return $(locBtnLogout).waitWhile(hidden, VISIBILITY_WAIT_TIME);
	}
	private SelenideElement getDivChoosenCulture() {
		return $(".choosen-culture").waitWhile(hidden, VISIBILITY_WAIT_TIME);
	}
	
	private ElementsCollection getAvlCultureAnchors() {		
		return getDivChoosenCulture().$$(By.tagName("a"));		
	}
	
	
	public CommonPage() {
		super();
	}
	
	
	public MainMenu navigate() {
		return new MainMenu();
	}
	
	public SearchForm getSearchForm() {
		navigate().toSearchPage();
		if (SearchResultsPage.isLoadingOffers()) {
			Log.debug(getClass().getSimpleName() + ".getSearchForm: Currently offers are on the loading process. Waiting for the loading completion... ");
			SearchResultsPage
				.waitForOffers(SEARCH_MAX_WAIT_TIME, true)
				.changeSearchCriteria();
    	}
		
		return page(SearchForm.class);
	}
	
	@Override
	public LoginPage logout() {
		getBtnLogout().click();
		
		Alert logoutConfirm = switchTo().alert();
		logoutConfirm.accept();
		
		return page(LoginPage.class);
	}

	@Override
	public CommonPage chooseCulture(Locale curLocale) {
				
		ElementsCollection locCultureBtns = getAvlCultureAnchors();
		
		for (int i = 0; i < locCultureBtns.size(); i++) {
			String anchorHref = locCultureBtns.get(i).getAttribute("href");
			String localeName = anchorHref.substring(anchorHref.lastIndexOf("/") + 1, anchorHref.lastIndexOf("."));
			
			if (localeName.equals(curLocale.toString().toLowerCase())) {
				locCultureBtns.get(i).click();
				break;
			}
			else {
				continue;
			}					
					
		}

		return page(this);		
	}

	@Override
	public Locale getChosenCulture() {
		String anchorHref = getDivChoosenCulture().$(By.xpath("a/img[@class='active']/..")).getAttribute("href");
		String localeName = anchorHref.substring(anchorHref.lastIndexOf("/") + 1, anchorHref.lastIndexOf("."));
		return Locale.find(localeName);
	}
	
	@Override
	protected boolean onThisPage() {
		return Manager.getInstance().getCurrentPage() instanceof CommonPage ? true : false;
	}
}
