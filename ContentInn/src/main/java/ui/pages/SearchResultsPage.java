package main.java.ui.pages;


import static com.codeborne.selenide.Selenide.*;

import java.util.ArrayList;
import java.util.List;

import main.java.ui.webElements.SearchForm;
import main.java.ui.webElements.searchResultsPage.HotelOffer;
import main.java.util.LogUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import ru.yandex.qatools.allure.annotations.Step;
import test.java.base.TestHelper.Log;


public class SearchResultsPage extends CommonPage {
		
	
	private SelenideElement resultsGrid() {
		return $(By.xpath("//table[@class='grid-data-table']"));
	}
	private SelenideElement btnChange() {
		return $(By.id("search_container_toggler")).$(By.tagName("a"));
	}
	private static SelenideElement loadingIndicator() {
		return $(By.xpath("//div[@class='indicator']"));
	}
	
	public SearchResultsPage() {
		super();
	}
	
	@Step
	public static SearchResultsPage waitForOffers(long maxTime) {
		loadingIndicator().waitUntil(Condition.disappear, maxTime);
		return page(SearchResultsPage.class);
	}
	@Step
	public static SearchResultsPage waitForOffers(long maxTime, boolean stop) {
		try {
			loadingIndicator().waitUntil(Condition.disappear, maxTime);
		}
		catch (com.codeborne.selenide.ex.ElementShould e) {
			Log.warning("The search process isn't completed within " + maxTime / 1000 + " second(s)");
			if (stop) {
				$(By.id("searchStopButton")).click();
				loadingIndicator().waitUntil(Condition.disappear, maxTime);
			}
			else {
				throw e;
			}
		}
		return page(SearchResultsPage.class);
	}
	@Step
	public static boolean isLoadingOffers() {
		LogUtils.takeScreenshot("IsLoadingOffers screenshot");
		return loadingIndicator().isDisplayed();
	}
	@Step		
	public boolean isOffersFound() {
		return resultsGrid().isDisplayed();
	}
	
	@Step
	public SearchForm changeSearchCriteria() {
		btnChange().click();
		$(By.id(SearchForm.containerId())).waitUntil(Condition.appear, 5000);		
		return page(SearchForm.class);
	}
	
	@Step
	public HotelOffer hotelOffer(String hotelName) {
		return HotelOffer.find(hotelName);		
	}
	
	@Step
	public List<HotelOffer> hotelOffers() {
		List<HotelOffer> hotelOffers  = new ArrayList<HotelOffer>();
		
		
		//List<SelenideElement> hotelBlocks = resultsGrid().$$(By.xpath("./tbody/tr/td/child::div"));
		//List<WebElement> hotelBlocks = resultsGrid().toWebElement().findElements(By.xpath("./tbody/tr/td/child::div"));
		//List<SelenideElement> hotelBlocks = $$(By.xpath("//table[@class='grid-data-table']/tbody/tr/td/div"));
		List<WebElement> hotelBlocks = resultsGrid().findElements(By.xpath("./tbody/tr/td/child::div"));
		for (WebElement hotelBlock : hotelBlocks) {
			hotelOffers.add(new HotelOffer($(hotelBlock)));
		}		
		return hotelOffers;
	}
	
	@Step
	public HotelOffer hotelOffer(int index) {
		return hotelOffers().get(index);
	}
	
	@Override
	public boolean onThisPage() {
		return isLoadingOffers() || isOffersFound();
	}
}
