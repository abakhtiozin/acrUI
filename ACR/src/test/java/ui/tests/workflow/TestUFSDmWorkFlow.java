package test.java.ui.tests.workflow;

import main.java.model.Journey;
import main.java.model.Reseller;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerGender;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import test.java.base.StepInitializer;

import static main.java.model.BookingStatus.*;
import static main.java.model.Supplier.UFS;
import static main.java.model.passenger.PassengerAge.ADULT;
import static main.java.model.passenger.PassengerAge.KID;
import static main.java.model.passenger.PassengerDocumentType.*;
import static main.java.model.passenger.PassengerTariffType.CHILD;
import static main.java.model.passenger.PassengerTariffType.FULL;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;
import static util.other.RandomDataGenerator.getRandomLiteralsString;
import static util.other.RandomDataGenerator.getRandomNumericString;

/**
 * Created by Andrii.Bakhtiozin on 10.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestUFSDmWorkFlow extends StepInitializer {
    private Reseller getReseller() {
        return BaseReseller.getReseller("DM");
    }

    @Features("Booking")
    @Stories({"ufs booking", "dm model", "Lux carriage"})
    @Test
    public void ufsDMBookingWithOneAdultLux() {
        atLoginPageSteps.loginAs(getReseller());
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 0, 4));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Люкс").withTrainNumber("020У"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1987")
                .withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 1);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Cancel Booking")
    @Stories({"ufs cancel", "dm model", "Плацкартный carriage"})
    @Test
    public void ufsDMCancelWithOneAdultCoupe() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(44), 0, 4));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный").withTrainNumber("030А"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1987").withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 1);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(XX);
        atBookingDetailsPageSteps.shouldSeeErrorAlertWithText("Заказ отменен.");
    }

    @Features("Cancel Booking")
    @Stories({"ufs cancel", "dm model", "Купейный carriage", "Загранпаспорт_РФ", "Паспорт_моряка"})
    @Test
    public void ufsCancelDmBookingTest() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new Passenger("12.12.2005").withDocumentType(Загранпаспорт_РФ)
                .withTariffType(CHILD).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        Passenger passenger2 = new Passenger("12.01.1977").withDocumentType(Паспорт_моряка)
                .withTariffType(FULL).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber("ПП" + getRandomNumericString(7)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(XX);
        atBookingDetailsPageSteps.shouldSeeErrorAlertWithText("Заказ отменен.");
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
    }

    @Features("Refund")
    @Stories({"ufs refund", "full refund", "dm model", "child tariff"})
    @Test
    public void ufsDmFullRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS, Свидетельство_о_рождении_РФ).withBirthDate(KID).withTariffType(CHILD);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS, Паспорт_РФ).withBirthDate(ADULT).withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "Пенза", currentDatePlusNDays(25), 16, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.addSupplierBookingConfirmationNumber();
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atBookingDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        systemSteps.orderACRStatusShouldBe(RF);
    }

    @Features("Refund")
    @Stories({"ufs refund", "partial refund", "dm model", "child tariff"})
    @Test
    public void ufsDmPartialRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.2007").withDocumentType(Иностранный_документ)
                .withTariffType(CHILD);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "Пенза", currentDatePlusNDays(25), 16, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger1);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund(); //open claim
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atBookingDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        systemSteps.orderACRStatusShouldBe(PRF);
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
