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

    private Map<String,SelenideElement> passengerFieldsLocators;

    public BookFormPageUFSBuilder() {
        passengerFieldsLocators = new HashMap<String, SelenideElement>();
        this.passengerFieldsLocators.put("tarif",passengerTariff());
        this.passengerFieldsLocators.put("fathers",passengerFathers());
        this.passengerFieldsLocators.put("documentType",passengerDocumentType());
        this.passengerFieldsLocators.put("stateIssuedDocument",passengerStateIssuedDocument());
        this.passengerFieldsLocators.put("gender",passengerGender());
        this.passengerFieldsLocators.put("placeOfBirth",passengerPlaceOfBirth());
    }
    private SelenideElement passengerTariff(){
        return $(By.xpath(".//input[@class='span12 tariff']"));
    }
    private SelenideElement passengerFathers(){
        return $(By.xpath(".//input[@class='span12 fathers']"));
    }
    private SelenideElement passengerDocumentType(){
        return $(By.xpath(".//input[@class='span12 documentType']"));
    }
    private SelenideElement passengerStateIssuedDocument(){
        return $(By.xpath(".//input[@class='span12 stateWhichIssuedDocument']"));
    }
    private SelenideElement passengerGender(){
        return $(By.xpath(".//input[@class='span12 gender']"));
    }
    private SelenideElement passengerPlaceOfBirth(){
        return $(By.xpath(".//input[@class='span12 placeOfBirth']"));
    }



    @Override
    public void setPassengerLocators() {
        BookFormPage.initBasePassengerFieldsLocators();
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
