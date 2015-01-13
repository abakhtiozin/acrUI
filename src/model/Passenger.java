package model;

import java.util.Date;

/**
 * Created by AA on 24.12.2014.
 */
public class Passenger {

    private String salutation;
    private String name;
    private String surname;
    private int documentType = 0;
    private String documentNumber;
    private String birthDate;
    private String birthPlace;
    private String nationality;
    private String residence;

    public Passenger(String birthDate){
        this.birthDate = birthDate;
    }
    public Passenger(){}

    public Passenger withSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }
    public Passenger withName(String name) {
        this.name = name;
        return this;
    }
    public Passenger withSurname(String surname) {
        this.surname = surname;
        return this;
    }
    public Passenger withDocumentType(int documentType) {
        this.documentType = documentType;
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

    public int getDocumentType() {
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
