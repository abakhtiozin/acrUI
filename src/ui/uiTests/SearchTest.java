package ui.uiTests;

import model.Journey;
import model.Passenger;
import model.Reseller;
import model.SearchMode;
import org.junit.BeforeClass;
import org.junit.Test;
import ui.pages.JourneySearchPage;
import ui.pages.LoginPage;
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

    @BeforeClass
    public void setReseller(){
        reseller = new Reseller("Andrew-usd","andrew", "1234");
    }

    @Test
    public void userCanLoginByUsername() {
        LoginPage loginPage = open("http://contentrail.com", LoginPage.class);

        List<Passenger> passengers = new ArrayList<Passenger>();
        Collections.addAll(passengers, new Passenger("12.12.1990"), new Passenger("01.01.1963"), new Passenger("02.07.1962"));

        JourneySearchPage journeySearchPage = loginPage.validLogin(reseller);

        SearchResultPage searchResultPage = journeySearchPage.search(
                new Journey("Стокгольм","Гетеборг","30.01.2015","3","17"),
                passengers,
                new SearchMode().toInternationalSystem());
        //searchResultPage.getTrips -private
        //searchResultPage.get
//
    }
}
