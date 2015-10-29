package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.ModalWindow;
import org.openqa.selenium.By;
import util.ui.JQueryWorker;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 14.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookFormUfsPage extends BookFormPage implements PassengerActionsOnBookForm, WithBedClothing {

    private BookFormPlaceSelectionFields bookFormPlaceSelectionFields;

    public BookFormUfsPage() {
        this.bookFormPlaceSelectionFields = new BookFormPlaceSelectionFields();
        this.bookFormPagePassengerAction = new BookFormPagePassengerAction();
    }

    @Override
    public BookFormPage addPassenger(Passenger passenger, int passengerIndex) {
        bookFormPagePassengerAction.setPlaceOfBirth(passenger.getBirthPlace(), passengerIndex);
        bookFormPagePassengerAction.setPassengerBirthDate(passenger, passengerIndex);
        bookFormPagePassengerAction.setTariff(passenger.getTariffType(), passengerIndex);
        bookFormPagePassengerAction.setName(passenger.getName(), passengerIndex);
        bookFormPagePassengerAction.setSurname(passenger.getSurname(), passengerIndex);
        if (bookFormPagePassengerAction.isFathersNameFieldExist(passengerIndex)) {
            bookFormPagePassengerAction.setFathersName(passenger.getFathersName(), passengerIndex);
        }
        bookFormPagePassengerAction.setDocumentType(passenger.getDocumentType(), passengerIndex);
        bookFormPagePassengerAction.setDocumentNumber(passenger.getDocumentNumber(), passengerIndex);
        bookFormPagePassengerAction.setStateIssuedDocument(passenger.getStateIssuedDocument(), passengerIndex);
        bookFormPagePassengerAction.setGender(passenger.getGender(), passengerIndex);
        return page(this);
    }

    public BookFormPage setWithElectronicRegistration(boolean withElectronicRegistration) {
        SelenideElement inputElement = $("#book_request_withElectronicRegistration");
        if (withElectronicRegistration) {
            if (!JQueryWorker.isChecked(inputElement.getAttribute("id"))) {
                inputElement.click();
                new ModalWindow(eRegistrationAcceptModalWindowElement()).pressYes();
            }
        } else if (JQueryWorker.isChecked(inputElement.getAttribute("id"))) {
            inputElement.click();
            new ModalWindow(eRegistrationRefuseModalWindowElement()).pressYes();
        }
        return page(this);
    }

    public BookFormPage selectDeck(String deck) {
        bookFormPlaceSelectionFields.getDeckFieldLocator().selectOption(deck);
        $(By.xpath(".//*[@id='placesSelection']/li[1]/a")).click();
        $(By.xpath(".//*[@id='placesSelection']/li[1]/a")).click();
        return page(this);
    }

    public BookFormPage selectGenderCoupe(String coupeType) {
        if (coupeType != null && !coupeType.isEmpty()) {
            bookFormPlaceSelectionFields.getGenderCoupeFieldLocator().selectOptionByValue(coupeType);
        }
        return page(this);
    }

    private SelenideElement eRegistrationRefuseModalWindowElement() {
        return $("#eRegisterRefuseModal");
    }

    private SelenideElement eRegistrationAcceptModalWindowElement() {
        return $("#eRegisterAcceptModal");
    }

}
