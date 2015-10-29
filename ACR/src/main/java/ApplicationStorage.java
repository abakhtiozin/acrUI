package main.java;

import main.java.model.*;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.AnyPage;

import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 03.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class ApplicationStorage {

    private static ApplicationStorage instance;
    private AnyPage currentPage;
    private List<Passenger> passengers;
    private Reseller reseller;
    private Booking booking;
    private Journey journey;
    private Trip chosenTrip;
    private Supplier supplier;

    // --------------------------- CONSTRUCTORS ---------------------------
    private ApplicationStorage() {
    }

    public static ApplicationStorage getInstance() {
        if (instance == null) {
            instance = new ApplicationStorage();
        }
        return instance;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Trip getChosenTrip() {
        return chosenTrip;
    }

    public void setChosenTrip(Trip chosenTrip) {
        this.chosenTrip = chosenTrip;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Reseller getReseller() {
        return reseller;
    }

    public void setReseller(Reseller reseller) {
        this.reseller = reseller;
    }

    public AnyPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(AnyPage currentPage) {
        this.currentPage = currentPage;
    }

    public void clean() {
        instance = null;
    }
}
