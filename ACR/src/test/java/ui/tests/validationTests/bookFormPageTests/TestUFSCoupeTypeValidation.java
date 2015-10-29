package test.java.ui.tests.validationTests.bookFormPageTests;

import main.java.model.BookingPlacesSettings;
import main.java.model.Journey;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerDocumentType;
import main.java.model.passenger.PassengerGender;
import main.java.model.passenger.PassengerTariffType;
import main.java.utils.PassengerDataGenerator;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.UFS;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 09.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestUFSCoupeTypeValidation extends StepInitializer {
    @BeforeClass
    public void prepareData() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(UFS));
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MOW", "AER", currentDatePlusNDays(35)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTrainNumber("104В")
                .withTransporterName("РЖД")
                .withCarriageType("Купейный")
                .withTariffType(new String[]{"МЖ", "У1", "2Т"}));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.clickOnAddPassengerButton();
    }

    @DataProvider(name = "data provider for UFSBookFormValidationTest")
    public Object[][] createDataForValidationTest() {
        return new Object[][]{
                //Мужчина и Женщина едут в женском купе
                {new Passenger[]{
                        new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withDocumentType(PassengerDocumentType.Иностранный_документ)
                                .withBirthPlace("Kiev").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL),
                        new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.01.1991").withDocumentType(PassengerDocumentType.Иностранный_документ)
                                .withGender(PassengerGender.FEMALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings()
                                .withCoupeType(BookingPlacesSettings.CoupeType.FEMALE),
                        "Пол пассажира должен соответствовать выбранному типу купе."
                },
                //Мужчина и Женщина едут в мужском купе
                {new Passenger[]{
                        new Passenger("12.12.1990").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL),
                        new Passenger("12.01.1991").withGender(PassengerGender.FEMALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings().withCoupeType(BookingPlacesSettings.CoupeType.MALE),
                        "Пол пассажира должен соответствовать выбранному типу купе."
                },
                //Женщина и Женщина едут в мужском купе
                {new Passenger[]{
                        new Passenger("12.12.1990").withGender(PassengerGender.FEMALE).withTariffType(PassengerTariffType.FULL),
                        new Passenger("12.01.1991").withGender(PassengerGender.FEMALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings()
                                .withCoupeType(BookingPlacesSettings.CoupeType.MALE),
                        "Пол пассажира должен соответствовать выбранному типу купе."
                },
                //Мужчина и Мужчина едут в женском купе
                {new Passenger[]{
                        new Passenger("12.12.1990").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL),
                        new Passenger("12.01.1991").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings()
                                .withCoupeType(BookingPlacesSettings.CoupeType.FEMALE),
                        "Пол пассажира должен соответствовать выбранному типу купе."
                },
                //Пассажир по детскому тарифу, пассажиру больше 10-и лет
                {new Passenger[]{
                        new Passenger("12.12.2000").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.CHILD),
                        new Passenger("12.01.1997").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings()
                                .withCoupeType(BookingPlacesSettings.CoupeType.MALE),
                        "Детский тариф доступен только для возврастов от 0 до 10 лет."
                },
                //Пассажир по детскому без места тарифу, хоть пассажиру более 5 лет
                {new Passenger[]{
                        new Passenger("12.12.2008").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.NO_PLACE),
                        new Passenger("12.01.1997").withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL)},
                        new BookingPlacesSettings()
                                .withCoupeType(BookingPlacesSettings.CoupeType.MALE),
                        "Бесплатный проезд возможен только для детей от 0 до 5 лет."
                }
        };
    }

    @Test(testName = "BookFormValidationTest", dataProvider = "data provider for UFSBookFormValidationTest", enabled = false)
    public void ufsBookFormValidation(Passenger[] passengers, BookingPlacesSettings bookingPlacesSettings, String expectedAlert) {
        atBookFormPageSteps.refillPassengerInfo(passengers[0], 1);
        atBookFormPageSteps.refillPassengerInfo(passengers[1], 2);
        atBookFormPageSteps.setGenderCoupe(bookingPlacesSettings.getCoupeType());
        atBookFormPageSteps.pressOnBookAndStayOnBookForm();
        atBookFormPageSteps.shouldBeOnThisPage();
        atBookFormPageSteps.shouldSeeErrorAlertWithText(expectedAlert);
    }

    @AfterClass
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
