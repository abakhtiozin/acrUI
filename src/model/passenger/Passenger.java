package model.passenger;

/**
 * Created by AA on 24.12.2014.
 */
public class Passenger {

    private String salutation;
    private String name;
    private String surname;
    private String fathersName;
    private PassengerDocumentType documentType;
    private String documentNumber;
    private String birthDate;
    private String birthPlace;
    private String nationality;
    private String residence;
    private String stateIssuedDocument;

    public String getStateIssuedDocument() {
        return stateIssuedDocument;
    }

    private PassengerGender gender;
    private PassengerTariffType tariffType;

    public String getFathersName() {
        return fathersName;
    }

    public PassengerGender getGender() {
        return gender;
    }

    public PassengerTariffType getTariffType() {
        return tariffType;
    }

    public Passenger(String birthDate) {
        this.birthDate = birthDate;
    }

    public Passenger() {
    }

    public Passenger withSalutation(String salutation) {
        this.salutation = salutation;
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

    public Passenger withBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getSalutation() {
        return salutation;
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

    public String getBirthDate() {
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
