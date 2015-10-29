package main.java.ui.pages.bookFormPage;

import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerGender;

/**
 * Created by Andrii.Bakhtiozin on 14.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookFormAcpPage extends BookFormPage {

    @Override
    public BookFormPage addPassenger(Passenger passenger, int passengerIndex) {
        this.setPassengerSalutation(passenger.getGender(), passengerIndex);
        bookFormPagePassengerAction.setName(passenger.getName(), passengerIndex);
        bookFormPagePassengerAction.setSurname(passenger.getSurname(), passengerIndex);
        bookFormPagePassengerAction.setDocumentNumber(passenger.getDocumentNumber(), passengerIndex);
        bookFormPagePassengerAction.setStateIssuedDocument(passenger.getStateIssuedDocument(), passengerIndex);
        return this;
    }

    private BookFormPage setPassengerSalutation(PassengerGender gender, int passengerIndex) {
        if (gender != null) {
            if (gender.toString().toLowerCase().equals("male")) {
                bookFormPassengerFields.getSalutationFieldElement(passengerIndex).selectOptionByValue("Mr");
            }
            if (gender.toString().toLowerCase().equals("female")) {
                bookFormPassengerFields.getSalutationFieldElement(passengerIndex).selectOptionByValue("Ms");
            }
        }
        return this;
    }

}
