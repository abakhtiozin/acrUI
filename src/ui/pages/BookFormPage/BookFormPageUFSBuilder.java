package ui.pages.BookFormPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by bakhtiozin on 23.01.2015.
 */
public class BookFormPageUFSBuilder extends BookFormPageBuilder {

    // --------------------------- CONSTRUCTORS ---------------------------
    public BookFormPageUFSBuilder() {
        passengerFieldsLocators = new HashMap<BookFormPageFieldsName, String>();
        this.passengerFieldsLocators.put(BookFormPageFieldsName.TARIFF, ".//select[@class='span12 tariff']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.FATHERS, ".//input[@class='span12 fathers']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.DOCUMENT_TYPE, ".//select[@class='span12 documentType']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.STATE_ISSUED_DOCUMENT, ".//select[@class='span12 stateWhichIssuedDocument']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.GENDER, ".//select[@class='span12 gender']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.PLACE_OF_BIRTH, ".//input[@class='span12 placeOfBirth']");
    }

    // ------------------------------ FIELDS ------------------------------
    private Map<BookFormPageFieldsName, String> passengerFieldsLocators;

    // -------------------------- METHODS --------------------------

    @Override
    protected void setPassengerLocators() {
        BookFormPage.addPassengerFieldsLocators(passengerFieldsLocators);
    }
      /*
    TODO тест удаление пассажира на форме
    Сценарий:
    1) Добавить пассажиров
    2) сделать поиск
    3) удалить пассажира(ов) на форме пребукинга
    4) сделать заказ
    Ожидаемый результат: кол-во пассажиров и их данные соответствуют тем данным что были при поиске
    */
}
