package main.java.steps.reseller;

import main.java.model.Journey;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.JourneySearchPage;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtJourneySearchPageSteps extends AtInnerPageSteps {

    private JourneySearchPage journeySearchPage;
    private Journey journey;

    // --------------------------- CONSTRUCTORS ---------------------------


    @Step
    public void addPassengerToJourney(Passenger passenger) {
        journeySearchPage.addPassenger(passenger);
    }

    @Step
    public void fillInJourneyData(Journey journey) {
        this.journey = journey;
        journeySearchPage.addJourney(journey);
    }

    @Step
    public void searchJourney() {
        journeySearchPage.clickOnSearchButton();
        applicationStorage.setJourney(this.journey);
        stepManager.setUpResellerAtSearchResultPage();
    }

    @Step
    public void clearLastSearchData() {
        this.shouldBeOnThisPage();
        journeySearchPage.pressResetButton();
    }

    @Override
    public void shouldBeOnThisPage() {
        Assert.assertTrue(journeySearchPage.onThisPage(), "Not on journey search page");
    }

    @Override
    public void setUpResellerAtPage() {
        journeySearchPage = (JourneySearchPage) applicationStorage.getCurrentPage();
    }
}
