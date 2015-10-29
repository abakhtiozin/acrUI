package test.java.ui.tests.workflow;

import main.java.model.BookingStatus;
import main.java.model.Journey;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerGender;
import main.java.utils.DateTimeHelper;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.ACP;
import static main.java.utils.DateTimeHelper.currentDateMinusNYears;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;
import static util.other.RandomDataGenerator.randomNumber;

/**
 * Created by Andrii.Bakhtiozin on 16.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestACPWorkFlow extends StepInitializer {

    @BeforeClass
    public void login() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
//        atLoginPageSteps.loginAs(new Reseller("and-dr-gb","andrew","1234"));
    }

    @BeforeMethod
    public void openSearchPage() {
        systemSteps.openJourneySearchPageByUrl();
        atJourneySearchPageSteps.clearLastSearchData();
    }

    private void simpleACPBooking(Journey journey, Trip trip) {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP).withBirthDate(currentDateMinusNYears(randomNumber(19, 62))).withGender(PassengerGender.MALE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate(currentDateMinusNYears(randomNumber(19, 62))).withGender(PassengerGender.FEMALE);
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.fillInJourneyData(journey);
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(trip);
        atSearchResultPageSteps.checkBaseTripInfo();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.checkBalance();
        atBookingDetailsPageSteps.downloadCoupon();
    }

    @Test(enabled = false)
    public void acpFivePassengersBooking() {
        Passenger passenger = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.12.1990").withGender(PassengerGender.MALE);
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        Passenger passenger3 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.02.1980").withGender(PassengerGender.FEMALE);
        Passenger passenger4 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.03.1980").withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.addPassengerToJourney(passenger3);
        atJourneySearchPageSteps.addPassengerToJourney(passenger4);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MUC", "BER", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("DB").withCarriageType("Standart").withTariffType(new String[]{"TCV PAPER TICKET ONLY"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger, 1);
        atBookFormPageSteps.addPassengerInfo(passenger1, 2);
        atBookFormPageSteps.addPassengerInfo(passenger2, 3);
        atBookFormPageSteps.addPassengerInfo(passenger3, 4);
        atBookFormPageSteps.addPassengerInfo(passenger4, 5);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Paper tickets Booking")
    @Stories("Paper ticket only")
    @Test(enabled = false)
    public void acpBookingPaperTicketOnly() {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.12.1990").withGender(PassengerGender.MALE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        Passenger passenger3 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.addPassengerToJourney(passenger3);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MUC", "BER", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("DB").withCarriageType("Standart").withTariffType(new String[]{"TCV PAPER TICKET ONLY"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(passenger3, 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
    }

    @Features("Paper tickets Booking")
    @Stories("Paper ticket and reservation")
    @Test(enabled = false)
    public void acpBookingPaperAndReservation() {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.12.1990").withGender(PassengerGender.MALE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        Passenger passenger3 = new PassengerDataGenerator().generateFor(ACP).withBirthDate("12.01.1980").withGender(PassengerGender.FEMALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.addPassengerToJourney(passenger3);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MUC", "BER", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("DB").withCarriageType("Standart").withTariffType(new String[]{"TCV PAPER TICKET ONLY"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(passenger3, 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
    }

    @Features("Cancel Booking")
    @Test
    public void acpCancelBookingTest() {
        systemSteps.setUpBalance();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP)
                .withBirthDate(DateTimeHelper.currentDateMinusNYears(randomNumber(22, 35)))
                .withGender(PassengerGender.MALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("STO", "GOT", currentDatePlusNDays(12)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("SJ AB")
                .withCarriageType("2 class")
                .withTariffType(new String[]{"SJ Compartment E-Ticket and Reservation - Base Fare"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.XX);
        systemSteps.orderBackendStatusShouldBe("XX");
        atBookingDetailsPageSteps.shouldSeeErrorAlert();
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
        systemSteps.checkBalance();
    }

    @Features("Download offers pdf")
    @Test
    public void acpDownloadOffersPdf() {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP)
                .withBirthDate(DateTimeHelper.currentDateMinusNYears(randomNumber(22, 35)))
                .withGender(PassengerGender.MALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("STO", "GOT", currentDatePlusNDays(12)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.downloadRoutes();
        atSearchResultPageSteps.shouldNotSeeErrorAlert();
    }

    @Features("Refund")
    @Stories("acp refund")
    @Test
    public void acpRefundBookingTest() {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP)
                .withBirthDate(DateTimeHelper.currentDateMinusNYears(randomNumber(22, 35)))
                .withGender(PassengerGender.MALE);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("STO", "GOT", currentDatePlusNDays(12)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("SJ AB").withTariffType(new String[]{"SJ Compartment E-Ticket and Reservation - Base Fare"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.clickOnBookingDetails();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnOpenRefundDetails();
        systemSteps.orderACRStatusShouldBe(BookingStatus.RF);
    }

    @Features("Booking")
    @Stories({"acp booking", "SJ"})
    @Test(testName = "validACPBookingSJTest")
    public void acpBookingSJTest() {
        simpleACPBooking(
                new Journey("STO", "GOT", currentDatePlusNDays(12)),
                new Trip().withTransporterName("SJ AB")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "Eurostar"})
    @Test(testName = "validACPBookingEurostarTest")
    public void acpBookingEurostarTest() {
        simpleACPBooking(
                new Journey("LON", "PAR", currentDatePlusNDays(25)),
                new Trip().withTransporterName("EUROSTAR")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "Atoc"})
    @Test(testName = "validACPBookingAtocTest")
    public void acpBookingAtocTest() {
        simpleACPBooking(
                new Journey("LON", "GLA", currentDatePlusNDays(20), 11, 24),
                new Trip().withTransporterName("Atoc")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "Trenitalia"})
    @Test(testName = "validACPBookingTrenitaliaTest")
    public void acpBookingTrenitaliaTest() {
        simpleACPBooking(
                new Journey("MIL", "ROM", currentDatePlusNDays(20)),
                new Trip().withTransporterName("Trenitalia")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "Renfe-SNCF"})
    @Test(testName = "validACPBookingRenfe-SNCFTest")
    public void acpBookingRenfeSNCFTest() {
        simpleACPBooking(
                new Journey("BCN", "PAR", currentDatePlusNDays(15)),
                new Trip().withTransporterName("Renfe-SNCF")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "Renfe"})
    @Test(testName = "validACPBookingRenfeTest")
    public void acpBookingRenfeTest() {
        simpleACPBooking(
                new Journey("BCN", "MAD", currentDatePlusNDays(15)),
                new Trip().withTransporterName("RenFe")
        );
    }

    @Features("Booking")
    @Stories({"acp booking", "VIA rail"})
    @Test(testName = "validACPBookingVIArailTest")
    public void acpBookingViaRailTest() {
        Passenger passenger1 = new PassengerDataGenerator().generateFor(ACP).withBirthDate(DateTimeHelper.currentDateMinusNYears(randomNumber(20, 50)));
        Passenger passenger2 = new PassengerDataGenerator().generateFor(ACP).withBirthDate(DateTimeHelper.currentDateMinusNYears(randomNumber(12, 25)));
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Монреаль", "Торонто", currentDatePlusNDays(15)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("VIA"));
        atSearchResultPageSteps.checkBaseTripInfo();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton();
        atBookingDetailsPageSteps.shouldBeTextInModalWindowBody("Купоны готовятся. Повторите запрос позже.");
    }

    @AfterClass
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
