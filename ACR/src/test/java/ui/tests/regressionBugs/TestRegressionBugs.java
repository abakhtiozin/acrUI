package test.java.ui.tests.regressionBugs;

import main.java.model.Journey;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.TF;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 16.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestRegressionBugs extends StepInitializer {

    @Test(testName = "RI-1405 - bug with passenger birthDate", enabled = false)
    public void checkIfPassengerBirthDateSaveValid() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger alexii = new PassengerDataGenerator().generateFor(TF).withBirthDate("10.04.1990");
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("PAR", "LON", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("easyJet"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(alexii, 1);
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
