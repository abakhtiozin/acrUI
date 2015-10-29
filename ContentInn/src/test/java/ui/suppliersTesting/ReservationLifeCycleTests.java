package test.java.ui.suppliersTesting;

import static util.other.RandomDataGenerator.getRndCalendarDate;
import static util.other.RandomDataGenerator.randomNumber;

import java.util.Date;
import java.util.List;

import main.java.Manager;
import main.java.model.City;
import main.java.model.Language;
import main.java.model.Reseller;
import main.java.model.pricing.Earning;
import main.java.model.pricing.EarningChain;
import main.java.model.reservation.ReservationRecord;
import main.java.model.reservation.ReservationStatus;
import main.java.model.restriction.Limit;
import main.java.model.searchParameters.HotelLocation;
import main.java.model.searchParameters.Period;
import main.java.model.searchParameters.RoomsOptions;
import main.java.ui.pages.BookingPage;
import main.java.ui.pages.CommonPage;
import main.java.ui.pages.HomePage;
import main.java.ui.pages.ReservationsPage;
import main.java.ui.pages.SearchResultsPage;
import main.java.ui.webElements.ReservationsPage.Reservation;
import main.java.ui.webElements.searchResultsPage.HotelOffer;
import main.java.ui.webElements.searchResultsPage.RoomOffer;
import main.java.util.DateUtils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import test.java.base.TestBase;


public class ReservationLifeCycleTests extends TestBase implements Limit {
	protected HomePage		homePage;
	protected HotelLocation location;
	protected Period		period;
	protected RoomsOptions	options;
	
	@Parameters({"resellerCode", "resellerUsername", "resellerPassword", "language"})
	@BeforeSuite
	public void doValidLogin(String resellerCode, String resellerUsername, String resellerPassword, @Optional("English") Language language) {
		reseller = new Reseller(resellerCode, resellerUsername, resellerPassword, language);
		homePage = loginPage.forceLogin(reseller, false);
	}
	
	@AfterSuite
	public void doLogout() {
		((CommonPage) Manager.getInstance().getCurrentPage()).logout();
	}
	
	
	@Parameters({"city", "rangeCheckIn", "rangeNightsCount", "rangeRoomsNumber", "rangeAdultsNumber", "rangeChildAge" })
	@BeforeClass
	public void setUpSearchCriteria(
			City city,
			@Optional("40:90") String rangeCheckIn,
			@Optional("1:1") String rangeNightsCount,
			@Optional("1:1") String rangeRoomsNumber,
			@Optional("1:1") String rangeAdultsNumber,
			@Optional("0:0") String rangeChildAge)
	{
		String divider = ":";
		this.location = new HotelLocation(city);
		
		String[] checkIn = rangeCheckIn.split(divider);
		String[] nightsCount = rangeNightsCount.split(divider);
		this.period = new Period(getRndCalendarDate(Integer.parseInt(checkIn[MIN]), Integer.parseInt(checkIn[MAX])), 
								 randomNumber(Integer.parseInt(nightsCount[MIN]), Integer.parseInt(nightsCount[MAX])));
		
		String[] roomsNumber = rangeRoomsNumber.split(divider);
		String[] adultsNumber = rangeAdultsNumber.split(divider);
		String[] childAge = rangeChildAge.split(divider);
		this.options = new RoomsOptions(randomNumber(Integer.parseInt(roomsNumber[MIN]), Integer.parseInt(roomsNumber[MAX])),
										randomNumber(Integer.parseInt(adultsNumber[MIN]), Integer.parseInt(adultsNumber[MAX])),
										randomNumber(Integer.parseInt(childAge[MIN]), Integer.parseInt(childAge[MAX])));				
	}
	
		
	
	private RoomOffer getRandomRoomOfer(SearchResultsPage searchResults) {
		assertTrue(searchResults.onThisPage(), "Could not execute getRandomRoomOfer: Search results page should be opened; ");
		
		/* Get random hotel */
		List<HotelOffer> hotelOffers = searchResults.hotelOffers();
		int hotelOffersCount = hotelOffers.size();
		HotelOffer hotelOffer = hotelOffers.get(randomNumber(0, hotelOffersCount));
		
		/* Get random room */
		int roomOffersCount = hotelOffer.roomOffers().size();
		RoomOffer selectedRoom = hotelOffer.roomOffers().get(randomNumber(0, roomOffersCount));
		
		return selectedRoom;		
	}	
	
	
	
	
	@Features({ "Search for Hotel offers" })
	@Test(
		priority = 1,
		enabled = true
	)
	public void TestSearch() {
		reseller.clearOwnSearchCache(period);
				
		CommonPage page = (CommonPage) Manager.getInstance().getCurrentPage();
		SearchResultsPage results = page.getSearchForm().reset()
	  													.setSearchParameters(location, period, options)
	  													.search();
		
		assertTrue(results.isOffersFound(), "Offers are not found for such search criteria");		
	}
	
	@Features({ "Search for Hotel offers", "Search results" })
	@Test(
		dependsOnMethods = {"TestSearch"},
		enabled = true
	)
	public void TestThatRoomsOptionsAsInSearch() {
		SearchResultsPage searchResults = ((SearchResultsPage) Manager.getInstance().getCurrentPage());
		assertTrue(searchResults.onThisPage(), "Search results page should be opened; ");
		
		/* Get random room */
		RoomOffer selectedRoom = getRandomRoomOfer(searchResults);
				
		assertEquals(selectedRoom.roomsQuantity(), options.roomsNumber(), "Room amount isn't equal betwees search request and the results; "); 
		assertEquals(selectedRoom.adultsPerRoom(), options.adultsNumber(), "Adults number isn't equal betwen search request and the results; ");
		assertEquals(selectedRoom.withChild(), options.childAge() > 0, "Child is present in the search results but shouldn't be; ");
		
	}
	
