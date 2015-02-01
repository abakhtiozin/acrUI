package ui.pages.BookFormPage;

import com.codeborne.selenide.SelenideElement;
import model.passenger.Passenger;
import org.openqa.selenium.By;
import ui.pages.InnerPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by AA on 07.01.2015.
 */
public class BookFormPage extends InnerPage {

    // ------------------------------ FIELDS ------------------------------

    private Map<BookFormPageFieldsName, String> passengerFieldsLocators;

    // --------------------------- CONSTRUCTORS ---------------------------
    public BookFormPage() {
        passengerFieldsLocators = new HashMap<BookFormPageFieldsName, String>();
        this.passengerFieldsLocators.put(BookFormPageFieldsName.BIRTH_DATE, ".//input[@class='span12 birthDate hasDatepicker']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.SURNAME, ".//input[@class='span12 surname']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.NAME, ".//input[@class='span12 name']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.DOCUMENT, ".//input[@class='span12 documentSeriesAndNumber']");
    }

    // ------------------------------ Locators ------------------------------
    public SelenideElement bookingButton() {
        return $(By.xpath(".//*[@id='book_request_doBooking']"));
    }

    private List<SelenideElement> passengersElements() {
        return $$(By.xpath(".//div[@class='span3 passengerContainer']"));
    }

    private SelenideElement bookInfo() {
        return $(By.xpath(".//*[@id='replacedContent']/div[@class='row-fluid']"));
    }

    // ------------------------------ METHODS -----------------------------
    protected void addPassengerFieldsLocators(Map<BookFormPageFieldsName, String> passengerFieldsLocators) {
        for (Map.Entry<BookFormPageFieldsName, String> pair : passengerFieldsLocators.entrySet()) {
            this.passengerFieldsLocators.put(pair.getKey(), pair.getValue());
        }
    }

    public void addPassengers(List<Passenger> passengers) {
        for (int i = 0; i < passengers.size(); i++) {
            for (Map.Entry<BookFormPageFieldsName, String> pair : this.passengerFieldsLocators.entrySet()) {
                switch (pair.getKey()) {
                    case SURNAME:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).setValue(passengers.get(i).getSurname());
                        break;
                    case NAME:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).setValue(passengers.get(i).getName());
                        break;
                    case DOCUMENT:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).setValue(passengers.get(i).getDocumentNumber());
                        break;
                    case PLACE_OF_BIRTH:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).setValue(passengers.get(i).getBirthPlace());
                        break;
                    case TARIFF:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).selectOptionByValue(passengers.get(i).getTariffType().toString().toLowerCase());
                        break;
                    case STATE_ISSUED_DOCUMENT:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).selectOptionByValue(passengers.get(i).getStateIssuedDocument());
                        break;
                    case FATHERS:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).setValue(passengers.get(i).getFathersName());
                        break;
                    case GENDER:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).selectOptionByValue(passengers.get(i).getGender().toString().toLowerCase());
                        break;
                    case DOCUMENT_TYPE:
                        passengersElements().get(i).$(By.xpath(this.passengerFieldsLocators.get(pair.getKey()))).selectOptionByValue(passengers.get(i).getDocumentType().toString());
                        break;
                }
            }
        }

    }

    @Override
    public boolean onThisPage() {
        return bookInfo().isDisplayed();
    }
}
