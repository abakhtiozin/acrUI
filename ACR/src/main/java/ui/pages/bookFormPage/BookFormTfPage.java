package main.java.ui.pages.bookFormPage;

import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerContactInfo;

/**
 * Created by Andrii.Bakhtiozin on 14.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookFormTfPage extends BookFormPage {

    private BookFormContactInfoFields bookFormContactInfoFields;

    public BookFormTfPage() {
        this.bookFormContactInfoFields = new BookFormContactInfoFields();
    }

    @Override
    public BookFormPage addPassenger(Passenger passenger, int passengerIndex) {
        bookFormPagePassengerAction.setPassengerBirthDate(passenger, passengerIndex);
        bookFormPagePassengerAction.setPassengerDocumentExpirationDate(passenger.getBirthDate(), passengerIndex);
        bookFormPagePassengerAction.setName(passenger.getName(), passengerIndex);
        bookFormPagePassengerAction.setSurname(passenger.getSurname(), passengerIndex);
        bookFormPagePassengerAction.setDocumentNumber(passenger.getDocumentNumber(), passengerIndex);
        bookFormPagePassengerAction.setStateIssuedDocument(passenger.getStateIssuedDocument(), passengerIndex);
        bookFormPagePassengerAction.setGender(passenger.getGender(), passengerIndex);
        return this;
    }

    public BookFormPage addContactInfo(PassengerContactInfo passengerContactInfo) {
        bookFormContactInfoFields.getContactAppartmentFieldLocator().setValue(passengerContactInfo.getApartment());
        bookFormContactInfoFields.getContactCityFieldLocator().setValue(passengerContactInfo.getCity());
        bookFormContactInfoFields.getContactCountryFieldLocator().selectOptionByValue(passengerContactInfo.getCountry());
        bookFormContactInfoFields.getContactEmailFieldLocator().setValue(passengerContactInfo.getEmail());
        bookFormContactInfoFields.getContactHouseFieldLocator().setValue(passengerContactInfo.getHouse());
        bookFormContactInfoFields.getContactIndexFieldLocator().setValue(passengerContactInfo.getIndex());
        bookFormContactInfoFields.getContactStreetFieldLocator().setValue(passengerContactInfo.getStreet());
        bookFormContactInfoFields.getContactPhoneCodeFieldLocator().setValue(passengerContactInfo.getPhoneCode());
        bookFormContactInfoFields.getContactPhoneNumberFieldLocator().setValue(passengerContactInfo.getPhoneNumber());
        return this;
    }

    public BookFormPage selectPassengerSeat(int passengerIndex) {
        bookFormPagePassengerAction.setRandomSeat(passengerIndex);
        return this;
    }

    public BookFormPage selectPassengerLuggage(int passengerIndex) {
        bookFormPagePassengerAction.setRandomLuggage(passengerIndex);
        return this;
    }
}
