package main.java.ui.pages.bookFormPage;

import main.java.model.passenger.Passenger;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 15.10.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookFormHexPage extends BookFormPage implements PassengerActionsOnBookForm {
    @Override
    public BookFormPage addPassenger(Passenger passenger, int passengerIndex) {
        bookFormPagePassengerAction.setName(passenger.getName(), passengerIndex);
        bookFormPagePassengerAction.setSurname(passenger.getSurname(), passengerIndex);
        bookFormPagePassengerAction.setTariff(passenger.getTariffType(), passengerIndex);
        bookFormPagePassengerAction.setGender(passenger.getGender(), passengerIndex);
        return this;
    }

    public BookFormPage addEmail(String email) {
        $("#book_request_passengers_0_email").setValue(email);
        return this;
    }
}
