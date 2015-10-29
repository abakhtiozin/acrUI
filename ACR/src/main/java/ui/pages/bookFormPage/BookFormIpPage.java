package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import main.java.model.passenger.Passenger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 27.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookFormIpPage extends BookFormPage implements PassengerActionsOnBookForm, WithBedClothing {

    private SelenideElement paymentTyp() {
        return $("#book_request_paymentType");
    }

    @Override
    public BookFormPage addPassenger(Passenger passenger, int passengerIndex) {
        bookFormPagePassengerAction.setPassengerBirthDate(passenger, passengerIndex);
        bookFormPagePassengerAction.setTariff(passenger.getTariffType(), passengerIndex);
        bookFormPagePassengerAction.setName(passenger.getName(), passengerIndex);
        bookFormPagePassengerAction.setSurname(passenger.getSurname(), passengerIndex);
        bookFormPagePassengerAction.setFathersName(passenger.getFathersName(), passengerIndex);
        bookFormPagePassengerAction.setDocumentType(passenger.getDocumentType(), passengerIndex);
        bookFormPagePassengerAction.setDocumentNumber(passenger.getDocumentNumber(), passengerIndex);
        bookFormPagePassengerAction.setGender(passenger.getGender(), passengerIndex);
        return page(this);
    }

    public BookFormPage setByCashPaymentType(boolean byCash) {
        if (byCash) {
            paymentTyp().selectOptionByValue("3");
        } else paymentTyp().selectOptionByValue("1");
        return page(this);
    }
}
