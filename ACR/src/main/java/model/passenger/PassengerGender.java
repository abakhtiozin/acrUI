package main.java.model.passenger;

/**
 * Created by Andrii.Bakhtiozin on 01.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public enum PassengerGender {
    MALE("Mr"),
    FEMALE("Ms");

    private String gender;

    PassengerGender(String gender) {
        this.gender = gender;
    }

    public String getSalutation() {
        return gender;
    }
}
