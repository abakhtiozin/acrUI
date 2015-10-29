package main.java.ui.webElements;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import ru.yandex.qatools.allure.annotations.Step;
import test.java.base.TestHelper.Log;
import main.java.CustomConditions;
import main.java.model.pricing.Currency;
import main.java.model.pricing.ResellerCommission;
import main.java.model.restriction.Limit;
import main.java.model.searchParameters.HotelLocation;
import main.java.model.searchParameters.Period;
import main.java.model.searchParameters.RoomsOptions;
import main.java.ui.pages.SearchResultsPage;

import java.util.List;

import javax.naming.TimeLimitExceededException;



public class SearchForm extends ElementBase implements Limit {
	
	private static int roomsIndex = 0;
	
	private enum Locators {
		searchContainer	("search_container",			null,	null),
		fldCountry		("filters_country_value",		"filters_country_choises",		""),
		fldCountryIso	("filters_country_iso_value",	"filters_country_iso_choises",	""),
		fldCity			("filters_city_value",			"filters_city_choises",			""),
		fldIata			("filters_iata_value",			"filters_iata_choises",			""),
		fldCheckIn		("filters_check_in",			null,	""),
		fldNights		("filters_nights",				null,	""),
		fldCheckOut		("filters_check_out",			null,	""),
		fldRooms		("filters_rooms_%d_quantity",	null,	"1"),
		fldAdults		("filters_rooms_%d_adults",		null,	"1"),
		fldChildAge		("filters_rooms_%d_children_1",	null,	"0"),
		fldHotelName	("filters_hotel_name_value", 	"filters_hotel_name_choises",	null),
		fldHotelAddress	("filters_hotel_address_value",	"filters_hotel_address_choises",null),
		fldPriceFrom	("filters_price_from",			null,	null),
		fldPriceTo		("filters_price_to",			null,	null),
		fldPriceCurrency("filters_price_currency",		null,	null),
		fldStars		("filters_stars_%d",			null,	null),
		fldOwnCommission("filters_own_commission_id",	null,	null)
		;
		
		private final String id;
		private final String autoCpltrId;
		@SuppressWarnings("unused")
		private final String defaultValue;
		
		private Locators(String locatorId, String autocompleterId, String defaultVal) {
			this.id = locatorId;
			this.autoCpltrId = autocompleterId;
			this.defaultValue = defaultVal;
		}		
	}	
		
	private SelenideElement searchContainer()	{	return $(By.id(Locators.searchContainer.id));	}
	private SelenideElement fldCountry()		{	return $(By.id(Locators.fldCountry.id));		}
	private SelenideElement fldCountryIso()		{	return $(By.id(Locators.fldCountryIso.id));		}
	private SelenideElement fldCity()			{	return $(By.id(Locators.fldCity.id));			}
	private SelenideElement fldIata()			{	return $(By.id(Locators.fldIata.id));			}
	private SelenideElement fldCheckIn()		{	return $(By.id(Locators.fldCheckIn.id));		}
	private SelenideElement fldNights()			{	return $(By.id(Locators.fldNights.id));			}
	private SelenideElement fldCheckOut()		{	return $(By.id(Locators.fldCheckOut.id));		}
	private SelenideElement fldRooms(int index)		{	return $(By.id(String.format(Locators.fldRooms.id, index+1)));	}
	private SelenideElement fldAdults(int index)	{	return $(By.id(String.format(Locators.fldAdults.id, index+1)));	}
	private SelenideElement fldChildAge(int index)	{	return $(By.id(String.format(Locators.fldChildAge.id, index+1)));	}
	
	private SelenideElement fldStars(int index)	{	return $(By.id(String.format(Locators.fldStars.id, index+1)));	}
	private SelenideElement fldPriceCurrency()	{	return $(By.id(Locators.fldPriceCurrency.id));	}
	
	private SelenideElement fldOwnCommission()	{	return $(By.id(Locators.fldOwnCommission.id));	}
	
	private SelenideElement btnSearch()			{	return searchContainer().$(".search_button");	}
	private SelenideElement btnReset()			{	return searchContainer().$(By.xpath(".//input[@value='Reset']"));	}
	private SelenideElement btnAddRooms() 		{	return searchContainer().$(By.id("add_rooms_btn"));	}
	private SelenideElement btnDeleteRooms(int index)	{	return searchContainer().$(By.id(String.format("del_rooms_%d_btn", index+1)));	}
	
	private SelenideElement btnShowHideParams() {	return	searchContainer().$(By.xpath(".//span[contains(text(), 'Show parameters') or contains(text(), 'Hide parameters')]"));	}
	
	
	
