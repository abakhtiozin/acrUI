package main.java.model;

import main.java.ApplicationStorage;
import main.java.model.passenger.Passenger;

import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Booking {
    private int bookingId;
    private List<Passenger> bookingPassengers;
    private BookingStatus bookingStatus;
    private Supplier bookingSupplier;
    private int backendServiceId;
    private Reseller reseller;
    private String supplierConfirmationNumber;
    private Amount amount;

    public Booking(int bookingId) {
        ApplicationStorage applicationStorage = ApplicationStorage.getInstance();
        this.reseller = applicationStorage.getReseller();
        this.bookingId = bookingId;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getSupplierConfirmationNumber() {
        return supplierConfirmationNumber;
    }

    public void setSupplierConfirmationNumber(String supplierConfirmationNumber) {
        this.supplierConfirmationNumber = supplierConfirmationNumber;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public List<Passenger> getBookingPassengers() {
        return bookingPassengers;
    }

    public void setBookingPassengers(List<Passenger> bookingPassengers) {
        this.bookingPassengers = bookingPassengers;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Supplier getBookingSupplier() {
        return bookingSupplier;
    }

    public void setBookingSupplier(Supplier bookingSupplier) {
        this.bookingSupplier = bookingSupplier;
    }

    public int getBackendServiceId() {
        return backendServiceId;
    }

    public void setBackendServiceId(int backendServiceId) {
        this.backendServiceId = backendServiceId;
    }

    public Reseller getReseller() {
        return reseller;
    }
}
