package test.java.ui;

import main.java.model.City;
import main.java.model.Reseller;
import main.java.model.searchParameters.*;
import main.java.ui.pages.HomePage;
import main.java.ui.pages.SearchPage;
import main.java.ui.pages.SearchResultsPage;

import org.testng.annotations.*;

import test.java.base.TestBase;
import static util.other.RandomDataGenerator.*;


public class SearchTest extends TestBase {
    
	private SearchPage searchPage;
	private SearchResultsPage resultsPage;
	
		
	@BeforeClass
	public void doLogin() {
		Reseller reseller2 = new Reseller("AutoRes1", "AutoRes1", "AutoRes1");
        HomePage homePage = loginPage.forceLogin(reseller2, true);
        searchPage = homePage.navigate().toSearchPage();
	}
	
	@AfterClass
	public void doLogout() {
		searchPage.logout();
	}
	
	
	@DataProvider(name = "positiveSearchParameters")
	public Object[][] getPositiveSearchParameters() {
		int startDays	= 40;
		int finishDays	= 60;
		int maxNights	= 7;
        HotelLocation[] locations = {
        	new HotelLocation("WAW"), 
			//new HotelLocation(City.LUXEMBOURG), 
			new HotelLocation(City.PARIS),
			new HotelLocation(City.LONDON),
			new HotelLocation("NYC"),
			//new HotelLocation(City.LVIV),
			new HotelLocation(City.BERLIN),
			//new HotelLocation(City.CHISINAU)										
		};
        Object[][] positiveSearchParameters = new Object[locations.length][];
                
        for (int i = 0; i < locations.length; i++) {
			Object[] testDataRow = {locations[i],
									new Period(getRndCalendarDate(startDays, finishDays), randomNumber(1, maxNights)), 
									new RoomsOptions(randomNumber(1, 3), randomNumber(1, 4), randomNumber(0, 12)),
									true};
			positiveSearchParameters[i] = testDataRow;			
		}
		
        return positiveSearchParameters;
		
		/* Static data for test
		return new Object[][] {
				{new HotelLocation("WAW"),				new Period("2015-04-22", "2015-03-24"), new RoomsOptions(1, 2, 0),	true },
				//{new HotelLocation(City.WARSAW_AIRPORT),	new Period("2015-04-10", "2015-04-11"),	new RoomsOptions(2, 2, 7),	true },
				{new HotelLocation(City.LUXEMBOURG),	new Period("2015-04-10", "2015-04-11"),	new RoomsOptions(2, 2, 7),	true },
				{new HotelLocation(City.PARIS),			new Period("2015-04-15", "2015-04-20"),	new RoomsOptions(1, 2, 0),	true },
				{new HotelLocation(City.LONDON),		new Period("2015-03-01", "2015-03-11"), new RoomsOptions(1, 1, 0),	true },
				{new HotelLocation("NYC"),				new Period("2015-04-10", 7), 			new RoomsOptions(3, 2, 9),	true },
				{new HotelLocation(City.LVIV),			new Period("2015-03-01", "2015-03-11"), new RoomsOptions(1, 1, 0),	true },
				{new HotelLocation(City.BERLIN),		new Period("2015-04-01", "2015-03-11"), new RoomsOptions(1, 1, 0),	true },
				{new HotelLocation(City.CHISINAU),		new Period("2015-04-10", "2015-04-11"),	new RoomsOptions(2, 2, 7),	true },
			};
		*/		
	}
	
	
	
	@Test(dataProvider = "positiveSearchParameters")
    public void searchDifferrentlocationsTest(HotelLocation location, Period period, RoomsOptions options, boolean expected) {
		//if (testMethodIteration > 0) resultsPage.changeSearchCriteria();
		
		searchPage.searchForm().reset()
							   .setSearchParameters(location, period, options);
		
		try {	Thread.sleep(2000);	} catch (InterruptedException e) {}
		
		resultsPage = searchPage.searchForm().search();
		
		SearchResultsPage.waitForOffers(90000);
		boolean offersFound = resultsPage.isOffersFound();
		
    	assertEquals(offersFound, expected, "Hotel offers weren't found for such serach parameters: (" + location.toString() +", " + period.toString() + ", " + options.toString() + "); ");
    	
    }
	
	
	
	
    
}
