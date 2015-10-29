package test.java.ui.tests.workflow;

import main.java.model.*;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerAge;
import main.java.model.passenger.PassengerTariffType;
import main.java.utils.PassengerDataGenerator;
import org.joda.time.DateTime;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;
import util.other.RandomDataGenerator;

import static main.java.model.Supplier.ACP;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 15.10.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestHexWorkFlow extends StepInitializer {
    private CreditCardInfo generateCreditCardData() {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        creditCardInfo.setCardNumber("5453010000070151");
        creditCardInfo.setAddressLine(RandomDataGenerator.getRandomLiteralsString());
        creditCardInfo.setCardType("MasterCard");
        creditCardInfo.setCountry("UA");
        creditCardInfo.setCVV("000");
        creditCardInfo.setExpiryMonth(RandomDataGenerator.randomNumber(1, 12).toString());
        creditCardInfo.setExpiryYear(new DateTime().getYear() + 2 + "");
        creditCardInfo.setPostCode(RandomDataGenerator.getRandomNumericString(5));
        return creditCardInfo;
    }

    @Test
    public void hexBooking() {
        atLoginPageSteps.loginAs(new Reseller("and-dr-gb", "andrew", "1234"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Лондон Паддингтон", "Аэропорт Хитроу (Все станции)", currentDatePlusNDays(15)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("HEX"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerEmail("andrii.bakhtiozin@viaamadeus.com");
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.addCreditCardInfo(generateCreditCardData());
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        systemSteps.checkBalance();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.sendCouponsOnEmail("andrii.bakhtiozin@viaamadeus.com");
    }

    @Test
    public void hexBookingWithChild() {
        atLoginPageSteps.loginAs(new Reseller("and-dr-gb", "andrew", "1234"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate(PassengerAge.JUNIOR).withTariffType(PassengerTariffType.CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Лондон Паддингтон", "Аэропорт Хитроу (Все станции)", currentDatePlusNDays(15)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("HEX"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerEmail("andrii.bakhtiozin@viaamadeus.com");
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.addCreditCardInfo(generateCreditCardData());
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnBookingDetails();
        systemSteps.checkBalance();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.downloadCoupon();
    }
}