	private void setCountry(String name) {
		setValue(fldCountry(), name);
		new Autocompleter(Locators.fldCountry.autoCpltrId).selectItem(name);
	}
	@SuppressWarnings("unused")
	private void setCountryIso(String iso) {
		setValue(fldCountryIso(), iso);
		new Autocompleter(Locators.fldCountryIso.autoCpltrId).selectItem(iso);
	}
	private void setCity(String name) {
		setValue(fldCity(), name);
		new Autocompleter(Locators.fldCity.autoCpltrId).selectItem(name);
	}
	@SuppressWarnings("unused")
	private void setIataCode(String iata) {
		setValue(fldIata(), iata);
		new Autocompleter(Locators.fldCity.autoCpltrId).selectItem(iata);
	}
	private void setCheckInDate(String date) {
		resetValue(fldCheckIn(), date);
	}
	@SuppressWarnings("unused")
	private void setNights(int nights) {
		resetValue(fldNights(), Integer.toString(nights));
	}
	private void setCheckOutDate(String date) {
		resetValue(fldCheckOut(), date);
	}
	private void togleParametersBlock(boolean toShow) {
		if (toShow) {
			if (btnShowHideParams().text().toLowerCase().indexOf("show parameters") >= 0) {
				btnShowHideParams().click();
				fldOwnCommission().waitUntil(Condition.appear, ELM_VISIBILITY_MAX_WAIT_TIME);
			}
		}
		else {
			if (btnShowHideParams().text().toLowerCase().indexOf("hide parameters") >= 0) {
				btnShowHideParams().click();
				fldOwnCommission().waitUntil(Condition.appear, ELM_VISIBILITY_MAX_WAIT_TIME);
			}
		}
	}
	
	
	@Step
	public int addRooms() {
		btnAddRooms().click();
		roomsIndex++;
		searchContainer().$(By.id("rooms_" + (roomsIndex+1) + "_container")).waitUntil(Condition.appear, 2 * SECOND);
		return roomsIndex;
	}	
	
	@Step
	public SearchForm deleteRooms(int index) {
		btnDeleteRooms(index).click();
		return this;
	}
	
	@Step
	public SearchForm setRoomsOptions(RoomsOptions rooms, int index)	{		
		fldRooms(index).selectOptionByValue(Integer.toString(rooms.roomsNumber())); 
		fldAdults(index).selectOptionByValue(Integer.toString(rooms.adultsNumber()));
		fldChildAge(index).selectOptionByValue(Integer.toString(rooms.childAge()));		
		return this;
	}
	
	@Step
	public SearchForm setFilterStars(int... stars) {
		int[] HOTEL_STARS_RANGE = {1, 5};
		
		/* Unselect all checkboxes before new setup */	
		for (int s = HOTEL_STARS_RANGE[MIN]; s <= HOTEL_STARS_RANGE[MAX]; s++) {
			setCheckbox(fldStars(s-1), false);				
		}
		/* New setup */
		for (int s : stars) {
			if (s >= HOTEL_STARS_RANGE[MIN] && s <= HOTEL_STARS_RANGE[MAX] ) {
				setCheckbox(fldStars(s-1), true);
			}
			else {
				Log.warning("setFilterStars: Passed Star Rate (" + s + ") is not in the range [" + HOTEL_STARS_RANGE[MIN] + "..." + HOTEL_STARS_RANGE[MAX] + "]. Operation is skipped; ");
			}
		}		
		return this;
	}
	
	@Step
	public SearchForm setFilterShowIn(Currency currency) {
		if (currency.name() != fldPriceCurrency().getSelectedValue()) {
    		fldPriceCurrency().selectOptionByValue(currency.name());
    	}		
		
		return this;
	}
	
	public SearchForm setResellerOwnCommission(ResellerCommission commission) {
		togleParametersBlock(true);
		if (null != commission)	
			fldOwnCommission().selectOption(commission.name());
		else
			fldOwnCommission().selectOptionByValue("0");				
		return this;
	}	
	public ResellerCommission getSelectedResellerOwnCommission() {
		long cid = Long.parseLong(fldOwnCommission().getSelectedValue());
		return cid == 0 ? null : ResellerCommission.findById(cid);
	}
	
	
	@Step
	public SearchForm setSearchParameters(HotelLocation location, Period period) {
				
		/* Hotel Location parameters setting */
		setCountry(location.countryName());
		setCity(location.cityName());
		
		/* Living period parameters setting. Used CheckIn and CheckOut */
		setCheckInDate(period.checkInDate());
		setCheckOutDate(period.checkOutDate());
		
		/* Other parameters leaved defaulted */
		
		return this;
	}
	
	@Step
	public SearchForm setSearchParameters(HotelLocation location, Period period, RoomsOptions rooms) {
		setSearchParameters(location, period);
		setRoomsOptions(rooms, 0);			
		return this;
	}
	
	@Step
	public SearchForm setSearchParameters(HotelLocation location, Period period, List<RoomsOptions> rooms) {
		setSearchParameters(location, period);
		for (int i = 0, s = rooms.size(); i < s; i++) {
			if (i > 0)	addRooms();				
			setRoomsOptions(rooms.get(i), i);			
		}			
		return this;
	}	
	
	@Step
	public SearchForm reset() {
		btnReset().click();
		try {
			CustomConditions.waitFor(CustomConditions.ajaxCompleted, AJAX_EXECUTION_MAX_TIME);
		} catch (TimeLimitExceededException e) {
			throw new IllegalStateException(Log.compose(Log.ERROR, "Couldn't reset the search form: " + e.getExplanation()));
		}
		return this;
	}
	@Step
	public SearchResultsPage search() {
		btnSearch().click();
		SearchResultsPage.waitForOffers(SEARCH_MAX_WAIT_TIME, true);
		return page(SearchResultsPage.class);
	}
	
	
	public static String containerId() {
		return Locators.searchContainer.id;
	}
	
		
}
