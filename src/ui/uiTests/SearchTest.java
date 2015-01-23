package ui.uiTests;

import model.*;
import org.junit.Assert;


import org.testng.annotations.*;
import ui.pages.JourneySearchPage;
import ui.pages.LoginPage;
import ui.pages.OrdersPage;
import ui.pages.SearchResultPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.codeborne.selenide.Selenide.open;


/**
 * Created by AA on 11.01.2015.
 */
public class SearchTest {

    private static Reseller reseller;
    private static String baseUrl;
    private static JourneySearchPage journeySearchPage;

    @BeforeSuite
    public static void setReseller(){
        baseUrl = "http://dev.acr.local/";
        reseller = new Reseller("andrew-usd","andrew", "1234");
        LoginPage loginPage = open(baseUrl, LoginPage.class);
        journeySearchPage = loginPage.validLogin(reseller);
    }

    @BeforeTest
    public void setUp(){
        if (!journeySearchPage.onThisPage()){

        }
        journeySearchPage.pressResetButton();
    }

    @DataProvider(name="testData")
    public Object[][] createDataForTes() {
        return new String[][] {
                {"GOT","STO"},
                {"MAD","BSN"},
                {"GLA","LON"},
                {"ROM","MIL"},
                {"BSN","PAR"},
                {"LON","PAR"}
        };
    }


    @Test(testName = "CalcCurrency", dataProvider = "testData")
    public void userCanLoginByUsername(String from, String to) {

        List<Passenger> passengers = new ArrayList<Passenger>();
        Collections.addAll(passengers,
                new Passenger("12.12.1990"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("01.01.1963"),
                new Passenger("02.07.1962")
        );
        Journey journey = new Journey(from, to,"10.02.2015",0,24);
        SearchMode searchMode = new SearchMode().toInternationalSystem();

        SearchResultPage searchResultPage = journeySearchPage.search(
                journey,
                passengers,
                searchMode);
        List<Trip> trips = searchResultPage.getRailTrips();
        Assert.assertTrue("Ничего не найдено по направлению " + journey.getOriginLocation() + " - " + journey.getDestinationLocation(), trips.size()>0);


        for (Trip trip : trips){
            System.out.println(trip);
        }
    }


    @Test
    public void deletingPassengersTest(){
        List<Passenger> passengers = new ArrayList<Passenger>();
        Collections.addAll(passengers,
                new Passenger("12.12.1990"),
                new Passenger("01.01.1963"),
                new Passenger("02.07.1962"));
        journeySearchPage.addPassengers(passengers);
        journeySearchPage.deletePassenger(passengers.get(1));
        passengers.remove(1);
        Assert.assertTrue(journeySearchPage.compareFieldsToPassengers(passengers));
    }

//    @Test
//    public void invalidLogin(){
//        LoginPage loginPage = open(baseUrl, LoginPage.class);
//        loginPage.invalidLogin(new Reseller("andresd","rt5ew", "1jkuy4"));
//        Assert.assertTrue("Не на странице логина!", loginPage.onThisPage());
//    }

    @Test
    public void mainMenuTest(){
        OrdersPage ordersPage = journeySearchPage.openOrdersPage();
        journeySearchPage = ordersPage.openSearchPage();
        Assert.assertTrue("Не на странице поиска!", journeySearchPage.onThisPage());
    }
}