	@Features({ "Search for Hotel offers", "Search results" })
	@Test(
		dependsOnMethods = {"TestSearch"},
		enabled = true
	)
	public void TestBoardBasisPresence() {
		SearchResultsPage searchResults = ((SearchResultsPage) Manager.getInstance().getCurrentPage());
		assertTrue(searchResults.onThisPage(), "Search results page should be opened; ");
		
		/* Get random room */
		RoomOffer selectedRoom = getRandomRoomOfer(searchResults);
				
		assertTrue(selectedRoom.boardBasis().length() > 0, "The Board Basis description isn't present for this room offer; ");		
	}
	
	
	@Features({ "Search for Hotel offers", "Search results", "Prices calculation" })
	@Test(
		dependsOnMethods = {"TestSearch"},
		enabled = false
	)
	public void TestSearchPrice() {
		SearchResultsPage searchResults = ((SearchResultsPage) Manager.getInstance().getCurrentPage());
		assertTrue(searchResults.onThisPage(), "Search results page should be opened; ");
		
		/* Get random room */
		RoomOffer selectedRoom = getRandomRoomOfer(searchResults);
		
		
		Earning searchPrice = new Earning().setBasePrice(selectedRoom.originalPrice());
		EarningChain chain = EarningChain.defineChain(reseller, location, selectedRoom);
		
		searchPrice.applyChain(chain);	
		
	}
	
	
	@Features({ "Booking offers" })
	@Test (
		dependsOnMethods = {"TestSearch", "TestThatRoomsOptionsAsInSearch", "TestBoardBasisPresence"}, 
		alwaysRun = true,
		enabled = true
	)
	public void TestRoomOfferBooking() {
		SearchResultsPage searchResults = ((SearchResultsPage) Manager.getInstance().getCurrentPage());
		assertTrue(searchResults.onThisPage(), "Search results page should be opened; ");
		RoomOffer selectedRoom = getRandomRoomOfer(searchResults);
		
		/* Booking */
		BookingPage bookingPage = selectedRoom.book();
		bookingPage.setTravelers(generateRndTravelers(options.roomsNumber(), options.adultsNumber(), options.childAge()));
		bookingPage.clickTermsAndConditions();
		assertTrue(bookingPage.clickFinishBooking(), Log.compose(Log.ERROR, "Booking was completed unsuccessfully"));
		
		/* Check that the reservation has a confirmation number */
		long reservationId = reseller.getLatestReservationId();
		assertNotEquals(ReservationRecord.getConfirmationNumber(reservationId), "UNDEFINED", Log.compose(Log.ERROR, "The reservation " + reservationId + " has UNDEFINED confirmation number"));
		
		
		
	}
	
	@Features({ "Reservation manipulation", "Voucher generation" })
	@Test(
		dependsOnMethods = {"TestRoomOfferBooking"},
		enabled = true
	)
	public void TestVoucherGeneration() {
		ReservationsPage reservationsPage = ((CommonPage) Manager.getInstance().getCurrentPage()).navigate().toReservationsPage();
		assertTrue(reservationsPage.onThisPage(), "Reservations Page shuld be opened; ");
		
		Reservation reservation = reservationsPage.reservation(reseller.getLatestReservationId());
		if (reservation.getStatus().equals(ReservationStatus.OK)) {		// Voucher can be generated for the reservations with OK status only
			assertTrue(reservation.getVoucher(), "Reservation has 'OK'-status but voucher wasn't generated; ");
			Date voucherDate = reservation.getVoucherDate();
			assertTrue(DateUtils.compareDate(voucherDate, new Date(), 2 * MINUTE), "Wrong voucher generation date (" + DateUtils.getHumanReadebleDate(voucherDate) + "): the generation date should be near '" + DateUtils.getHumanReadebleDate(new Date()) + "'");
		}
		else {
			assertFalse(reservation.getVoucher(), "Voucher generation should be impossible for non OK reservations; ");			
		}
		
	}
	
	@Features({ "Reservation manipulation", "Cancellation of reservation" })
	@Test(
		dependsOnMethods={"TestRoomOfferBooking", "TestVoucherGeneration"}, 
		enabled = true
	) 
	public void TestReservationCancellation() {
		ReservationsPage reservationsPage = ((CommonPage) Manager.getInstance().getCurrentPage()).navigate().toReservationsPage();
		assertTrue(reservationsPage.onThisPage(), "Reservations Page shuld be opened; ");
		
		Reservation reservation = reservationsPage.reservation(reseller.getLatestReservationId());
		if (reservation.getStatus().equals(ReservationStatus.OK) && reservation.getCheckInDate().getTime() > new Date().getTime()) {
			assertTrue(reservation.cancelBooking(), "Error occured during cancellation of the reservation #" + reservation.getId() + ". Reservation status after cancellation is: " + reservation.getStatus());
		}
		else {
			assertFalse(reservation.cancelBooking(), "Cancellation should be impossible for non OK reservation or reservations with CheckIn date passed via B2B interface, but reservation #" + reservation.getId() + "was cancelled; ");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
}
