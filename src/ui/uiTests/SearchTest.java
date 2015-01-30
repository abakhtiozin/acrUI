package ui.uiTests;

import com.codeborne.selenide.Condition;
import model.*;
import org.junit.Assert;


import org.openqa.selenium.By;
import org.testng.annotations.*;
import ui.pages.*;
import ui.pages.BookFormPage.BookFormPage;
import ui.pages.BookFormPage.BookFormPageUFSBuilder;
import ui.pages.BookFormPage.GetBookFormPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
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

    @Test
    public void userCanLoginByUsername() {

        List<Passenger> passengers = new ArrayList<Passenger>();
        Passenger mihail = new Passenger("12.12.1990").withSurname("rakotaKrab");
        Collections.addAll(passengers, mihail);
        Journey journey = new Journey("MOW", "LED","10.02.2015",0,24);
        SearchMode searchMode = new SearchMode().toRussianSystem();
        SearchResultPage searchResultPage = journeySearchPage.search(
                journey,
                passengers,
                searchMode);
        Trip desireTrip = new Trip().withTrainNumber("020У")
                .withTransporterName("РЖД")
                .withCarriageType("Купейный")
                .withTariffType("1У");
        BookFormPage bookFormPage = searchResultPage.chooseTripByDesireTripOptions(desireTrip, Supplier.UFS);
        bookFormPage.addPassenger(mihail);

        $(By.xpath(".//*[@id='book_request_passengers_17_surname']")).waitUntil(Condition.appear,10000);
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
