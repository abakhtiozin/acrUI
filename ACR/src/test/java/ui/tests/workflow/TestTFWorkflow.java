package test.java.ui.tests.workflow;

import main.java.model.BookingStatus;
import main.java.model.Journey;
import main.java.model.RefundStatus;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerAge;
import main.java.model.passenger.PassengerContactInfo;
import main.java.model.passenger.PassengerGender;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.TF;
import static main.java.utils.DateTimeHelper.currentDateMinusNYears;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 17.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestTFWorkflow extends StepInitializer {

    @Features("Booking")
    @Stories({"tf booking", "two segments", "Norwegian transporter"})
    @Test()
    public void tfBookingNorwegianTest() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
//                atLoginPageSteps.loginAs(new Reseller("and-dr-gb", "andrew", "1234"));

        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("OSL", "STO", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("Norwegian").withCarriageType("FULLFLEX"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail.withBirthDate(PassengerAge.ADULT), 1);
        atBookFormPageSteps.addPassengerInfo(alexii.withBirthDate(PassengerAge.KID), 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnCheckBookingStatusButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"tf booking", "two segments", "Norwegian transporter"})
    @Test()
    public void tfBookingNorwegianWithChangesTest() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF);
        Passenger elena = new PassengerDataGenerator().generateFor(TF);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.addPassengerToJourney(elena);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MAD", "OSL", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("Norwegian Air International, Norwegian"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail.withBirthDate(PassengerAge.ADULT), 1);
        atBookFormPageSteps.addPassengerInfo(alexii.withBirthDate(PassengerAge.JUNIOR), 2);
        atBookFormPageSteps.addPassengerInfo(elena.withBirthDate(PassengerAge.ADULT), 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnCheckBookingStatusButton();
        atBookingDetailsPageSteps.sendCouponsOnEmail("andrii.bakhtiozin@viaamadeus.com");
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"tf booking", "easyJet transporter", "luggage choosing", "seats choosing"})
    @Test
    public void tfBookingWithSeatAndLuggageChoosing() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF).withBirthDate(currentDateMinusNYears(25)).withGender(PassengerGender.MALE);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF).withBirthDate(currentDateMinusNYears(46)).withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("PAR", "BER", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("easyJet").withCarriageType("Y"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail, 1);
        atBookFormPageSteps.addPassengerInfo(alexii, 2);
        atBookFormPageSteps.selectRandomSeatOnBoard(1);
        atBookFormPageSteps.selectRandomSeatOnBoard(2);
        atBookFormPageSteps.selectRandomLuggage(1);
        atBookFormPageSteps.selectRandomLuggage(2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnCheckBookingStatusButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        systemSteps.checkBalance();
    }

    @Features("Booking")
    @Stories({"tf booking", "Ryanair transporter", "luggage choosing", "seats choosing"})
    @Test
    public void tfBookingWithSeatAndLuggageChoosingRyanair() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF).withBirthDate(currentDateMinusNYears(25)).withGender(PassengerGender.MALE);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF).withBirthDate(currentDateMinusNYears(46)).withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("PAR", "WAW", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("Ryanair"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail, 1);
        atBookFormPageSteps.addPassengerInfo(alexii, 2);
        atBookFormPageSteps.selectRandomSeatOnBoard(2);
        atBookFormPageSteps.selectRandomLuggage(1);
        atBookFormPageSteps.selectRandomLuggage(2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnCheckBookingStatusButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }


    @Features("Cancel Booking")
    @Stories({"tf cancel"})
    @Test
    public void tfCancelBookingTest() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF).withBirthDate("12.12.1990").withGender(PassengerGender.MALE);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("OSL", "STO", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("Norwegian").withCarriageType("FULLFLEX"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail, 1);
        atBookFormPageSteps.addPassengerInfo(alexii, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.XX);
        systemSteps.orderBackendStatusShouldBe("XX");
        atBookingDetailsPageSteps.shouldSeeErrorAlert();
        systemSteps.checkBalance();
    }

    @Features("Refund")
    @Stories({"Refund", "refund Claim"})
    @Test(enabled = false)
    public void tfRefundTest() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(TF).withBirthDate("12.12.1990").withGender(PassengerGender.MALE);
        Passenger alexii = new PassengerDataGenerator().generateFor(TF).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(mihail);
        atJourneySearchPageSteps.addPassengerToJourney(alexii);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("OSL", "STO", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("Norwegian").withCarriageType("FULLFLEX"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.fillContactDetails(new PassengerContactInfo().generate());
        atBookFormPageSteps.addPassengerInfo(mihail, 1);
        atBookFormPageSteps.addPassengerInfo(alexii, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnCheckBookingStatusButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnBookingDetails();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.pressOnCheckRefundStatusButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        systemSteps.orderACRStatusShouldBe(BookingStatus.RF);
        systemSteps.refundAcrStatusShouldBe(RefundStatus.OK);
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
