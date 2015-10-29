package main.java.model.passenger;

import org.joda.time.DateTime;
import org.joda.time.Years;

import static main.java.utils.DateTimeHelper.toJodaTime;

/**
 * Created by Andrii.Bakhtiozin on 24.12.2014.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Passenger {

    private String name;
    private String surname;
    private String fathersName;
    private PassengerDocumentType documentType;
    private String documentNumber;
    private DateTime birthDate;
    private String birthPlace;
    private String nationality;
    private String residence;
    private String stateIssuedDocument;
    private DateTime documentExpiryDate;
    private PassengerGender gender;
    private PassengerTariffType tariffType;
    private String email;

    public Passenger(String birthDate) {
        this.birthDate = toJodaTime(birthDate);
    }

    public Passenger() {
    }

    public String getEmail() {
        return email;
    }

    public Passenger withEmail(String email) {
        this.email = email;
        return this;
    }

    public int getAge() {
        return Years.yearsBetween(this.getBirthDate(), new DateTime()).getYears();
    }


    @Override
    public String toString() {
        if (gender != null && this.birthDate != null) {
            return getSurname() + " " + getAge() + " " + gender.toString();
        }
        if (this.birthDate != null) {
            return getAge() + "";
        }
        return getSurname();
    }

    public DateTime getDocumentExpiryDate() {
        return documentExpiryDate;
    }


    public Passenger withDocumentExpiryDate(String documentExpiryDate) {
        this.documentExpiryDate = toJodaTime(documentExpiryDate);
        return this;
    }

    public Passenger withName(String name) {
        this.name = name;
        return this;
    }

    public Passenger withStateIssuedDocument(String stateIssuedDocument) {
        this.stateIssuedDocument = stateIssuedDocument;
        return this;
    }

    public Passenger withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Passenger withDocumentType(PassengerDocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public Passenger withTariffType(PassengerTariffType tariffType) {
        this.tariffType = tariffType;
        return this;
    }

    public Passenger withGender(PassengerGender gender) {
        this.gender = gender;
        return this;
    }

    public Passenger withFathersName(String fathersName) {
        this.fathersName = fathersName;
        return this;
    }

    public Passenger withDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public Passenger withBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Passenger withBirthDate(String birthDate) {
        this.birthDate = toJodaTime(birthDate);
        return this;
    }

    public Passenger withBirthDate(PassengerAge age) {
        this.birthDate = new DateTime().minusYears(age.getAge());
        return this;
    }

    public Passenger withBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        return this;
    }

    public Passenger withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public Passenger withResidence(String residence) {
        this.residence = residence;
        return this;
    }

    public String getStateIssuedDocument() {
        return stateIssuedDocument;
    }

    public String getFathersName() {
        return fathersName;
    }

    public PassengerGender getGender() {
        return gender;
    }

    public PassengerTariffType getTariffType() {
        return tariffType;
    }

    public String getSalutation() {
        return gender.getSalutation();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public PassengerDocumentType getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public String getResidence() {
        return residence;
    }
}
