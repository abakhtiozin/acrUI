package main.java.model.passenger;

import db.SQL;

import static util.other.RandomDataGenerator.*;

/**
 * Created by Andrii.Bakhtiozin on 17.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PassengerContactInfo {

    private String email;
    private String street;
    private String house;
    private String apartment;
    private String index;
    private String city;
    private String country;
    private String phoneCode;
    private String phoneNumber;
    private String phoneExtension;
    private String title;

    public PassengerContactInfo generate() {
        this.email = getRandomEmail();
        this.street = getRandomLiteralsString();
        this.house = getRandomNumericString(2);
        this.apartment = getRandomNumericString(2);
        this.index = getRandomNumericString(5);
        this.city = getRandomLiteralsString();
        this.country = SQL.getCountryCode(randomNumber(0, 190).toString());
        this.phoneCode = getRandomNumericString(3);
        this.phoneNumber = getRandomNumericString(4);
        if (this.phoneExtension == null) this.phoneExtension = getRandomNumericString(2);
        return this;
    }


    public PassengerContactInfo withEmail(String email){
        this.email = email;
        return this;
    }
    public PassengerContactInfo withStreet(String street){
        this.street = street;
        return this;
    }
    public PassengerContactInfo withHouse(String house){
        this.house = house;
        return this;
    }
    public PassengerContactInfo withAppartment(String appartment){
        this.apartment = appartment;
        return this;
    }
    public PassengerContactInfo withIndex(String index){
        this.index = index;
        return this;
    }
    public PassengerContactInfo withCity(String city){
        this.city = city;
        return this;
    }
    public PassengerContactInfo withCountry(String country){
        this.country = country;
        return this;
    }
    public PassengerContactInfo withPhoneCode(String phoneCode){
        this.phoneCode = phoneCode;
        return this;
    }
    public PassengerContactInfo withPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }
    public PassengerContactInfo withPhoneExtension(String phoneExtension){
        this.phoneExtension = phoneExtension;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public String getIndex() {
        return index;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
    public String getPhoneExtension() {
        return phoneExtension;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneCode() {
        return phoneCode;
    }
}
