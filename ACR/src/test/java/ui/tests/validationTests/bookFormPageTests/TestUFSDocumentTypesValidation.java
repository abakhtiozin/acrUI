package test.java.ui.tests.validationTests.bookFormPageTests;

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
 * Created by Andrii.Bakhtiozin on 17.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestUFSDocumentTypesValidation extends StepInitializer {

    @BeforeClass
    public void prepareData() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(UFS));
        atJourneySearchPageSteps.clearLastSearchData();
        Passenger alexii = new Passenger("12.01.1977");
        atJourneySearchPageSteps.fillInJourneyData(new Journey("LED", "MOW", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("РЖД").withCarriageType("Плацкартный"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
    }

    @DataProvider(name = "data provider for UFSBookFormValidationTest")
    public Object[][] createDataForValidationTest() {
        return new Object[][]{
                //Другой документ Фамилия с числом
                {new PassengerDataGenerator().generateFor(UFS).withBirthDate("12.12.1990").withSurname("Test1").withDocumentType(PassengerDocumentType.Иностранный_документ)
                        .withGender(PassengerGender.MALE).withTariffType(PassengerTariffType.FULL),
                        "Фамилия может содержать только буквы русского и латинского алфавита, а также дефис."
                },
                //Другой документ, пустая фамилия
                {new Passenger("12.12.1990").withSurname(" "),
                        "Пожалуйста укажите фамилию пассажира."
                },
                //Другой документ, имя с цифрой
                {new Passenger("12.12.1990").withSurname("test").withName("Test1"),
                        "Имя может содержать только буквы русского и латинского алфавита, а также дефис."
                },
                //Другой документ, пустое имя
                {new Passenger("12.12.1990").withName(" "),
                        "Пожалуйста укажите имя пассажира."
                },
                //Другой документ, отчество с цифрой
                {new Passenger("12.12.1990").withName("test").withFathersName("test123"),
                        "Отчество может содержать только буквы русского и латинского алфавита, а также дефис."
                },
                //Другой документ, пустой номер документа
                {new Passenger("12.12.1990").withFathersName("test").withDocumentNumber(" "),
                        "Пожалуйста укажите серию и номер документа."
                },
                //Другой документ, некоректный номер документа
                {new Passenger("12.12.1990").withDocumentNumber("*-%"),
                        "Иностранный документ должен содержать хотя бы одну цифру и состоять только из цифр, а также русских/латинских букв (или не содержать цифр - но в таком случае он может содержать только латинские буквы и русскую букву С)."
                },
                //Другой документ, пустая страна выдачи документа
                {new Passenger("12.12.1990").withDocumentNumber("tegsgsg43").withStateIssuedDocument(""),
                        "Пожалуйста укажите страну, в которой был выпущен документ."
                },
                //Другой документ, другой документа и страна Россия
                {new Passenger("12.12.1990").withStateIssuedDocument("RU"),
                        "Тип документа не соответствует государству выдачи документа."
                },
                //Другой документ, пустое место рождения пассажира
                {new Passenger("12.12.1990").withStateIssuedDocument("UA").withBirthPlace(""),
                        "Пожалуйста укажите место рождения."
                },
                //Паспорт РФ, пустая фамилия
                {new Passenger("12.12.1990").withStateIssuedDocument("RU").withSurname(" ").withName("ыуаыуаыа").withFathersName("папфы")
                        .withDocumentType(PassengerDocumentType.Паспорт_РФ).withDocumentNumber("1234567890")
                        .withBirthPlace("калынивка"),
                        "Пожалуйста укажите фамилию пассажира."
                },
                //Паспорт РФ, фамилия на английском
                {new Passenger("12.12.1990").withSurname("kfsw"),
                        "Фамилия может содержать только буквы русского алфавита и дефис."
                }
        };
    }

    @Test(testName = "BookFormValidationTest", dataProvider = "data provider for UFSBookFormValidationTest", enabled = false)
    public void ufsBookFormValidation(Passenger passenger, String expectedAlert) {
        atBookFormPageSteps.addPassengerInfo(passenger, 1);
        atBookFormPageSteps.pressOnBookAndStayOnBookForm();
        atBookFormPageSteps.shouldBeOnThisPage();
        atBookFormPageSteps.shouldSeeErrorAlertWithText(expectedAlert);
    }

    @AfterClass
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
