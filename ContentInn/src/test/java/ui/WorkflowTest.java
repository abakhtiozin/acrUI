package test.java.ui;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import main.java.Manager;
import main.java.model.*;
import main.java.model.reservation.ReservationRecord;
import main.java.model.restriction.Limit;
import main.java.model.searchParameters.*;
import main.java.ui.pages.*;
import main.java.ui.webElements.searchResultsPage.HotelOffer;
import main.java.ui.webElements.searchResultsPage.RoomOffer;

import org.testng.annotations.*;

import test.java.base.TestBase;
import static util.other.RandomDataGenerator.*;



public class WorkflowTest extends TestBase implements Limit {
	
	@BeforeClass
	public void doLogin() {
		reseller = new Reseller("AutoRes1", "AutoRes1", "AutoRes1", Language.English);
		
        loginPage.forceLogin(reseller, true);
        //searchPage = homePage.navigate().toSearchPage();        
	}
	
	@AfterClass
	public void doLogout() {
		new CommonPage() {}.logout();
	}
		
	
	@DataProvider(name = "dataSingleSupllierBooking")
	public Object[][] getDataSingleSupllierBooking(Method m) {
		
		Object[][] suppliersLocations = {
				
				//{Supplier.Acase,			City.KYIV},
				//{Supplier.Acase,			City.MOSCOW},

				{Supplier.ContentHellas,	City.PARIS},
				//{Supplier.ContentHellas,	City.WARSAW},

				{Supplier.ContentLink,		City.PARIS},
				//{Supplier.ContentLink,		City.LONDON},
				
				{Supplier.Dotw,				City.LONDON},
				//{Supplier.Dotw,				City.WARSAW},
				
				{Supplier.GoGlobal,			City.NEW_YORK},
				//{Supplier.GoGlobal,			City.LONDON},
				
				{Supplier.HotelBeds,		City.PARIS},
				
				{Supplier.JacTravel,		City.LONDON},
				//{Supplier.JacTravel,		City.ROMA},
				
				{Supplier.Tourico,			City.NEW_YORK},
				//{Supplier.Tourico,			City.PARIS},
				
				{Supplier.Travco,			City.LONDON}
				
		};
				
		
		final int TEST_CASES_AMOUNT = 4;
		int rowIndex = 0;
		Object[][] dataSnglSupplBook = new Object[suppliersLocations.length * TEST_CASES_AMOUNT][4];
		
		for (int sl = 0; sl < suppliersLocations.length; sl++) {
			Supplier supplier2 = (Supplier)suppliersLocations[sl][0];
			HotelLocation location = new HotelLocation((City)suppliersLocations[sl][1]);
			Object[][] searchOptions = {
					/* Nearest dates(booking in penalty), one room one traveler no children */
					{new Period(Calendar.getInstance(), randomNumber(1, 3)),		new RoomsOptions(1, 1, 0)},
					
					/* Nearest dates(booking in penalty) */
					{new Period(Calendar.getInstance(), randomNumber(1, 7)),
						new RoomsOptions(randomNumber(1, 3),																				//Rooms number
								randomNumber(supplier2 == Supplier.ContentHellas ? 2 : 1, supplier2 == Supplier.ContentHellas ? 2 : 4),		//Adults number
								randomNumber(supplier2 == Supplier.ContentHellas ? 2 : 1, 10))												//Child age
					},
					
					/* Future dates, one room several travelers */
					//{new Period(getRndCalendarDate(40, 80), randomNumber(1, 5)),	new RoomsOptions(1, randomNumber(1, 4), randomNumber(0, 10)),},
					
					/* Future dates, several rooms several travelers */
					{new Period(getRndCalendarDate(40, 80), randomNumber(1, 5)),
						new RoomsOptions(randomNumber(1, 3),																				//Rooms number
								randomNumber(supplier2 == Supplier.ContentHellas ? 2 : 1, supplier2 == Supplier.ContentHellas ? 2 : 4), 	//Adults number
								randomNumber(supplier2 == Supplier.ContentHellas ? 2 : 1, 10))												//Child age
					},
				
					/* Future dates, several rooms several travelers no children */
					{new Period(getRndCalendarDate(40, 80), randomNumber(1, 5)),
						new RoomsOptions(randomNumber(1, 3), 
								randomNumber(1, 4), 
								0)},

					
				};
			for (int i = 0; i < searchOptions.length; i++) {
				Period period 			= (Period)searchOptions[i][0];
				RoomsOptions roomsOpts	= (RoomsOptions)searchOptions[i][1];
				Object[] testDataRow	= {supplier2, location, period, roomsOpts};
				dataSnglSupplBook[rowIndex] = testDataRow;
				rowIndex++;
			}
			
		}
		
		
		return dataSnglSupplBook;
	}
	
	
	@Test(enabled = true, dataProvider = "dataSingleSupllierBooking")
	public void bookWithOneSupplierEnabledTest(Supplier supplier, HotelLocation location, Period period, RoomsOptions options) {
		
		reseller.setUnblockedSuppliers(supplier);			// Enable only one supplier
		reseller.clearOwnSearchCache(period);				// Clear search cache
		
		CommonPage page = (CommonPage) Manager.getInstance().getCurrentPage();
		SearchResultsPage results = page.getSearchForm().reset()
											  			.setSearchParameters(location, period, options)
											  			.search();
		SearchResultsPage.waitForOffers(SEARCH_MAX_WAIT_TIME, true);
		assertTrue(results.isOffersFound(), Log.compose(Log.ERROR, "Offers are not found for such search criteria"));
		
		List<HotelOffer> hotelOffers = results.hotelOffers();
		int hotelOffersCount = hotelOffers.size();
		HotelOffer hotelOffer = hotelOffers.get(randomNumber(0, hotelOffersCount));
		int roomOffersCount = hotelOffer.roomOffers().size();
		RoomOffer selectedRoom = hotelOffer.roomOffers().get(randomNumber(0, roomOffersCount));
		
		BookingPage bookingPage = selectedRoom.book();
		bookingPage.setTravelers(generateRndTravelers(options.roomsNumber(), options.adultsNumber(), options.childAge()));
		bookingPage.clickTermsAndConditions();
		assertTrue(bookingPage.clickFinishBooking(), Log.compose(Log.ERROR, "Booking was completed unsuccessfully"));
		
		long reservationId = reseller.getLatestReservationId();
		assertNotEquals(ReservationRecord.getConfirmationNumber(reservationId), "UNDEFINED", Log.compose(Log.ERROR, "The reservation " + reservationId + " has UNDEFINED confirmation number"));
	}
	
	    
    
}
