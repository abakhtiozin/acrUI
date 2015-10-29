package test.java.ui.tests.workflow;

import main.java.model.BookingStatus;
import main.java.model.Journey;
import main.java.model.TicketType;
import main.java.model.Trip;
import main.java.model.passenger.*;
import main.java.utils.DateTimeHelper;
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
import static main.java.model.RefundStatus.LO;
import static main.java.model.RefundStatus.OK;
import static main.java.model.Supplier.UFS;
import static main.java.model.passenger.PassengerDocumentType.*;
import static main.java.model.passenger.PassengerTariffType.CHILD;
import static main.java.model.passenger.PassengerTariffType.FULL;
import static main.java.utils.DateTimeHelper.*;
import static util.other.RandomDataGenerator.getRandomLiteralsString;
import static util.other.RandomDataGenerator.getRandomNumericString;

/**
 * Created by Andrii.Bakhtiozin on 11.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestUFSWorkFlow extends StepInitializer {


    @Features("Booking")
    @Stories({"ufs booking", "child", "Свидетельство_о_рождении_РФ", "Паспорт_РФ"})
    @Test
    public void ufsBookingWithChildCoupe() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS, Военный_билет_РФ);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS, Свидетельство_о_рождении_РФ).withBirthDate(PassengerAge.KID).withTariffType(CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(15), 22, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withTrainNumber("026А").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1.withBirthDate(PassengerAge.ADULT), 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"ufs booking", "child no place"})
    @Test
    public void ufsBookingWithChildNoPlaceCoupe() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
//                        atLoginPageSteps.loginAs(new Reseller("and-dr-gb", "andrew", "1234"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS, Военный_билет_РФ).withBirthDate(PassengerAge.ADULT);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS, Свидетельство_о_рождении_РФ).withBirthDate(PassengerAge.KID).withTariffType(PassengerTariffType.NO_PLACE);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withTrainNumber("026А").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"ufs booking", "platskart", "Паспорт_РФ", "Загранпаспорт_РФ"})
    @Test
    public void ufsBookingWithThreeAdultPlatskart() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new Passenger("12.12.1987").withDocumentType(Загранпаспорт_РФ)
                .withTariffType(PassengerTariffType.FULL).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.FULL);
        Passenger vera = new Passenger("11.04.1962").withDocumentType(Паспорт_РФ)
                .withTariffType(PassengerTariffType.FULL).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber(getRandomNumericString(10)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 22, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный").withTrainNumber("064А"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(vera, 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        systemSteps.checkBalance();
    }

    @Features("Booking")
    @Stories({"ufs booking", "lux carriage", "Загранпаспорт_РФ"})
    @Test
    public void ufsBookingWithThreeAdultsLux() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS, Загранпаспорт_РФ).withBirthDate(PassengerAge.ADULT);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS, Загранпаспорт_РФ).withBirthDate(PassengerAge.ADULT);
        Passenger vera = new PassengerDataGenerator().generateFor(UFS, Загранпаспорт_РФ).withBirthDate(PassengerAge.ADULT);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 0, 4));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Люкс").withTrainNumber("020У"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(vera, 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"ufs booking", "seat carriage", "Свидетельство_о_рождении_РФ", "Военный_билет_РФ"})
    @Test
    public void ufsBookingWithFamilyOfFourSeat() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.2008").withDocumentType(Свидетельство_о_рождении_РФ)
                .withTariffType(PassengerTariffType.CHILD).withSurname("ТестПетров").withFathersName("ТестОтч")
                .withName("ТестВася").withDocumentNumber("XКЕ132222").withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString());
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.2012").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.NO_PLACE);
        Passenger vera = new Passenger("11.04.1963").withDocumentType(Военный_билет_РФ)
                .withTariffType(PassengerTariffType.FULL).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        Passenger kolya = new PassengerDataGenerator().generateFor(UFS).withBirthDate("11.12.1961").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 6, 9));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Сидячий").withTrainNumber("752А").withTariffType(new String[]{"2С"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(vera, 3);
        atBookFormPageSteps.addPassengerInfo(kolya, 4);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"ufs booking", "seat first class", "Военный_билет_РФ"})
    @Test
    public void ufsBookingWithTwoAdultsSeatFirstClass() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new Passenger("12.12.1967").withDocumentType(Военный_билет_РФ)
                .withTariffType(PassengerTariffType.FULL).withSurname("ТестПетров")
                .withName("ТестПетя").withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU")
                .withGender(PassengerGender.MALE).withBirthPlace(getRandomLiteralsString(3, 6)).withFathersName("Артемович");
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1969").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 6, 9));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Сидячий").withTrainNumber("752А").withTariffType(new String[]{"У1", "1Р"}));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.shouldSeeInfoAlert("В данном вагоне купе можно выкупать только целиком. Указана цена за купе. Детский тариф не действует!");
        atBookFormPageSteps.shouldSeeInfoAlert("Курить запрещено.");
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        systemSteps.checkBalance();
    }


    @Features("Refund")
    @Stories({"ufs refund", "full refund", "prerefund claim"})
    @Test
    public void ufsFullRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.2007").withDocumentType(Иностранный_документ)
                .withTariffType(CHILD);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "Пенза", currentDatePlusNDays(25)));
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
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.addSupplierBookingConfirmationNumber();
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund();
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt();
        systemSteps.orderACRStatusShouldBe(RF);
        systemSteps.refundAcrStatusShouldBe(OK);
    }

    @Features("Refund")
    @Stories({"ufs refund", "download coupon", "partial refund", "prerefund claim", "download charges receipt"})
    @Test
    public void ufsPartialRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton(); //downloadCoupon window
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger1);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund(); //open claim
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt(); //download receip
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        systemSteps.orderACRStatusShouldBe(PRF);
        systemSteps.refundAcrStatusShouldBe(OK);
    }

    @Features("Refund")
    @Stories({"ufs refund", "download coupon", "partial refund", "prerefund claim", "download charges receipt"})
    @Test
    public void ufsSeveralPartialRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1992").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger nikolai = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1987").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(nikolai, 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton(); //downloadCoupon window
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger1);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund(); //open claim
        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt(); //download receip
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        systemSteps.orderACRStatusShouldBe(PRF);
        systemSteps.refundAcrStatusShouldBe(OK);
        atBookingDetailsPageSteps.goBackToThisPage();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(nikolai);
        atRefundPageSteps.addPassengerDoctypeNumber(nikolai.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        atRefundDetailsPageSteps.clickOnOpenClaimPreRefund(); //open claim

        atRefundDetailsPageSteps.pressOnConfirmRefundButton();
        atRefundDetailsPageSteps.shouldSeeSuccessAlertWithText("Возврат подтвержден.");
        atRefundDetailsPageSteps.clickOnDownloadChargesReceipt(); //download receip
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        systemSteps.orderACRStatusShouldBe(PRF);
        systemSteps.refundAcrStatusShouldBe(OK);
        atBookingDetailsPageSteps.goBackToThisPage();
        atBookingDetailsPageSteps.shouldSeeErrorAlertWithText("Часть билетов заказа возвращена.");
        atBookingDetailsPageSteps.downloadCoupon();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
    }

    @Features("Refund")
    @Stories({"ufs refund", "claim blank", "download coupon"})
    @Test
    public void ufsCantRefundWithoutClickingOnClaimRefund() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnDownloadCouponButton(); //downloadCoupon window
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger1);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger1.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        atRefundDetailsPageSteps.shouldBeRefundButtonDisabled();
        atRefundDetailsPageSteps.shouldBeOnThisPage();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.refundAcrStatusShouldBe(LO);
    }

    @Features("Refund")
    @Stories({"ufs refund", "can't refund adult"})
    @Test
    public void ufsCantRefundAdultTicketIfKidTicketExists() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = (new PassengerDataGenerator().generateFor(UFS)).withBirthDate("12.12.2009").withDocumentType(Иностранный_документ)
                .withTariffType(CHILD);
        Passenger passenger2 = (new PassengerDataGenerator().generateFor(UFS)).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        systemSteps.updateBookingConfirmationTime();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(passenger2);
        atRefundPageSteps.addPassengerDoctypeNumber(passenger2.getDocumentNumber());
        atRefundPageSteps.refundTickets(false);
        atRefundPageSteps.shouldSeeErrorAlert();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
    }

    @Features("Refund")
    @Stories({"ufs refund"})
    @Test
    public void ufsCantRefundWithinFiveMinutesLeftAfterBooking() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger alexii = (new PassengerDataGenerator().generateFor(UFS)).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL);
        Passenger passenger1 = (new PassengerDataGenerator().generateFor(UFS)).withBirthDate("12.12.2009").withDocumentType(Иностранный_документ)
                .withTariffType(CHILD);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(alexii, 1);
        atBookFormPageSteps.addPassengerInfo(passenger1, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
        atBookingDetailsPageSteps.clickOnRefundBookingButton();
        atRefundPageSteps.shouldBeOnThisPage();
        atRefundPageSteps.clickOnRefundAllTicketsOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(alexii);
        atRefundPageSteps.addPassengerDoctypeNumber(alexii.getDocumentNumber());
        atRefundPageSteps.refundTickets(true);
        atRefundPageSteps.shouldSeeErrorAlertWithText("Изменения возможны только через 5 минут после подтверждения заказа.");
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
    }

    @Features("Booking")
    @Stories({"ufs booking", "double deck"})
    @Test()
    public void ufsBookingDoubleDeck() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "AER", currentDatePlusNDays(20), 9, 12));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.clickOnTrainNumberButtonToExpand();
        atSearchResultPageSteps.clickOnMoreInfoButtonToLookOnOffers();
        atSearchResultPageSteps.chooseCarriageType();
        atSearchResultPageSteps.findSingleTripOffer();
        atSearchResultPageSteps.shouldBeDoubleDeckCarriage();
        atSearchResultPageSteps.orderOffer();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(6)).withDocumentType(Иностранный_документ)
                .withTariffType(CHILD), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(37)).withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.setDeckNum(2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Электронная регистрация включена");
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Электронный билет можно распечатать на станции отправления");
        atBookingDetailsPageSteps.downloadCoupon();
    }

    @Features("Booking")
    @Stories({"ufs booking", "eregistration"})
    @Test()
    public void ufsBookingRefuseERegistrationOnBookForm() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 1, 23));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(6)).withDocumentType(Иностранный_документ)
                .withTariffType(CHILD), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(37)).withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.setWithElectronicRegistration(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBeRightTicketType(TicketType.SP);
        systemSteps.shouldBookingBeWithBedClothing(true);
        atBookFormPageSteps.shouldSeeInfoAlert("Без электронной регистрации");
        atBookFormPageSteps.shouldSeeInfoAlert("Электронный билет можно распечатать на станции отправления");
    }

    @Features("Booking")
    @Stories({"ufs booking", "eregistration"})
    @Test()
    public void ufsBookingRefuseERegistrationOnBookingDetails() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 1, 23));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(6)).withDocumentType(Иностранный_документ)
                .withTariffType(CHILD), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(37)).withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.updateBookingConfirmationTime();
        atBookingDetailsPageSteps.shouldBeDisableChangeERegistration(true);
        atBookingDetailsPageSteps.clickOnRequestChangeERegistration();
        atBookingDetailsPageSteps.shouldBeDisableChangeERegistration(false);
        atBookingDetailsPageSteps.clickOnChangeERegistration();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        systemSteps.shouldBeRightTicketType(TicketType.SP);
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Без электронной регистрации");
        atBookingDetailsPageSteps.shouldSeeInfoAlert("Электронный билет можно распечатать на станции отправления");
    }

    @Features("Booking")
    @Stories({"ufs booking", "eregistration"})
    @Test()
    public void ufsBookingCantChangeERegistrationWithinFiveMinutesLeftAfterBooking() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 1, 23));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(6)).withDocumentType(Иностранный_документ)
                .withTariffType(CHILD), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(37)).withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        atBookingDetailsPageSteps.clickOnChangeERegistration();
        atBookingDetailsPageSteps.shouldSeeErrorAlertWithText("Изменения возможны только через 5 минут после подтверждения заказа.");
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        systemSteps.shouldBeRightTicketType(TicketType.ETK);
        atBookingDetailsPageSteps.goBackToThisPage();
        atBookFormPageSteps.shouldSeeInfoAlert("Электронная регистрация включена");
        atBookFormPageSteps.shouldSeeInfoAlert("Электронный билет можно распечатать на станции отправления");
    }

    @Features("Booking")
    @Stories({"ufs booking", "eregistration"})
    @Test()
    public void ufsBookingRefuseThenAcceptERegistrationOnBookingDetails() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 1, 23));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(6)).withDocumentType(Иностранный_документ)
                .withTariffType(CHILD), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate(currentDateMinusNYears(37)).withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.setWithElectronicRegistration(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.shouldBeRightTicketType(TicketType.SP);
        systemSteps.updateBookingConfirmationTime();
        atBookingDetailsPageSteps.clickOnChangeERegistration();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        systemSteps.shouldBeRightTicketType(TicketType.ETK);
        atBookingDetailsPageSteps.sendCouponsOnEmail("andrii.bakhtiozin@gmail.com");
        atBookFormPageSteps.shouldSeeInfoAlert("Электронная регистрация включена");
        atBookFormPageSteps.shouldSeeInfoAlert("Электронный билет можно распечатать на станции отправления");
    }

    @Features("Booking")
    @Stories({"ufs booking", "linen"})
    @Test
    public void ufsBookingWithLinen() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.2012").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.NO_PLACE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withTrainNumber("030А").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(true);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Features("Booking")
    @Stories({"ufs booking", "linen"})
    @Test
    public void ufsBookingWithoutLinen() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.2012").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.NO_PLACE);
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(PassengerTariffType.FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withTrainNumber("030А").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.setWithBedClothing(false);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnPreBookingDetails(); //Проверка кнопки деталей заказа
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        systemSteps.shouldBookingBeWithBedClothing(false);
        atBookingDetailsPageSteps.sendCouponsOnEmail("andrii.bakhtiozin@gmail.com");
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();

    }

    @Features("Cancel Booking")
    @Stories({"ufs cancel"})
    @Test
    public void ufsBookingCancel() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        systemSteps.setUpBalance();
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(20), 0, 8));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 1);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1977").withDocumentType(Иностранный_документ)
                .withTariffType(FULL), 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnCancelBookingButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.XX);
        systemSteps.orderBackendStatusShouldBe("XX");
        atBookingDetailsPageSteps.shouldSeeErrorAlert();
        atBookingDetailsPageSteps.shouldNotSeeDownloadCouponButton();
        systemSteps.checkBalance();
    }

    @Features("Booking")
    @Stories({"ufs booking", "international trip", "Загранпаспорт_РФ"})
    @Test
    public void ufsBookingInternationalToParis() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("DM"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate(DateTimeHelper.currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 60)))
                .withDocumentType(Загранпаспорт_РФ).withTariffType(FULL)
                .withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU");
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "PAR", currentDatePlusWeekAndDayOfTheWeek(1, 5)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Купейный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.addPassengerInfo(passenger1, 1);
        atBookFormPageSteps.addPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Test
    public void ufsBookingDM() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("admin"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate(DateTimeHelper.currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 60)))
                .withDocumentType(Загранпаспорт_РФ).withTariffType(FULL)
                .withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU");
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 22, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Люкс").withTrainNumber("004А"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.refillPassengerInfo(passenger1, 1);
        atBookFormPageSteps.refillPassengerInfo(passenger2, 2);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }

    @Test
    public void ufsAddingNewPassengerOnPreBook() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller("admin"));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger passenger1 = new PassengerDataGenerator().generateFor(UFS).withBirthDate(DateTimeHelper.currentDateMinusNYears(RandomDataGenerator.randomNumber(20, 60)))
                .withDocumentType(Загранпаспорт_РФ).withTariffType(FULL)
                .withDocumentNumber(getRandomNumericString(9)).withStateIssuedDocument("RU");
        Passenger passenger2 = new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(FULL);
        atJourneySearchPageSteps.addPassengerToJourney(passenger1);
        atJourneySearchPageSteps.addPassengerToJourney(passenger2);
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "LED", currentDatePlusNDays(22), 21, 24));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        systemSteps.tripPriceShouldBeInResellerCurrency();
        systemSteps.checkIsValidPrice();
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.validateDataOnPage();
        atBookFormPageSteps.refillPassengerInfo(passenger1, 1);
        atBookFormPageSteps.refillPassengerInfo(passenger2, 2);
        atBookFormPageSteps.addPassengerInfo(new PassengerDataGenerator().generateFor(UFS).withBirthDate("10.04.1963").withDocumentType(PassengerDocumentType.Иностранный_документ)
                .withTariffType(FULL), 3);
        atBookFormPageSteps.bookAndGoToBookingPage();
        atBookingDetailsPageSteps.shouldBeOnThisPage();
        atBookingDetailsPageSteps.clickOnBookOrderButton();
        systemSteps.orderACRStatusShouldBe(BookingStatus.OK);
        atBookingDetailsPageSteps.shouldSeeSuccessAlert();
    }


    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
