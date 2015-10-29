package main.java.ui.webElements.searchResultsPage;


import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.*;

import main.java.model.restriction.Limit;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;




public class HotelOffer implements Limit {
	@SuppressWarnings("unused")
	private String hotelContainerId;
	private SelenideElement hotelContainer;	
	
	private SelenideElement anchorTrigOffers() {
		return hotelContainer.$(".trigger-offers");
	}
	private SelenideElement anchorHotelName() {
		return hotelContainer.$(By.xpath(".//a[@class='hotel_title']"));
	}
	
	public HotelOffer(SelenideElement mainContainer) {
		this.hotelContainer = mainContainer;
		hotelContainerId = mainContainer.getAttribute("id");
	}
	public HotelOffer(String id) {
		hotelContainerId = id;
		hotelContainer = $(By.id(id));
	}
	
	@Step
	public HotelOffer showRoomOffers() {
		SelenideElement anchor = anchorTrigOffers();
		if (anchor.text().toUpperCase().indexOf("SHOW ALL OFFERS") >= 0) {
			anchor.click();
		}
		hotelContainer.$(".hotel-offers").$(By.tagName("table")).waitUntil(Condition.appear, ELM_VISIBILITY_MAX_WAIT_TIME);
		return this;
	}
	
	@Step
	public HotelOffer hideRoomOffers() {
		SelenideElement anchor = anchorTrigOffers();
		if (anchor.text().toUpperCase().indexOf("HIDE OFFERS") >= 0) {
			anchor.click();
		}
		hotelContainer.$(".hotel-offers").waitWhile(Condition.visible, ELM_VISIBILITY_MAX_WAIT_TIME);
		return this;
	}
	
	@Step
	public List<RoomOffer> roomOffers() {
		List<RoomOffer> roomOffers  = new ArrayList<RoomOffer>();
		showRoomOffers();		
		
		List<SelenideElement> offerTableRows = hotelContainer.$(".hotel-offers").$$(By.xpath("table/tbody/tr")); 
		for (SelenideElement offerRow : offerTableRows) {
			roomOffers.add(new RoomOffer(offerRow));
		}		
		return roomOffers;
	}
	
	@Step
	public List<RoomOffer> roomOffers(String availability) {
		List<RoomOffer> roomOffers  = new ArrayList<RoomOffer>();
		for (RoomOffer room : roomOffers()) {
			if (room.availability().equals(availability)) {
				roomOffers.add(room);
			}
		}				
		return roomOffers;
	}
	
	@Step
	public List<RoomOffer> roomOffers(String availability, float price ) {
		List<RoomOffer> roomOffers  = new ArrayList<RoomOffer>();
		for (RoomOffer room : roomOffers()) {
			if (room.availability().equals(availability) && room.priceAmmount() == price) {
				roomOffers.add(room);
			}
		}				
		return roomOffers;
	}
	
	
	/** <b>Please, do not use this method! It will fail as there is no appropriate DIV on the page.</b> */
	@Deprecated
	@Step
	public int roomOffersDispAmount() {
		SelenideElement p = anchorTrigOffers().$(By.xpath("./parent::p"));
		String pText = p.text();
		String amt = pText.substring(pText.lastIndexOf("(") + 1, pText.lastIndexOf(")"));
		return Integer.parseUnsignedInt(amt);
	}
	
	@Step
	public String hotelName() {
		return anchorHotelName().text();
	}
	
	@Step
	public int hotelStars() {
		return anchorHotelName().$$(By.xpath("./following-sibling::span/img")).size();
	}
	

	@Step
	public static HotelOffer find(String hotelName) {
		SelenideElement name = $(By.xpath("//a[@class='hotel_title' and contains(text(), '" + hotelName + "')]"));		
		return new HotelOffer(name.$(By.xpath("ancestor::div[@class='hotel_details_container']/parent::div")));
	}
	
	
}
