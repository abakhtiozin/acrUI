package main.java.model.passenger;

/**
 * Created by Andrii.Bakhtiozin on 18.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public enum PassengerAge {
    ADULT(30),
    KID(4),
    JUNIOR(12);

    private int age;

    PassengerAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
