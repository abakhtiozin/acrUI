package ui.uiTests;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    private Reseller reseller;
    private String baseUrl = "http://dev.acr.local/";

    @Before
    public void setReseller(){
        reseller = new Reseller("andrew-usd","andrew", "1234");
    }

    @Test
    public void userCanLoginByUsername() {

        LoginPage loginPage = open(baseUrl, LoginPage.class);
        List<Passenger> passengers = new ArrayList<Passenger>();
        Collections.addAll(passengers,
                new Passenger("12.12.1990"),
                new Passenger("01.01.1963"),
                new Passenger("02.07.1962"));

        Journey journey = new Journey("MOW","LED","22.01.2015","7","17");
        SearchMode searchMode = new SearchMode().toInternationalSystem();
        JourneySearchPage journeySearchPage = loginPage.validLogin(reseller);
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
    public void invalidLogin(){
        LoginPage loginPage = open(baseUrl, LoginPage.class);
        loginPage.invalidLogin(new Reseller("andresd","rt5ew", "1jkuy4"));
        Assert.assertTrue("Не на странице логина!", loginPage.onThisPage());
    }

    @Test
    public void mainMenuTest(){
        LoginPage loginPage = open(baseUrl, LoginPage.class);
        JourneySearchPage journeySearchPage = loginPage.validLogin(reseller);
        OrdersPage ordersPage = journeySearchPage.openOrdersPage();
        journeySearchPage = ordersPage.openSearchPage();
        Assert.assertTrue("Не на странице поиска!", journeySearchPage.onThisPage());
    }
}
