package test.java.ui.tests.workflow;

import main.java.model.BookingStatus;
import main.java.model.Journey;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import test.java.base.StepInitializer;
import util.other.RandomDataGenerator;

import static main.java.model.BookingStatus.PRF;
import static main.java.model.BookingStatus.RF;
import static main.java.model.RefundStatus.OK;
import static main.java.model.Supplier.IP;
import static main.java.model.passenger.PassengerDocumentType.*;
import static main.java.model.passenger.PassengerTariffType.CHILD;
import static main.java.model.passenger.PassengerTariffType.FULL;
import static main.java.utils.DateTimeHelper.currentDateMinusNYears;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 17.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestIPWorkFlow extends StepInitializer {

    @Features("Booking")
    @Stories({"ip booking", "payment type : wire"})
    @Test
    public void ipBookingWireTransfer() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger mihail = new PassengerDataGenerator().generateFor(IP).withBirthDate("12.12.2000").withDocumentType(Иностранный_документ)
                .withTariffType(CHILD);
        Passenger alexii = new PassengerDataGenerator().generateFor(IP).withBirthDate("10.04.1990").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger tasha = new PassengerDataGenerator().generateFor(IP).withBirthDate("13.03.1992").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("ALA", "TSE", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Люкс"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(alexii, 1);
        atBookFormPageSteps.addPassengerInfo(mihail, 2);
        atBookFormPageSteps.addPassengerInfo(tasha, 3);
        atBookFormPageSteps.setPaymentTypeByCash(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Тип оплаты: Безналичная");
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Электронная регистрация включена");
        atBookingDetailsPageSteps.downloadCoupon();
    }

    @Features("Booking")
    @Stories({"ip booking", "payment type : cash", "Вид_на_жительство"})
    @Test
    public void ipBookingByCash() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Вид_на_жительство).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Свидетельство_о_рождении_РК).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(2, 12))).withTariffType(CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("ALA", "TSE", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(true);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Тип оплаты: Наличная");
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Электронная регистрация включена");
        atBookingDetailsPageSteps.downloadCoupon();
    }

    @Features("Booking")
    @Stories({"ip booking", "payment type : cash", "Удостоверение_личности_РК", "Паспорт_РК", "child", "linen", "download coupon"})
    @Test
    public void ipBookingWithBedClothing() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Астана", "Караганды", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Вид_на_жительство).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Свидетельство_о_рождении_РК).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(2, 12))).withTariffType(CHILD);
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(true);
        atBookFormPageSteps.setWithBedClothing(true);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton();
        atBookingDetailsPageSteps.downloadCoupon();
    }

    @Features("Booking")
    @Stories({"ip booking", "payment type : cash", "Удостоверение_личности_РК", "Паспорт_РК", "child", "without linen", "download coupon", "email", "prebook details"})
    @Test
    public void ipBookingWithoutBedClothing() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
//        atLoginPageSteps.loginAs(new Reseller("and-dr-gb", "andrew", "1234"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Астана", "Караганды", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Паспорт_РФ).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Загранпаспорт_РФ).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(true);
        atBookFormPageSteps.setWithBedClothing(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(false);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton();
        atBookingDetailsPageSteps.downloadCoupon();
        atBookingDetailsPageSteps.sendCouponsOnEmail("andrii.bakhtiozin@gmail.com");
        systemSteps.checkBalance();
    }

    @Features("Cancel Booking")
    @Stories({"ip cancel", "payment type : wire", "child"})
    @Test
    public void ipBookingCancelWireTransfer() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Паспорт_Узбекистана).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Иностранный_документ).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("ALA", "TSE", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.XX);
        systemSteps.orderBackendStatusShouldBe("XX");
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
        atBookingDetailsPageSteps.shouldSeeErrorAlert();
        systemSteps.checkBalance();
    }

    @Features("PaymentType")
    @Stories({"ip paymentType is mandatory"})
    @Test
    public void ipPaymentTypeIsMandatoryCheck() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Паспорт_Узбекистана).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Загранпаспорт_РФ).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("ALA", "TSE", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.pressOnBookAndStayOnBookForm();
        atBookFormPageSteps.shouldBeOnThisPage();
        atBookFormPageSteps.shouldSeeErrorAlertWithText("Пожалуйста укажите тип оплаты.");
    }

    @Features("Refund")
    @Stories({"ip refund", "full refund", "prerefund claim"})
    @Test
    public void ipFullRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Вид_на_жительство).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Свидетельство_о_рождении_РК).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(5, 12))).withTariffType(CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("Караганды", "TSE", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Сидячий"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.addSupplierBookingConfirmationNumber();
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.pressOnCheckRefundStatusButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt();
        systemSteps.orderACRStatusShouldBe(RF);
        systemSteps.refundAcrStatusShouldBe(OK);
    }

    @Features("Refund")
    @Stories({"ip refund", "refund if payment type is cash"})
    @Test
    public void ipRefundIfPaymentTypeCash() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Удостоверение_личности_РК).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 70))).withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Паспорт_РК).withBirthDate(currentDateMinusNYears(RandomDataGenerator.randomNumber(5, 12))).withTariffType(CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("ALA", "TSE", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(true);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger2);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger2.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.pressOnCheckRefundStatusButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt();
        systemSteps.orderACRStatusShouldBe(PRF);
        systemSteps.refundAcrStatusShouldBe(OK);
    }

    @Features("Refund")
    @Stories({"ip refund", "refund if payment type is cash"})
    @Test
    public void ipPartialRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(IP, Паспорт_РК).withBirthDate("12.01.1977").withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(IP, Паспорт_РК).withBirthDate("12.12.2007").withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("TSE", "Караганды", currentDatePlusNDays(20)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("КТЖ").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setPaymentTypeByCash(true);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger1);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.pressOnCheckRefundStatusButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt();
        atRefundDetailsPageSteps.openRefundPage();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger2);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger2.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.pressOnCheckRefundStatusButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        systemSteps.orderACRStatusShouldBe(BookingStatus.RF);
        systemSteps.refundAcrStatusShouldBe(OK);
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
