package main.java.ui.webElements.searchResultsPage;

import static com.codeborne.selenide.Selenide.*;
import main.java.model.CacheItem;
import main.java.model.Supplier;
import main.java.model.pricing.Currency;
import main.java.model.pricing.Price;
import main.java.ui.pages.BookingPage;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


public class RoomOffer extends CacheItem {
	private static final long WAIT_TIME = 5000;
	
	private SelenideElement rowContainer;
	
	private SelenideElement btnBook() {
		return rowContainer.$(By.xpath(".//button"));
	}
	
	
	public RoomOffer(SelenideElement element) {
		rowContainer = $(element);		
	}
	
	public long searchCacheId() {
		String attrVal = btnBook().attr("onclick");
		return Long.parseUnsignedLong(attrVal.substring(attrVal.lastIndexOf("/") + 1, attrVal.lastIndexOf(".")));
	}
	public Supplier supplier() {
		return getSupplier(searchCacheId());
	}
	public Price originalPrice() {
		return getOrignalPrice(searchCacheId());
	}
	
	@Step
	public BookingPage book() {
		btnBook().click();		
		return page(BookingPage.class);
	}	
	@Step	
	public RoomOffer addToBasket() {
		SelenideElement btnAdd = rowContainer.$(By.xpath(".//div[@class='basket_actions basket_action_add']/a")).waitUntil(Condition.exist, WAIT_TIME); 
//		SelenideElement btnAdd = rowContainer.$(By.xpath("td/div/div[@class='basket_actions basket_action_add']/a")).waitUntil(Condition.exist, WAIT_TIME); 
		if (btnAdd.isDisplayed()) {		
			btnAdd.click();
			btnAdd.waitUntil(Condition.disappears, WAIT_TIME);
		}
		return this;
	}
	@Step
	public RoomOffer removeFromBasket() {
		SelenideElement btnDel = rowContainer.$(By.xpath(".//div[@class='basket_actions basket_action_del']/a")).waitUntil(Condition.exist, WAIT_TIME);
//		SelenideElement btnDel = rowContainer.$(By.xpath("td/div/div[@class='basket_actions basket_action_del']/a")).waitUntil(Condition.exist, WAIT_TIME);
		if (btnDel.isDisplayed()) {		
			btnDel.click();
			btnDel.waitUntil(Condition.disappears, WAIT_TIME);
		}
		return this;
	}
	
	@Step
	public String availability() {
		return rowContainer.$(By.xpath("td[2]/span")).text().trim();
	}
	public double priceAmmount() {		
		return Double.parseDouble(rowContainer.$(By.xpath("td[6]/div/span")).text().replace(",", ".").replace(" ", ""));
	}
	public Currency priceCurrency() {
		return Currency.find(rowContainer.$(By.xpath("td[6]/div/strong")).text());
	}
	@Step
	public Price price() {
		return new Price(priceAmmount(), priceCurrency());
	}
	@Step
	public Price altPrice() {
		String elmXPath = "td[6]/div/em";
		if (rowContainer.$(By.xpath(elmXPath)).isDisplayed()) {
			return Price.parse(rowContainer.$(By.xpath(elmXPath)).text());
		}
		return null;
	}	
	@Step
	public String boardBasis() {
		return rowContainer.$(By.xpath("td[5]")).text().trim();		
	}
	@Step
	public String roomCategory() {
		return rowContainer.$(By.xpath("td[4]")).text().trim();		
	}
	@Step
	public int roomsQuantity() {
		String elmXPath = "td[3]/div/span/b";
		return rowContainer.$(By.xpath(elmXPath)).isDisplayed() ? Integer.parseUnsignedInt(rowContainer.$(By.xpath(elmXPath)).text().trim()) : 1;		
	}
	@Step
	public int adultsPerRoom() {
		String elementClass = rowContainer.$(By.xpath("td[3]/div/img[1]")).attr("class");
		return Integer.parseUnsignedInt(""+elementClass.charAt(elementClass.length()-1));
	}
	@Step
	public boolean withChild() {
		return rowContainer.$(By.xpath("td[3]/div/img[2]")).isDisplayed();
	}
	
	
	
}
