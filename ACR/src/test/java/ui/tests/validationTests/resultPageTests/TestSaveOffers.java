package test.java.ui.tests.validationTests.resultPageTests;

import main.java.model.Journey;
import main.java.model.Reseller;
import main.java.model.passenger.Passenger;
import main.java.utils.DateTimeHelper;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;
import util.other.RandomDataGenerator;

import static main.java.utils.DateTimeHelper.currentDatePlusWeekAndDayOfTheWeek;

/**
 * Created by Andrii.Bakhtiozin on 30.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestSaveOffers extends StepInitializer {
    @Test
    public void severalSuppliersDownloadOffersPdf() {
        atLoginPageSteps.loginAs(new Reseller("autotest", "all_b2b", "1234"));
        atJourneySearchPageSteps.addPassengerToJourney(new Passenger().withBirthDate(DateTimeHelper.currentDateMinusNYears(RandomDataGenerator.randomNumber(22, 35))));
        atJourneySearchPageSteps.fillInJourneyData(new Journey("PAR", "WAW", currentDatePlusWeekAndDayOfTheWeek(3, 5)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.downloadRoutes();
        atSearchResultPageSteps.shouldNotSeeErrorAlert();
    }
}
