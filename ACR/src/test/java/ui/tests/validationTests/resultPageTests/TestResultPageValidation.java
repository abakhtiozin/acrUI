package test.java.ui.tests.validationTests.resultPageTests;

import main.java.model.Journey;
import main.java.model.passenger.Passenger;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.*;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 17.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestResultPageValidation extends StepInitializer {

    @Test(testName = "checkAllTripsOnResultPageUfs")
    public void checkAllTripsOnResultPageUfs() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(UFS));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.checkAllTrips();
    }

    @Test(testName = "checkAllTripsOnResultPageTf")
    public void checkAllTripsOnResultPageTf() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.addPassengerToJourney(new Passenger("12.01.1980"));
        atJourneySearchPageSteps.fillInJourneyData(new Journey("OSL", "STO", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.checkAllTrips();
    }

    @Test
    public void checkAllTripsOnResultPageAcp() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.addPassengerToJourney(new Passenger("12.01.1980"));
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MIL", "ROM", currentDatePlusNDays(20)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.checkAllTrips();
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
